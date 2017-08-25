package com.hxy.gen.utils;

import com.hxy.base.common.Constant;
import com.hxy.base.common.RRException;
import com.hxy.base.utils.DateUtils;
import com.hxy.gen.entity.ColumnEntity;
import com.hxy.gen.entity.TableEntity;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 类GenUtils的功能描述:
 * 代码生成器   工具类
 * @auther hxy
 * @date 2017-08-25 16:12:47
 */
public class GenUtils {
	//配置信息
	private final static Configuration config = getConfig();
	private static Logger log = Logger.getLogger(GenUtils.class);

	public static List<String> getTemplates(){
		List<String> templates = new ArrayList<String>();
		templates.add("template/Entity.java.vm");
		templates.add("template/Dao.java.vm");
		templates.add("template/Dao.xml.vm");
		templates.add("template/Service.java.vm");
		templates.add("template/ServiceImpl.java.vm");
		templates.add("template/Controller.java.vm");
		templates.add("template/list.html.vm");
		templates.add("template/list.js.vm");
		templates.add("template/menu.sql.vm");
		return templates;
	}


	/**
	 * 生成代码
	 * @param table
	 * @param columns
	 * @param zip
	 * @param genType 生成方式
	 */
	public static void generatorCode(Map<String, String> table,
			List<Map<String, String>> columns, ZipOutputStream zip,int genType){
		//配置信息
		//Configuration config = getConfig();
		
		//表信息
		TableEntity tableEntity = new TableEntity();
		tableEntity.setTableName(table.get("tableName"));
		tableEntity.setComments(table.get("tableComment"));
		//表名转换成Java类名
		String className = tableToJava(tableEntity.getTableName(), config.getString("tablePrefix"));
		tableEntity.setClassName(className);
		tableEntity.setClassname(StringUtils.uncapitalize(className));
		
		//列信息
		List<ColumnEntity> columsList = new ArrayList<ColumnEntity>();
		for(Map<String, String> column : columns){
			ColumnEntity columnEntity = new ColumnEntity();
			columnEntity.setColumnName(column.get("columnName"));
			columnEntity.setDataType(column.get("dataType"));
			columnEntity.setComments(column.get("columnComment"));
			columnEntity.setExtra(column.get("extra"));
			
			//列名转换成Java属性名
			String attrName = columnToJava(columnEntity.getColumnName());
			columnEntity.setAttrName(attrName);
			columnEntity.setAttrname(StringUtils.uncapitalize(attrName));
			
			//列的数据类型，转换成Java类型
			String attrType = config.getString(columnEntity.getDataType(), "unknowType");
			columnEntity.setAttrType(attrType);
			
			//是否主键
			if("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null){
				tableEntity.setPk(columnEntity);
			}
			
			columsList.add(columnEntity);
		}
		tableEntity.setColumns(columsList);
		
		//没主键，则第一个字段为主键
		if(tableEntity.getPk() == null){
			tableEntity.setPk(tableEntity.getColumns().get(0));
		}
		
		//设置velocity资源加载器
		Properties prop = new Properties();  
		prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");  
		Velocity.init(prop);
		
		//封装模板数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", tableEntity.getTableName());
		map.put("comments", tableEntity.getComments());
		map.put("pk", tableEntity.getPk());
		map.put("className", tableEntity.getClassName());
		map.put("classname", tableEntity.getClassname());
		map.put("pathName", tableEntity.getClassname().toLowerCase());
		map.put("columns", tableEntity.getColumns());
		map.put("package", config.getString("package"));
		map.put("author", config.getString("author"));
		map.put("email", config.getString("email"));
		map.put("baseDao", config.getString("baseDao"));
		map.put("utils", config.getString("utils"));
		map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        VelocityContext context = new VelocityContext(map);
        
        //获取模板列表
		List<String> templates = getTemplates();
		for(String template : templates){
			//渲染模板
			Template tpl = Velocity.getTemplate(template, "UTF-8");
			//本地项目生成
			if(genType == Constant.genType.local.getValue()){
                local(template,tableEntity,tpl,context);
			}
			//web页面生成
			if(genType == Constant.genType.webDown.getValue()){
                try {
                    webDown(zip,template,tableEntity,tpl,context);
                } catch (IOException e) {
                    throw new RRException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
                }
            }
		}
	}

	/**
	 * 方法webDown的功能描述:
	 * 在web页面中生成代码，并打包zip文件下载
	 * @params [zip, template, tableEntity, tpl, context]
	 * @return void
	 * @auther hxy
	 * @date 2017-06-28 16:24:33
	 */
	public static void webDown(ZipOutputStream zip,String template,TableEntity tableEntity,Template tpl,VelocityContext context) throws IOException {
		StringWriter sw = new StringWriter();
		tpl.merge(context, sw);
		//添加到zip
		zip.putNextEntry(new ZipEntry(getWebFileName(template, tableEntity.getClassName(), config.getString("package"))));
		IOUtils.write(sw.toString(), zip, "UTF-8");
		IOUtils.closeQuietly(sw);
		zip.closeEntry();
	}

	/**
	 * 在本地工程中生成代码
	 * @param template
	 * @param tableEntity
	 * @param tpl
	 * @param context
	 */
	public static void local(String template,TableEntity tableEntity,Template tpl,VelocityContext context){
		try {
			String filePath=getLocalFileName(template, tableEntity.getClassName(), "");
			File file = new File(filePath);
			if(!file.exists()){
				file.createNewFile();
			}
			FileOutputStream fos=new FileOutputStream(filePath);
			BufferedWriter writer  = new BufferedWriter(new OutputStreamWriter(
					fos,"UTF-8"));
			tpl.merge(context, writer);
			log.info("生成文件"+tableEntity.getClassName()+"生成成功！");
			writer.close();
		} catch (Exception  e) {
			e.printStackTrace();
		}
	}

	
	
	/**
	 * 列名转换成Java属性名
	 */
	public static String columnToJava(String columnName) {
		return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
	}
	
	/**
	 * 表名转换成Java类名
	 */
	public static String tableToJava(String tableName, String tablePrefix) {
		if(StringUtils.isNotBlank(tablePrefix)){
			tableName = tableName.replace(tablePrefix, "");
		}
		return columnToJava(tableName);
	}
	
	/**
	 * 获取配置信息
	 */
	public static Configuration getConfig(){
		try {
			return new PropertiesConfiguration("generatorjsp.properties");
		} catch (ConfigurationException e) {
			throw new RRException("获取配置文件失败，", e);
		}
	}


	/**
	 * 获取文件名 本地项目中生成
	 */
	public static String getLocalFileName(String template, String className, String packageName){

		if(template.contains("Entity.java.vm")){
			return config.getString("entity") + File.separator + className + "Entity.java";
		}
		
		if(template.contains("Dao.java.vm")){
			return config.getString("dao") + File.separator + className + "Dao.java";
		}
		
		if(template.contains("Dao.xml.vm")){
			return config.getString("mapping") + File.separator + className + "Mapper.xml";
		}
		
		if(template.contains("Service.java.vm")){
			return config.getString("service") + File.separator + className + "Service.java";
		}
		
		if(template.contains("ServiceImpl.java.vm")){
			return config.getString("serviceImpl") +File.separator+className+ "ServiceImpl.java";
		}
		
		if(template.contains("Controller.java.vm")){
			return config.getString("controller") +File.separator+className+ "Controller.java";
		}
		
		if(template.contains("list.html.vm")){
			return config.getString("view")+ File.separator + className.toLowerCase() + ".html";
		}
		
		if(template.contains("list.js.vm")){
			return config.getString("js") + File.separator + className.toLowerCase() + ".js";
		}

		if(template.contains("menu.sql.vm")){
			return config.getString("sql") + File.separator + className.toLowerCase() + "_menu.sql";
		}
		return null;
	}
    /**
     * 获取文件名 web中生成代码使用
     */
    public static String getWebFileName(String template, String className, String packageName){
        String packagePath = "main" + File.separator + "java" + File.separator;
        if(StringUtils.isNotBlank(packageName)){
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }

        if(template.contains("Entity.java.vm")){
            return packagePath + "entity" + File.separator + className + "Entity.java";
        }

        if(template.contains("Dao.java.vm")){
            return packagePath + "dao" + File.separator + className + "Dao.java";
        }

        if(template.contains("Dao.xml.vm")){
            return packagePath + "dao" + File.separator + className + "Dao.xml";
        }

        if(template.contains("Service.java.vm")){
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if(template.contains("ServiceImpl.java.vm")){
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if(template.contains("Controller.java.vm")){
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if(template.contains("list.html.vm")){
            return "main" + File.separator + "webapp" + File.separator + "WEB-INF" + File.separator + "page"
                    + File.separator + "generator" + File.separator + className.toLowerCase() + ".html";
        }

        if(template.contains("list.js.vm")){
            return "main" + File.separator + "webapp" + File.separator + "js" + File.separator + "generator" + File.separator + className.toLowerCase() + ".js";
        }

        if(template.contains("menu.sql.vm")){
            return className.toLowerCase() + "_menu.sql";
        }

        return null;
    }
}
