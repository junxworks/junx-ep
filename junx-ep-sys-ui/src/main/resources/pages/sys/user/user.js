var tableIns;//表格实例
layui.config({
    base: '../../../plugins/layui-extend/' //这是你存放拓展模块的根目录
});
layui.use(['table','form','xmSelect'], function () {
    var table = layui.table;
    var form = layui.form;
    var xmSelect = layui.xmSelect;
    io.get("/ep/sys/roles?pageNo=1&pageSize=-1",function(res){
		var data=res.data.list;
		var map=new Map();
		for(var i in data){
			var d=data[i];
    		map.set(d.id.toString(),d.roleName);
		}
		map2MSelect(map,{
			el:'#roles',
			name: 'roles',
			filterable: true,
		});
	    form.render();
	});
    
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
    			return res.data;
    		}
    	});
	});
    
    tableIns = table.render({
          elem: '#userTable'
        , url: appendCtx('/ep/sys/users')
        , page: true
        , cols: [[ //表头
              {field: 'id', title: '用户ID',width:80}
            , {field: 'userName', title: '账号',width:180}
            , {field: 'name', title: '用户姓名',width:120}
            , {field: 'orgName', title: '所属组织',width:180}
            , {field: 'mobile', title: '手机号',align:'center',width:140}
            , {field: 'email', title: '邮箱',width:300}
            , {field: 'roleName', title: '角色',width:200}
            , {field: 'status', title: '状态',align:'center',width:100, templet: '#statusTp'}
            , {field: 'operate', title: '操作',width:300, templet: '#operateTp'}
        ]]
        , request: {
            pageName: 'pageNo' //页码的参数名称，默认：page
            , limitName: 'pageSize' //每页数据量的参数名，默认：limit
        }
        , parseData: function (res) { //res 即为原始返回的数据
            return {
                "code": res.code, //解析接口状态
                "msg": res.msg, //解析提示文本
                "count": res.data.total, //解析数据长度
                "data": res.data.list //解析数据列表
            };
        }
    });

    form.on('checkbox(userLock)', function(data){
        if(data.elem.checked){
            updateLockStstus(data.value,2);
        }else{
            updateLockStstus(data.value,0);
        }
    });
    
    form.on('submit(search)', function(formData){
    	tableIns.reload({
            where:formData.field,
			page:{
                curr: 1
            }
        });
        return false;
      });
    
});

function refreshTableData() {
    tableIns.reload({});
}

function addUserPage(userId) {
    layer.open({
        type: 2,
        title: typeof(userId) != "undefined" ? '修改用户' : '新增用户',
        shadeClose: true,
        area: ['800px', '720px'],
        content: 'addUser.html?userId=' + userId,
    });
}

function resetPassPage(userId) {
    layer.open({
        type: 2,
        title: '重置密码',
        shadeClose: true,
        area: ['500px', '300px'],
        content: 'resetPass.html?userId=' + userId,
    });
}

function updateLockStstus(userId,status){
    $.ajax({
        url: appendCtx('/ep/sys/users/' + userId + '/status'),
        type: "put",
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify({'status' :status}),
        success: function (result) {
            if(result.code == 0){
            	refreshTableData();
            }else{
            	layer.alert(result.msg, {icon: 2});
            }

        },
        error: function (data) {
        	layer.alert(data.responseText, {icon: 2});
        }
    });
}