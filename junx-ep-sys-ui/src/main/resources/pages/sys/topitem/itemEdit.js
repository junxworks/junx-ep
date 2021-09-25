layui.use(['form'], function () {
	var form = layui.form;
	var id = getParam("id");
    if(!isNull(id)){
    	io.get('/ep/sys/top-items/' + id,function(result){
            var data = result.data;
            //表单初始赋值
            form.val('editForm', data);
    	});
    }
    form.on('submit(save)', function (form) {
        var data = form.field;
        io.post('/ep/sys/top-items',JSON.stringify(data),function(res){
        	 //关闭当前页
            setTimeout(function(){return closePage();},1000);
            //刷新父页面列表
            window.parent.refreshTableData();
        });
        return false;
    });
});
