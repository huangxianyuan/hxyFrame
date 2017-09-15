$(function () {
    $("#jqGrid").jqGrid({
        url: '../sys/user/list',
        datatype: "json",
        colModel: [			
			{ label: '用户名', name: 'userName', width: 75 },
			{ label: '登陆名', name: 'loginName', width: 75 },
			{ label: '部门', name: 'baName', width: 75 },
			{ label: '机构', name: 'bapName', width: 75 },
			{ label: '邮箱', name: 'email', width: 90 },
			{ label: '手机号', name: 'phone', width: 100 },
			{ label: '状态', name: 'status', width: 80, formatter: function(value, options, row){
				return value == 0 ?
					'<span class="label label-success">正常</span>' :
					'<span class="label label-danger">禁用</span>';
			}},
			{ label: '创建时间', name: 'createTime', index: "create_time", width: 80}
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var organTree ="";
var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};

/**
 * 初始化机构树
 */
function initTree() {
    $.get("../sys/organ/organTree", function(r){
        //初始化zTree，三个参数一次分别是容器(zTree 的容器 className 别忘了设置为 "ztree")、参数配置、数据源
        organTree = $.fn.zTree.init($("#organTree"), setting, r.organTree);
        var node = organTree.getNodeByParam("id", vm.user.baid);//得到当前上级菜单节点
        organTree.selectNode(node);//选中新增加的节点
        //vm.menu.parentName = node.name;
    })
}
var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			userName: null
		},
		showList: true,
		title:null,
		roleList:{},
		user:{
			status:1,
			baid:'',
			baName:'',
			roleIdList:[]
		}
	},
    created:function(){
        initTree();
    },
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.roleList = {};
			vm.user = {status:1,roleIdList:[],baid:'',baName:''};
			//获取角色信息
			this.getRoleList();
		},
		update: function () {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
			vm.getUser(id);
			//获取角色信息
			this.getRoleList();
		},
		del: function () {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../sys/user/delete",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							toast(r, function(index){
                                vm.reload();
							});
						}else{
                            alertMsg(r.msg);
						}
					}
				});
			});
		},
        enabled: function () {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			confirm('确定要启用选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../sys/user/enabled",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
                            toast(r, function(index){
                                vm.reload();
							});
						}else{
                            alertMsg(r.msg);
						}
					}
				});
			});
		},
        reset: function () {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			confirm('确定要重置选中的用户密码？', function(){
				$.ajax({
					type: "POST",
				    url: "../sys/user/reset",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
                            toast(r, function(index){
                                vm.reload();
							});
						}else{
                            alertMsg(r.msg);
						}
					}
				});
			});
		},
		saveOrUpdate: function (event) {
			var url = vm.user.id == null ? "../sys/user/save" : "../sys/user/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.user),
			    success: function(r){
                    if(r.code == 0){
                        if(vm.user.id==null){
                            toast(r,function (index,layer) {
                                vm.reload();
                            },function (index) {
                                vm.menu.sort=1;
                            })
                        }else{
                            toast(r.msg,function(){
                                vm.reload();
                            });
                        }

                    }else{
                        alertMsg(r.msg);
                    }
				}
			});
		},
		getUser: function(id){
			$.get("../sys/user/info/"+id, function(r){
				vm.user = r.user;
			});
		},
		getRoleList: function(){
			$.get("../sys/role/select", function(r){
				vm.roleList = r.list;
			});
		},
        organTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择机构",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: $("#menuLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = organTree.getSelectedNodes();
                    //选择上级菜单
                    vm.user.baid = node[0].id;
                    vm.user.baName = node[0].name;
                    layer.close(index);
                }
            });
        },
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'userName': vm.q.userName},
                page:page
            }).trigger("reloadGrid");
		}
	}
});