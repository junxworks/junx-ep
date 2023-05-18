var needVerify=false;
layui.use(["form", "layer"], function () {
    var form = layui.form;
    var layer = layui.layer;
    $.ajax({
		url: appendCtx("/ep/sys/system-name")
		,type: "get"
		,success: function(res){
			if(res.ok){
				 $("#systemName").html(res.data);
			}else{
				 layer.msg(res.msg, { anim: 10, icon: 0 });
			}
		}
	});
    //自定义验证规则
    form.verify({
    	username: function (value) {
            if (value.length < 1) {
                return '请输入账号';
            }
        }
        , password: function (value) {
            if (value.length < 1) {
                return '请输入密码';
            }
        },vcode :function(value){
        	if(needVerify&&value.length < 1){
                return '请输入验证码';
        	}
        }
    });
    //提交
    form.on('submit(user-login-submit)', function (obj) {
        var data = obj.field;
        var user = {
        	username: data.username,
        	password: createMD5(data.password),
        	verificationCode: data.verificationCode
        }
        $("#submitBtn").addClass("layui-btn-disabled").prop("disabled", true).text("登录中…");
		$.ajax({
			url: appendCtx("/ep/sys/login")
			,async: false
			,data: JSON.stringify(user)
			,dataType: "JSON"
			,contentType: "application/json;charset=UTF-8"
			,type: "POST"
			,success: function(res){
				if(res.ok){
					setTimeout("top.location.replace('index.html')", "1000");
				}else{
					 if(res.code==-2){
						 $("#vcarea").show();
						 resetVCode();
					 }
					 layer.msg(res.msg, { anim: 10, icon: 0 });
                     $("#submitBtn").removeClass("layui-btn-disabled").prop("disabled", false).text("登入");
				}
			}
		});
        return false;
    });
    
    $(window).resize(function () {
        try {
            //解决移动端输入法弹出收回提示位置错误问题
            layui.layer.closeAll('tips'); //关闭所有的tips层 
        } catch (e) { }
    });

});

function resetVCode(){
	 $("#vcImg").attr("src",appendCtx("/ep/sys/verification-codes?_t="+new Date().getMilliseconds()));
}
