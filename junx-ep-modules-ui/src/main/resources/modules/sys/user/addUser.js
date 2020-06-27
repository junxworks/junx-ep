var isEdit=false;
$(function () {
    var userId = getParam("userId");
    if(userId != null && typeof(userId) != "undefined" && userId != "undefined"){
    	isEdit=true;
    	io.get('/ep/sys/users/' + userId,function(result){
    		$("#password").remove();
            var data = result.data;
            var form = layui.form;
            //表单初始赋值
            form.val('userForm', {
                "id": data.id
                ,"userName": data.userName
                ,"name": data.name
                ,"mobile": data.mobile
                ,"idCard": data.idCard
                ,"email": data.email
                ,"status": data.status
                ,"orgName": data.orgName
                ,"orgNo": data.orgNo
            });
    	});
    }else{
        userId = 0;
    }

    //初始化角色信息
    io.get('/ep/sys/users/'+userId+'/roles',function(result){
        var roleList = result.data;
        var roleCheck = '';
        $.each(roleList,function(i,role) {
            var checked = role.checked ? 'checked' : '';
            roleCheck = roleCheck + '<input type="checkbox" name="role" title="'+role.roleName+'" value="'+role.id+'" '+checked+'>';
        });
        $('#roleDiv').append(roleCheck);
        var form = layui.form;
        form.render();
    });
});

$.fn.serializeObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
    	if(this.name=='password'){
    		if(!isEdit){
        		o[this.name]=createMD5(this.value);
    		}
    	}else if (o[this.name]) {
            o[this.name] = o[this.name] + ',' + this.value || '';
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
}

layui.use('form', function () {
    var form = layui.form;
    //监听提交
    form.on('submit(save)', function (data) {
        var reqData = $('#userForm').serializeObject();
        $.ajax({
            url: appendCtx('/ep/sys/users'),
            type: "post",
            data: JSON.stringify(reqData),
            contentType: 'application/json; charset=utf-8',
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
                    window.parent.refreshTableData();
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

function chooseOrg(){
    layer.open({
        type: 2,
        title: '选择组织',
        shadeClose: true,
        area: ['400px', '500px'],
        content: appendCtx('/eui/modules/sys/orgTree.html?callback=setOrg'),
    });
}

function setOrg(org){
	$("#orgNo").val(org.id);
	$("#orgName").val(org.title);
	
}

