layui.use(['form'], function () {
    var form = layui.form;
    var jobId = getParam("id");
    if(!isNull(jobId)){
    	io.get('/ep/sys/schedule/jobs/' + jobId,function(res){
    		var data = res.data;
            var form = layui.form;
            //表单初始赋值
            form.val('jobForm', data)
    	});
    }
    //监听提交
    form.on('submit(save)', function (form) {
		io.post('/ep/sys/schedule/jobs',JSON.stringify(form.field),function(res){
	        setTimeout(function(){return closePage();},1000);
	        window.parent.search();
		});
		return false;
    });
});
