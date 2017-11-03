package com.hxy;

import com.hxy.base.common.Constant;
import com.hxy.base.utils.PageUtils;
import com.hxy.base.utils.Utils;
import com.hxy.solr.entity.SolrTest;
import com.hxy.solr.utils.SolrUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类的功能描述.
 *
 * @Auther hxy
 * @Date 2017/5/27
 */

public class TestSolr {


    /**
     * addBean
     */
    @Test
    public void createIndex(){
        SolrUtils utils = new SolrUtils(Constant.CORE_ARTICLE);
        String[] docs = {"Solr是一个独立的企业级搜索应用服务器",
                "它对外提供类似于Web-service的API接口",
                "用户可以通过http请求",
                "向搜索引擎服务器提交一定格式的XML文件生成索引",
                "也可以通过Http Get操作提出查找请求",
                "并得到XML格式的返回结果"};
        try {
            for (int j=0;j<100;j++){
                for (int i=0;i<docs.length;i++){
                    SolrTest solrTest = new SolrTest();
                    solrTest.setId(j+"");
                    solrTest.setContent(docs[i]);
                    solrTest.setTitle("hxy"+Utils.uuid());
                    utils.addBean(solrTest);
                }
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * deleteAll
     */
    @Test
    public void deleteAll(){
        SolrUtils utils = new SolrUtils("article");
        try {
            utils.deleteAll();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getByPage(){
        SolrUtils utils = new SolrUtils("new_core");
        String searchStr="keyWord:hxy";
        try {
            Map<String,SolrQuery.ORDER> sortMap = new HashMap<>();
            sortMap.put("id", SolrQuery.ORDER.desc);
            PageUtils byPage = utils.getByPage(searchStr, 1, 2, SolrTest.class,sortMap);
            System.out.println("搜索到数据:"+byPage.getTotalCount()+"条。");
            for (Object obj:byPage.getList()){
                SolrTest solrTest=(SolrTest) obj;
                System.out.println(solrTest.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
    }
    /*@Test
    public void getHighterByPage(){
        SolrUtils utils = new SolrUtils("new_core");
            String searchStr="keyWord:服务器";
        try {
            Map<String,SolrQuery.ORDER> sortMap = new HashMap<>();
            sortMap.put("id", SolrQuery.ORDER.desc);
            List<String> hlFields=new ArrayList<>();
            hlFields.add("title");
            hlFields.add("content");
            String preTag ="<test>";
            String postTag ="</test>";
            PageUtils byPage = utils.getHighterByPage(searchStr, 1, 2,hlFields,preTag,postTag,SolrTest.class,"id");
            System.out.println("搜索到数据:"+byPage.getTotalCount()+"条。");
            for (Object obj:byPage.getList()){
                SolrTest solrTest=(SolrTest) obj;
                System.out.println(solrTest.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
    }*/




}
