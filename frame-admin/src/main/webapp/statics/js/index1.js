layui.config({
  base:'statics/js/'
}).use(['navtab'],function(){
	window.jQuery = window.$ = layui.jquery;
	window.layer = layui.layer;
    var element = layui.element(),
	navtab = layui.navtab({
		elem: '.larry-tab-box'
	});

    //iframe自适应
	$(window).on('resize', function() {
		var $content = $('#larry-tab .layui-tab-content');
		$content.height($(this).height() - 140);
	    $content.find('iframe').each(function() {
	    	$(this).height($content.height());
	    });
	}).resize();
  
	$(function(){
	    $('#larry-nav-side').click(function(){
	        if($(this).attr('lay-filter')!== undefined){
	            $(this).children('ul').find('li').each(function(){
	                var $this = $(this);
	                if($this.find('dl').length > 0){
	                   var $dd = $this.find('dd').each(function(){
	                       $(this).on('click', function() {
	                           var $a = $(this).children('a');
	                           var href = $a.data('url');
	                           var icon = $a.children('i:first').data('icon');
	                           var title = $a.children('span').text();
	                           var data = {
	                                 href: href,
	                                 icon: icon,
	                                 title: title
	                           }
	                           navtab.tabAdd(data);
	                       });
	                   });
	                }else{
	                	$this.on('click', function() {
                           var $a = $(this).children('a');
                           var href = $a.data('url');
                           var icon = $a.children('i:first').data('icon');
                           var title = $a.children('span').text();
                           var data = {
                                 href: href,
                                 icon: icon,
                                 title: title
                           }
                           navtab.tabAdd(data);
	                    });
	                }
	            });
	        }
	    }).trigger("click");
	});
});

layui.use(['jquery','layer','element'],function(){
	window.jQuery = window.$ = layui.jquery;
	window.layer = layui.layer;
    initMenu();
	// larry-side-menu向左折叠
	$('.larry-side-menu').click(function() {
	  var sideWidth = $('#larry-side').width();
	  if(sideWidth === 200) {
	      $('#larry-body').animate({
	        left: '0'
	      }); 
	      $('#larry-footer').animate({
	        left: '0'
	      });
	      $('#larry-side').animate({
	        width: '0'
	      });
	  } else {
	      $('#larry-body').animate({
	        left: '200px'
	      });
	      $('#larry-footer').animate({
	        left: '200px'
	      });
	      $('#larry-side').animate({
	        width: '200px'
	      });
	  }
	});
});


/**
 * 加载菜单树
 */
function initMenu() {
    var element = layui.element();
    var clickMenus = ("demo/leave/list");
    $.getJSON("sys/menu/userMenu?_"+$.now(), function(r){
        var menuList = r.menuList;
        var html="";
        for(var i=0;i<menuList.length;i++){
            var menu = menuList[i];
            html+='<li class="layui-nav-item">';
            if(menu.type=='0'){ //目录
                var icon = menu.icon;
                var name = menu.name;
                html+='<a href="javascript:;">';
                html+='    	<i class="'+icon+'"></i>';
                html+='    	<span>'+name+'</span>';
                html+='		<em class="layui-nav-more"></em>';
                html+='</a>';
                //子菜单
                var childList = menu.list;
                for (var j=0;j<childList.length;j++){
                    var childMenu=childList[j];
                    if(childMenu.type == '1'){
                        var icon = childMenu.icon;
                        var name = childMenu.name;
                        var url = childMenu.url;
                        //菜单
                        if(childMenu.type == '1'){
                            html+='<dl class="layui-nav-child">';
                            html+='	<dd>';
                            html+='		<a href="javascript:;" data-url="'+url+'">';
                            html+='			<i class="'+icon+'" data-icon="'+icon+'"></i>';
                            html+='			<span>'+name+'</span>';
                            html+='		</a>';
                            html+='	</dd>';
                            html+='</dl>';
                        }
                    }else if(childMenu.type == '1'){

					}
                }
            }
            if(menu.type == '1'){
                var icon = menu.icon;
                var name = menu.name;
                var url = menu.url;
                html+='<a href="javascript:;" data-url="'+url+'">';
                html+='	<i class="'+icon+'" data-icon="'+icon+'"></i>';
                html+='	<span>'+name+'</span>';
                html+='</a>';
            }
            html+='</li>';
        }
        $("#menuTree").append(html);
        element.init();  //初始化页面元素
    });
}

var vm = new Vue({
	el:'#layui_layout',
	data:{
		user:{},
		menuList:{},
        passWord:'',
        newPassWord:'',
        navTitle:"首页",
        myUpcomingCount:"",
        myNoticeCount:""
	},
	methods: {
		getUser: function(){
			$.getJSON("sys/user/info?_"+$.now(), function(r){
				vm.user = r.user;
				vm.myUpcomingCount=r.myUpcomingCount;
				vm.myNoticeCount=r.myNoticeCount;
			});
		},
        myUpcoming:function () {
            var $Upcoming = $("#menuTree a[data-url='act/deal/myUpcoming']");
            $Upcoming.parent().parent().parent().click();
            $Upcoming.parent().parent().parent().addClass("layui-nav-itemed");
            $Upcoming.click();
        },
        myNotice:function () {
            var $notice = $("#menuTree a[data-url='sys/notice/myList']");
            $notice.parent().parent().parent().click();
            $notice.parent().parent().parent().addClass("layui-nav-itemed");
            $notice.click();
        },
		updatePassword: function(){
			layer.open({
				type: 1,
				skin: 'layui-layer-molv',
				title: "修改密码",
				area: ['550px', '270px'],
				shadeClose: false,
				content: $("#passwordLayer"),
				btn: ['修改','取消'],
				btn1: function (index) {
					var data = "passWord="+vm.passWord+"&newPassWord="+vm.newPassWord;
					$.ajax({
						type: "POST",
					    url: "sys/user/updatePassword",
					    data: data,
					    dataType: "json",
					    success: function(result){
							if(result.code == 0){
                                layer.alert('修改成功', function(index){
                                    location.reload();
                                });
								layer.close(index);
							}else{
                                layer.alert(result.msg);
							}
						}
					});
	            }
			});
		}
	},
	created: function(){
		this.getUser();
	}
});
