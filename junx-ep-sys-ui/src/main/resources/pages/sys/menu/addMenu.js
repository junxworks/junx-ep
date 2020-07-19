layui.config({
    base: '../../../plugins/layui-extend/' //这是你存放拓展模块的根目录
});
layui.use(['tree', 'util', 'layer','form','xmSelect'], function () {
    var form = layui.form;
    var tree = layui.tree;
    var xmSelect = layui.xmSelect;
    var id = getParam("id");
    if (!isNull(id)) {
    	io.get('/ep/sys/menus/'+id,function(res){
    		var data = res.data ;
            if(data){
                var form = layui.form;
                //表单初始赋值
                form.val('menuForm', data);
                initSelectTree(data.parentId);
                changeType();
                form.render();
            }
    	});
    }else {
        var form = layui.form;
        //表单初始赋值
        form.val('menuForm', {
            "sort": "100" // "name": "value"
            ,"type":"0"
        })
        initSelectTree();
        changeType();
        form.render();
    }
    
    //初始化菜单树
    function initSelectTree(selectId){
        io.get("/ep/sys/menus/tree-select",function(res){
        	xmSelect.render({
        		el: '#parentId', 
    			name: 'parentId',
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
        			expandedKeys: [], 
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
    form.on('submit(save)', function (form) {
    	console.log(form)
        var reqData = form.field;
        io.post("/ep/sys/menus",JSON.stringify(reqData),function(res){
        	 //提示信息
            layer.msg('保存成功',{
                zIndex:layer.zIndex
            });
            //关闭当前页
            setTimeout(function(){return closePage();},500);
            //刷新列表
            window.parent.renderTable();
        });
        return false;
    });
    
    function changeType(){
    	recover();
    	var type=$("#type").val();
    	if(type==0){
        	$('#marki').hide();
        	$('#mark').val("");
    	}else if(type==1){
        	$('#urli').hide();
        	$('#url').val("");
    	}else{
    		$('#marki').hide();
    		$('#urli').hide();
        	$('#mark').val("");
        	$('#url').val("");
    	}
    }
    
    function recover(){
    	$('#marki').show();
    	$('#urli').show();
    }
    
    form.on('select(type)', changeType);
    
});

function closePage(){
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}

function openTree(){
    $("#Tree").show();
}
