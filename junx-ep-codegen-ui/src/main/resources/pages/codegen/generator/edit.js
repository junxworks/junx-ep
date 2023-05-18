layui.config({
    base: '../../../plugins/layui-extend/' //这是你存放拓展模块的根目录
});
var transfer;
layui.use(['form','transfer'], function () {
    var form = layui.form;
    var id = getParam("id");
   	transfer = layui.transfer;
    if(!isNull(id)){
    	io.get('/ep/codegen/generators/' + id,function(res){
    		var data = res.data;
            var form = layui.form;
            //表单初始赋值
            form.val('lForm', data)
            initTemplates(data.templates);
    	});
    }else{
		initTemplates("");
	}
    //监听提交
    form.on('submit(save)', function (form) {
		var data=form.field;
        data.templateList = transfer.getData('templateList');
        if(data.templateList.length==0){
			layer.msg("请选择模板", {icon: 2});
			return false;
		}
		io.post('/ep/codegen/generators',JSON.stringify(data),function(res){
	        setTimeout(function(){return closePage();},1000);
	        window.parent.refreshTableData();
		});
		return false;
    });
});

function initTemplates(templates){
    	io.get('/ep/codegen/templates?pageNo=1&pageSize=1000',function(res){
			transfer.render({
				  id:"templateList"
				  ,elem: '#templatesDiv'
				  ,title: ['备选模板', '已选模板']
				  ,data: res.data.list
				  ,height:300
				  ,width:250
				  ,showSearch: true
				  ,parseData: function(d){
				    return {
				      "value": d.tmpId //数据值
				      ,"title": d.tmpId //数据标题
				      ,"disabled": false  //是否禁用
				      ,"checked": false //是否选中
				    }
				  }
				 ,value:templates.split(",")
			});
		});
}