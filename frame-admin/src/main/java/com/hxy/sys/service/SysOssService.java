package com.hxy.sys.service;


import com.hxy.sys.entity.SysOssEntity;

import java.util.List;
import java.util.Map;

/**
 * 类SysOssService的功能描述:
 * 文件上传
 * @auther hxy
 * @date 2017-08-25 16:13:56
 */
public interface SysOssService {
	
	SysOssEntity queryObject(Long id);
	
	List<SysOssEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysOssEntity sysOss);
	
	void update(SysOssEntity sysOss);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
