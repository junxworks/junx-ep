layui.use(['form'], function () {
	var form = layui.form;
	var id = getParam("id");
    if(!isNull(id)){
    	io.get('/ep/sys/dictionaries/' + id,function(result){
            var data = result.data;
            var form = layui.form;
            //表单初始赋值
            form.val('dictForm', data);
    	});
    }
    form.on('submit(save)', function (form) {
        var data = form.field;
        io.post('/ep/sys/dictionaries',JSON.stringify(data),function(res){
        	 //关闭当前页
            setTimeout(function(){return closePage();},1000);
            //刷新父页面列表
            window.parent.refresh();
        });
        return false;
    });
});
