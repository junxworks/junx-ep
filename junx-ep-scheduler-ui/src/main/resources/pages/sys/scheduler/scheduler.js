var logTable;
var jobTable;
layui.use(['table','laydate','form','element'], function(){
    var laydate = layui.laydate;
    var form = layui.form;
    var ltable=layui.table;
    laydate.render({ 
    	  elem: '#startTime'
    	  ,type: 'datetime'
    	});
    laydate.render({ 
    	  elem: '#endTime'
    	  ,type: 'datetime'
    	});
    jobTable= ltable.render({
        title:"定时任务表",
        elem:'#jobsTable',
        id:'jobsTable',
        url:appendCtx('/ep/sys/schedule/jobs'),
        cols:[[
            {field: 'id', title: '任务ID', align: 'center', width: 80},
            {field: 'jobName', title: '任务名称',  align: 'left', width: 250},
            {field: 'beanName', title: 'Bean名称',  align: 'left',width: 200},
            {field: 'methodName', title: '方法名', align: 'left', width: 200},
            {field: 'params', title: '执行参数',align: 'left',width: 120},
            {field: 'cronExpression', title: 'cron表达式', align: 'left',width: 150},
            {field: 'remark', title: '任务备注',align: 'left',width: 200},
            {field: 'status', title: '任务状态', width: 100, align: 'center' ,
            	templet:function(d){
                        if( d.status==0){
                        	return '<span class="label  label-info">正常</span>';
                        }else{
                        	return '<span class="label  label-warning">暂停</span>';
                        }
                }
            },
            {field: 'createTime', title: '创建时间', align: 'center',width: 170},
            {field: 'creatUserName', title: '创建者', align: 'center',width: 100},
            {field: 'updateTime', title: '修改时间', align: 'center',width: 170},
            {field: 'updateUserName', title: '修改者', align: 'center',width: 100},
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
        	{field: 'id', title: '日志ID', align: 'center', width: 100},
            {field: 'jobId', title: '任务ID', align: 'center', width: 80},
            {field: 'jobName', title: '任务名称',  align: 'left',width: 180},
            {field: 'beanName', title: 'Bean名称',  align: 'left',width: 120},
            {field: 'methodName', title: '方法名', align: 'left', width: 120},
            {field: 'params', title: '执行参数',align: 'left',width: 300},
            {field: 'status', title: '任务状态', width: 100, align: 'center' ,
            	templet:function(d){
                        if( d.status==1){
                        	return '<span class="label  label-info">成功</span>';
                        }else{
                        	return '<span class="label  label-warning">失败</span>';
                        }
                }
            },
            {field: 'times', title: '执行耗时（毫秒）', align: 'center',width: 150},
            {field: 'createTime', title: '执行时间', align: 'center',width: 180},
            {field: 'error', title: '异常信息', align: 'center',minWdith:300}
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
    
    form.on('submit(searchJobs)', function(formData){
    	jobTable.reload({
            where:formData.field,
			page:{
                curr: 1
            }
        });
        return false;
      });
    form.on('submit(addJob)', function(formData){
    	layer.open({
            type: 2,
            title: typeof(id) != "undefined" ? '修改任务' : '新增任务',
            shadeClose: true,
            area: ['800px', '650px'],
            content: 'editJob.html',
        });	
        return false;
      });
    form.on('submit(searchLogs)', function(formData){
    	logTable.reload({
            where:formData.field,
			page:{
                curr: 1
            }
        });
        return false;
      });
    
});

function search()
{
	jobTable.reload({});
}

function exec(id) {
	layer.confirm('确认要执行选择的任务吗?', {icon: 3, title:'提示'}, function(){
		io.put("/ep/sys/schedule/jobs/"+id+"/run",null,function(res){
			search();
		});
	},function(index){
		  layer.close(index);
		});
}

function resume(id) {
	layer.confirm('确认要恢复选择的任务吗?', {icon: 3, title:'提示'}, function(){
		io.put("/ep/sys/schedule/jobs/"+id+"/resume",null,function(res){
			search();
		});	
	},function(index){
		  layer.close(index);
	});
}

function pause(id) {
	layer.confirm('确认要暂停选择的任务吗?', {icon: 3, title:'提示'}, function(){
		io.put("/ep/sys/schedule/jobs/"+id+"/pause",null,function(res){
			search();
		});	
	},function(index){
		  layer.close(index);
		});
}

function del(id){
	io.delete("/ep/sys/schedule/jobs/"+id,function(res){
		search();
	});	 
}

function editJob(id){
	layer.open({
        type: 2,
        title: typeof(id) != "undefined" ? '修改任务' : '新增任务',
        shadeClose: true,
        area: ['800px', '650px'],
        content: 'editJob.html?id=' + id,
    });	
}




