

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

var setting2 = {
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
    	//复选框
        //enable:true,
        nocheckInherit:true
    },
    callback: {
        onClick: zTreeOnClick
    }
};

function zTreeOnClick(event, treeId, treeNode) {
    var url="../sys/menu/info/"+treeNode.id;
    $.get(url,function (result) {
		vm.menu=result.menu;
		vm.showInfo=true;
		vm.title='修改菜单';
    })
};
var ztree;
var ztree2;

/**
 * 初始化菜单
 */
function initTree() {
    //加载菜单树
    $.get("../sys/menu/perms", function(r){
        ztree2 = $.fn.zTree.init($("#allMenuTree"), setting2, r.menuList);
        //展开所有节点
        //ztree2.expandAll(true);
    });

    $.get("../sys/menu/selectMenu", function(r){
        //初始化zTree，三个参数一次分别是容器(zTree 的容器 className 别忘了设置为 "ztree")、参数配置、数据源
        ztree = $.fn.zTree.init($("#menuTree"), setting, r.menuEntities);
        var node = ztree.getNodeByParam("id", vm.menu.parentId);//得到当前上级菜单节点
        ztree.selectNode(node);//选中新增加的节点
        //vm.menu.parentName = node.name;
    })
}

var vm = new Vue({
	el:'#rrapp',
	data:{
        showInfo: false,
		title: null,
		menu:{
			parentName:null,
			parentId:0,
			type:1,
			sort:0,
            icon:"",
			url:"",
            permission:""
		},
	},
    created:function(){
        initTree();
    },
	methods: {
		getMenu: function(menuId){
			//加载菜单树
			$.get("../sys/menu/selectMenu", function(r){
                //初始化zTree，三个参数一次分别是容器(zTree 的容器 className 别忘了设置为 "ztree")、参数配置、数据源
				ztree = $.fn.zTree.init($("#menuTree"), setting, r.menuEntities);
				var node = ztree.getNodeByParam("id", vm.menu.parentId);//得到当前上级菜单节点
                ztree.selectNode(node);//选中新增加的节点
				//vm.menu.parentName = node.name;
			})
		},
		add: function(){
            var nodes=ztree2.getSelectedNodes();
            if(nodes.length<=0){
            	alertMsg("请选择父级菜单");
            	return
			}
			vm.showInfo = true;
			vm.title = "新增菜单";
            vm.menu.type='1';
            vm.menu.parentId=nodes[0].id;
            vm.menu.permission="";
            vm.menu.url="";
            vm.menu.name="";
            vm.menu.parentName=nodes[0].name;
            vm.menu.id=null;
		},
		del: function (event) {
            var nodes=ztree2.getSelectedNodes();
            if(nodes.length<=0){
                alertMsg("请选择要删除的菜单");
            }
            confirm('确定要删除菜单【'+nodes[0].name+'】,及下面的子菜单么？', function(){
				var url="../sys/menu/delete";
                var childs=nodes[0].id;
                childs=getAllNodes(nodes[0],childs);
                $.post(url,JSON.stringify(childs),function (r) {
                    if(r.code == 0){
					   toast(r.msg,function(){
						   ztree2.removeNode(nodes[0]);
					   });
					}else{
                        alertMsg(r.msg);
					}
                });
			});
		},
		saveOrUpdate: function (event) {
			var url = vm.menu.id == null ? "../sys/menu/save" : "../sys/menu/update";
            vm.menu.icon=$("#icon").val();
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.menu),
			    success: function(r){
					if(r.code == 0){
						var menu=r.menu;
                        var nodes = ztree2.getSelectedNodes();
                        if(vm.menu.id==null){
                            toast(r.msg,function(){
                                ztree2.addNodes(nodes[0],{id:menu.id,parentId:menu.parentId,name:menu.name});
                            });
                        }else{
                            toast(r.msg,function(){
                            	nodes[0].name=menu.name;
                            	ztree2.updateNode(nodes[0]);
                            });
                        }
					}else{
                        alertMsg(r.msg);
					}
				}
			});
		},
		menuTree: function(){
			layer.open({
				type: 1,
				offset: '50px',
				skin: 'layui-layer-molv',
				title: "选择菜单",
				area: ['300px', '450px'],
				shade: 0,
				shadeClose: false,
				content: jQuery("#menuLayer"),
				btn: ['确定', '取消'],
				btn1: function (index) {
					var node = ztree.getSelectedNodes();
					//选择上级菜单
					vm.menu.parentId = node[0].id;
					vm.menu.parentName = node[0].name;
					layer.close(index);
	            }
			});
		},
        reload: function (event) {
            $("#menuFrom")[0].reset();
        },
        menuIcons:function () {
			var url="../sys/menuIcons.html";
            layer.open({
                type: 2,
                skin: 'layui-layer-molv',
                title: "选择菜单图片",
                area: ['90%', '70%'],
                shade: 0,
                shadeClose: false,
                content: [url,'no'],

            });
        }
	}
});