package ${package}.service;

import ${package}.entity.${className}Entity;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 * 
 * @author ${author}
 * @email ${email}
 * @date ${datetime}
 */
public interface ${className}Service {
	
	${className}Entity queryObject(${pk.attrType} ${pk.attrname});
	
	List<${className}Entity> queryList(Map<String, Object> map);

    List<${className}Entity> queryListByBean(${className}Entity entity);
	
	int queryTotal(Map<String, Object> map);
	
	int save(${className}Entity ${classname});
	
	int update(${className}Entity ${classname});
	
	int delete(${pk.attrType} ${pk.attrname});
	
	int deleteBatch(${pk.attrType}[] ${pk.attrname}s);
}
