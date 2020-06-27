layui.use(['tree', 'util', 'layer','form'], function () {
    var form = layui.form;
    dict2Select("orgType","orgType");
    form.render();
	var id = getParam("id");
	var isAdd=true;
    if (!isNull(id)) {
    	isAdd=false;
        io.get('/ep/sys/orgs/'+id,function(result){
        	var respData = result.data ;
            if(respData){
                var form = layui.form;
                //表单初始赋值
                form.val('myForm', respData)
            }
        });
    }
    var tree = layui.tree;
    //初始化菜单树
    io.get('/ep/sys/orgs/tree/0',
         function (result) {
                var data = result.data;
                data.push({
                    checked:false,
                    id:0,
                    disabled:false,
                    parentId: 0,
                    sort: "1",
                    spread: false,
                    title: "无"
                });
                tree.render({
                    elem: '#treeItem'
                    , data: data
                    , id: 'orgTree'
                    , showCheckbox: false
                    , onlyIconControl: true
                    , click: function(item){
                        $("#parentNo").val(item.data.id);
                        $("#parentName").val(item.data.title);
                      }
         });
    });
    
    //监听提交
    form.on('submit(save)', function (formData) {
    	var data=JSON.stringify(formData.field);
    	if(isAdd){
    		 io.post('/ep/sys/orgs',data,
    	           function (result) {
    			 		closeAndRefresh();
    	           }
    	     );
    	}else{
    		 io.put('/ep/sys/orgs',data,
      	           function (result) {
      			 		closeAndRefresh();
      	           }
      	     );
    	}
       
        return false;
    });
});

function closeAndRefresh(){
	closePage();
	window.parent.renderTable();
}