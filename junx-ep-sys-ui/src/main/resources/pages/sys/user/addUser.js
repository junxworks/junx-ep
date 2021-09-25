layui.config({
    base: '../../../plugins/layui-extend/' //这是你存放拓展模块的根目录
});
layui.use(['form','xmSelect','transfer'], function () {
    var form = layui.form;
    var xmSelect = layui.xmSelect;
    var transfer=layui.transfer;
    var userId = getParam("userId");
    if(!isNull(userId)){
    	io.get('/ep/sys/users/' + userId,function(result){
    		$("#password").remove();
            var data = result.data;
            var form = layui.form;
            //表单初始赋值
            form.val('userForm', data);
            initOrgTree(data.orgNo);
            $("#username").attr("disabled","disabled");
    	});
    }else{
        userId = 0;
        initOrgTree();
    }
    //初始化角色信息
    io.get('/ep/sys/users/'+userId+'/roles',function(res){
    	transfer.render({
    		  id:"roles"
    		  ,elem: '#roleDiv'
    		  ,title: ['备选角色', '已有角色']
    		  ,data: res.data
    		  ,height:300
    		  ,width:250
    		  ,showSearch: true
    		  ,parseData: function(res){
    		    return {
    		      "value": res.id //数据值
    		      ,"title": res.roleName //数据标题
    		      ,"disabled": false  //是否禁用
    		      ,"checked": false //是否选中
    		    }
    		  }
    		 ,value:res.attr.checked
    		});
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
        			initXMTreeSelect(res.data,selectId);
        			return res.data;
        		}
        	});
    	});
    }
    
    //监听提交
    form.on('submit(save)', function (form) {
        var data = form.field;
        if(isNull(data.orgNo)){
        	layer.msg("请选择组织", {icon: 2});
        	return false;
        }
        data.roles = transfer.getData('roles');
        io.post('/ep/sys/users',JSON.stringify(data),function(res){
        	 //关闭当前页
            setTimeout(function(){return closePage();},1000);
            //刷新列表
            window.parent.refreshTableData();
        });
        return false;
    });
    
});

function closePage(){
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}

