/**
  * Copyright 2018 bejson.com 
  */
package com.cncustompoc.SingletonSrvcs.domains;
import com.cncustompoc.SingletonSrvcs.domains.Authors;
import com.cncustompoc.SingletonSrvcs.domains.Entries;
import com.cncustompoc.SingletonSrvcs.domains.Links;

import java.util.Date;
import java.util.List;

/**
 * Auto-generated: 2018-01-31 17:15:46
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Feed {

    private String id;
    private String title;
    private String subtitle;
    private Date updated;
    private List<Authors> authors;
    private List<Links> links;
    private List<Entries> entries;
    public void setId(String id) {
         this.id = id;
     }
     public String getId() {
         return id;
     }

    public void setTitle(String title) {
         this.title = title;
     }
     public String getTitle() {
         return title;
     }

    public void setSubtitle(String subtitle) {
         this.subtitle = subtitle;
     }
     public String getSubtitle() {
         return subtitle;
     }

    public void setUpdated(Date updated) {
         this.updated = updated;
     }
     public Date getUpdated() {
         return updated;
     }

    public void setAuthors(List<Authors> authors) {
         this.authors = authors;
     }
     public List<Authors> getAuthors() {
         return authors;
     }

    public void setLinks(List<Links> links) {
         this.links = links;
     }
     public List<Links> getLinks() {
         return links;
     }

    public void setEntries(List<Entries> entries) {
         this.entries = entries;
     }
     public List<Entries> getEntries() {
         return entries;
     }

}