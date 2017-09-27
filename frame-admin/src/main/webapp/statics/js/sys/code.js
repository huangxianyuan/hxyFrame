

var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parentId",
            rootPId: -1,
            open:"open",
        },
        key: {
            url:"nourl"
        }
    },
    check:{
        //enable:true,
        nocheckInherit:true
    },
    callback: {
        onClick: zTreeOnClick
    }
};

function zTreeOnClick(event, treeId, treeNode) {
    var url="../sys/code/info/"+treeNode.id;
    $.get(url,function (result) {
        vm.code=result.code;
        vm.showInfo=true;
        vm.title='修改字典';
    })
};
var ztree;

/**
 * 初始化菜单
 */
function initTree() {
    //加载菜单树
    $.get("../sys/code/codeTree", function(r){
        ztree = $.fn.zTree.init($("#allCodeTree"), setting, r.codeTree);
        //展开所有节点
        //ztree.expandAll(true);
    });
}

var vm = new Vue({
    el:'#rrapp',
    data:{
        showInfo: false,
        title: null,
        code:{
            parentName:null,
            parentId:0,
            sort:'0',
            value:"",
            mark:"",
            name:"",
            remark:"",
            type:"1",
            open:"1"
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
            vm.code.parentId=nodes[0].id;
            vm.code.name="";
            vm.code.remark="";
            vm.code.mark="";
            vm.code.type="1",
            vm.code.parentName=nodes[0].name;
            vm.code.id = null;
        },
        del: function (event) {
            var nodes=ztree.getSelectedNodes();
            if(nodes.length<=0){
                alertMsg("请选择要删除的字典");
            }
            confirm('确定要删除'+nodes[0].name+'？', function(){
                var url="../sys/code/delete";
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
            var url = vm.code.id == null ? "../sys/code/save" : "../sys/code/update";
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.code),
                success: function(r){
                    if(r.code == 0){
                        var codeInfo=r.codeInfo;
                        var nodes = ztree.getSelectedNodes();
                        if(vm.code.id==null || vm.code.id==''){
                            toast(r.msg,function(){
                                ztree.addNodes(nodes[0],{id:codeInfo.id,parentId:codeInfo.parentId,name:codeInfo.name});
                            });
                        }else{
                            toast(r.msg,function(){
                                nodes[0].name=codeInfo.name;
                                ztree.updateNode(nodes[0]);
                            });
                        }
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        reload: function (event) {
            $("#codeFrom")[0].reset();
        }
    }
});