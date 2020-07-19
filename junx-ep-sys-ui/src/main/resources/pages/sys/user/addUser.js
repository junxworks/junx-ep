var isEdit=false;
layui.config({
    base: '../../../plugins/layui-extend/' //这是你存放拓展模块的根目录
});
layui.use(['form','xmSelect'], function () {
    var form = layui.form;
    var xmSelect = layui.xmSelect;
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
            });
            initOrgTree(data.orgNo);
    	});
    }else{
        userId = 0;
        initOrgTree();
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
    
    function initOrgTree(selectId){
        io.get("/ep/sys/orgs/tree-select/0",function(res){
        	var org=xmSelect.render({
        		el: '#orgNo', 
    			name: 'orgNo',
        		model: { label: { type: 'text' } },
        		radio: true,
        		clickClose: true,
        		tree: {
        			//是否显示树状结构
        			show: true,
        			//是否严格遵守父子模式
        			strict: false,
        			//是否展示三角图标
        			//showFolderIcon: true,
        			//是否显示虚线
        			//showLine: true,
        			//间距
        			//indent: 20,
        			//默认展开节点的数组, 为 true 时, 展开所有节点
        			expandedKeys: true, 
        			//是否开启极简模式
        			//simple: false,
        		},
        		height: 'auto',
        		data(){
        			initSelect(res.data,selectId);
        			return res.data;
        		}
        	});
    	});
    }
    
    function initSelect(treeData,selectId){
    	if(isNull(selectId)){
    		return;
    	}
    	for(var i in treeData){
			var item = treeData[i];
			if(item.value==selectId){
				item.selected=true;
				return;
			}
			if(item.children!=null){
				initSelect(item.children,selectId);
			}
		}
    }
    
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

function closePage(){
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}

