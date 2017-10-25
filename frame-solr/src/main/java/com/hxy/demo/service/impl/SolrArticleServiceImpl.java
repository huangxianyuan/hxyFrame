package com.hxy.demo.service.impl;

import com.hxy.base.common.Constant;
import com.hxy.base.page.Page;
import com.hxy.base.utils.PageUtils;
import com.hxy.base.utils.StringUtils;
import com.hxy.base.utils.Utils;
import com.hxy.solr.entity.HightQueryParams;
import com.hxy.solr.utils.SolrUtils;
import com.hxy.utils.UserUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import com.hxy.sys.dao.SolrArticleDao;
import com.hxy.sys.entity.SolrArticleEntity;
import com.hxy.sys.service.SolrArticleService;



@Service("solrArticleService")
public class SolrArticleServiceImpl implements SolrArticleService {
	@Autowired
	private SolrArticleDao solrArticleDao;

	private SolrUtils utils = new SolrUtils(Constant.CORE_ARTICLE);
	
	@Override
	public SolrArticleEntity queryObject(String id){
		return solrArticleDao.queryObject(id);
	}
	
	@Override
	public List<SolrArticleEntity> queryList(Map<String, Object> map){

		return solrArticleDao.queryList(map);
	}

    @Override
    public List<SolrArticleEntity> queryListByBean(SolrArticleEntity entity) {
        return solrArticleDao.queryListByBean(entity);
    }
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return solrArticleDao.queryTotal(map);
	}
	
	@Override
	public int save(SolrArticleEntity solrArticle) throws Exception {
		solrArticle.setCreateId(UserUtils.getCurrentUserId());
		solrArticle.setCreateTime(new Date());
		solrArticle.setId(Utils.uuid());
		addSolrIndex(solrArticle);
		return solrArticleDao.save(solrArticle);
	}

	/**
	 * 增加/更新索引
	 * @param articleEntity
	 * @throws Exception
	 */
	public void addSolrIndex(SolrArticleEntity articleEntity) throws Exception{
		utils.addBean(articleEntity);
	}

	@Override
	public int update(SolrArticleEntity solrArticle) throws Exception {
		solrArticle.setUpdateId(UserUtils.getCurrentUserId());
		solrArticle.setUpdateTime(new Date());
		addSolrIndex(solrArticle);
        return solrArticleDao.update(solrArticle);
	}
	
	@Override
	public int delete(String id) throws Exception {
		utils.deleteById("id",id);
        return solrArticleDao.delete(id);
	}
	
	@Override
	public int deleteBatch(String[] ids) throws Exception {
		utils.deleteByIds(Constant.CORE_ARTICLE, Arrays.asList(ids));
        return solrArticleDao.deleteBatch(ids);
	}

	@Override
	public Page findPage(SolrArticleEntity solrArticle, int pageNum) throws Exception {
		String queryStr = solrArticle.getKeyWords();
		if(StringUtils.isEmpty(queryStr)){
			queryStr="*:*";
		}else {
			queryStr="keyWord:"+queryStr+"&sort=id desc";
		}
		List<String> hlFields=new ArrayList<>();
		hlFields.add("title");
		hlFields.add("content");
		HightQueryParams params = new HightQueryParams();
		params.setClzz(SolrArticleEntity.class);
		params.setHlFields(hlFields);
		params.setQueryStr(queryStr);
		params.setIdName("id");
		params.setPageNum(pageNum);
		params.setPageSize(Constant.pageSize);
		params.setPostTag(SolrUtils.getPostTag());
		params.setPreTag(SolrUtils.getPreTag());
		return utils.getHighterByPage(params);
	}

	@Override
	public Page search(SolrArticleEntity solrArticle, int pageNum) throws IOException, SolrServerException {
		String queryStr = solrArticle.getKeyWords();
		if(StringUtils.isEmpty(queryStr)){
			queryStr="*:*";
		}else {
			queryStr="keyWord:"+queryStr+"&sort=id desc";
		}
		List<String> hlFields=new ArrayList<>();
		hlFields.add("title");
		hlFields.add("content");
		HightQueryParams params = new HightQueryParams();
		params.setClzz(solrArticle.getClass());
		params.setHlFields(hlFields);
		params.setQueryStr(queryStr);
		params.setIdName("id");
		params.setPageNum(pageNum);
		params.setPageSize(Constant.pageSize);
		params.setPostTag(SolrUtils.getPostTag());
		params.setPreTag(SolrUtils.getPreTag());
		return utils.getHighterByPage(params);
	}
}
