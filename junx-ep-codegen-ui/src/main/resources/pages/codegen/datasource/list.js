var tableIns;//表格实例
layui.config({
    base: '../../../plugins/layui-extend/' //这是你存放拓展模块的根目录
});
layui.use(['table','form'], function () {
    var table = layui.table;
    var form = layui.form;
    tableIns = table.render({
          elem: '#lTable'
        , url: appendCtx('/ep/codegen/datasources')
        , page: true
        , cols: [[ //表头
              {field: 'id', title: 'ID',width:80}
            , {field: 'dsId', title: '数据源标识',align:'center',width:160}
            , {field: 'dsDesc', title: '数据源描述',align:'center',width:160}
            , {field: 'dbType', title: '类型',align:'center',width:120}
            , {field: 'connUrl', title: '数据库连接地址',align:'left',width:400}
            , {field: 'dbName', title: '数据库实例名',align:'center',width:160}
            , {field: 'dbUsername', title: '数据库用户名',align:'center',width:200}
            , {field: 'operate', title: '操作',width:200, templet: '#operateTp'}
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

function editPage(id) {
    layer.open({
        type: 2,
        title: typeof(id) != "undefined" ? '修改数据源' : '新增数据源',
        shadeClose: true,
        area: ['750px', '550px'],
        content: 'edit.html?id=' + id,
    });
}

function del(id){
	io.delete("/ep/codegen/datasources/"+id,function(res){
		refreshTableData();
	});
}