layui.use(['tree', 'util', 'layer','form'], function () {
	var callback = getParam("callback");
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
                    	if(!isNull(callback)){
                    		window.parent[callback](item.data);
                    	}
                        closePage();
                    }
         });
    });
});
