var tableIns;//表格实例
layui.config({
    base: '../../../plugins/layui-extend/' //这是你存放拓展模块的根目录
});
layui.use(['table','form'], function () {
    var table = layui.table;
    var form = layui.form;
    tableIns = table.render({
          elem: '#lTable'
        , url: appendCtx('/ep/codegen/generators')
        , page: true
        , cols: [[ //表头
              {field: 'id', title: 'ID',width:80}
            , {field: 'genName', title: '生成器名称',align:'left',width:200}
            , {field: 'genDesc', title: '描述',align:'left',width:400}
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

function edit(id) {
    layer.open({
        type: 2,
        title: typeof(id) != "undefined" ? '修改' : '新增',
        shadeClose: true,
        area: ['1000px', '600px'],
        content: 'edit.html?id=' + id,
    });
}
function exec(id,name) {
    layer.open({
        type: 2,
        title: '生成器['+name+']代码生成',
        shadeClose: true,
        area: ['1000px', '600px'],
        content: 'exec.html?id=' + id,
    });
}
function del(id){
	io.delete("/ep/codegen/generators/"+id,function(res){
		refreshTableData();
	});
}