package com.hxy.demo.controller;

import com.hxy.base.common.Constant;
import com.hxy.base.page.Page;
import com.hxy.base.utils.JsonUtil;
import com.hxy.base.utils.Result;
import com.hxy.base.utils.Utils;
import com.hxy.demo.entity.SolrArticleEntity;
import com.hxy.demo.service.SolrArticleService;
import com.hxy.sys.entity.CodeEntity;
import com.hxy.sys.service.CodeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @Autowired
    private CodeService codeService;

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
            model.addAttribute("contentHtml", solrArticle.getContentHtml());
            model.addAttribute("img", solrArticle.getImg());
            model.addAttribute("article", JsonUtil.getJsonByObj(solrArticle));
        }
        return "demo/artcleinfo.jsp";
    }

    /**
     * 文章详情 搜索用
     * @param id
     * @return
     */
    @RequestMapping("detailInfo/{id}")
    @ResponseBody
    public Result detailInfo(@PathVariable String id){
        SolrArticleEntity solrArticle = solrArticleService.queryObject(id);
        return Result.ok().put("article",solrArticle);
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
            return Result.error();
        }
        return Result.ok();
    }

    /**
     * 搜索文章
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/search/{pageNum}")
    @ResponseBody
    public Result search(@PathVariable("pageNum") int pageNum,SolrArticleEntity articleEntity){
        Page<SolrArticleEntity> page = null;
        try {
            page = solrArticleService.search(articleEntity, pageNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.ok().put("page",page);
    }

    /**
     * 获取所有文章类型
     * @return
     */
    @RequestMapping(value = "types",method = RequestMethod.POST)
    @ResponseBody
    public Result types(){
        List<CodeEntity> codes = codeService.queryChildsByMark(Constant.ARTCLE_TYPE);
        return Result.ok().put("codes",codes);
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
