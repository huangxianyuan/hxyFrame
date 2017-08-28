**项目说明** 
- hxyFrame是一个OA办公系统，其集成了权限管理（菜单权限、数据权限），完善的代码生成器，solr全文搜索引擎，工作流程引擎，cas单点登陆等功能，努力做到快速开发OA办公系统。


**项目功能** 
- 权限管理：Spring+Shiro+Mybatis 实现功能权限和机构部门的数据控件权限，可控件菜单权限、按钮权限、机构部门权限，满足一般的权限控制
- 工作流程引擎：采用主流的activiti流程引擎,在原基础上扩展了动态添加审批人员范围选择、会签节点的动态设置、排它路由条件设置、
              节点可编辑字段设置、节点执行后回调函数、办理任务、驳回到发起人从新发起、退回到上一步、自由跳转、转办等功能。在开发的过程中，
              只需要简单业务流程树，尊守一些规则就可以很方便的使用流程。
- 引入quartz定时任务，可动态完成任务的添加、修改、删除、暂停、恢复及日志查看等功能
- 页面交互使用了vue+html和最普通的jsp+jstl标签，两种交互都写了相应的模板，可以选择适合的交互方式。
- solr全文搜索引擎，最基本的增、删、改、查、关键字分页查询、带高亮的关键字查询，建立索引分为两种方式，一种是在做CRUD时调用solr添加索引
    另一是写sql直接从数据库中导入索引,目录还没在项目中使用，后期会加入
- 缓存使用ehcache，后期会考虑改为使用ehcache+Redis结合
- CAS单点登陆,整合cas+shiro单点登陆
- activitiMQ 消息中间件，目录还没在项目中使用，后期会加入
- 完善的代码生成机制，可在线生成entity、xml、dao、service、html、js、sql代码,可快速开发基本功能代码，能把更多的精力放在问题难点。
- 采用layer友好的弹框，和layerUI相对漂亮的界面，让OA系统看起来不那么Low....

**项目结构** 
![项目结构](http://osaowv4s0.bkt.clouddn.com/upload/20170828/9906e6ef50914343ad7a896975f1fe7b "项目结构")


**项目信息** 
- 项目demo地址：
- 开发文档：待完善
- oschina仓库：
- github仓库：https://github.com/huangxianyuan/hxyFrame.git
- 交流QQ群：
- 麻烦帮忙Watch、Star项目，这样才能关注到项目的最新更新，谢谢亲的支持！
 **技术选型：** 
- 核心框架：Spring Framework 4.3.7.RELEASE
- 工作流引擎：Activiti 5.22.0
- 全文搜索：Solr 6.5.1
- 消息中间件：activitiMQ 5.9.0
- 权限框架：Apache Shiro 1.3
- 视图框架：Spring MVC 4.3
- 持久层框架：MyBatis 3.3
- 数据库：mysql 5.7
- 定时器：Quartz 2.2.3
- 页面交互：Vue2.x、jstl


 **软件需求** 
- JDK1.7+
- MySQL5.5+
- Tomcat7.0+
- Maven3.0+



 **本地部署**
- 通过git下载源码
- 创建数据库hxyframe，数据库编码为UTF-8
- 修改db.properties文件，更新MySQL账号和密码
- 项目访问路径：http://localhost
- 非Maven方式启动，则默认访问路径为：http://localhost:8080/
- 账号密码：hxy/a

**项目图片：**
![图片说明](http://osaowv4s0.bkt.clouddn.com/upload/20170828/0e4ae20f3963422c8c93ae0408956be3 "图片说明")
![图片说明](http://osaowv4s0.bkt.clouddn.com/upload/20170828/7ca41c6baa324f729cf9ef82994fa056 "图片说明")
![图片说明](http://osaowv4s0.bkt.clouddn.com/upload/20170828/a4e43a7f37314fc0aeaa02a53de75ae2 "图片说明")
![图片说明](http://osaowv4s0.bkt.clouddn.com/upload/20170828/fd4d21b767b440e5845364e696801690 "图片说明")
![图片说明](http://osaowv4s0.bkt.clouddn.com/upload/20170828/590dc2d93f91436299ca7b32f50fd388 "图片说明")
![图片说明](http://osaowv4s0.bkt.clouddn.com/upload/20170828/2400965ea8534360840553657549a5cb "图片说明")
![图片说明](http://osaowv4s0.bkt.clouddn.com/upload/20170828/2fa6368a983a4f14a4ee95a9379092a7 "图片说明")
![图片说明](http://osaowv4s0.bkt.clouddn.com/upload/20170828/932cc2ed67c34690bea97c3c9766ab2c "图片说明")
![图片说明](http://osaowv4s0.bkt.clouddn.com/upload/20170828/f07ca986851248cb98ea2a5008c5daea "图片说明")
![图片说明](http://osaowv4s0.bkt.clouddn.com/upload/20170828/859c2607a84340529114e618ac293872 "图片说明")
![图片说明](http://osaowv4s0.bkt.clouddn.com/upload/20170828/2a7b4ab64d51457fb7bd2bde3575e4a1 "图片说明")
![图片说明](http://osaowv4s0.bkt.clouddn.com/upload/20170828/31fa0f9cc88241ec80ca76f90a462243 "图片说明")



