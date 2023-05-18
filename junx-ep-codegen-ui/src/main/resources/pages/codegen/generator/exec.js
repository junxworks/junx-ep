var tableIns;//表格实例
layui.config({
    base: '../../../plugins/layui-extend/' //这是你存放拓展模块的根目录
});
var id=getParam("id");
layui.use(['table','form'], function () {
    var table = layui.table;
    var form = layui.form;
    io.get("/ep/codegen/datasources?pageNo=1&pageSize=-1",function(res){
		var data=res.data.list;
		var map=new Map();
		for(var i in data){
			var d=data[i];
    		map.set(d.dsId.toString(),d.dsId);
		}
		map2Select(map,'dsId');
	    form.render();
	});
    tableIns = table.render({
        elem: '#lTable'
        ,loading:false
        ,height: 400
        , cols: [[ //表头
       		 {type:'checkbox'}
            , {field: 'tableName', title: '表名',align:'left',width:400}
            , {field: 'tableComment', title: '描述',align:'left',width:400}
        ]]
        , page: false
        , limit : 10000
        , parseData: function (res) { //res 即为原始返回的数据
            return {
                "code": res.code, //解析接口状态
                "msg": res.msg, //解析提示文本
                "data": res.data //解析数据列表
            };
        }
    });

    form.on('submit(search)', function(formData){
    	tableIns.reload({
			url: appendCtx('/ep/codegen/generators/tables'),
            where:formData.field,
        });
        return false;
      });
    
    form.on('submit(generate)', function(formData){
    	var checkStatus = table.checkStatus('lTable');
    	if(checkStatus.data.length==0){
			layer.msg("请选择需要生成代码的表",{icon:7});
			return false;
		}
		var tables;
		for(var i=0,len=checkStatus.data.length;i<len;i++){
			var o=checkStatus.data[i];
			if(i==0){
				tables=o.tableName;
			}else{
				tables+=","+o.tableName;
			}
		}
		var data = formData.field;
		window.location.href =appendCtx("/ep/codegen/generators/files?genId="+id+"&dsId="+data.dsId+"&tables="+tables);
		layer.msg("代码生成成功，等待下载，请不要关闭窗口",{icon:1});
        return false;
      });
});