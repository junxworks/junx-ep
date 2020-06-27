var logTable;
	layui.use(['table','laydate','form','element'], function(){
	    var laydate = layui.laydate;
	    var form = layui.form;
	    var ltable=layui.table;
	    form.render();
	    
	    ltable.render({
	        title:"定时任务表",
	        elem:'#jobsTable',
	        id:'jobsTable',
	        url:appendCtx('/ep/sys/schedule/jobs'),
	        cols:[[
	            {field: 'jobId', title: '任务ID', align: 'center', width: 80},
	            {field: 'remark', title: '任务名称',  align: 'left', width: 250},
	            {field: 'beanName', title: 'Bean名称',  align: 'left',width: 200},
	            {field: 'methodName', title: '方法名', align: 'left', width: 200},
	            {field: 'params', title: '执行参数',align: 'left',width: 120},
	            {field: 'cronExpression', title: 'cron表达式', align: 'left',width: 150},
	            {field: 'status', title: '任务状态', width: 100, align: 'center' ,
	            	templet:function(d){
	                        if( d.status==0){
	                        	return '<span class="label  label-info">正常</span>';
	                        }else{
	                        	return '<span class="label  label-warning">暂停</span>';
	                        }
	                }
	            },
	            {field: 'createTime', title: '创建日期', align: 'center',width: 200 ,templet:function(d){
	                    return dateFormat(d.createTime,"yyyy-MM-dd hh:mm:ss");}},
	            {title: '操作', width: 350, align: 'center', toolbar: '#operBar', fixed: 'right'}
	        ]],
	        page: true,
	        even: true,
	        parseData:function(res)
	        {
	            var retObj=
	                {
	                    "code": res.code, //解析接口状态
	                    "msg": res.msg, //解析提示文本
	                    "count": res.data.total, //解析数据长度
	                    "data": res.data.list //解析数据列表
	                };
	            return retObj ;
	        },
	        response: {statusCode:0},
	        request:
	            {
	                pageName: 'pageNo',
	                limitName: 'pageSize'
	            }
	    });
	    
	    logTable=ltable.render({
	        title:"定时任务日志表",
	        elem:'#jobLogTable',
	        id:'jobLogTable',
	        url:appendCtx('/ep/sys/schedule/logs'),
	        cols:[[
	        	{field: 'logId', title: '日志ID', align: 'center', width: 100},
	            {field: 'jobId', title: '任务ID', align: 'center', width: 80},
	            {field: 'remark', title: '任务名称',  align: 'left',width: 180},
	            {field: 'beanName', title: 'Bean名称',  align: 'left',width: 120},
	            {field: 'methodName', title: '方法名', align: 'left', width: 120},
	            {field: 'params', title: '执行参数',align: 'left',width: 300},
	            {field: 'status', title: '任务状态', width: 100, align: 'center' ,
	            	templet:function(d){
	                        if( d.status==0){
	                        	return '<span class="label  label-info">成功</span>';
	                        }else{
	                        	return '<span class="label  label-warning">失败</span>';
	                        }
	                }
	            },
	            {field: 'times', title: '执行耗时（毫秒）', align: 'center',width: 150},
	            {field: 'createTime', title: '执行时间', align: 'center',width: 180,templet:function(d){
                    return dateFormat(d.createTime,"yyyy-MM-dd hh:mm:ss");}},
	            {field: 'error', title: '执行异常', align: 'center',width: 800}
	        ]],
	        page: true,
	        even: true,
	        parseData:function(res)
	        {
	            var retObj=
	                {
	                    "code": res.code, //解析接口状态
	                    "msg": res.msg, //解析提示文本
	                    "count": res.data.total, //解析数据长度
	                    "data": res.data.list //解析数据列表
	                };
	            return retObj ;
	        },
	        response: {statusCode:0},
	        request:
	            {
	                pageName: 'pageNo',
	                limitName: 'pageSize'
	            }
	    });
	});

function refreshLogs(){
	logTable.reload({});
}
	
function search()
{
	layui.table.reload('jobsTable', {
        where:{
    	    remark:$("#remark").val(),
    	    beanName:$("#bean_name").val()
        },
        pageNo:{
            curr: 1
        }
    });
}

function exec(id) {
	layer.confirm('确认要执行选择的任务吗?', {icon: 3, title:'提示'}, function(){
		layer.msg('操作中', {
  		  icon: 16
		  ,time: 1200000
  		  ,shade: 0.01
  		});
	  	$.ajax({
	  		   type:"GET",
	  		   url:appendCtx("/ep/sys/schedule/jobs/"+id+"/run"),
	  		   async: true,
	  		   success: function (data){
	  		        if (data.ok) {
	  		        	layer.msg("执行成功",{icon:1});
	  		        	search();
	  		        }
	  		        else {
	  		        	 layer.alert(data.msg,{icon:5,title:"提示",closeBtn: 0});
	  		        }
	  		    }
	  		}); 
	},function(index){
		  layer.close(index);
		});
}

function resume(id) {
	layer.confirm('确认要恢复选择的任务吗?', {icon: 3, title:'提示'}, function(){
		layer.msg('操作中', {
  		  icon: 16
		  ,time: 1200000
  		  ,shade: 0.01
  		});
	  	$.ajax({
	  		   type:"GET",
	  		   url:appendCtx("/ep/sys/schedule/jobs/"+id+"/resume"),
	  		   async: true,
	  		   success: function (data){
	  		        if (data.ok) {
	  		        	layer.msg("执行成功",{icon:1});
	  		        	search();
	  		        }
	  		        else {
	  		        	 layer.alert(data.msg,{icon:5,title:"提示",closeBtn: 0});
	  		        }
	  		    }
	  		}); 
	},function(index){
		  layer.close(index);
		});
}

function pause(id) {
	layer.confirm('确认要暂停选择的任务吗?', {icon: 3, title:'提示'}, function(){
		layer.msg('操作中', {
  		  icon: 16
		  ,time: 1200000
  		  ,shade: 0.01
  		});
	  	$.ajax({
	  		   type:"GET",
	  		   url:appendCtx("/ep/sys/schedule/jobs/"+id+"/pause"),
	  		   async: true,
	  		   success: function (data){
	  		        if (data.ok) {
	  		        	layer.msg("执行成功",{icon:1});
	  		        	search();
	  		        }
	  		        else {
	  		        	 layer.alert(data.msg,{icon:5,title:"提示",closeBtn: 0});
	  		        }
	  		    }
	  		}); 
	},function(index){
		  layer.close(index);
		});
}

function del(id){
	layer.confirm('确认要删除选择的任务吗?', {icon: 3, title:'提示'}, function(){
		layer.msg('操作中', {
  		  icon: 16
		  ,time: 1200000
  		  ,shade: 0.01
  		});
	  	$.ajax({
	  		   type:"delete",
	  		   url:appendCtx("/ep/sys/schedule/jobs/"+id),
	  		   async: true,
	  		   success: function (data){
	  		        if (data.ok) {
	  		        	layer.msg("执行成功",{icon:1});
	  		        	search();
	  		        }
	  		        else {
	  		        	 layer.alert(data.msg,{icon:5,title:"提示",closeBtn: 0});
	  		        }
	  		    }
	  		}); 
	},function(index){
		  layer.close(index);
		});
}

function editJob(id){
	layer.open({
        type: 2,
        title: typeof(id) != "undefined" ? '修改任务' : '新增任务',
        shadeClose: true,
        area: ['800px', '600px'],
        content: 'editJob.html?id=' + id,
    });	
}

function add(){
	layer.open({
        type: 2,
        title: typeof(id) != "undefined" ? '修改任务' : '新增任务',
        shadeClose: true,
        area: ['800px', '600px'],
        content: 'editJob.html',
    });	
}




