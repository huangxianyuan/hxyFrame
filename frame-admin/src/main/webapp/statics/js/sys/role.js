$(function () {
    $("#jqGrid").jqGrid({
        url: '../sys/role/list',
        datatype: "json",
        colModel: [			
			{ label: '角色名称', name: 'name', index: "name", width: 75 },
            { label: '状态', name: 'status', width: 80, formatter: function(value, options, row){
                return value == 0 ?
                    '<span class="label label-success">正常</span>' :
                    '<span class="label label-danger">禁用</span>';
            }},
			{ label: '备注', name: 'remark', width: 100 },
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
	},
	check:{
		enable:true,
		nocheckInherit:true
	}
};
var ztree;
var ztree2;

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			name: null
		},
		showList: true,
		title:null,
		role:{}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.role = {};
			vm.getMenuTree(null);
		},
		update: function () {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			
			vm.showList = false;
            vm.title = "修改";
            vm.getMenuTree(id);
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要禁用选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../sys/role/delete",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
                            toast(r.msg,function(){
                                vm.reload();
                            });
						}else{
                            alertMsg(r.msg);
						}
					}
				});
			});
		},
        enabled: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}

			confirm('确定要启用选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../sys/role/enabled",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
                            toast(r.msg,function(){
                                vm.reload();
                            });
						}else{
                            alertMsg(r.msg);
						}
					}
				});
			});
		},
		getRole: function(id){
            $.get("../sys/role/info/"+id, function(r){
            	vm.role = r.role;
                //勾选角色所拥有的菜单
    			var menuIds = vm.role.menuIdList;
    			for(var i=0; i<menuIds.length; i++) {
    				var node = ztree.getNodeByParam("id", menuIds[i]);
    				ztree.checkNode(node, true, false);
    			}
                //勾选角色所在的组织机构
                var organIds = vm.role.organIdList;
                for(var i=0; i<organIds.length; i++) {
                    var node = ztree2.getNodeByParam("id", organIds[i]);
                    ztree2.checkNode(node, true, false);
                }
    		});
		},
		saveOrUpdate: function (event) {
			//获取选择的菜单
			var nodes = ztree.getCheckedNodes(true);
			var menuIdList = new Array();
			for(var i=0; i<nodes.length; i++) {
				menuIdList.push(nodes[i].id);
			}
			vm.role.menuIdList = menuIdList;
            //获取选择的组织
            var organNodes = ztree2.getChangeCheckedNodes(true);
			var organIdList = new Array();
			for (var i=0; i<organNodes.length;i++){
				var status = organNodes[i].getCheckStatus();
				if(status.half){
				    continue;
                }
				organIdList.push(organNodes[i].id);
			}
            vm.role.organIdList=organIdList;
			var url = vm.role.id == null ? "../sys/role/save" : "../sys/role/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.role),
			    success: function(r){
                    if(r.code == 0){
                        if(vm.role.id==null){
                            alert(r,function (index,layer) {
                                vm.reload();
                            },function (index) {
                                vm.role.name='';
                                vm.role.remark='';
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
		getMenuTree: function(id) {
			//加载菜单树
			$.get("../sys/menu/perms", function(r){
				ztree = $.fn.zTree.init($("#menuTree"), setting, r.menuList);
				//展开所有节点
				//ztree.expandAll(true);
				if(id != null){
					vm.getRole(id);
				}
			});
            //加载资源树
            $.get("../sys/organ/organTree", function(r){
                ztree2 = $.fn.zTree.init($("#organTree"), setting, r.organTree);
                //展开所有节点
                //ztree.expandAll(true);
            });
	    },
	    reload: function (event) {
	    	vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'name': vm.q.name},
                page:page
            }).trigger("reloadGrid");
		}
	}
});