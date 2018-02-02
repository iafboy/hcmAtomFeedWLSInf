package com;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class testMain {
    public static void main(String[] args){

        String dateString="2016-03-02 10:03:11";
        DateTimeFormatter beijingFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").withZone(ZoneId.of("Asia/Shanghai"));
        ZonedDateTime beijingDateTime = ZonedDateTime.parse(dateString+".000", beijingFormatter);
        String lasttime=beijingDateTime.format(DateTimeFormatter.ISO_INSTANT);
        System.out.println(lasttime);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        System.out.println(ZonedDateTime.ofInstant(Instant.ofEpochMilli(ts.getTime()), ZoneId.of("Asia/Shanghai")).format(DateTimeFormatter.ISO_INSTANT));
        System.out.println(System.currentTimeMillis());
        Timestamp t1 = Timestamp.from(Instant.parse("2016-03-02 10:03:11"));
        System.out.println(ZonedDateTime.ofInstant(Instant.ofEpochMilli(t1.getTime()), ZoneId.of("Asia/Shanghai")).format(DateTimeFormatter.ISO_INSTANT));



    }
}
