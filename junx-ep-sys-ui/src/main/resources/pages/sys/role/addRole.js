layui.config({
    base: '../../../../eui/plugins/layui-extend/' //这是你存放拓展模块的根目录
});

var tree;
layui.use(['tree', 'util', 'layer', 'eleTree'], function () {
    var eleTree = layui.eleTree;
    var roleId = getParam("roleId");
    var roleName = getParam("roleName");
    var roleTag = getParam("roleTag");
    if (!isNull(roleId)) {
        $('#roleId').val(roleId);
        $('#roleName').val(roleName);
        $('#roleTag').val(roleTag);
    } else {
        roleId = -1;
    }
    // 初始化菜单树
    io.get('/ep/sys/roles/' + roleId + '/menus', function (res) {
        var menuList = res.data;
        tree = eleTree({
            el: '#menuTree',
            data: menuList,
            showCheckbox: true,
            highlightCurrent: true,
            isDefaultChangePstatus: true,
            request: {     // 对后台返回的数据格式重新定义
                name: "title",
                key: "id",
                children: "children",
                checked: "checked",
                disabled: "disabled",
                isLeaf: "isLeaf"
            },
        })
    });
});

function addRole() {
    var roleId = $('#roleId').val();
    var roleName = $('#roleName').val();
    var roleTag = $('#roleTag').val();
    if (roleName == '') {
        layer.msg("角色名不能为空");
        return;
    }

    var menuTrees = tree.getChecked(false, true);
    var data = JSON.stringify({'id': roleId, 'roleName': roleName,'roleTag':roleTag, 'menuInfoList': JSON.stringify(menuTrees)});
    io.post("/ep/sys/roles",data,function(res){
    	setTimeout(function () {
            return closePage();
        }, 1000);
        //刷新列表
        window.parent.refreshTableData();
    });
    
}

function closePage() {
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}