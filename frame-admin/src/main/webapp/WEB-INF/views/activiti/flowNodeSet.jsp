<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>新建模型</title>
	    <%@include file="/common/commonCSS.jsp" %>
	    <%@include file="/common/commonJS.jsp" %>
		<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	</head>

	<body >
		<div class="main-container" id="main-container">
			<div class="row" style="margin-left: 20px;margin-top: 20px;">
				<div class="col-md-3 panel panel-default">
					<div style="font-size: 20px;margin-bottom: 10px">
						<i class="layui-icon" style="font-size: 20px; color: #009688;">&#xe62e;</i> 流程节点树
					</div>
					<div class="panel-body" style="padding: 10px;">
						<ul id="modelTree" class="ztree" ></ul>
					</div>
				</div>
				<div class="col-md-7 panel panel-default" style="margin-left: 100px">
					<div style="font-size: 20px;margin-bottom: 10px">
						<i class="layui-icon panel-title" style="font-size: 20px; color: #009688;">&#xe63c;</i> 节点信息
					</div>
					<div class="row form-content">
						<div class="col-xs-12">
							<form class="form-horizontal  bv-form" id="flowSetForm" action="#">
								<div style="display: none">
									<input name="nodeId"/>
									<input name="id"/>
									<input name="modelId"/>
								</div>
								<div class="row">
									<div class="form-group col-sm-6 col-md-5 ">
										<label class="col-sm-3 control-label no-padding-right">名称:</label>
										<div class="col-sm-9">
											<span class="col-xs-11 block input-icon input-icon-right">
												<input id="name" name="name" type="text"  class="form-control" disabled />
											</span>
										</div>
									</div>
									<div class="form-group col-sm-6 col-md-5 ">
										<label class="col-sm-3 control-label no-padding-right">类型:</label>
										<div class="col-sm-9">
											<span class="col-xs-11 block input-icon input-icon-right">
												<select name="nodeType" class="form-control" readonly="true">
													<option value="1">开始节点</option>
													<option value="2">审批节点</option>
													<option value="3">分支</option>
													<option value="4">连线 </option>
													<option value="5">结束节点 </option>
												</select>
											</span>
										</div>
										<div class="readOnly"></div>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-sm-6 col-md-5 " isHide="2">
										<label class="col-sm-3 control-label no-padding-right"><i class="red"> * </i>行为:</label>
										<div class="col-sm-9">
											<span class="col-xs-11 block input-icon input-icon-right">
												<tag:select nameKey="act_node_action" name="nodeAction" clazz="form-control"/>
											</span>
										</div>
									</div>
									<div class="form-group col-sm-6 col-md-5 " isHide="25">
										<label class="col-sm-3 control-label no-padding-right">回调:</label>
										<div class="col-sm-9">
											<span class="col-xs-11 block input-icon input-icon-right">
												<tag:select name="callBack"  clazz="form-control" isJXdata="true" data="${calBacks}" isAddDefaltOption="true" defaltOptionValue="--请选择--"/>
											</span>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-sm-12 " isHide="2">
										<label class="col-sm-2"><i class="red"> * </i>可修改信息:</label>
										<div class="col-sm-10">
											<span class="col-xs-11" style='margin-left: -50px'>
												<tag:checkbox name="changeFiles" isJXdata="true" data="${writes }"/>
											</span>
										</div>
									</div>
								</div>
								<div isHide="2">
									<button class="layui-btn layui-btn-small" type="button" onclick="userWindow()"><i class="layui-icon">&#xe612;</i>审批范围</button>
								</div>
								<table id="userTab" class="layui-table" isHide="2">
									<thead>
									<tr>
										<th>类型</th>
										<th>名称</th>
										<th>操作</th>
									</tr>
									</thead>
									<tbody>

									</tbody>
								</table>
								<div class="row" isHide="4" id="actRules">
									<div class="form-group col-sm-12">
										<div class="form-group">
											<label class="control-label col-sm-1">条件1</label>
											<div class="col-sm-3">
												<tag:select name="judgList[0].fieldName" clazz="form-control" isJXdata="true" data="${judgs }" isAddDefaltOption="true"/>
											</div>
											<div class="col-sm-2" style="padding: 0px;">
												<tag:select name="judgList[0].rule" clazz="form-control" nameKey="act_judg" isAddDefaltOption="true" />
											</div>
											<div class="col-sm-3">
												<input name="judgList[0].fieldVal" class="form-control" placeholder="" >
											</div>
											<div class="col-sm-2">
												<tag:select name="judgList[1].elOperator" clazz="form-control" nameKey="act_el_operator" isAddDefaltOption="true" />
											</div>
											<%--<button class="layui-btn" onclick="addActRule()">
                                                <i class="layui-icon">&#xe608;</i> 添加
                                            </button>--%>
										</div>
									</div>
									<div class="form-group col-sm-12 ">
										<div class="form-group">
											<label class="control-label col-sm-1">条件2</label>
											<div class="col-sm-3">
												<tag:select name="judgList[1].fieldName" clazz="form-control" isJXdata="true" data="${judgs }" isAddDefaltOption="true"/>
											</div>
											<div class="col-sm-2" style="padding: 0px;">
												<tag:select name="judgList[1].rule" clazz="form-control" nameKey="act_judg"  isAddDefaltOption="true"/>
											</div>
											<div class="col-sm-3">
												<input name="judgList[1].fieldVal" class="form-control" placeholder="" >
											</div>
											<div class="col-sm-2">
												<tag:select name="judgList[2].elOperator" clazz="form-control" nameKey="act_el_operator" isAddDefaltOption="true" />
											</div>
										</div>
									</div>
									<div class="form-group col-sm-12">
										<div class="form-group">
											<label class="control-label col-md-1">条件3</label>
											<div class="col-md-3">
												<tag:select name="judgList[2].fieldName" clazz="form-control" isJXdata="true" data="${judgs }" isAddDefaltOption="true"/>
											</div>
											<div class="col-md-2" style="padding: 0px;">
												<tag:select name="judgList[2].rule" clazz="form-control" nameKey="act_judg"  isAddDefaltOption="true"/>
											</div>
											<div class="col-md-3">
												<input name="judgList[2].fieldVal" class="form-control"  >
											</div>
										</div>
									</div>
								</div>
								<div class="row" style="margin-left: 180px;padding-bottom: 28px;margin-top: 25px;">
									<button class="layui-btn" type="button" id="submitBtn">保 存</button>
									<button class="layui-btn layui-btn-primary" type="button" onclick="closeThisWindow()">关 闭</button>
								</div>
							</form>
						</div>
					</div>
				</div><!-- /.col-lg-6 -->
			</div><!-- /.row -->
		</div>
	</body>

	<script>
		 $(function () {
			 //页面加载都隐藏
			 $("[isHide]").hide();
             $("#flowSetForm select[name='nodeType']").on("change",function(){
                 $("[isHide]").hide();
                 $("[isHide*='"+$(this).val()+"'").show();
             });
         });
		
        var setting = {
            view: {dblClickExpand: false}
            ,data: {key: {name:"treeName"},simpleData: {enable: true,idKey: "treeId",pIdKey: "treePid"}}
            ,callback: {onClick: zTreeOnClick}
        };
        $(function(){
            var data='${flows}';
            var ztreeobj=$.fn.zTree.init($("#modelTree"), setting, eval(''+data+''));//ztree树加载
            ztreeobj.expandAll(true);
        });

        /**
		 * 节点单击事件
         */
        function zTreeOnClick(event, treeId, treeNode) {
            //清空表单信息
            $("#flowSetForm")[0].reset();
            $("#flowSetForm input[name='name']").val(treeNode.treeName);
            $("#flowSetForm input[name='nodeId']").val(treeNode.treeId);
            $("#flowSetForm input[name='modelId']").val(treeNode.modelId);
            var selectChange=$("#flowSetForm select[name='nodeType']").val(treeNode.type)[0];
            simulateClick(selectChange);
            var url = "${webRoot}/act/model/flowSetInfo";
			var params={
			    nodeId:treeNode.treeId,
				type:treeNode.type
            }
            $.post(url,params,function (result) {
				if(result.code == '0'){
					var nodeSet = result.nodeSet;
					if(nodeSet){
                        formload("flowSetForm",nodeSet);
					}
					//审批范围
                    var userLists = result.userList;
                    if(userLists){
                        var html="";
						for (var i=0;i<userLists.length;i++){
                            html+="<tr id='"+userLists[i].id+"'>";
                            html+="  <td>"+userLists[i].typeName+"</td>";
                            html+="  <td style='display: none'>";
                            html+="  	<input name='userTypes' value='"+userLists[i].userType+"'/>";
                            html+="  	<input name='userIds' value='"+userLists[i].id+"'/>";
                            html+="  </td>";
                            html+="  <td>"+userLists[i].userTitle+"</td>";
                            html+='<td><button type="button" onclick="delUser(this)" class="btn btn-xs btn-white btn-danger"><i class="fa fa-trash-o"></i> 删 除 </button></td>';
                            html+="</tr>";
						}
						$("#userTab tbody").html(html);
                    }
					//节点条件
                    var fields = result.fields;
                    if(fields){
						for(var i=0;i<fields.length;i++){
                            simulateClick($("#flowSetForm select[name='judgList["+i+"].fieldName']").val(fields[i].fieldName)[0]);
                            $("#flowSetForm select[name='judgList["+i+"].rule']").val(fields[i].rule);
                            $("#flowSetForm input[name='judgList["+i+"].fieldVal']").val(fields[i].fieldVal);
                            simulateClick($("#flowSetForm select[name='judgList["+i+"].elOperator']").val(fields[i].elOperator)[0]);
						}
                    }
				}else {
				    alertMsg(result.msg);
				}
            });
        };


        //条件增加 待开发
        function addActRule() {
			$("#actRules").append();
        }

         /**
		  * 选择审批范围
          */
         function userWindow() {
             var url="${webRoot}/act/model/userAreaSelect";
             //弹框层
             layer.open({
                 scrollbar: false,
                 type: 2,
                 title : ["审批者选择范围" , true],
                 area: ['90%', '90%'], //宽高
                 content: [url,'no'],
                 shadeClose : false,
             });
         }

         /**
		  * 删除审批人
          */
         function delUser(_this) {
			$(_this).parent().parent().remove();
         }

         /**
		  * 保存节点信息
          */
         $("#submitBtn").click(function () {
             var url="${webRoot}/act/model/saveNode";
			 $.post(url,$("#flowSetForm").serialize(),function (result) {
                 if(result.code == '0'){
					toast(result.msg);
					var nodeSet = result.nodeSet;
					$("#flowSetForm input[name='id']").val(nodeSet.id);
                 }else {
                     alertMsg(result.msg);
                 }
             });
         });

         /**
		  * 条件限制　待完善
          */
         $("#flowSetForm select[name$='fieldName']").off().on("change",function () {

         });

	</script>
</html>