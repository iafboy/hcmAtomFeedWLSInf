/**
  * Copyright 2018 bejson.com 
  */
package com.cncustompoc.SingletonSrvcs.domains;
import com.cncustompoc.SingletonSrvcs.domains.Authors;
import com.cncustompoc.SingletonSrvcs.domains.Links;

import java.util.Date;
import java.util.List;

/**
 * Auto-generated: 2018-01-31 17:15:46
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Entries {

    private String id;
    private String title;
    private String summary;
    private String content;
    private String updated;
    private String published;
    private List<Authors> authors;
    private List<Links> links;
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

    public void setSummary(String summary) {
         this.summary = summary;
     }
     public String getSummary() {
         return summary;
     }

    public void setContent(String content) {
         this.content = content;
     }
     public String getContent() {
         return content;
     }

    public void setUpdated(String updated) {
         this.updated = updated;
     }
     public String getUpdated() {
         return updated;
     }

    public void setPublished(String published) {
         this.published = published;
     }
     public String getPublished() {
         return published;
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

}