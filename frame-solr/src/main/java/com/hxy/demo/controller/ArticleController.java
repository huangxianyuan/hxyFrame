package com.hxy.demo.controller;

import com.hxy.base.page.Page;
import com.hxy.base.utils.Result;
import com.hxy.base.utils.Utils;
import com.hxy.sys.entity.SolrArticleEntity;
import com.hxy.sys.service.SolrArticleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 类的功能描述.
 * 业务树
 * @Auther hxy
 * @Date 2017/7/25
 */
@RequestMapping("demo/article")
@Controller
public class ArticleController {

    @Autowired
    private SolrArticleService solrArticleService;

    /**
     * 文章列表
     * @param model
     * @param solrArticleEntity
     * @param request
     * @return
     */
    @RequestMapping("list")
    public String list(Model model , SolrArticleEntity solrArticleEntity, HttpServletRequest request){
        int pageNum = Utils.parseInt(request.getParameter("pageNum"), 1);
        Page<SolrArticleEntity> page = null;
        try {
            page = solrArticleService.findPage(solrArticleEntity, pageNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("page",page);
        model.addAttribute("article",solrArticleEntity);
        return "demo/artcle.jsp";
    }

    /**
     * 文章信息
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("info")
    public String info(Model model , String id){
        if(StringUtils.isNotEmpty(id)){
            SolrArticleEntity solrArticle = solrArticleService.queryObject(id);
            model.addAttribute("article",solrArticle);
        }
        return "demo/artcleinfo.jsp";
    }

    /**
     * 保存/更新
     * @param articleEntity
     * @return
     */
    @RequestMapping(value = "edit",method = RequestMethod.POST)
    @ResponseBody
    public Result edit(SolrArticleEntity articleEntity){
        try {
            if(StringUtils.isEmpty(articleEntity.getId())){
                solrArticleService.save(articleEntity);
            }else {
                solrArticleService.update(articleEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.ok();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @ResponseBody
    public Result edit(String id){
        try {
            if(solrArticleService.delete(id)<1){
                return Result.error("删除请假条失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.ok("删除请假条成功");
    }











}
