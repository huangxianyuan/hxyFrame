var $layer = parent.layer;
/**
 * 新增 提示消息
 * layer.alert 弹窗
 * btn: ["返回列表","继续添加"]
 * @param option 显示的内容
 * @param yesCallback		点击[返回列表]是否需要回调的方法
 * @param cancelCallback	点击[继续添加]的时候需要回调的方法
 */
window.alert = function(option, yesCallback, cancelCallback){
    if(typeof(option) == 'string'){
        $layer.alert(option, {
            title: "提示",
            shade: 0.6,
            //icon: 1,
            closeBtn: 0,
            btn: ["确定"],
            cancel: function(index){
                $layer.close(index);
                if(typeof cancelCallback == 'function'){
                    cancelCallback();
                }
            }
        });
    }else{
        var ops = $.extend({
            title: "提示",
            shade: 0.6,
            icon: 1,
            closeBtn: 0,
            btn: ["返回列表","继续添加"],
            yes: function(index, layero){
                $layer.close(index);
                if(typeof yesCallback == 'function'){
                    yesCallback();
                }
            },
            cancel: function(index){
                $layer.close(index);
                if(typeof cancelCallback == 'function'){
                    cancelCallback();
                }
            }
        },option);
        updateOptionIcon(option, ops);
        $layer.alert(option.msg, ops);
    }
}

/**
 * 弹出消息提示，点击确定执行函数
 * layer.alert 弹窗
 * btn: ["返回列表","继续添加"]
 * @param option 显示的内容
 * @param yesCallback		点击[返回列表]是否需要回调的方法
 */
window.alert = function(option, yesCallback){
    if(typeof(option) == 'string'){
        $layer.alert(option, {
            title: "提示",
            shade: 0.6,
            //icon: 1,
            closeBtn: 0,
            btn: ["确定"],
            cancel: function(index){
                $layer.close(index);
                if(typeof cancelCallback == 'function'){
                    cancelCallback();
                }
            }
        });
    }else{
        var ops = $.extend({
            title: "提示",
            shade: 0.6,
            icon: 1,
            closeBtn: 0,
            btn: ["确定"],
            yes: function(index, layero){
                $layer.close(index);
                if(typeof yesCallback == 'function'){
                    yesCallback();
                }
            }
        },option);
        updateOptionIcon(option, ops);
        $layer.alert(option.msg, ops);
    }
}


/**
 * 只提示消息用
 * @param content
 */
function alertMsg(content){
//	alert({msg:content,btn:["确定"]});
    $layer.alert(content,{
        title: "提示",
        shade: 0.6,
        icon: 2,
        closeBtn: 0,
        btn: ["确定"],
        yes: function(index, layero){
            $layer.close(index);
        }
    });
}

/**
 * 保存 提示消息
 * layer.msg 提示消息
 * @param option			提示消息内容
 * @param cancelCallback	2秒钟后的回调方法
 */
function toast(option,cancelCallback){
    if(typeof option == 'string'){
        $layer.msg(option,{icon: 1, shade:0.6, time: 1500 }, function(){
            if(typeof cancelCallback=='function'){
                cancelCallback();
            }
        });
    }else{
        var ops = $.extend({icon: 1, shade:0.6, time: 1500 },option);

        updateOptionIcon(option, ops);
        if(ops.icon==2){
            $layer.alert(option.msg,{
                title: "提示",
                shade: ops.shade,
                icon: ops.icon,
                closeBtn: 0,
                btn: ["确定"],
                yes: function(index, layero){
                    $layer.close(index);
                    if(typeof cancelCallback=='function'){
                        cancelCallback();
                    }
                }
            });
        }else{
            $layer.msg(option.msg,ops,function(){
                if(typeof cancelCallback=='function'){
                    cancelCallback();
                }
            });
        }
    }
}

/**
 * 删除 提示消息
 * layer.confirm 提示
 * @param option			需要提示的消息内容
 * @param yesCallback		点击确定的回调函数
 */
window.confirm = function(option, yesCallback, cancelCallback){
    if(typeof option == 'string'){
        $layer.confirm(option,{icon: 3,title:'提示'}, function(index){
            $layer.close(index);
            if(typeof yesCallback=='function'){
                yesCallback();
            }
        }, function(index){
            $layer.close(index);
            if(typeof cancelCallback=='function'){
                cancelCallback();
            }
        });
    }else{
        var ops = $.extend({icon: 3,title:'提示'}, option);

        updateOptionIcon(option, ops);
        $layer.confirm(option.msg, ops, function(index){
            $layer.close(index);
            if(typeof yesCallback=='function'){
                yesCallback();
            }
        }, function(index){
            $layer.close(index);
            if(typeof cancelCallback=='function'){
                cancelCallback();
            }
        });
    }
}

window.prompt = function(option, yesCallback){
    var ops = $.extend({formType: 2,value: '',title: '请输入'}, option);
    $layer.prompt(ops, function(value, index, elem){
        $layer.close(index);
        if(typeof yesCallback=='function'){
            yesCallback(value);
        }
    });
}

/**
 * 系统内部错误 提示消息
 * layer.alert提示
 * btn: ["取消"]
 * @param cancelCallback 点击[取消]回调函数
 */
function error(option, cancelCallback){
    if(typeof option == 'undefined'){
        $layer.alert("系统内部错误！",{
            title: "提示",
            shade: 0.6, //遮罩透明度
            icon: 2,
            closeBtn: 0,
            btn: ["取消"],
            yes: function(index){
                $layer.close(index);
                if(typeof cancelCallback=='function'){
                    cancelCallback();
                }
            }
        });
    }else{
        var ops = $.extend({
            title: "错误提示",
            shade: 0.6, //遮罩透明度
            icon: 2,
            closeBtn: 0,
            btn: ["取消"],
            yes: function(index){
                $layer.close(index);
                if(typeof cancelCallback=='function'){
                    cancelCallback();
                }
            }
        },option);

        if(option.code==403 || option.code=='403' ){
            $layer.alert(option.msg, ops);
        }else if(option.code== 504 || option.code=='504' ){
            $layer.alert(option.msg, ops);
            top.location = webRoot + "/login.do";
        }else{
            if(option.code == 1){
                $layer.alert(option.msg, ops);
            }else{
                $layer.alert("系统内部错误！", ops);
            }

        }
    }
}


/**
 * 判断是否传递参数icon
 * 没有传就根据option判断图标
 * option.code == 0 ? icon=1 : icon=2
 *  icon=1 操作成功图标 勾 icon=2操作失败图标 叉
 * @param option
 */
function updateOptionIcon(option,ops){
    if(typeof option.code != 'undefined'){
        if(option.code == 0){
            ops.icon = 1;
        }else{
            ops.icon = 2;
        }
    }
}

/**
 * 提交表单的时候的等待
 */
var load = function(option){
    var ops = $.extend({type:3,icon:1,shade:0.3},option);
    return $layer.load(0,ops);
}
/**
 * 根据load的index 关闭load
 */
var loadClose = function(loadIndex){
    return $layer.close(loadIndex);
}
/**
 * 关闭当前窗口
 */
var closeThisWindow= function () {
    var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
    parent.layer.close(index); //执行关闭
}


