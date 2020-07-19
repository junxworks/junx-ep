var tableIns;//表格实例

layui.use(['table','form'], function () {
    var table = layui.table;
    var form = layui.form;
    tableIns = table.render({
        elem: '#dictionarytable'
        , url: appendCtx('/ep/sys/dictionaries')
        , page: true
        , even: true
        , cols: [[ //表头
            {field: 'id', title: '字典ID'}
            , {field: 'parentCode', title: '上级编码'}
            , {field: 'dataCode', title: '数据项编码'}
            , {field: 'dataLabel', title: '数据项名称'}
            , {field: 'sort', title: '排序'}
            , {field: 'remark', title: '字段描述'}
            , {field: 'operate', title: '操作',width:300, templet: '#operBar'}
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

function refresh(){
	 tableIns.reload({});
}

function editDict(id) {
    layer.open({
        type: 2,
        title: isNull(id) ? '新增字典' : '修改字典',
        shadeClose: true,
        area: ['800px', '500px'],
        content: 'dictEdit.html?id=' + id,
    });
}

function delDict(id) {
    io.delete('/ep/sys/dictionaries/'+id,function(res){
    	refresh();
    });
}