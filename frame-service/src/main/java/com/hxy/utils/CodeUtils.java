package com.hxy.utils;

import com.hxy.base.common.Constant;
import com.hxy.base.utils.SpringContextUtils;
import com.hxy.sys.entity.CodeEntity;
import com.hxy.sys.service.CodeService;

import java.util.Map;

/**
 * 类的功能描述.
 * 数据字典工具类
 * @Auther hxy
 * @Date 2017/7/25
 */

public class CodeUtils {

    private static CodeService codeService = SpringContextUtils.getBean(CodeService.class);

    /**
     * 根据字典标识获取字典中文
     * @param value
     * @return
     */
    public static String getCodeName(String preName,String value){
        String name ="";
//        Map<String,Map<String,Object>> allMap = CodeCache.get(Constant.CODE_CACHE);
        Map<String,Map<String,Object>> allMap = null;
        try {
            allMap = (Map<String, Map<String, Object>>) RedisUtil.getObject(Constant.CODE_CACHE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //首先从redis中取，没有再去数据库查询
        if(allMap != null || allMap.size()>0){
            Map<String,Object> markMap = allMap.get(preName+"_"+value);
            if(markMap !=null && markMap.size()>0){
                name =(String) markMap.get("name");
                return name;
            }
        }
        //缓存中没有数据,数据库中查询
        CodeEntity codeEntity = codeService.queryByMark(preName + "_" + value);
        if(codeEntity != null){
            name = codeEntity.getName();
        }
        return name;
    }
}
