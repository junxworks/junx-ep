layui.config({
    base: '../../../plugins/layui-extend/' //这是你存放拓展模块的根目录
});
layui.use(['tree', 'util', 'layer','form','xmSelect'], function () {
    var form = layui.form;
    var xmSelect = layui.xmSelect;
    var tree = layui.tree;
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
                form.val('myForm', respData);
                initOrgTree(respData.parentNo);
            }
        });
    }else{
        initOrgTree();
    }
    
    function initOrgTree(selectId){
        io.get("/ep/sys/orgs/tree-select/0",function(res){
        	var org=xmSelect.render({
        		el: '#parentNo', 
    			name: 'parentNo',
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
    form.on('submit(save)', function (formData) {
    	var data=JSON.stringify(formData.field);
    	if(data.orgNo=='0'){
    		layer.msg("组织编码不能为0",{icon:2});
    	}
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