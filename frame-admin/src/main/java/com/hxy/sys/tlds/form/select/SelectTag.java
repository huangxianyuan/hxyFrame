package com.hxy.sys.tlds.form.select;

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

public class SelectTag extends SimpleTagSupport {
	/**select标签的属性*/
	private String name;
	private String clazz;
	private String disabled;
	private String id;
	private String multiple;
	private String style;
	private String tabindex;
	private String title;
	/**自定义属性*/
	private Boolean isAddDefaltOption;//是否加入默认项--初始化<option value=''>--请选择--</option>
	private String defaltOptionKey="";//默认项值
	private String defaltOptionValue="-请选择-";//默认项值
	private String initSelectedKey;//设置选中项项的key
	private String initDidableKey;//（值）设置下拉框不包含该值 （1,2）
	private String initCludeKey;//（值）设置下拉框包含值 （1,2）
    private String nameKey;//或者SEX 下面的 的key value
    private String data;//JSON的数据格式（由List转换成的json）
    private Boolean isJXdata;//是否解析json数据的格式数据
    private String otherAttr;//设置其他属性，格式为 otherAttr=" disabled onclick=\"onclick('123',this)\" "
    private Boolean isgetChildChild;//是否获取子节点的子节点
	
    @SuppressWarnings("unchecked")
	public void doTag()throws JspException,IOException {
		Writer out = getJspContext().getOut();
    	try {
        	StringBuffer sb = new StringBuffer() ;
        	//如果nameKey没有设置，默认等于name
        	if(StringUtils.hasText(name)&&!StringUtils.hasText(nameKey)){
        		nameKey=name;
        	}
    		if(StringUtils.hasText(nameKey)||(isJXdata!=null&&isJXdata)){
    			sb.append("<select ");
    			if(StringUtils.hasText(name)){
        			sb.append(" name='").append(name).append("' ");
    			}
    			if(StringUtils.hasText(clazz)){
    				sb.append(" class='").append(clazz).append("' ");
    			}
    			if(StringUtils.hasText(disabled)){
    				sb.append(" disabled='").append(disabled).append("' ");
    			}
    			if(StringUtils.hasText(id)){
    				sb.append(" id='").append(id).append("' ");
    			}
    			if(StringUtils.hasText(multiple)){
    				sb.append(" multiple='").append(multiple).append("' ");
    			}
    			if(StringUtils.hasText(style)){
    				sb.append(" style='").append(style).append("' ");
    			}
    			if(StringUtils.hasText(tabindex)){
    				sb.append(" tabindex='").append(tabindex).append("' ");
    			}
    			if(StringUtils.hasText(title)){
    				sb.append(" title='").append(title).append("' ");
    			}
    			if(StringUtils.hasText(otherAttr)){
    				sb.append(" ").append(otherAttr).append(" ");
    			}
    			sb.append(">");
    			//下拉框值不能有的值 集合
    			Map<String,String> disableMap=new HashMap<String, String>();
    			if(StringUtils.hasText(initDidableKey)){
    				String ss[]=StringUtils.getArrayByArray(initDidableKey.split(","));
    				for(String s:ss){
    					disableMap.put(s,"1");
    				}
    			}
    			//下拉框值只能有的值 集合
    			Map<String,String> cludeMap=new HashMap<String, String>();
    			if(StringUtils.hasText(initCludeKey)){
    				String ss[]=StringUtils.getArrayByArray(initCludeKey.split(","));
    				for(String s:ss){
    					cludeMap.put(s,"1");
    				}
    			}
    			//是否设置空值
    			if(isAddDefaltOption!=null&&isAddDefaltOption){
    				sb.append("<option value='").append(defaltOptionKey).append("'>").append(defaltOptionValue).append("</option>");
    			}
        		List<Map<String,Object>> childs=new ArrayList<Map<String,Object>>();
    			if(isJXdata!=null&&isJXdata){
    				childs= JsonUtil.getList(data, LinkedCaseInsensitiveMap.class);
    			}else{
                    //从缓存中获取全部字典
//                    Map<String,Map<String,Object>> allCodeMap= CodeCache.get(Constant.CODE_CACHE);
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
        	            if(StringUtils.hasText(initSelectedKey)&&initSelectedKey.equals(key)){
        	                sb.append("<option value='").append(key).append("' selected='selected'>");
        	            }else{
        	                sb.append("<option value='").append(key).append("'>");
        	            }
        	            sb.append(value).append("</option>");
        	        }
        		}
    			sb.append("</select>");
        	}
    		out.write(sb.toString());
		} catch (Exception e) {
			//e.printStackTrace();
			//out.write("<font color='red'>加载select标签失败</font>");
			StringBuffer sb = new StringBuffer() ;
    		if(StringUtils.hasText(nameKey)||(isJXdata!=null&&isJXdata)){
    			sb.append("<select ");
    			if(StringUtils.hasText(name)){
        			sb.append(" name='").append(name).append("' ");
    			}
    			if(StringUtils.hasText(clazz)){
    				sb.append(" class='").append(clazz).append("' ");
    			}
    			if(StringUtils.hasText(disabled)){
    				sb.append(" disabled='").append(disabled).append("' ");
    			}
    			if(StringUtils.hasText(id)){
    				sb.append(" id='").append(id).append("' ");
    			}
    			if(StringUtils.hasText(multiple)){
    				sb.append(" multiple='").append(multiple).append("' ");
    			}
    			if(StringUtils.hasText(style)){
    				sb.append(" style='").append(style).append("' ");
    			}
    			if(StringUtils.hasText(tabindex)){
    				sb.append(" tabindex='").append(tabindex).append("' ");
    			}
    			if(StringUtils.hasText(title)){
    				sb.append(" title='").append(title).append("' ");
    			}
    			if(StringUtils.hasText(otherAttr)){
    				sb.append(" ").append(otherAttr).append(" ");
    			}
    			sb.append(">");
    			sb.append("<option value=''>-请选择-</option>");
    			sb.append("</select>");
        		out.write(sb.toString());
        	}else {
        		out.write("<font color='red'>加载select标签失败</font>");
			}
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

	public String getNameKey() {
		return nameKey;
	}

	public void setNameKey(String nameKey) {
		this.nameKey = nameKey;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getTabindex() {
		return tabindex;
	}

	public void setTabindex(String tabindex) {
		this.tabindex = tabindex;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public Boolean getIsAddDefaltOption() {
		return isAddDefaltOption;
	}

	public void setIsAddDefaltOption(Boolean isAddDefaltOption) {
		this.isAddDefaltOption = isAddDefaltOption;
	}

	public String getDefaltOptionKey() {
		return defaltOptionKey;
	}

	public void setDefaltOptionKey(String defaltOptionKey) {
		this.defaltOptionKey = defaltOptionKey;
	}

	public String getDefaltOptionValue() {
		return defaltOptionValue;
	}

	public void setDefaltOptionValue(String defaltOptionValue) {
		this.defaltOptionValue = defaltOptionValue;
	}

	public String getInitSelectedKey() {
		return initSelectedKey;
	}

	public void setInitSelectedKey(String initSelectedKey) {
		this.initSelectedKey = initSelectedKey;
	}

	public String getOtherAttr() {
		return otherAttr;
	}

	public void setOtherAttr(String otherAttr) {
		this.otherAttr = otherAttr;
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

	public Boolean getIsgetChildChild() {
		return isgetChildChild;
	}

	public void setIsgetChildChild(Boolean isgetChildChild) {
		this.isgetChildChild = isgetChildChild;
	}
 
 
}
