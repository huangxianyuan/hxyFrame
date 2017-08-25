package com.hxy.solr.entity;

import org.apache.solr.client.solrj.beans.Field;

/**
 * 类的功能描述.
 *
 * @Auther hxy
 * @Date 2017/5/26
 */
public class SolrTest {
    @Field
    private String id;
    @Field
    private String title;
    @Field
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toString(){
        return "id:"+id+",title:"+title+",content"+content;
    }

}
