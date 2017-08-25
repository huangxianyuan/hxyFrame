package com.hxy.activiti.annotation;

import java.lang.annotation.*;

/**
 * 类的功能描述.
 * 在实体类中对字段进行注解，业务表名
 * @Auther hxy
 * @Date 2017/7/27
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ActTable {
    /**
     * 业务表名
     * @return
     */
    String tableName();

    /**
     * 数据库主键
     * @return
     */
    String pkName();
}
