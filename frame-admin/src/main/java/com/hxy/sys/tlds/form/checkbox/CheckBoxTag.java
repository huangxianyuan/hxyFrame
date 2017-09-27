package com.hxy.sys.tlds.form.checkbox;

import com.hxy.base.common.Constant;
import com.hxy.base.utils.JsonUtil;
import com.hxy.base.utils.StringUtils;
import com.hxy.utils.RedisUtil;
import org.springframework.util.LinkedCaseInsensitiveMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckBoxTag extends SimpleTagSupport {
	/**input标签的属性*/
	private String name;
	private String clazz;
	private String disabled;
	private String id;
	private String style;
	private String title;
	/**自定义属性*/
	private String initCheckKey;//设置复选框选中项的key（1,2）
	private String initDidableKey;//（值）设置复选框不包含该值 （1,2）
	private String initCludeKey;//（值）设置复选框包含值 （1,2）
    private String nameKey;//或者SEX 下面的 的key value
    private String data;//JSON的数据格式（由map转换成的json）
    private Boolean isJXdata;//是否解析json数据的格式数据
    private String otherAttr;//设置其他属性，格式为 otherAttr=" disabled onclick=\"onclick('123',this)\" "
    private Boolean isgetChildChild;//是否获取子节点的子节点
	
    @SuppressWarnings("unchecked")
	public void doTag()throws JspException,IOException {
    	StringBuffer sbcommon = new StringBuffer() ;
    	sbcommon.append("<label  ><input  type='checkbox' ");
		if(StringUtils.hasText(name)){
			sbcommon.append(" name='").append(name).append("' ");
		}
		if(StringUtils.hasText(clazz)){
			sbcommon.append(" class='").append(clazz).append("' ");
		}
		if(StringUtils.hasText(disabled)){
			sbcommon.append(" disabled='").append(disabled).append("' ");
		}
		if(StringUtils.hasText(style)){
			sbcommon.append(" style='").append(style).append("' ");
		}
		if(StringUtils.hasText(title)){
			sbcommon.append(" title='").append(title).append("' ");
		}
		if(StringUtils.hasText(otherAttr)){
			sbcommon.append(" ").append(otherAttr).append(" ");
		}
		Writer out = getJspContext().getOut();
    	try {
        	StringBuffer sb = new StringBuffer() ;
    		if(StringUtils.hasText(nameKey)||(isJXdata!=null&&isJXdata)){
    			//复选框值不能有的值 集合
    			Map<String,String> disableMap=new HashMap<String, String>();
    			if(StringUtils.hasText(initDidableKey)){
    				String ss[]=StringUtils.getArrayByArray(initDidableKey.split(","));
    				for(String s:ss){
    					disableMap.put(s,"1");
    				}
    			}
    			//复选框值只能有的值 集合
    			Map<String,String> cludeMap=new HashMap<String, String>();
    			if(StringUtils.hasText(initCludeKey)){
    				String ss[]=StringUtils.getArrayByArray(initCludeKey.split(","));
    				for(String s:ss){
    					cludeMap.put(s,"1");
    				}
    			}
    			//复选框值的初始值
    			Map<String,String> initMap=new HashMap<String, String>();
    			if(StringUtils.hasText(initCheckKey)){
    				String ss[]=StringUtils.getArrayByArray(initCheckKey.split(","));
    				for(String s:ss){
    					initMap.put(s,"1");
    				}
    			}
    			//所获得的数据
        		List<Map<String,Object>> childs=new ArrayList<Map<String,Object>>();
    			if(isJXdata!=null&&isJXdata){
    				childs= JsonUtil.getList(data, LinkedCaseInsensitiveMap.class);
    			}else{
					//从缓存中获取全部字典
//					Map<String,Map<String,Object>> allCodeMap= CodeCache.get(Constant.CODE_CACHE);
					Map<String,Map<String,Object>> allCodeMap= (Map<String, Map<String, Object>>) RedisUtil.getObject(Constant.CODE_CACHE);
					//获取mark=nameKey的字典
					Map<String,Object> objmap = allCodeMap.get(nameKey);
    				childs=(List<Map<String, Object>>) objmap.get("childList");
    				//是否获取子节点的子节点
    				if(isgetChildChild!=null&&isgetChildChild&&childs!=null&&childs.size()>0){
    					List<Map<String,Object>> cchilds=new ArrayList<Map<String,Object>>();
    					List<Map<String,Object>> cclist=null;
    					for(Map<String, Object> cc:childs){
    						cclist=(List<Map<String, Object>>) cc.get("childList");
    						if (cclist!=null&&cclist.size()>0) {
    							cchilds.addAll(cclist);
							}
    					}
    					childs=cchilds;
    				}
    			} 
        		if(childs!=null&&childs.size()>0){
        			for (Map<String,Object> option : childs) {
        				Object key=option.get("value");
        				//如果禁用一些值不显示，则跳过
        				if(StringUtils.hasText(disableMap.get(key))){
        					continue;
        				}
        				//如果只能让一些值显示，否则执行下一个
        				if(StringUtils.hasText(initCludeKey)){
            				if(!StringUtils.hasText(cludeMap.get(key))){
            					continue;
            				}
            			}
        				Object value=option.get("name");
        				sb.append(sbcommon).append(" value='").append(key).append("' ");
        	            if("1".equals(initMap.get(key))){
        	                sb.append(" checked='checked' ");
        	            }
        	            sb.append("/>").append(value).append("</label>");
        	        }
        		}
        	}
    		if(StringUtils.hasText(id)){
				out.write("<div id='"+id+"'  >"+sb.toString()+"</div>");
			}else{
				out.write("<div  >"+sb.toString()+"</div>");
			}
		} catch (Exception e) {
			out.write("<font color='red'>加载checkbox失败</font>");
		}
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInitCheckKey() {
		return initCheckKey;
	}

	public void setInitCheckKey(String initCheckKey) {
		this.initCheckKey = initCheckKey;
	}

	public String getInitDidableKey() {
		return initDidableKey;
	}

	public void setInitDidableKey(String initDidableKey) {
		this.initDidableKey = initDidableKey;
	}

	public String getInitCludeKey() {
		return initCludeKey;
	}

	public void setInitCludeKey(String initCludeKey) {
		this.initCludeKey = initCludeKey;
	}

	public String getNameKey() {
		return nameKey;
	}

	public void setNameKey(String nameKey) {
		this.nameKey = nameKey;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Boolean getIsJXdata() {
		return isJXdata;
	}

	public void setIsJXdata(Boolean isJXdata) {
		this.isJXdata = isJXdata;
	}

	public String getOtherAttr() {
		return otherAttr;
	}

	public void setOtherAttr(String otherAttr) {
		this.otherAttr = otherAttr;
	}

	public Boolean getIsgetChildChild() {
		return isgetChildChild;
	}

	public void setIsgetChildChild(Boolean isgetChildChild) {
		this.isgetChildChild = isgetChildChild;
	}
 
}
