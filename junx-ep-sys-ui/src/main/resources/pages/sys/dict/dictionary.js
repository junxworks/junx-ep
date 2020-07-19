var tableIns;//表格实例

layui.use(['table','form'], function () {
    var table = layui.table;
    tableIns = table.render({
        elem: '#dictionarytable'
        , url: appendCtx('/ep/sys/dictionaries')
        , page: true
        , even: true
        , cols: [[ //表头
            {field: 'id', title: '数据字典编号'}
            , {field: 'parentCode', title: '上级编码'}
            , {field: 'dataCode', title: '数据编码'}
            , {field: 'dataValue', title: '数据值'}
            , {field: 'sort', title: '排序'}
            , {field: 'memo', title: '字段描述'}
            , {field: 'operate', title: '操作',width:300, templet: '#operBar'}
        ]]
        , request: {
            pageName: 'pageNo' //页码的参数名称，默认：page
            , limitName: 'pageSize' //每页数据量的参数名，默认：limit
        }
        , parseData: function (res) { //res 即为原始返回的数据
            return {
                "code": res.code, //解析接口状态
                "msg": res.msg, //解析提示文本
                "count": res.data.total, //解析数据长度
                "data": res.data.list //解析数据列表
            };
        }
    });
});
$("#divDialog").hide();
function refresh(){
	 tableIns.reload({});
}
function search() {
    tableIns.reload({
        where: { //设定异步数据接口的额外参数，任意设
            'dataValue': $("#dataValue").val(),
            'parentCode': $("#parentCode").val(),
            'dataCode': $("#dataCode").val(),
        }
        , page: {
            curr: 1 //重新从第 1 页开始
        }
    });
}
//修改
function update(cid) {
    $("#divDialog input").val("");
    $("#div_parentCode").val('0');
    if(cid){
        if(!fillData(cid))
        {
            return ;
        }
    }
    g_index=layer.open({btn: ['提交', '取消'],yes:func_submit,btn2:func_cancel,btnAlign: 'c',area:['700px', '500px'],type: 1,content: $('#divDialog'),cancel: function(){
        $("#divDialog").css({"display":"none"})
     }});
}

function func_cancel(index)
{
    layer.close(index);
    $("#divDialog").hide();
}
function deleteFunc(id) {
    var url=appendCtx('/ep/sys/dictionaries/'+id);
    layer.confirm('确定删除该数据字典吗?', {icon: 3, title:'确认提示'},function(index) {
            $.ajax({
                url: url,
                dataType: 'json',
                type:"delete",
                contentType: 'application/json; charset=utf-8',
                beforeSend: function(XMLHttpRequest){
                    layer.msg('正在处理', { icon: 16,time: 1200000,shade: [0.3,'#000']});//背景虚化
                },
                success: function(data, textStatus)
                {
                    if(data.code==0)
                    {
                        layer.msg("删除成功");
                        search();
                    }else{
                        layer.alert(data.msg,{icon:5,title:"提示",closeBtn: 0});
                    }
                }
            });
            layer.close(index);
        }
    );
}
function func_submit(index){
    var g_load = 0 ;
    var id=$("#divDialog input[name='id']").val();
    var parentCode=$("#divDialog input[name='div_parentCode']").val();
    var dataCode=$("#divDialog input[name='div_dataCode").val();
    var dataValue=$("#divDialog input[name='div_dataValue']").val();
    var sort=$("#divDialog input[name='sort']").val();
    var memo=$("#divDialog textarea[name='memo']").val();
    var url=appendCtx('/ep/sys/dictionaries');
    $.ajax({
        url: url,
        dataType: 'json',
        type: "post",
        contentType: 'application/json; charset=utf-8',
        data:JSON.stringify({id:id,parentCode:parentCode,dataCode:dataCode,dataValue:dataValue,sort:sort,memo:memo}),
        beforeSend: function(XMLHttpRequest){
            layer.msg('正在处理', { icon: 16,time: 1200000,shade: [0.3,'#000']});//背景虚化
        },
        success: function(data, textStatus)
        {
            layer.close(g_load);
            if(data.code==0)
            {
                layer.close(index);
                layer.msg("保存成功",{
                    icon: 1,
                    time: 2000
                },null);
                refresh();
                $("#divDialog").hide();
            }
            else
            {
                layer.alert(data.msg,{icon:5,title:"提示",closeBtn: 0});
            }

        }
    }).fail(function(jqXHR, textStatus, error)
    {
        layer.alert(error,{icon:5,title:"提示",closeBtn: 0});
        refresh();
        $("#divDialog").hide();
    });
}
function fillData(id)
{
    var bRet = true;
    var url=appendCtx('/ep/sys/dictionaries/')+id;
    $.ajax({
        url: url,
        cache: false,
        async: false,
        success: function(data, textStatus)
        {
            if(data.code!=0)
            {
                bRet=false ;
                layer.alert(data.msg,{icon:0});
            }
            var respData = data.data ;
            if(respData){
                $("#divDialog input[name='id']").val(respData.id);
                $("#divDialog input[name='div_parentCode']").val(respData.parentCode);
                $("#divDialog input[name='div_dataCode").val(respData.dataCode);
                $("#divDialog input[name='div_dataValue']").val(respData.dataValue);
                $("#divDialog input[name='sort']").val(respData.sort);
                $("#divDialog textarea[name='memo']").val(respData.memo);
            }
        }
    })
    return bRet;
}

layui.use('laydate', function(){
    var laydate = layui.laydate;
    //执行一个laydate实例
    laydate.render({
        elem: '#createDate' //指定元素
    });
});
layui.use('form', function(){
    var form = layui.form;
    form.render();
});