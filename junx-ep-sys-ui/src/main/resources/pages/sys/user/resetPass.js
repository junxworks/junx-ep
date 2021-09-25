
$(function () {
    var userId = getParam("userId");
    if(userId != null && typeof(userId) != "undefined" && userId != "undefined"){
        $('#userId').val(userId);
    }
});

layui.use('layer', function () {

});

function resetPass(){
    var userId = $('#userId').val();
    var pass = $('#pass').val();
    if(pass == ''){
        layer.msg("密码不能为空");
        return;
    }
    $.ajax({
        url: appendCtx('/ep/sys/users/pass'),
        type: "put",
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify({
        	"id":userId,
        	"pass" :createMD5(pass)
        	}),
        success: function (result) {
            if(result.code == 0){
                layer.msg('重置成功',{
                    zIndex:layer.zIndex
                });
                setTimeout(function(){return closePage();},1000);
            }else{
            	layer.alert(result.msg, {icon: 2});
            }
        },
        error: function (data) {
        	layer.alert(data.responseText, {icon: 2});
        }
    });
}

function closePage(){
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}