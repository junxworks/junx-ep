
//$(function () {
//
//    //config的设置是全局的
//    layui.config({
//        base: '../../plugins/layui-extend/' //这是你存放拓展模块的根目录
//    });
//
//    layui.use(['form', 'layer', 'element', 'laydate', 'table', 'echarts','treetable'], function () {
//    	var treetable = layui.treetable;
//        
//        // 渲染表格
//        treetable.render({
//            treeColIndex: 2,          // treetable新增参数
//            treeSpid: -1,             // treetable新增参数
//            treeIdName: 'd_id',       // treetable新增参数
//            treePidName: 'd_pid',     // treetable新增参数
//            treeDefaultClose: true,   // treetable新增参数
//            treeLinkage: true,        // treetable新增参数
//            elem: '#table1',
//            url: appendCtx('/eui/test.json'),
//            cols: [[
//                {type: 'numbers'},
//                {field: 'id', title: 'id'},
//                {field: 'name', title: 'name'},
//                {field: 'sex', title: 'sex'},
//                {field: 'pid', title: 'pid'},
//            ]]
//        });
//
//    });//layui.use的结束括号
//
//});//jquery的结束括号
