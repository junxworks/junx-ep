
layui.use(['form'], function () {
    var form = layui.form;
    var jobId = getParam("id");
    if(!isNull(jobId)){
        $.ajax({
            url: appendCtx('/ep/sys/schedule/jobs/' + jobId),
            type: "get",
            success: function (res) {
                if(res.ok){
                    var data = res.data;
                    var form = layui.form;
                    //表单初始赋值
                    form.val('jobForm', data)
                }else{
                	layer.alert(res.msg, {icon: 2});
                }
            },
            error: function (data) {
            	layer.alert(data.responseText, {icon: 2});
            }
        });
    }
    //监听提交
    form.on('submit(jobForm)', function (data) {
    	var id=data.field.jobId;
    	var method="put";
    	if(isNull(id)){
    		method="post";
    	}
        $.ajax({
            url: appendCtx('/ep/sys/schedule/jobs'),
            type: method,
            dataType: "JSON",
			contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(data.field),
            beforeSend: function () {
                layer.msg('保存中', {icon: 16,shade: 0.01});
            },
            success: function (result) {
                if(result.code == 0){
                    //提示信息
                    layer.msg('保存成功',{
                        zIndex:layer.zIndex
                    });
                    //关闭当前页
                    setTimeout(function(){return closePage();},1000);
                    //刷新列表
                    window.parent.search();
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
});

function closePage(){
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}
