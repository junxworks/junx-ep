var tableIns;//表格实例
layui.config({
    base: '../../plugins/layui-extend/' //这是你存放拓展模块的根目录
});
layui.use(['table','form','formSelects','treeSelect'], function () {
    var table = layui.table;
    var form = layui.form;
    var treeSelect=layui.treeSelect;
    io.get("/ep/sys/roles?pageNo=1&pageSize=1000",function(res){
		var data=res.data.list;
		var map=new Map();
		for(var i in data){
			var d=data[i];
    		map.set(d.id,d.roleName);
		}
		map2MSelect(map,'roles');
	    form.render();
	});
    
    treeSelect.render({
        // 选择器
        elem: '#orgNo',
        // 数据
        data: appendCtx('/ep/sys/orgs/tree-select/0'),
        // 请求头
        headers: {},
        // 异步加载方式：get/post，默认get
        type: 'get',
        // 占位符
        placeholder: '请选择组织',
        // 是否开启搜索功能：true/false，默认false
        search: true,
        // 一些可定制的样式
        style: {
            folder: {
                enable: false
            },
            line: {
                enable: true
            }
        },
        // 点击回调
        click: function(d){
        },
        // 加载完成后的回调函数
        success: function (d) {
/*            console.log(d);
//            选中节点，根据id筛选
            treeSelect.checkNode('tree', 3);
            console.log($('#tree').val());
//            获取zTree对象，可以调用zTree方法
           var treeObj = treeSelect.zTree('tree');
           console.log(treeObj);
//            刷新树结构
           treeSelect.refresh('tree');*/
        }
    });
    
    tableIns = table.render({
          elem: '#userTable'
        , url: appendCtx('/ep/sys/users')
        , page: true
        , height: 500
        , cols: [[ //表头
              {field: 'id', title: '用户ID',width:80}
            , {field: 'userName', title: '账号',width:180}
            , {field: 'name', title: '用户姓名',width:120}
            , {field: 'orgName', title: '所属组织',width:180}
            , {field: 'mobile', title: '手机号',align:'center',width:120}
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
        area: ['800px', '600px'],
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