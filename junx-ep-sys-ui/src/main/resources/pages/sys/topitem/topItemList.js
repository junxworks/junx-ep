var tableIns;//表格实例
layui.config({
	base: '../../../plugins/layui-extend/' //这是你存放拓展模块的根目录
});
layui.use(['table', 'form'], function() {
	var table = layui.table;
	var form = layui.form;

	tableIns = table.render({
		elem: '#itemTable'
		, url: appendCtx('/ep/sys/top-items')
		, page: true
		, cols: [[ //表头
			{ field: 'id', title: '组件ID',align: 'center', width: 80 }
			, { field: 'itemName', title: '组件名称', align: 'center', width: 200 }
			, { field: 'itemIndex', title: '组件顺序', align: 'center',width: 100 }
			, { field: 'remark', title: '组件备注', align: 'left',width: 300 }
			, { field: 'createUserName', title: '创建人员', align: 'center', width: 180 }
			, { field: 'createDate', title: '创建时间', align: 'center', width: 180 }
			, { field: '', title: '启用', width: 100, align: 'center', templet: '#availableTp' }
			, { field: '', title: '操作', width: 300, templet: '#operateTp' }
		]]
		, request: {
			pageName: 'pageNo' //页码的参数名称，默认：page
			, limitName: 'pageSize' //每页数据量的参数名，默认：limit
		}
		, parseData: function(res) { //res 即为原始返回的数据
			return {
				"code": res.code, //解析接口状态
				"msg": res.msg, //解析提示文本
				"count": res.data.total, //解析数据长度
				"data": res.data.list //解析数据列表
			};
		}
	});

	form.on('submit(search)', function(formData) {
		tableIns.reload({
			where: formData.field,
			page: {
				curr: 1
			}
		});
		return false;
	});

	form.on('switch(itemUse)', function(data) {
		if (data.elem.checked) {
			updateItemStstus(data.value, 1);
		} else {
			updateItemStstus(data.value, 0);
		}
	});
});

function refreshTableData() {
	tableIns.reload({});
}

function editItem(id) {
	layer.open({
		type: 2,
		title: typeof (id) != "undefined" ? '修改组件' : '新增组件',
		shadeClose: true,
		area: ['750px', '650px'],
		content: 'itemEdit.html?id=' + id,
	});
}

function updateItemStstus(id, status) {
	io.put("/ep/sys/top-items/status", JSON.stringify({ 'id': id, 'status': status }), function(res) {
		refreshTableData();
	});
}

function deleteItem(id,name){
	layer.confirm("是否要删除顶部组件["+name+"]", {icon: 3, title:'提示'}, function(index){
		io.put("/ep/sys/top-items/status", JSON.stringify({ 'id': id, 'status': -1 }), function(res) {
			refreshTableData();
			layer.close(index);
		});
	});
}