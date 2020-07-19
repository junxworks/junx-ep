var tt;
var treetable;
//config的设置是全局的
var renderTable =function (){
	layui.config({
	    base: '../../../plugins/tree-table/' //这是你存放拓展模块的根目录
	});
	layui.use(['treeTable','layer'], function () {
        treetable = layui.treeTable;
        $.get(appendCtx('/ep/sys/menus'), function (res) {
        	 	tt=treetable.render({
				elem: '#tree-table',
        		icon_key: 'name',
				is_checkbox: false,
				is_click_icon: false,
                data: res.data,
                top_value: 0,
                primary_key: 'id',
            	parent_key: 'parentId',
            	hide_class: 'layui-hide',
            	icon: {
            		open: 'layui-icon layui-icon-triangle-d',
            		close: 'layui-icon layui-icon-triangle-r',
            		left: 16,
            	},
                cols:  [
                	{
						key: 'id',
						title: 'ID',
						width: '50px',
						align: 'center',
					},
					{
						key: 'name',
						title: '菜单名称',
						width: '300px'
					},
					{
						key: 'parentId',
						title: '上级编号',
						width: '100px',
						align: 'center',
					},
					{key: 'typeDesc', title: '菜单类型',align: 'center',width: '100px'},
	                {key: 'mark', title: '权限标识', align: 'left',width: '150px',template:
	                	function(d){
		                	if(d.mark){
		                        return d.mark;
		                	}else{
		                		return '';
		                	}
                    }},
	                {key: 'url', title: '菜单链接', align: 'left',width: '300px' ,template:
	                	function(d){
	                	if(d.url){
	                        return d.url;
	                	}else{
	                		return '';
	                	}
	                }},
	                {key: 'icon', title: '菜单图标', width: '120px', align: 'center' ,template:
	                	function(d){
	                        return ' <i class="'+d.icon+'"></i>';
	                    }
	                },
	                {key: 'sort', title: '排序', align: 'center',width: '100px' },
					{title: '操作',align: 'center',width: '300px',template: function(d){
							return '<button class="layui-btn layui-btn-xs" onclick="update('+d.id+')"><i class="fa fa-edit"></i>修改 </button><button class="layui-btn layui-btn-xs layui-btn-normal" onclick="roles(\''+d.name+'\','+d.id+')"><i class="fa fa-users"></i>查看关联角色 </button><button class="layui-btn layui-btn-xs layui-btn-danger" onclick="deleteFunc('+d.id+')"><i class="fa fa-remove"></i>删除 </button>';
						}
					}
				],
                end: function(e){
                }
            });
        	//treetable.openAll(tt);
        	 $('#search').click(function () {
                 var keyword = $('#menuName').val();
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
function update(cid) {
    layer.open({
        type: 2,
        title: typeof(cid) != "undefined" ? '修改菜单项' : '新增菜单项',
        shadeClose: true,
        area: ['800px', '400px'],
        content: 'addMenu.html?id=' + cid,
    });
}

function roles(name,cid){
    layer.open({
        type: 2,
        title: name+'-关联角色',
        shadeClose: true,
        area: ['700px', '500px'],
        content: 'showMenuRoles.html?id=' + cid,
    });
}

function deleteFunc(id) {
    var url=appendCtx('/ep/sys//menus/'+id);
    layer.confirm('确定删除该菜单吗?', {icon: 3, title:'确认提示'},function(index) {
            $.ajax({
                url: url,
                dataType: 'json',
                type: "delete",
                contentType: 'application/json; charset=utf-8',
                beforeSend: function(XMLHttpRequest){
                    layer.msg('正在处理', { icon: 16,time: 1200000,shade: [0.3,'#000']});//背景虚化
                },
                success: function(data, textStatus)
                {
                    if(data.code==0)
                    {
                        layer.msg("删除成功");
                        renderTable();
                    }else{
                        layer.alert(data.msg,{icon:5,title:"提示",closeBtn: 0});
                    }
                }
            });
            layer.close(index);
        }
    );
}
