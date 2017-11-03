package com.hxy.solr.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hxy.base.page.Page;
import com.hxy.base.utils.PageUtils;
import com.hxy.base.utils.PropertiesLoader;
import com.hxy.solr.entity.HightQueryParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.NamedList;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 类的功能描述.
 *
 * @Auther hxy
 * @Date 2017/5/26
 */

public class SolrUtils {

    private static final Log logger = LogFactory.getLog(SolrUtils.class);
    private String coreName;
    private static   SolrClient solrClient;

    public SolrUtils (String coreName){
        this.solrClient = SolrClientFactory.getSolrClient(coreName);
        this.coreName=coreName;
    }

    /**
     * 获取高亮前缀
     * @return
     */
    public static String getPreTag(){
        PropertiesLoader loader = new PropertiesLoader("solrConfig.properties");
        return loader.getProperty("heihtpreTag");
    }

    /**
     * 获取高亮后缀
     * @return
     */
    public static String getPostTag(){
        PropertiesLoader loader = new PropertiesLoader("solrConfig.properties");
        return loader.getProperty("heihtpostTag");
    }

    public void addBean(Object object) throws IOException, SolrServerException {
        solrClient.addBean(object);
        //第一个参数 block until index changes are flushed to disk(直到索引更改刷新到磁盘为止)
        //第二个参数 block until a new searcher is opened and registered as the main query searcher, making the changes visible
        //(阻塞，直到新的搜索者被打开并注册为主查询搜索器，使得更改可见)
        //不传参数默认都为true
        solrClient.commit(false,false);
    }

    public <E> void addBeans(List<E> lists) throws SolrServerException, IOException
    {
        solrClient.addBeans(lists);
        solrClient.commit(false, false);
    }

    public static void deleteById(String idName, Object id) throws SolrServerException, IOException
    {
        solrClient.deleteByQuery(idName + ":" + id.toString());
        solrClient.commit(false, false);
    }

    /**
     * 方法deleteByIds的功能描述:
     * 根据ids批量删除
     * @params [idName, ids]
     * @return void
     * @auther hxy
     * @date 2017-09-11 21:48:14
     */
    public static <E> void deleteByIds(String idName, List<E> ids) throws SolrServerException, IOException {
        if (ids.size() > 0)
        {
            StringBuffer query = new StringBuffer(idName + ":" + ids.get(0));
            for (int i = 1; i < ids.size(); i++)
            {
                if (null != ids.get(i))
                {
                    query.append(" OR " + idName + ":" + ids.get(i).toString());
                }
            }
            solrClient.deleteByQuery(query.toString());
            solrClient.commit(false, false);
        }
    }

    /**
     *
     * @Description: 根据查询从索引删除
     * @author kang
     * @创建时间 2015下午5:32:35
     * @param @param query
     * @param @throws SolrServerException
     * @param @throws IOException
     * @return void
     * @throws
     */
    public void deleteByQuery(String query) throws SolrServerException, IOException
    {
        solrClient.deleteByQuery(query);
        solrClient.commit(false, false);
    }

    /**
     *
     * @Description: 删除所有
     * @author kang
     * @创建时间 2015下午5:36:21
     * @param @throws SolrServerException
     * @param @throws IOException
     * @return void
     * @throws
     */
    public void deleteAll() throws SolrServerException, IOException
    {
        solrClient.deleteByQuery("*:*");
        solrClient.commit(false, false);
    }

    /**
     * 方法addDoc的功能描述:
     * 根据 requestJson 对象 添加索引
     * @params [jsonArr]
     * @return org.apache.solr.client.solrj.response.UpdateResponse
     * @auther hxy
     * @date 2017-05-27 14:15:14
     */
    public UpdateResponse addDoc(JSONArray jsonArr) throws IOException, SolrServerException {
        UpdateResponse response = null;
        if (jsonArr == null || jsonArr.size() < 1) {
            System.out.println("待索引的数据为空！");
            return response;
        }
        for (int i = 0; i < jsonArr.size(); i++) {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            if (jsonObj == null) {
                continue;
            }
            // solr 索引document
            SolrInputDocument document = this._addDocument(jsonObj);
            // 提交solr 生成索引
            response = solrClient.add(document);
            // commit后才保存到索引库
            solrClient.commit(false,false);
        }
        return response;
    }

    /**
     * 方法_addDocument的功能描述:
     * 用于普通添加索引
     * @params [jsonObj]
     * @return org.apache.solr.common.SolrInputDocument
     * @auther hxy
     * @date 2017-05-27 14:13:44
     */
    @SuppressWarnings("unchecked")
    private SolrInputDocument _addDocument(JSONObject jsonObj) {
        SolrInputDocument document = null;
        document = new SolrInputDocument();
        Set<String> keys = jsonObj.keySet();
        for (String key:keys){
            document.addField(key,jsonObj.get(key));
        }
        return document;
    }

    /**
     * @Description: 关键字分页查询
     * @author kang
     * @创建时间 2015下午4:54:24
     * @param searchStr 查询语句
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param clzz 实体类class
     * @param sortMap 排序集 key:排序字段 value:SolrQuery.ORDER 排序类型 例 sortFiled:desc
     * @param @return
     * @return Page<T>
     * @throws
     */
    public static <T> PageUtils getByPage(String searchStr, int pageNum, int pageSize, Class<T> clzz, Map<String,SolrQuery.ORDER> sortMap) throws IOException, SolrServerException {
        SolrQuery query = new SolrQuery();
        query.setQuery(searchStr)// 查询内容
                .setStart((pageNum - 1) * pageSize)// 分页
                .setRows(pageSize);//
        if(sortMap !=null && sortMap.size()>0){
            for (String key:sortMap.keySet()){
                query.setSort(key,sortMap.get(key));
            }
        }
        QueryResponse response = solrClient.query(query);
        // 查询结果集
        SolrDocumentList results = response.getResults();
        // 获取对象
        List<T> beans = solrClient.getBinder().getBeans(clzz, results);
        // 总记录数
        int total = new Long(response.getResults().getNumFound()).intValue();
        return new PageUtils(beans, total, pageSize, pageNum);
    }

    /**
     * 方法hasIndex的功能描述:
     * 判断是否有索引数据
     * @params []
     * @return boolean
     * @auther hxy
     * @date 2017-05-27 14:26:16
     */
    public boolean hasIndex() throws IOException, SolrServerException {
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*")// 查询内容
                .setStart(0)// 分页
                .setRows(1);//条数
        QueryResponse response = solrClient.query(query);
        // 总记录数
        int total = new Long(response.getResults().getNumFound()).intValue();
        return total == 0 ? false : true;
    }

    /**
     * 方法getHighterByPage的功能描述:
     * 带高亮的关键字查询
     * @params [params]
     * @return com.hxy.base.utils.PageUtils
     * @auther hxy
     * @date 2017-09-12 11:17:05
     */
    public <T> Page getHighterByPage(HightQueryParams params) throws IOException, SolrServerException {
        List<String> hlFields = params.getHlFields();
        Class<T> clzz = params.getClzz();
        String queryStr=params.getQueryStr();
        String preTag = params.getPreTag();
        String postTag = params.getPostTag();
        int pageNum = params.getPageNum();
        int pageSize = params.getPageSize();
        String idName = params.getIdName();

        SolrQuery query = new SolrQuery();
        //排序字段
        Map<String, String> sorts = params.getSorts();
        for (String key:sorts.keySet()){
            if(sorts.get(key).equals("asc")){
                query.setSort(key, SolrQuery.ORDER.asc);
            }
            if(sorts.get(key).equals("desc")){
                query.setSort(key, SolrQuery.ORDER.desc);
            }
        }
        query.setQuery(queryStr)// 查询内容
                .setHighlight(true)// 设置高亮显示
                .setHighlightSimplePre(preTag)// 渲染头标签
                .setHighlightSimplePost(postTag)// 尾标签
                .setHighlightFragsize(150)//返回语段数量
                .setStart(( pageNum- 1) * pageSize)// 分页
                .setRows(pageSize).setFilterQueries("");//
//        if (distinguish) {
//            query.addFilterQuery("lang:" + lang);// 中英文区别
//        }
        // 设置高亮区域
        for (String hl : hlFields) {
            query.addHighlightField(hl);
        }
        QueryResponse response = solrClient.query(query);
        SolrDocumentList results = response.getResults();
        // 总记录数
        int total = new Long(results.getNumFound()).intValue();
        // 查询结果集
        ArrayList<T> list = new ArrayList<T>();
        try
        {
            Object object = null;
            Method method = null;
            Class<?> fieldType = null;
            Map<String, Map<String, List<String>>> map = response.getHighlighting();
            for (SolrDocument solrDocument : results)
            {
                object = clzz.newInstance();
                // 得到所有属性名
                Collection<String> fieldNames = solrDocument.getFieldNames();
                for (String fieldName : fieldNames)
                {
                    Field[] fields = clzz.getDeclaredFields();
                    for (Field f : fields)
                    {
                        // 如果实体属性名和查询返回集中的字段名一致，填充对应的set方法
                        if (f.getName().equals(fieldName))
                        {
                            f = clzz.getDeclaredField(fieldName);
                            fieldType = f.getType();
                            // 构造set方法名 setId
                            String dynamicSetMethod = dynamicMethodName(f.getName(), "set");
                            // 获取方法
                            method = clzz.getMethod(dynamicSetMethod, fieldType);
                            // 获取fieldType类型
                            // fieldType = getFileType(fieldType);
                            // 获取到的属性
                            method.invoke(object, fieldType.cast(solrDocument.getFieldValue(fieldName)));
                            for (String hl : hlFields)
                            {
                                if (hl.equals(fieldName))
                                {
                                    String idv = solrDocument.getFieldValue(idName).toString();
                                    List<String> hfList = map.get(idv).get(fieldName);
                                    if (null != hfList && hfList.size() > 0)
                                    {
                                        // 高亮添加
                                        method.invoke(object, fieldType.cast(hfList.get(0)));
                                    }
                                    else
                                    {
                                        method.invoke(object, fieldType.cast(solrDocument.getFieldValue(fieldName)));
                                    }
                                }
                            }
                        }
                    }
                }
                list.add(clzz.cast(object));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return new Page(list,total, pageSize, pageNum);
    }

    public static void queryHighlight(String keywords) throws SolrServerException, IOException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("name:" + keywords + "or content:" + keywords); // 设置查询关键字
        solrQuery.setHighlight(true); // 开启高亮
        solrQuery.addHighlightField("name"); // 高亮字段
        solrQuery.addHighlightField("content"); // 高亮字段
        solrQuery.setHighlightSimplePre("<font color='red'>"); // 高亮单词的前缀
        solrQuery.setHighlightSimplePost("</font>"); // 高亮单词的后缀
        /**
         * hl.snippets
         * hl.snippets参数是返回高亮摘要的段数，因为我们的文本一般都比较长，含有搜索关键字的地方有多处，如果hl.snippets的值大于1的话，
         * 会返回多个摘要信息，即文本中含有关键字的几段话，默认值为1，返回含关键字最多的一段描述。solr会对多个段进行排序。
         * hl.fragsize
         * hl.fragsize参数是摘要信息的长度。默认值是100，这个长度是出现关键字的位置向前移6个字符，再往后100个字符，取这一段文本。
         */
        solrQuery.setHighlightFragsize(15);

        QueryResponse query = solrClient.query(solrQuery);
        SolrDocumentList results = query.getResults();
        NamedList<Object> response = query.getResponse();
        NamedList<?> highlighting = (NamedList<?>) response.get("highlighting");
        for (int i = 0; i < highlighting.size(); i++) {
            System.out.println(highlighting.getName(i) + "：" + highlighting.getVal(i));
        }
        for (SolrDocument result : results) {
            System.out.println(result.toString());
        }
    }

    /**
     * @Description: 动态生成方法名
     * @author kang
     * @创建时间 2015下午9:16:59
     * @param @param name
     * @param @param string
     * @param @return
     * @return String
     * @throws
     */
    private String dynamicMethodName(String name, String string)
    {
        if (Character.isUpperCase(name.charAt(0))) return string + name;
        else return (new StringBuilder()).append(string + Character.toUpperCase(name.charAt(0))).append(name.substring(1)).toString();
    }

    /**
     * @Description:因为反射的属性可能是一个集合,所以在利用反射转换之前,需要进行更精确地判断,这实例中实体对象中的属性为简单类型,所以这个方法可以处理
     * @author kang
     * @创建时间 2015下午6:59:17
     * @param @param fieldType
     * @param @return
     * @return Class<?>
     * @throws
     */
    public Class<?> getFileType(Class<?> fieldType)
    {
        // 如果是 int, float等基本类型，则需要转型
        if (fieldType.equals(Integer.TYPE))
        {
            return Integer.class;
        }
        else if (fieldType.equals(Float.TYPE))
        {
            return Float.class;
        }
        else if (fieldType.equals(Double.TYPE))
        {
            return Double.class;
        }
        else if (fieldType.equals(Boolean.TYPE))
        {
            return Boolean.class;
        }
        else if (fieldType.equals(Short.TYPE))
        {
            return Short.class;
        }
        else if (fieldType.equals(Long.TYPE))
        {
            return Long.class;
        }
        else if (fieldType.equals(String.class))
        {
            return String.class;
        }
        else if (fieldType.equals(Collection.class)) { return Collection.class; }
        return null;
    }






}
