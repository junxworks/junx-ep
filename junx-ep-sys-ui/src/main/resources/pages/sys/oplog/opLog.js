var form = null ;
$(window).load(function (){
    search();
});
function search()
{
    ltable.render(layRenderParam);
    var reloadParam =
        {
            url:appendCtx('/ep/sys/system-logs'),
            where:
                {
                    name:$("#userName").val(),
                    createDate:$("#createDate").val(),
                    opName:$("#opName").val()
                },
            pageNo:
                {
                    curr: 1
                }
        };
     layui.table.reload('systemLogtable',reloadParam);
    $("#divDialog").hide();
}


function func_parase_data(res)
{
    var retObj=
        {
            "code": res.code, //解析接口状态
            "msg": res.msg, //解析提示文本
            "count": res.data.total, //解析数据长度
            "data": res.data.list //解析数据列表
        };
    return retObj ;
}
var ltable = layui.table;
var tableClls=
    [[
        {field: 'id', title: '编号', align: 'center', width: 140},
        {field: 'name', title: '操作用户名',  align: 'center', width: 100},
        {field: 'operation', title: '操作名称',  align: 'center',width: 130},
        {field: 'url', title: '请求url', align: 'left', width: 250},
        {field: 'ip', title: '客户端ip',align: 'center',width: 150},
        {field: 'data', title: '请求数据', align: 'left',width: 300},
        {field: 'method', title: '请求方法', align: 'left',width: 300 },
        {field: 'cost', title: '执行耗时', width: 120, align: 'center' ,templet:function(d){
                    return d.cost+'毫秒';}},
        {field: 'createDate', title: '操作日期', align: 'center',width: 200 ,templet:function(d){
                return dateFormat(d.createDate,"yyyy-MM-dd hh:mm:ss");}},
        {title: '操作', width: 100, align: 'center', toolbar: '#operBar', fixed: 'right'}
    ]];
var layRenderParam=
    {
        title:"日志表",
        elem:'#systemLogtable',
        id:'systemLogtable',
        cols:tableClls,
        page: true,
        even: true,
        parseData:func_parase_data,
        response: {statusCode:0},
        request:
            {
                pageName: 'pageNo',
                limitName: 'pageSize'
            }
    } ;
ltable.render(layRenderParam);
//修改
function update(cid) {
    $("#divDialog textarea").val("");
    $("#divDialog input").val("");
    $("#divDialog input").attr("readOnly",true);
    $("#divDialog textarea").attr("readOnly",true);
    if(cid){
        if(!fillData(cid))
        {
            return ;
        }
    }
    g_index=layer.open({btn: ['取消'],btn1:func_cancel,btnAlign: 'c',area:['800px', '720px'],type: 1,content: $('#divDialog'),cancel: function(){
        $("#divDialog").css({"display":"none"})
     }});
}

function func_cancel(index)
{
    layer.close(index);
    $("#divDialog").hide();
}

function fillData(id)
{
    var bRet = true;
    var url=appendCtx('/ep/sys/system-logs/')+id;
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
                $("#divDialog input[name='div_userName']").val(respData.name);
                $("#divDialog input[name='operation']").val(respData.operation);
                $("#divDialog input[name='ip']").val(respData.ip);
                $("#divDialog textarea[name='data").val(respData.data);
                $("#divDialog textarea[name='method']").val(respData.method);
                $("#divDialog input[name='cost']").val(respData.cost+"毫秒");
                $("#divDialog input[name='url']").val(respData.url);
                $("#divDialog input[name='div_createDate']").val(dateFormat(respData.createDate,"yyyy-MM-dd hh:mm:ss"));
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