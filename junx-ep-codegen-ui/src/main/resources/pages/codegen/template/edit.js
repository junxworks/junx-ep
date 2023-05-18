layui.use(['form'], function () {
    var form = layui.form;
    var id = getParam("id");
    if(!isNull(id)){
    	io.get('/ep/codegen/templates/' + id,function(res){
    		var data = res.data;
            var form = layui.form;
            //表单初始赋值
            form.val('lForm', data)
    	});
    }
    //监听提交
    form.on('submit(save)', function (form) {
		io.post('/ep/codegen/templates',JSON.stringify(form.field),function(res){
	        setTimeout(function(){return closePage();},1000);
	        window.parent.refreshTableData();
		});
		return false;
    });
});
