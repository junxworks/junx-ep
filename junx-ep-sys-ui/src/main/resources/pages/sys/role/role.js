var tableIns;//表格实例

layui.use('table', function () {
    var table = layui.table;
    tableIns = table.render({
        elem: '#roleTable'
        ,url: appendCtx('/ep/sys/roles')
        , page: true
        , cols: [[ //表头
            {field: 'id',width:100, title: '角色ID'}
            , {field: 'roleName',width:300, title: '角色名称'}
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
});

function queryTableData() {
    var roleName = $('#roleName').val();
    tableIns.reload({
        where: { //设定异步数据接口的额外参数，任意设
            'roleName': roleName
        }
        , page: {
            curr: 1 //重新从第 1 页开始
        }
    });
}

function refreshTableData() {
    tableIns.reload({});
}

function addRolePage(roleId,roleName,roleTag) {
    layer.open({
        type: 2,
        title: typeof(roleId) != "undefined" ? '修改角色' : '新增角色',
        shadeClose: true,
        area: ['500px', '600px'],
        content: 'addRole.html?roleId=' + roleId + '&roleName=' + roleName+'&roleTag='+roleTag,
    });
}

function deleteRole(roleId){
	io.delete("/ep/sys/roles/"+roleId,function(res){
		queryTableData();
	});
}