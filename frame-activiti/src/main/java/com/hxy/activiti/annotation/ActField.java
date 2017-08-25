package com.hxy.activiti.annotation;

import java.lang.annotation.*;

/**
 * 类的功能描述.
 * 在实体类中对字段进行注解，用于流程表单权限和分支条件设置
 * @Auther hxy
 * @Date 2017/7/27
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ActField {
    /**
     * 显示名字
     * @return
     */
    String name();

    /**
     * 是否用于分支条件判断
     * @return
     */
    boolean isJudg() default false;
}
