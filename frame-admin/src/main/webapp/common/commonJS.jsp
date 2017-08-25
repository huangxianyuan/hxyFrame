<script src="${webRoot}/statics/js/jquery-2.0.3.min.js"></script>
<script src="${webRoot}/statics/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
    window.jQuery || document.write("<script src='${webRoot}/statics/js/jquery-2.0.3.min.js'>" + "<" + "script>");
</script>
<script type="text/javascript">
    window.jQuery || document.write("<script src='${webRoot}/statics/js/jquery-1.10.2.min.js'>" + "<" + "script>");
</script>
<script type="text/javascript">
    if ("ontouchend" in document) document.write("<script src='${webRoot}/statics/js/jquery.mobile.custom.min.js'>" + "<" + "script>");
</script>
<script>
	var webRoot  =  "${webRoot}";
</script>
<script src="${webRoot}/statics/js/html5shiv.js"></script>
<script src="${webRoot}/statics/js/respond.min.js"></script>
<script src="${webRoot}/statics/js/typeahead-bs2.min.js"></script><%--自动完成 AutoComplete--%>
<script type="text/javascript" src="${webRoot}/statics/js/chosen.jquery.min.js"></script> <%--标签进行级联--%>
<script type="text/javascript" src="${webRoot}/statics/js/select2.min.js"></script> <%--下框插件--%>
<script type="text/javascript" src="${webRoot}/statics/plugins/layer/layer.js"></script>
<script type="text/javascript" src="${webRoot}/statics/plugins/layui/layui.js"></script>
<script type="text/javascript" src="${webRoot}/statics/plugins/layui/lay/modules/form.js"></script>
<script type="text/javascript" src="${webRoot}/statics/plugins/layui/lay/modules/element.js"></script>
<script type="text/javascript" src="${webRoot}/statics/plugins/layer/mylayer.js"></script>
<script type="text/javascript" src="${webRoot}/statics/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${webRoot}/statics/plugins/validator/js/bootstrapValidator.js"></script> <%--前端验证bootstrapValidator--%>
<script type="text/javascript" src="${webRoot}/statics/plugins/ztree/jquery.ztree.all.min.js"></script><%--ztree树结构--%>
<script type="text/javascript" src="${webRoot}/statics/plugins/validform/js/Validform_v5.3.2.js"></script> <%--前端表单验证validform--%>
<script>

    function updateActionIcons(table) {
        var replacement =
        {
            'ui-icon-pencil': 'icon-pencil blue',
            'ui-icon-trash': 'icon-trash red',
            'ui-icon-disk': 'icon-ok green',
            'ui-icon-cancel': 'icon-remove red'
        };
        $(table).find('.ui-pg-div span.ui-icon').each(function () {
            var icon = $(this);
            var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
            if ($class in replacement) icon.attr('class', 'ui-icon ' + replacement[$class]);
        })
    }

    function updatePagerIcons(table) {
        var replacement =
        {
            'ui-icon-seek-first': 'icon-double-angle-left bigger-140',
            'ui-icon-seek-prev': 'icon-angle-left bigger-140',
            'ui-icon-seek-next': 'icon-angle-right bigger-140',
            'ui-icon-seek-end': 'icon-double-angle-right bigger-140'
        };
        $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function () {
            var icon = $(this);
            var $class = $.trim(icon.attr('class').replace('ui-icon', ''));

            if ($class in replacement) icon.attr('class', 'ui-icon ' + replacement[$class]);
        })
    }

    function enableTooltips(table) {
        $('.navtable .ui-pg-button').tooltip({container: 'body'});
        $(table).find('.ui-pg-div').tooltip({container: 'body'});
    }
    
    $(function() {
		$("form").prop("autocomplete", "off");
		$("input[type='text']").prop("autocomplete", "off");
		//console.log(navigator.userAgent.toLowerCase().indexOf("chrome")+":"+$("input:password").size());
		if (navigator.userAgent.toLowerCase().indexOf("chrome") != -1){
			var pwd = $("input:password");
			if (pwd.size() > 0) {
				pwd.prop("disabled", true);
				setTimeout(function(){
					pwd.prop("disabled", false);
				},400);
			}
		}
	});
    
	var goBack = function() {
		$.get("${webRoot}/get/referer.do", function(res) {
			if (res == "") {
				history.back();
			} else {
				location.href = res;
			}
		});
	}
	function DatePicker(beginSelector){
		// 仅选择日期
        $(beginSelector).datepicker(
            {
                language:'zh-CN',
                autoclose: true,
                startView: 0,
                format: 'yyyy-mm-dd',
                clearBtn:true,
                todayBtn:true,
                endDate:new Date()
            }).on('changeDate', function(ev){
            if(ev.date){
                $(endSelector).datepicker('setStartDate', new Date(ev.date.valueOf()))
            }else{
                $(endSelector).datepicker('setStartDate',null);
            }
        })
	}
	function DateTimePicker(beginSelector,endSelector){
		// 仅选择日期
        $(beginSelector).datetimepicker(
            {
                language:'zh-CN',
                autoclose: true,
                startView: 2,
                format: "yyyy-mm-dd hh:ii",
                clearBtn:true,
                todayBtn:true,
                endDate:new Date()
            }).on('changeDate', function(ev){
            if(ev.date){
                $(endSelector).datetimepicker('setStartDate', new Date(ev.date.valueOf()))
            }else{
                $(endSelector).datetimepicker('setStartDate',null);
            }
        })
        $(endSelector).datetimepicker(
            {
                language:'zh-CN',
                autoclose: true,
                startView: 2,
                format: "yyyy-mm-dd hh:ii",
                clearBtn:true,
                todayBtn:true,
                endDate:new Date()
            }).on('changeDate', function(ev){
            if(ev.date){
                $(beginSelector).datetimepicker('setEndDate', new Date(ev.date.valueOf()))
            }else{
                $(beginSelector).datetimepicker('setEndDate',new Date());
            }
        })
    }
	
	
	function DatePicker(beginSelector,endSelector){
		// 仅选择日期
        $(beginSelector).datepicker(
            {
                language:'cn',
                autoclose: true,
                startView: 0,
                format: 'yyyy-mm-dd',
                clearBtn:true,
                todayBtn:true,
                endDate:new Date()
            }).on('changeDate', function(ev){
            if(ev.date){
                $(endSelector).datepicker('setStartDate', new Date(ev.date.valueOf()))
            }else{
                $(endSelector).datepicker('setStartDate',null);
            }
        })
        $(endSelector).datepicker(
            {
                language:'cn',
                autoclose: true,
                startView: 0,
                format: 'yyyy-mm-dd',
                clearBtn:true,
                todayBtn:true,
                linked:true,
                endDate:new Date()
            }).on('changeDate', function(ev){
            if(ev.date){
                $(beginSelector).datepicker('setEndDate', new Date(ev.date.valueOf()))
            }else{
                $(beginSelector).datepicker('setEndDate',new Date());
            }
        })
    }
	
	function SaleDatePicker(beginSelector,endSelector){
		// 仅选择日期
        $(beginSelector).datepicker({
                language:'cn',
                autoclose: true,
                startView: 0,
                format: 'yyyy-mm-dd',
                clearBtn:true,
                todayBtn:true,
                startDate:Date()
            }).on('changeDate', function(ev){
            if(ev.date){
                $(endSelector).datepicker('setStartDate', new Date(ev.date.valueOf()))
            }else{
                $(endSelector).datepicker('setStartDate',null);
            }
        })
        $(endSelector).datepicker({
                language:'cn',
                autoclose: true,
                startView: 0,
                clearBtn:true,
                format: 'yyyy-mm-dd',
                clearBtn:true
            }).on('changeDate', function(ev){
            if(ev.date){
                $(beginSelector).datepicker('setEndDate', new Date(ev.date.valueOf()))
            }else{
                $(beginSelector).datepicker('setEndDate',new Date());
            }
        })
    }
	//不限制的时间
	function DatePickerNotRule(beginSelector,endSelector){
		// 仅选择日期
        $(beginSelector).datepicker({
                language:'cn',
                autoclose: true,
                startView: 0,
                format: 'yyyy-mm-dd',
                clearBtn:true,
                todayBtn:true,
                linked:true,
                endDate: $(endSelector).val()
            }).on('changeDate', function(ev){
            if(ev.date){
                $(endSelector).datepicker('setStartDate', new Date(ev.date.valueOf()))
            }else{
                $(endSelector).datepicker('setStartDate',null);
            }
        })
        $(endSelector).datepicker({
                language:'cn',
                autoclose: true,
                startView: 0,
                format: 'yyyy-mm-dd',
                clearBtn:true,
                todayBtn:true,
                linked:true,
                startDate: $(beginSelector).val()
            }).on('changeDate', function(ev){
            if(ev.date){
                $(beginSelector).datepicker('setEndDate', new Date(ev.date.valueOf()))
            }else{
                $(beginSelector).datepicker('setEndDate',new Date());
            }
        })
    }

    //JS动态设置select 后 触发 onchange事件
    //Chrome , Firfox 不支持fireEvent的方法
    //可以使用dispatchEvent的方法替代
    function simulateClick(el) {
        if(el){
            var evt;
            if (document.createEvent) { // DOM Level 2 standard
                evt = document.createEvent("MouseEvents");
                evt.initMouseEvent("change", true, true, window,
                    0, 0, 0, 0, 0, false, false, false, false, 0, null);
                el.dispatchEvent(evt);
            } else if (el.fireEvent) { // IE
                el.fireEvent('onchange');
            }
        }
    }

    /**
     *  获取zTree 节点下的所有叶子节点
     * @param treeNode
     * @param childs
     */
    function getAllChildNodes(treeNode, childs) {
        if(treeNode.isParent){
            var childNodes = treeNode.children;
            if(childNodes){
                for (var i=0;i<childNodes.length;i++){
                    childs+=childNodes[i].id+",";
                    if(childNodes[i].isParent){
                        childs=getAllChildNodes(childNodes[i],childs);
                    }
                }
            }
        }
        return childs;
    }

    /**
     *  获取zTree 节点下的所有节点
     * @param treeNode
     * @param childs
     */
    function getAllNodes(treeNode, childs) {
        var childNodes = treeNode.children;
        if(childNodes){
            for (var i=0;i<childNodes.length;i++){
                childs+="," + childNodes[i].id;
                if(childNodes){
                    childs=getAllNodes(childNodes[i],childs);
                }
            }
        }
        return childs;
    }

    /***
     * 判断 一个字符串是否 在 字符串数组中
     * @param search 一个字符串
     * @param array 字符串数组
     * @returns
     */
    function in_array(search,array){
        for(var i in array){
            if(array[i]==search){
                return true;
            }
        }
        return false;
    }

    /**
     * 根据Json字符串填充表单
     * @param form 表单id
     * @param data json数据对象
     */
    function formload(form,data){
        for(var name in data){
            var val=data[name];
            //var rr=$("input[name=\""+name+"\"][type=radio], input[name=\""+name+"\"][type=checkbox]",form);
            var rr=$("#"+form+" input[name='"+name+"'][type=checkbox],input[name='"+name+"'][type=radio]",);
            $.fn.prop?rr.prop("checked",false):rr.attr("checked",false);
            rr.each(function(){
                var f=$(this);
                var valarray =String(val).split(",");
                if(in_array(f.val(),valarray)){
                    $.fn.prop?f.prop("checked",true):f.attr("checked",true);
                }
            });
            if(!rr.length){
                $("#"+form+" input[name=\""+name+"\"]").val(val);
                $("#"+form+" textarea[name=\""+name+"\"]").val(val);
                simulateClick($("#"+form+" select[name=\""+name+"\"]").val(val)[0]);
            }
        }
    }

    //初始化表单验证结果 --删除表单的验证信息
    function initValidForm(obj){
        obj.find("span.Validform_checktip").empty().removeClass("Validform_wrong Validform_right");//移除表单中验证信息
        obj.find("select,input,textarea").removeClass("Validform_error");//移除表单中验证信息
    }

    //验证div中 是否 满足 验证条件
    //参数：某个表单对象、验证成功后执行的函数
    function checkFormValid(formObj,fun){
        var childSaveBtn=formObj.find("#child_SaveBtn");//查找form下的id="child_SaveBtn";
        if(childSaveBtn==null||childSaveBtn.length==0){
            formObj.append('<input type="button" id="child_SaveBtn" style="display:none;"/>');//为form元素结尾（仍然在内部）插入按钮
            childSaveBtn=formObj.find("#child_SaveBtn");
        }
        formObj.Validform({
            btnSubmit:"#child_SaveBtn",
            tiptype:function(msg,o,cssctl){
                o.obj.parent().find(".Validform_checktip").remove();
                o.obj.parent().append("<span class='Validform_checktip' />");
                var objtip=o.obj.parent().find(".Validform_checktip");
                cssctl(objtip,o.type);
                objtip.text(msg);
                //msg：提示信息;
                //o:{obj:*,type:*,curform:*}, obj指向的是当前验证的表单元素（或表单对象），type指示提示的状态，值为1、2、3、4， 1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态, curform为当前form对象;

                //cssctl:内置的提示信息样式控制函数，该函数需传入两个参数：显示提示信息的对象 和 当前提示的状态（既形参o中的type）;
                /*if(!o.obj.is("form")){//验证表单元素时o.obj为该表单元素，全部验证通过提交表单时o.obj为该表单对象;
                 var oobjtip=o.obj.siblings(".Validform_checktip");
                 cssctl(objtip,o.type);
                 objtip.text(msg);
                 }  */
            },
            showAllError:false,//true：提交表单时所有错误提示信息都会显示，false：一碰到验证不通过的就停止检测后面的元素，只显示该元素的错误信息;
            beforeSubmit:function(curform){
                //在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
                //这里明确return false的话表单将不会提交;
                fun();
                return false;
            }
        });
        childSaveBtn.click();
    }
    

</script>

