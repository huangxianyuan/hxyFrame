package com.hxy.solr.entity;

import java.util.List;
import java.util.Map;

/**
 * 类的功能描述.
 * 高亮查询参数 由于参数过多，特用bean封装
 * @Auther hxy
 * @Date 2017/9/12
 */
public class HightQueryParams {
    /**
     *查询字符串
     */
    private String queryStr;
    /**
     *分页Num
     */
    private int pageNum;
    /**
     *每页记录数
     */
    private int pageSize;
    /**
     *需要高亮显示的字段
     */
    private List<String> hlFields;
    /**
     *高亮显示前缀
     */
    private String preTag;
    /**
     *高亮显示后缀
     */
    private String postTag;
    /**
     *bean class
     */
    private Class clzz;
    /**
     *id的名字
     */
    private String idName;

    /**
     * 排序字段 key:字段名 value:排序类型 asc desc
     */
    private Map<String,String> sorts;


    public HightQueryParams(){

    }

    public HightQueryParams(String queryStr, int pageNum, int pageSize, List<String> hlFields, String preTag, String postTag, Class clzz, String idName) {
        this.queryStr = queryStr;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.hlFields = hlFields;
        this.preTag = preTag;
        this.postTag = postTag;
        this.clzz = clzz;
        this.idName = idName;
    }

    public String getQueryStr() {
        return queryStr;
    }

    public void setQueryStr(String queryStr) {
        this.queryStr = queryStr;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<String> getHlFields() {
        return hlFields;
    }

    public void setHlFields(List<String> hlFields) {
        this.hlFields = hlFields;
    }

    public String getPreTag() {
        return preTag;
    }

    public void setPreTag(String preTag) {
        this.preTag = preTag;
    }

    public String getPostTag() {
        return postTag;
    }

    public void setPostTag(String postTag) {
        this.postTag = postTag;
    }

    public Class getClzz() {
        return clzz;
    }

    public void setClzz(Class clzz) {
        this.clzz = clzz;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public Map<String, String> getSorts() {
        return sorts;
    }

    public void setSorts(Map<String, String> sorts) {
        this.sorts = sorts;
    }
}
