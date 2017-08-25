package com.hxy.activiti.utils;

import com.hxy.activiti.annotation.ActField;
import com.hxy.base.exception.MyException;
import com.hxy.base.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类的功能描述.
 *
 * @Auther hxy
 * @Date 2017/8/11
 */

public class AnnotationUtils {

    /**
     * 通过类路径,通过反射获取类属性上的ActField注解信息,在获取属性时，
     * 最多支持向上获取两层父类属性,如需要可自行添加
     * @param classurl
     * @return List<map<String,Object>> key:keyName 属性名 key:actField 属性注解
     */
    public static List<Map<String,Object>> getActFieldByClazz(String classurl){
        if(StringUtils.isEmpty(classurl)){
            throw new MyException("实体类不能为空");
        }
        List<Map<String,Object>> list=new ArrayList<>();
        //获取当前类以及父类中属性
        ActField actField=null;
        try {
            Class clazz = Class.forName(classurl);
            PropertyDescriptor sourcePds[] = BeanUtils.getPropertyDescriptors(clazz);
            int j = sourcePds.length;
            for (int i = 0; i < j; i++) {
                Map<String,Object> reMap = new HashMap<>();
                PropertyDescriptor sourcePd = sourcePds[i];
                if (sourcePd != null && sourcePd.getWriteMethod() != null) {
                    try {
                        String keyName=sourcePd.getName();
                        try {
                            actField= clazz.getDeclaredField(keyName).getAnnotation(ActField.class);
                        } catch (NoSuchFieldException e) {
                            try {
                                actField= clazz.getSuperclass().getDeclaredField(keyName).getAnnotation(ActField.class);
                            }catch (NoSuchFieldException el){
                                actField= clazz.getSuperclass().getSuperclass().getDeclaredField(keyName).getAnnotation(ActField.class);
                            }
                        }
                        if(actField != null){
                            reMap.put("actField",actField);
                            reMap.put("keyName",keyName);
                            list.add(reMap);
                        }
                    } catch (Throwable ex) {
                        throw new FatalBeanException("对象获取属性失败!", ex);
                    }
                }
            }
        } catch (Exception e1) {
            throw new MyException("获取业务实体数据失败!");
        }
        return list;
    }


}
