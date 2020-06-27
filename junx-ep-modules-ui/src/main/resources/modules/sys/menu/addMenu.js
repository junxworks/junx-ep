layui.use(['tree', 'util', 'layer','form'], function () {
    var form = layui.form;
    form.render();
    var tree = layui.tree;
    var id = getParam("id");
    if (id != null && typeof (id) != "undefined" && id != "undefined") {
    	io.get('/ep/sys/menus/'+id,function(res){
    		var data = res.data ;
            if(data){
                var form = layui.form;
                //表单初始赋值
                form.val('menuForm', data);
                changeType();
            }
    	});
    }else {
        var form = layui.form;
        //表单初始赋值
        form.val('menuForm', {
            "sort": "100" // "name": "value"
            ,"type":"0"
            ,"isParent":"0"
        })
        changeType();
    }
    //初始化菜单树
    $.ajax({
        url: appendCtx('/ep/sys/menus/all-menus'),
        type: "get",
        success: function (result) {
            if (result.code == 0) {
                var menuList = result.data;
                menuList.push({
                    checked:false,
                    id:0,
                    disabled:false,
                    parentId: 0,
                    sort: "1",
                    spread: false,
                    title: "根目录"
                });
                //开启复选框
                tree.render({
                    elem: '#menuTree'
                    , data: menuList
                    , id: 'menuTree'
                    , showCheckbox: false
                    , onlyIconControl: true
                    ,click: function(obj){
                        var data = obj.data;  //获取当前点击的节点数据
                        $("#parentId").val(data.id);
                        $("#parentName").val(data.title);
                        //$("#Tree").hide();
                    }
                });
            } else {
            	layer.alert(result.msg, {icon: 2});
            }

        },
        error: function (data) {
        	layer.alert(data.responseText, {icon: 2});
        }
    });
    //监听提交
    form.on('submit(save)', function (data) {
        var reqData = $('#menuForm').serializeObject();
        $.ajax({
            url: appendCtx('/ep/sys/menus'),
            type: "post",
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(reqData),
            beforeSend: function(XMLHttpRequest){
                layer.msg('正在处理', { icon: 16,time: 1200000,shade: [0.3,'#000']});//背景虚化
            },
            success: function (result) {
                if(result.code == 0){
                    //提示信息
                    layer.msg('保存成功',{
                        zIndex:layer.zIndex
                    });
                    //关闭当前页
                    setTimeout(function(){return closePage();},500);
                    //刷新列表
                    window.parent.renderTable();
                }else{
                	layer.alert(result.msg, {icon: 2});
                }

            },
            error: function (data) {
            	layer.alert(data.responseText, {icon: 2});
            }
        });
        return false;
    });
    
    function changeType(){
    	recover();
    	var type=$("#type").val();
    	if(type==0){
        	$('#marki').hide();
        	$('#mark').val("");
    	}else if(type==1){
        	$('#urli').hide();
        	$('#url').val("");
    	}else{
    		$('#marki').hide();
    		$('#urli').hide();
        	$('#mark').val("");
        	$('#url').val("");
    	}
    }
    
    function recover(){
    	$('#marki').show();
    	$('#urli').show();
    }
    
    form.on('select(type)', changeType);
    
});

function closePage(){
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}

function openTree(){
    $("#Tree").show();
}
$.fn.serializeObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            o[this.name] = o[this.name] + ',' + this.value || '';
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
}