package com.hxy.activiti.dto;

/**
 * 类的功能描述.
 * 流程节点dto类
 * @Auther hxy
 * @Date 2017/8/8
 */
public class ProcessNodeDto {
    /**
     * 结点id
     */
    private String nodeId;

    /**
     * 节点名字
     */
    private String nodeName;

    /**
     * 节点类型 =1开始节点 2=审批节点 3=分支 4=连线 5=结束节点
     */
    private String nodeType;

    /**
     * 节点行为 1=审批 2=会签
     */
    private String nodeAction;

    /**
     * 节点行为名字
     */
    private String nodeActionName;

    /**
     * 节点类型名字
     */
    private String nodeTypeName;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeAction() {
        return nodeAction;
    }

    public void setNodeAction(String nodeAction) {
        this.nodeAction = nodeAction;
    }

    public String getNodeActionName() {
        return nodeActionName;
    }

    public void setNodeActionName(String nodeActionName) {
        this.nodeActionName = nodeActionName;
    }

    public String getNodeTypeName() {
        return nodeTypeName;
    }

    public void setNodeTypeName(String nodeTypeName) {
        this.nodeTypeName = nodeTypeName;
    }
}
