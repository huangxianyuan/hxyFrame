
var vm = new Vue({
	el:'#main-container',
	data:{
        page:{
            pageNum:1,
            pageSize:'',
            pages:'',
            endRow:'',
            first:'',
            funcName:'',
            funcParam:'',
            length:'',
            next:'',
			result:{
                id:'',
                content:'',
                createId:'',
                createTime:'',
                keyWords:'',
                title:'',
                updateId:'',
                updateTime:'',
			}
		}
	},
	methods: {
		query: function () {
            var pageNum = 1;
            var keyWords = $("#keyWords").val();
            if(keyWords==''){
                location.replace(location.href);
                return;
            }
            var url = '../demo/article/search/'+pageNum+"?keyWords="+keyWords;
            $.get(url,function (r) {
                console.log(r);
                if(r.code=='0'){
                    vm.page =r.page.pages;
                }else {
                    alertMsg(r.msg);
                }
            });
		}
	}
});