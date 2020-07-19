layui.use(['tree', 'util', 'layer'], function () {
    var tree = layui.tree;
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
    //初始化菜单树
    io.get('/ep/sys/roles/'+roleId+'/menus',function(res){
    	var menuList = res.data;
        var tree = layui.tree;
        //开启复选框
        tree.render({
            elem: '#menuTree'
            , data: menuList
            , id: 'menuTree'
            , showCheckbox: true
        });
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

    var tree = layui.tree;
    var menuTrees = tree.getChecked('menuTree');
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