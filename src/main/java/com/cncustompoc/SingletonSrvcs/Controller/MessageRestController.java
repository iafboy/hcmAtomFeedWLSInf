package com.cncustompoc.SingletonSrvcs.Controller;

import com.alibaba.fastjson.JSON;
import com.cncustompoc.SingletonSrvcs.Common.CommonParams;
import com.cncustompoc.SingletonSrvcs.domains.Message;
import com.cncustompoc.SingletonSrvcs.domains.AtomFeedBean;
import com.cncustompoc.SingletonSrvcs.domains.Entries;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class MessageRestController {


    @Value("${hw.hcm.password}")
    private String hcmpwd;
    @Value("${hw.hcm.username}")
    private String hcmuser;

    public RestTemplate restTemplate()
            throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        SSLContext ctx = SSLContext.getInstance("TLS");
        X509TrustManager tm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
            }
            public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
            }
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        DefaultHttpClient base = new DefaultHttpClient();
        ctx.init(null, new TrustManager[]{tm}, null);
        SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        ClientConnectionManager ccm = base.getConnectionManager();
        SchemeRegistry sr = ccm.getSchemeRegistry();
        sr.register(new Scheme("https", 443, ssf));
        DefaultHttpClient httpClient = new DefaultHttpClient(ccm, base.getParams());
        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }

    @RequestMapping(value = "/AtomFeeds", method = RequestMethod.GET,headers="Accept=application/json",produces="application/json;charset=UTF-8")
    public List<String> getCurrentFeedList(){
        List<String> result=null;
        String lasttime=ZonedDateTime.now().format( DateTimeFormatter.ISO_INSTANT );
        String url="https://ucf5-fap0377-fa-ext.oracledemos.com/hcmCoreApi/atomservlet/employee/empupdate?updated-min="+lasttime;
        String plainCreds = hcmuser+":"+hcmpwd;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        HttpEntity<String> request = new HttpEntity<>(headers);
        RestTemplate resttemplate= null;
        try {
            resttemplate = this.restTemplate();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();}
        if(resttemplate==null) return null;
        HttpEntity<String> response = resttemplate.exchange(url, HttpMethod.GET, request, String.class);
        String reply = response.getBody();
        Message msg=new Message();
        msg.setMessageid(UUID.randomUUID().toString());
        StringBuffer sb=new StringBuffer();
        AtomFeedBean afb= JSON.parseObject(reply,AtomFeedBean.class);
        if(afb!=null&&afb.getFeed().getEntries()!=null&&afb.getFeed().getEntries().size()>0){
            result=new ArrayList();
            for(Entries entrie:afb.getFeed().getEntries()){
                result.add(entrie.getContent());
            }
        }
        return result;
    }

    @Scheduled(cron = "${hw.schedule.trigger}")
    private void getAtomFeedUpdates(){
        String lasttime= CommonParams.initTime;
        String url="https://ucf5-fap0377-fa-ext.oracledemos.com/hcmCoreApi/atomservlet/employee/empupdate?updated-min="+lasttime;
        lasttime=ZonedDateTime.now().format( DateTimeFormatter.ISO_INSTANT );
        String plainCreds = hcmuser+":"+hcmpwd;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        HttpEntity<String> request = new HttpEntity<>(headers);
        RestTemplate restTemplate= null;
        try {
            restTemplate = this.restTemplate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(restTemplate==null) return ;
        HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        String reply = response.getBody();
        Message msg=new Message();
        msg.setMessageid(UUID.randomUUID().toString());
        StringBuffer sb=new StringBuffer();
        AtomFeedBean afb= JSON.parseObject(reply,AtomFeedBean.class);
        if(afb!=null&&afb.getFeed().getEntries()!=null&&afb.getFeed().getEntries().size()>0){
            for(Entries entrie:afb.getFeed().getEntries()){
                sb.append(entrie.getContent());
                sb.append("\n");
                CommonParams.contentList.add(entrie.getContent());
                try {
                    CommonParams.messagequeue.put(entrie.getContent());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        msg.setText(sb.toString());
        CommonParams.messageList.add(msg);
    }
    @RequestMapping(value = "/invokeICaption", method = RequestMethod.GET,headers="Accept=application/json",produces="application/json;charset=UTF-8")
    public boolean invokeICaption(){

        return true;
    }
    @RequestMapping(value = "/singleAtomFeeds", method = RequestMethod.GET,headers="Accept=application/json",produces="application/json;charset=UTF-8")
    public String getSingleFeed(){
        String result=null;
        if(!CommonParams.messagequeue.isEmpty()){
            result=CommonParams.messagequeue.poll();
        }
        return result;
    }

    @RequestMapping(value = "/CurrentTimeStamp", method = RequestMethod.GET,headers="Accept=application/json",produces="application/json;charset=UTF-8")
    public long getCurrentTimestamp(){
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        return ts.getTime();
    }
    //input format /2016-03-02 10:03:11
    @RequestMapping(value = "/AtomFeeds/{dateString}", method = RequestMethod.GET,headers="Accept=application/json",produces="application/json;charset=UTF-8")
    public List<String> getFeedListbyTime(@PathVariable String dateString){
        List<String> result=null;
        //String lasttime=dateString;
        //String lasttime=ZonedDateTime.ofInstant(Instant.ofEpochSecond(querytimpstamp), ZoneId.of("Asia/Shanghai")).format(DateTimeFormatter.ISO_INSTANT);
        //String lasttime=ZonedDateTime.ofInstant(Timestamp.valueOf(querytimpstamp+".000").toInstant(), ZoneOffset.ofHours(8)).format(DateTimeFormatter.ISO_INSTANT);
        DateTimeFormatter beijingFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").withZone(ZoneId.of("Asia/Shanghai"));
        ZonedDateTime beijingDateTime = ZonedDateTime.parse(dateString+".000", beijingFormatter);
        String lasttime=beijingDateTime.format(DateTimeFormatter.ISO_INSTANT);

        String url="https://ucf5-fap0377-fa-ext.oracledemos.com/hcmCoreApi/atomservlet/employee/empupdate?updated-min="+lasttime;
        String plainCreds = hcmuser+":"+hcmpwd;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        HttpEntity<String> request = new HttpEntity<>(headers);
        RestTemplate resttemplate= null;
        try {
            resttemplate = this.restTemplate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(resttemplate==null) return null;
        HttpEntity<String> response = resttemplate.exchange(url, HttpMethod.GET, request, String.class);
        String reply = response.getBody();
        Message msg=new Message();
        msg.setMessageid(UUID.randomUUID().toString());
        StringBuffer sb=new StringBuffer();
        AtomFeedBean afb= JSON.parseObject(reply,AtomFeedBean.class);
        if(afb!=null&&afb.getFeed().getEntries()!=null&&afb.getFeed().getEntries().size()>0){
            result=new ArrayList();
            for(Entries entrie:afb.getFeed().getEntries()){
                result.add(entrie.getContent());
            }
        }
        return result;
    }
}
