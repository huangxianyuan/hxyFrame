package com.hxy.demo.service;

import com.hxy.base.page.Page;
import com.hxy.base.utils.PageUtils;
import com.hxy.demo.entity.SolrArticleEntity;
import com.hxy.dto.UserWindowDto;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * solr 文章demo
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-09-11 15:43:10
 */
public interface SolrArticleService {
	
	SolrArticleEntity queryObject(String id);
	
	List<SolrArticleEntity> queryList(Map<String, Object> map);

    List<SolrArticleEntity> queryListByBean(SolrArticleEntity entity);
	
	int queryTotal(Map<String, Object> map);
	
	int save(SolrArticleEntity solrArticle) throws Exception;
	
	int update(SolrArticleEntity solrArticle) throws Exception;
	
	int delete(String id) throws Exception;
	
	int deleteBatch(String[] ids) throws Exception;

	Page findPage(SolrArticleEntity solrArticle,int pageNum) throws Exception;

	Page search(SolrArticleEntity solrArticleEntity,int pageNum) throws IOException, SolrServerException;
}
