package com.hxy.sys.servelt;

import com.hxy.base.cache.CodeCache;
import com.hxy.base.common.Constant;
import com.hxy.base.utils.SpringContextUtils;
import com.hxy.base.utils.StringUtils;
import com.hxy.utils.RedisUtil;
import com.hxy.sys.dao.CodeDao;
import com.hxy.sys.entity.CodeEntity;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类的功能描述.
 * 继承HttpServlet，重写Init方法，实现加载servlet时，做一些操作
 * @Auther hxy
 * @Date 2017/4/28
 */
public class InitServlet extends HttpServlet{
    Logger log = Logger.getLogger(getClass());
    private static CodeDao codeDao = SpringContextUtils.getBean(CodeDao.class);
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        String webRoot = config.getServletContext().getContextPath();
        log.info("加载 servlet...");
        config.getServletContext().setAttribute("webRoot", webRoot);//Web根目录
        log.info("Web根目录:"+config.getServletContext().getAttribute("webRoot"));
        config.getServletContext().setAttribute("resRoot", webRoot+"/statics");//资态资源根目录
        log.info("资态资源根目录:"+config.getServletContext().getAttribute("resRoot"));
        CodeCache();
    }

    /**
     * 缓存全部数据字典
     */
    public void CodeCache(){
        List<CodeEntity> allCode = codeDao.queryAllCode();
        Map<String,Map<String,Object>> allMap = new HashMap<>();
        if(allCode != null && allCode.size()>0){
            Map<String,Object> attrMap =null;
            for (CodeEntity code:allCode){
                attrMap= new HashMap<>();
                attrMap.put("id",code.getId());
                attrMap.put("value",code.getValue());
                attrMap.put("childs",code.getChilds());
                attrMap.put("mark",code.getMark());
                attrMap.put("name",code.getName());
                allMap.put(code.getMark(),attrMap);
            }
        }
        for (String key:allMap.keySet()){
            //父字典
            Map<String, Object> parentMap = allMap.get(key);
            String childStr = (String) parentMap.get("childs");
            if(StringUtils.isEmpty(childStr)){
                continue;
            }
            String[] split = childStr.split(",");
            List<Map<String, Object>> childMaps = new ArrayList<>();

            for (String str:split){
                //子字典
                Map<String, Object> childMap = allMap.get(str);
                childMaps.add(childMap);
            }
            //将所有子字典设置到父级字典
            parentMap.put("childList",childMaps);
        }
        CodeCache.put(Constant.CODE_CACHE,allMap);
        try {
            RedisUtil.setObject(Constant.CODE_CACHE,allMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
