package com.hxy.gen.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 类SysGeneratorDao的功能描述:
 *  代码生成器
 * @auther hxy
 * @date 2017-08-25 16:19:43
 */
@Repository
public interface SysGeneratorDao {
	
	List<Map<String, Object>> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	Map<String, String> queryTable(String tableName);
	
	List<Map<String, String>> queryColumns(String tableName);
}
