package com.hxy.demo.service.impl;

import com.hxy.base.common.Constant;
import com.hxy.base.page.Page;
import com.hxy.base.page.PageHelper;
import com.hxy.base.utils.StringUtils;
import com.hxy.base.utils.Utils;
import com.hxy.demo.dao.SolrArticleDao;
import com.hxy.demo.entity.SolrArticleEntity;
import com.hxy.demo.service.SolrArticleService;
import com.hxy.solr.entity.HightQueryParams;
import com.hxy.solr.utils.SolrUtils;
import com.hxy.sys.entity.UserEntity;
import com.hxy.sys.service.UserService;
import com.hxy.utils.UserUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import org.springframework.transaction.annotation.Transactional;


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
	@Transactional
	public int save(SolrArticleEntity solrArticle) throws Exception {
		solrArticle.setCreateId(UserUtils.getCurrentUserId());
		solrArticle.setCreateTime(new Date());
		solrArticle.setId(Utils.uuid());
		int count = solrArticleDao.save(solrArticle);
		addSolrIndex(solrArticle);
		return count;
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
	@Transactional
	public int update(SolrArticleEntity solrArticle) throws Exception {
		solrArticle.setUpdateId(UserUtils.getCurrentUserId());
		solrArticle.setUpdateTime(new Date());
		SolrArticleEntity oldArticle = solrArticleDao.queryObject(solrArticle.getId());
		solrArticle.setCreateTime(oldArticle.getCreateTime());
		int count = solrArticleDao.update(solrArticle);
		addSolrIndex(solrArticle);
        return count;
	}
	
	@Override
	@Transactional
	public int delete(String id) throws Exception {
		int count = solrArticleDao.delete(id);
		utils.deleteById("id",id);
        return count;
	}
	
	@Override
	public int deleteBatch(String[] ids) throws Exception {
		int count = solrArticleDao.deleteBatch(ids);
		utils.deleteByIds(Constant.CORE_ARTICLE, Arrays.asList(ids));
        return count;
	}

	@Override
	public Page findPage(SolrArticleEntity solrArticle, int pageNum) throws Exception {
		PageHelper.startPage(pageNum,Constant.pageSize);
		solrArticleDao.queryListByBean(solrArticle);
		return PageHelper.endPage();
	}

	@Override
	public Page search(SolrArticleEntity solrArticle, int pageNum) throws IOException, SolrServerException {
		StringBuilder sb = new StringBuilder();
		if(!StringUtils.isEmpty(solrArticle.getKeyWords())){
			sb.append("keyWord:"+solrArticle.getKeyWords());
		}
		if(!StringUtils.isEmpty(solrArticle.getType())){
			sb.append(" AND type:"+solrArticle.getType());
		}
		List<String> hlFields=new ArrayList<>();
		hlFields.add("title");
		hlFields.add("content");
		hlFields.add("author");
		//排序字段
		Map<String,String> sortMap = new HashMap<>();
		String[] sorts = solrArticle.getSorts();
		for (String sort:sorts){
			String[] split = sort.split("_");
			if(split.length == 2){
				sortMap.put(split[0],split[1]);
			}
		}
		HightQueryParams params = new HightQueryParams();
		params.setClzz(solrArticle.getClass());
		params.setHlFields(hlFields);
		params.setQueryStr(sb.toString());
		params.setIdName("id");
		params.setPageNum(pageNum);
		params.setPageSize(Constant.pageSize);
		params.setPostTag(SolrUtils.getPostTag());
		params.setPreTag(SolrUtils.getPreTag());
		params.setSorts(sortMap);
		return utils.getHighterByPage(params);
	}
}
