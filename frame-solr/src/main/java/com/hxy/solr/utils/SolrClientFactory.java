package com.hxy.solr.utils;

import com.hxy.base.utils.PropertiesLoader;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 类的功能描述.
 *
 * @Auther hxy
 * @Date 2017/5/26
 */

public class SolrClientFactory {

    private static Logger logger = Logger.getLogger(SolrClientFactory.class);

    /**
     * 线程安全的
     */
    public static Map<String,SolrClient> solrClientMap = Collections.synchronizedMap(new HashMap<String, SolrClient>());


    public static void build(String coreName){
        PropertiesLoader loader = new PropertiesLoader("solrConfig.properties");
        SolrClient solrClient = new HttpSolrClient.Builder(loader.getProperty("url") + "/" + coreName).build();
        solrClientMap.put(coreName,solrClient);
    }

    public synchronized static SolrClient getSolrClient(String coreName){
        if(!solrClientMap.containsKey(coreName)){
            build(coreName);
        }
        return solrClientMap.get(coreName);
    }



}
