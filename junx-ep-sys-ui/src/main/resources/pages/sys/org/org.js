var tt;
var treetable;
//config的设置是全局的
var renderTable =function (){
	layui.config({
	    base: '../../../plugins/tree-table/' //这是你存放拓展模块的根目录
	});
	layui.use(['treeTable','layer'], function () {
        treetable = layui.treeTable;
        $.get(appendCtx('/ep/sys/orgs'), function (res) {
        	 	tt=treetable.render({
				elem: '#tree-table',
        		icon_key: 'orgName',
				is_checkbox: false,
				is_click_icon: false,
                data: res.data,
                top_value: 0,
                primary_key: 'orgNo',
            	parent_key: 'parentNo',
            	hide_class: 'layui-hide',
            	icon: {
            		open: 'layui-icon layui-icon-triangle-d',
            		close: 'layui-icon layui-icon-triangle-r',
            		left: 16,
            	},
                cols:  [
                	{
						key: 'orgNo',
						title: '组织编码',
						width: '150px',
						align: 'left',
					},
					{
						key: 'orgName',
						title: '组织名称',
						width: '300px'
					},
					{
						key: 'parentNo',
						title: '上级编码',
						width: '100px',
						align: 'center',
					},
					{
						key: 'orgType',
						title: '组织类型',
						width: '100px',
						align: 'center',template: function(d){
							return translate("orgType",d.orgType);
						}
					},
					{
						key: 'remark',
						title: '备注',
						width: '200px',
						align: 'center',
					},
					{title: '操作',align: 'center',width: '150px',template: function(d){
							return '<button class="layui-btn layui-btn-xs" onclick="edit('+d.id+')"><i class="fa fa-edit"></i>修改 </button><button class="layui-btn layui-btn-xs layui-btn-danger" onclick="deleteOrg('+d.id+')"><i class="fa fa-remove"></i>删除 </button>';
						}
					}
				],
                end: function(e){
                }
            });
        	//treetable.openAll(tt);
        	 $('#search').click(function () {
                 var keyword = $('#orgName').val();
                 var searchCount = 0;
                 treetable.openAll(tt);
                 setTimeout(function () {
                     $('#tree-table').find('tbody tr td').each(function () {
                         $(this).css('background-color', 'transparent');
                         var text = $(this).text();
                         if (keyword != '' && text.indexOf(keyword) >= 0) {
                             $(this).css('background-color', 'rgba(250,230,160,0.5)');
                             if (searchCount == 0) {
                                 $('html,body').stop(true);
                                 $('html,body').animate({ scrollTop: $(this).offset().top - 150 }, 500);
                             }
                             searchCount++;
                         }
                     });
                     if (keyword == '') {
                         layer.msg("请输入搜索内容", { icon: 5 });
                     } else if (searchCount == 0) {
                         layer.msg("没有匹配结果", { icon: 5 });
                     }
                 }, 200)
             });
	    }, 'json');
	});//layui.use的结束括号
}

renderTable();

function closeAll(){
	if(tt!=null){
		treetable.closeAll(tt);
	}
}

function openAll(){
	if(tt!=null){
    	treetable.openAll(tt);
	}
}


//修改
function edit(cid) {
    layer.open({
        type: 2,
        title: typeof(cid) != "undefined" ? '修改组织' : '新增组织',
        shadeClose: true,
        area: ['800px', '400px'],
        content: 'editOrg.html?id=' + cid,
    });
}

function deleteOrg(id) {
    io.delete('/ep/sys/orgs/'+id,function(res){
    	renderTable();
    });
}
