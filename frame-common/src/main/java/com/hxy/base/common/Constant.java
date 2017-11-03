package com.hxy.base.common;

/**
 * 枚举常量
 * @Auther hxy
 * @Date 2017/4/27
 */
public class Constant {
    /**
     * 超级用户
     */
    public static final String SUPERR_USER="026a564bbfd84861ac4b65393644beef";

    /**
     * 系统默认密码
     */
    public static final String DEF_PASSWORD="a";

    /**
     * 分页条数
     */
    public static final int pageSize=10;

    /**
     * 用户缓存
     */
    public static final String USER_CACHE="userCache";

    /**
     * 数据字典缓存
     */
    public static final String CODE_CACHE="codeCache";

    /**
     * 流程会签集合名称
     */
    public static final String ACT_MUIT_LIST_NAME="users";

    /**
     * 流程会签变量名称
     */
    public static final String ACT_MUIT_VAR_NAME="user";

    /**
     * 云存储配置KEY
     */
    public final static String CLOUD_STORAGE_CONFIG_KEY = "CLOUD_STORAGE_CONFIG_KEY";

    /*******************************solr Core名称 开始*************************/

    /**
     * 文章
     */
    public static final String CORE_ARTICLE ="article";

    /*******************************solr Core名称 结束*************************/

    /*******************************数据字典mark 开始*************************/

    /**
     * 文章
     */
    public static final String ARTCLE_TYPE ="artcle_type";

    /*******************************数据字典mark 结束*************************/

    /**
     * 菜单类型
     */
    public enum MenuType{
        /**
         * 菜单
         */
        MENU("1"),
        /**
         * 按钮
         */
        BUTTON("2"),
        /**
         * 目录
         */
        CATALOG("0");
        private String value;

        private MenuType(String value){
            this.value=value;
        }
        public String getValue(){
            return value;
        }
    }

    /**
     * 是否类型
     */
    public enum YESNO{
        /**
         * 是
         */
        YES("0"),
        /**
         * 否
         */
        NO("1");
        private String value;

        private YESNO(String value){
            this.value=value;
        }
        public String getValue(){
            return value;
        }
    }

    /**
     * 系统用户状态
     */
    public enum ABLE_STATUS{
        /**
         * 正常
         */
        YES("0"),
        /**
         * 禁用
         */
        NO("-1");
        private String value;

        ABLE_STATUS(String value){
            this.value=value;
        }
        public String getValue(){
            return value;
        }
    }


    /**
     * 返回状态值
     */
    public enum RESULT{
        /**
         * 成功
         */
        CODE_YES("0"),
        /**
         * 失败
         */
        CODE_NO("-1"),
        /**
         * 失败msg
         */
        MSG_YES("操作成功"),
        /**
         * 失败msg
         */
        MSG_NO("操作失败");
        private String value;

        private RESULT(String value){
            this.value=value;
        }
        public String getValue(){
            return value;
        }
    }

    /**
     * 定时任务状态
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        private ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 代码生成方式
     */
    public enum genType {
        /**
         * 本地项目
         */
        local(0),
        /**
         * web下载
         */
        webDown(1);

        private int value;

        private genType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        private CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 码值类型
     */
    public enum CodeType {
        /**
         * 目录
         */
        CATALOG("1"),
        /**
         * 字典码
         */
        CODE("2");

        private String value;

        private CodeType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 机构类型
     */
    public enum OrganType {
        /**
         * 根节点
         */
        CATALOG("0"),
        /**
         * 机构
         */
        ORGAN("1"),
        /**
         * 部门
         */
        DEPART("2");

        private String value;

        private OrganType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 流程节点类型
     */
    public enum NodeType {
        /**
         * 开始节点
         */
        START("1"),
        /**
         * 审批节点
         */
        EXAMINE("2"),

        /**
         * 分支
         */
        BRUNCH("3"),
        /**
         * 连线
         */
        LINE("4"),
        /**
         * 结束
         */
        END("5");


        private String value;

        private NodeType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 审批者类型
     */
    public enum ExamineType {
        /**
         * 用户
         */
        USER("1"),
        /**
         * 角色
         */
        ROLE("2"),

        /**
         * 组织
         */
        ORGAN("3");

        private String value;

        private ExamineType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 业务树类型
     */
    public enum ActBusType {
        /**
         * 根节点
         */
        ROOT("1"),
        /**
         * 分组
         */
        GROUP("2"),
        /**
         * 业务类
         */
        BUS("3"),
        /**
         * 回调
         */
        BACK("4");

        private String value;

        private ActBusType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 审批节点行为
     */
    public enum ActAction {
        /**
         * 审批
         */
        APPROVE("1"),
        /**
         * 会签
         */
        MULIT("2");

        private String value;

        private ActAction(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 流程状态
     */
    public enum ActStauts {
        /**
         * 草稿
         */
        DRAFT("1"),
        /**
         * 审批中
         */
        APPROVAL("2"),
        /**
         * 结束
         */
        END("3");

        private String value;

        private ActStauts(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 流程任务审批结果
     */
    public enum ActTaskResult {
        /**
         * 同意
         */
        AGREE("1"),
        /**
         * 反对
         */
        NO_AGREE("2"),
        /**
         * 弃权
         */
        ABSTAINED("3"),
        /**
         * 驳回
         */
        TURN_DOWN("4"),
        /**
         * 转办
         */
        TURN_DO("5");

        private String value;

        private ActTaskResult(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 整个流程的审批结果
     */
    public enum ActResult {
        /**
         * 同意
         */
        AGREE("1"),
        /**
         * 不同意
         */
        NO_AGREE("2"),
        /**
         * 审批中
         */
        DISAGREE("3");

        private String value;

        private ActResult(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 流程办理任务，和查询流程审批信息控件显示
     */
    public enum ActFlowDoView {
        /**
         * 查看审批过程
         */
        SHOW_FLOW("1"),
        /**
         * 办理任务
         */
        DO_TASK("2");

        private String value;

        private ActFlowDoView(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 数据权限
     */
    public enum DataAuth {
        /**
         * 部门数据权限
         */
        BA_DATA("1"),
        /**
         * 机构数据权限
         */
        BAP_DATA("2"),
        /**
         * 部门机构数据权限
         */
        ALL_DATA("3");

        private String value;

        private DataAuth(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 通知类型
     */
    public enum noticeType {
        /**
         *普通通知
         */
        UL_NOTICE("1"),
        /**
         * 流程通知
         */
        ACT_NOTICE("2");

        private String value;

        private noticeType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
