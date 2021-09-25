var bodyTab;
//config的设置是全局的
layui.config({
    base: 'plugins/layui-extend/' //这是你存放拓展模块的根目录
}).use(['bodyTab'], function () {
    var element = layui.element;
    var layer = layui.layer;
    bodyTab = layui.bodyTab;
    pageKeepTop();
    bodyTab.set({
        openTabNum: "50",  //最大可打开窗口数量
        tabFilter: "bodyTab",  //layui的element模块事件过滤器
        isRefreshCacheTab: true, //是否刷新页面时缓存tab
        ajaxSettings: {  //ajax参数，与jquery.ajax一致
            url: appendCtx("/ep/sys/menus/menus-bar"),
            type: 'get'
        }
    });
	io.get("/ep/sys/ep-config",function(res){
		var mainPage=res.data.mainPage;
		if(!isNull(mainPage)){
			$("#mainPage",parent.document.body).attr("src",appendCtx(mainPage));
		}
		var systemShortName=res.data.systemShortName;
		if(!isNull(systemShortName)){
    		$("#sysShortName").html(systemShortName);
		}else{
			$("#sysShortName").html("Junx-EP开发平台");
		}
		var systemName=res.data.systemName;
		document.title=systemName;
	});
	$.ajax({
		url: appendCtx("/ep/sys/users/current-user")
		,type: "get"
		,success: function(res){
			if(res.ok){
				$("#userName").html(res.data.name);
				$("#userNameAcc").val(res.data.username);
                $("#userId").val(res.data.id);
                sessionStorage.setItem("_u_",JSON.stringify(res.data));
               var authorizations= res.data.authorizations;
               if(authorizations!=null){
                   sessionStorage.setItem("authorizations",JSON.stringify(authorizations));
               }
			}
			else if(res.code==4){
				$("#userName").html(res.data.name);
				$("#userNameAcc").val(res.data.username);
                $("#userId").val(res.data.id);
				//需要重置密码
            	sessionStorage.setItem("_u_",JSON.stringify(res.data));
				resetPassPage();
			}else{
				 layer.msg(res.msg, { anim: 10, icon: 0 });
			}
		}
	});
    //退出
    $(".signOut").click(function (event) {
        event.preventDefault();
        layui.layer.load(2, { shade: [0.9, '#FFF'], content: '<div style="width:100px;line-height:32px;text-align:right;">正在退出...</div>' });
        //延迟一下使得用户能直观的感受到系统做了什么不得了的事（一本正经）
        $.ajax({
			url: appendCtx("/ep/sys/logout")
			,dataType: "JSON"
			,contentType: "application/json;charset=UTF-8"
			,type: "POST"
			,success: function(res){
				top.sessionStorage.clear();
				if(res.ok){
					setTimeout("top.location.replace('login.html')", "1000");
				}else{
					 layer.msg(res.msg, { anim: 10, icon: 0 });
				}
			}
		});
    });

    //隐藏左侧导航
    $(".hideMenu").click(function () {
        $(".layui-layout-admin").toggleClass("showMenu");
        //渲染顶部窗口
        bodyTab.tabMove();
        //layer.msg("如果页面显示不正常可在右方的页面操作里点击刷新当前页哦~", { time: 900 });
    });

    //手机设备的简单适配
    $('.site-tree-mobile').on('click', function () {
        $('body').addClass('site-mobile');
    });
    $('.site-mobile-shade').on('click', function () {
        $('body').removeClass('site-mobile');
    });

    //刷新当前
    $(".refresh").on("click", function () {
        //获取当前打开的元素
        var showElement = $(".clildFrame .layui-tab-item.layui-show").find("iframe")[0];
        //手动设置一下src刷新
        showElement.src = showElement.src;
        //从缓存取页面刷新
        showElement.contentWindow.location.reload(false);
        //从服务器重新取页面刷新
        showElement.contentWindow.location.reload(true);
        //跳转式刷新
        showElement.contentWindow.location.href = showElement.src;
    });

    //监听切换tab设置当前选中tab
    $(document).on("click", ".top_tab li", function () {
        bodyTab.monitorSwitchTab(this);
    });

    //删除tab，tab关闭 监听
    $(document).on("click", ".top_tab li i.layui-tab-close", function () {
        bodyTab.monitorCloseTab(this);
    });

    // 添加新窗口
    $(document).on("click", ".layui-nav .layui-nav-item a", function (event) {
        event.preventDefault();
        //如果不存在子级
        if ($(this).siblings().length == 0) {
            addTab($(this));
            $('body').removeClass('site-mobile');  //移动端点击菜单关闭菜单层
        }
        $(this).parent("li").siblings().removeClass("layui-nav-itemed");
    });

    //双击时刷新当前窗口
    $(document).on("dblclick", ".layui-nav .layui-nav-item a", function (event) {
        event.preventDefault();
        //如果不存在子级
        if ($(this).siblings().length == 0) {
            $(".refresh")[0].click();
        }
    });

    //刷新后还原打开的窗口
    if (window.sessionStorage.getItem("menu")) {
        bodyTab.refreshRestoreTab();
    }

    //判断是否是刷新来的tab，是的话刷新当前页面
    element.on('tab(bodyTab)', function (data) {
        var notNewTabEle = $(this).find("[layuiTabTypeOpen='notNewTab']");
        if (notNewTabEle[0] != undefined) {
            notNewTabEle.removeAttr('layuiTabTypeOpen');
            $(".refresh")[0].click();
        }

        //切换tab时选中左侧菜单
        bodyTab.selectedMenu($(this).find('cite').html());
    });

    //关闭其他
    $(".closePageOther").on("click", function () {
        bodyTab.CloseOtherTab();
    });
    //关闭全部
    $(".closePageAll").on("click", function () {
        bodyTab.CloseAllTab();
    });

	io.get('/ep/sys/top-items?pageNo=1&pageSize=-1&status=1',function(res){
		var items = res.data.list;
		if(items.length>0){
			for(var i=0,len=items.length;i<len;i++){
				var item = items[i];
				var index = $(".top_menu").children().length-2;
				$('.top_menu li:nth-child('+index+')').after('<li class="layui-nav-item" pc>'+item.itemInnerHtml+'</li>');
				if(!isNull(item.itemJsPath)){
					var oHead = document.getElementsByTagName('HEAD').item(0);
						var oScript = document.createElement( "script" );
						oScript.language = "javascript";
						oScript.type = "text/javascript";
						oScript.defer = true;
						oScript.src = appendCtx(item.itemJsPath);
						oHead.appendChild(oScript);
				}
				if(!isNull(item.itemCssPath)){
					var oHead = document.getElementsByTagName('HEAD').item(0);
						var oCss = document.createElement( "link" );
						oCss.rel = "stylesheet";
						oCss.defer = true;
						oCss.href = appendCtx(item.itemCssPath);
						oHead.appendChild(oCss);
				}
			}
		}
	});
	
});

function resetPassPage() {
	var _u = sessionStorage.getItem("_u_");
	var user=JSON.parse(_u);
    layer.open({
        title: false,
        type: 1,
        content: '	<div class="admin-header-lock" id="lock-box">' +
            '<div class="admin-header-lock-img"><img src="' + localStorage.getItem("userIconAs") + '"  onerror="javascript:this.src=\'images/face.jpg\'" /></div>' +
            '<div class="admin-header-lock-name" id="lockUserName">' + user.name + '</div>' +
            '<div class="input_btn">' +
            '<input type="password" class="admin-header-lock-input layui-input" autocomplete="off" placeholder="初次登陆请修改密码" id="newPwd" />' +
            '<button class="layui-btn layui-btn-normal" style="width: 80px;" id="resetPass">确定</button>' +
            '</div>' +
            '<p>为了安全考虑，请修改初始密码</p>' +
            '</div>',
        closeBtn: 0,
        shade: 0.9,
        zIndex: 19891020
    });
}

$(document).on("click", "#resetPass", function () {
	var _u = sessionStorage.getItem("_u_");
	var user=JSON.parse(_u);
    var thisEle = this;
    var newPwd = $("#newPwd").val();
    if (isNull(newPwd)) {
        layer.msg("请输入密码！", { anim: 10, icon: 0, zIndex: 19991014 });
        $(thisEle).siblings(".admin-header-lock-input").focus();
    } else if(newPwd=='123456'){
        layer.msg("请不要输入初始密码！", { anim: 10, icon: 0, zIndex: 19991014 });
        $(thisEle).siblings(".admin-header-lock-input").focus();
	}else {
        $(thisEle).addClass("layui-btn-disabled").prop("disabled", true).text("重置中…");
        $.ajax({
            url: appendCtx("/ep/sys/users/pass")
            , type: "put"
            , async: true
            , data: JSON.stringify({
            	"id":user.id,
            	"pass":createMD5(newPwd)
            })
            , contentType: "application/json;charset=UTF-8"
            , success: function (res) {
                if (res.ok) {
                    layer.closeAll("page");
                    layer.msg("修改成功.", { anim: 10, icon: 1, zIndex: 19991014 });
                    //如果没有菜单则刷新
                    if ($('.navBar').html() == '') {
                        //从缓存取页面刷新
                        window.location.reload(false);
                        //从服务器重新取页面刷新
                        window.location.reload(true);
                        //跳转式刷新
                        window.location.href = window.location.href;
                    }
                } else {
                    layer.msg(res.msg, {anim: 10, icon: 0});
                }
            }
        });

    }
});

/**
 * 打开新窗口
 * @param {object} _this jquery元素 
 */
function addTab(_this) {
    bodyTab.tabAdd(_this);
}

/**
 * 打开新tab
 * @param {string} url 要打开的地址
 * @param {string} title 显示的标题
 * @param {string} icon 显示的图标
 */
function addTabByUrlTitleIcon(url, title, icon) {
    var html = '<i data-url="' + url + '" ><cite>' + title + '</cite><i class="' + icon + '"></i></i>';
    bodyTab.tabAdd($(html));
}

/**
 * 关闭当前的tab页
 * @returns
 */
function closeCurrentTab(){
	bodyTab.CloseCurrentTab();
}