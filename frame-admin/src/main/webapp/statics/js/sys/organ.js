

var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parentId",
            rootPId: -1,
        },
        key: {
            url:"nourl"
        }
    },
    check:{
        // enable:true,
        nocheckInherit:true
    },
    callback: {
        onClick: zTreeOnClick
    }
};

function zTreeOnClick(event, treeId, treeNode) {
    var url="../sys/organ/info/"+treeNode.id;
    $.get(url,function (result) {
        vm.organ=result.organ;
        vm.showInfo=true;
        vm.title='修改组织';
    })
};
var ztree;

/**
 * 初始化菜单
 */
function initTree() {
    //加载菜单树
    $.get("../sys/organ/organTree", function(r){
        ztree = $.fn.zTree.init($("#allorganTree"), setting, r.organTree);
        //展开所有节点
        // ztree.expandAll(true);
    });
}

var vm = new Vue({
    el:'#rrapp',
    data:{
        showInfo: false,
        title: null,
        organ:{
            parentName:null,
            parentId:0,
            sort:'0',
            code:"",
            name:"",
            remark:"",
            type:"1",
            open:"1",
            name:""
        },
    },
    created:function(){
        initTree();
    },
    methods: {
        add: function(){
            var nodes=ztree.getSelectedNodes();
            if(nodes.length<=0){
                alertMsg("请选择父级菜单");
                return
            }
            vm.showInfo = true;
            vm.title = "新增字典";
            vm.organ.parentId=nodes[0].id;
            vm.organ.name="";
            vm.organ.sort="";
            vm.organ.code="";
            vm.organ.remark="";
            vm.organ.type="1",
			vm.organ.parentName=nodes[0].name;
            vm.organ.id = null;
        },
        del: function (event) {
            var nodes=ztree.getSelectedNodes();
            if(nodes.length<=0){
                alertMsg("请选择要删除的组织");
            }
            confirm('确定要删除【'+nodes[0].name+'】，及下面的组织么？', function(){
                var url="../sys/organ/delete";
                var childs=nodes[0].id;
                childs=getAllNodes(nodes[0],childs);
                $.post(url,JSON.stringify(childs),function (r) {
                    if(r.code == 0){
                        toast(r.msg,function(){
                            ztree.removeNode(nodes[0]);
                        });
                    }else{
                        alert(r.msg);
                    }
                });
            });
        },
        saveOrUpdate: function (event) {
            var url = vm.organ.id == null ? "../sys/organ/save" : "../sys/organ/update";
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.organ),
                success: function(r){
                    if(r.code == '0'){
                        var organInfo=r.organInfo;
                        var nodes = ztree.getSelectedNodes();
                        var icon = "";
                        if(organInfo.type == 1){
                            //机构
                            icon='../statics/plugins/ztree/css/zTreeStyle/img/diy/8.png';
                        }
                        if(organInfo.type == 2){
                            //部门
                            icon='../statics/plugins/ztree/css/zTreeStyle/img/diy/2.png';
                        }
                        if(vm.organ.id==null || vm.organ.id==''){
                            toast(r.msg,function(){
                                ztree.addNodes(nodes[0],{id:organInfo.id,parentId:organInfo.parentId,name:organInfo.name,icon:icon});
                            });
                        }else{
                            toast(r.msg,function(){
                                nodes[0].name=organInfo.name;
                                nodes[0].icon=icon;
                                ztree.updateNode(nodes[0]);
                            });
                        }
                    }else{
                        alertMsg(r.msg);
                    }
                }
            });
        },
        reload: function (event) {
            $("#organFrom")[0].reset();
        }
    }
});