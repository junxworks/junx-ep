var ltable = layui.table;
layui.use(['table'], function(){
	var ltable = layui.table;
	var id = getParam("id");
	var sumTable=ltable.render({
		title: "关联角色"
	    ,elem: '#detailTable'
	    ,url: appendCtx("/ep/sys/menus/"+id+"/roles")
	    ,height: '350px'
	    ,cols: [[
            {field: 'id',title: 'ID',fixed:'left',width: 80,align:"center"}
			,{field: 'roleName',title: '角色名称',fixed:'left',minWidth: 160,align:"center"}
			,{field: 'roleTag',title: '角色标识',fixed:'left',minWidth: 120,align:"center"}
	    	]
	    ]
	    ,page: false
	    ,parseData: function(res){ //res 即为原始返回的数据
	        return {
	    	  "code": res.code, //解析接口状态
	    	  "msg": res.msg, //解析提示文本
	          "data": res.data //解析数据列表
	        };
	      }
	    ,response: {
	        statusCode: 0 //规定成功的状态码，默认：0
	      }
	  });
});
