var reservedIndex=1;
layui.define(['element', 'layer', 'jquery'], function (drdrdr) {
    dictionaryInit();//初始化数据字典
    var menu = [], tabFilter, liIndex, curNav, delMenu;

    var element = layui.element;
    var layer = layui.layer;
    var $ = layui.jquery;
    var Tab = function () {
        this.tabConfig = {
            openTabNum: undefined,
            tabFilter: undefined,
            isRefreshCacheTab: undefined,
            ajaxSettings: {
                url: appendCtx("/ep/sys/menus/menus-bar"),
                type: 'get'
            }
        }
    };

    //需要渲染左侧菜单的html
    function navBar(strData) {


        //图标字符转换为html
        var convertIconHtml = function (iconStr) {
            var openTitle = '';
            if (iconStr) {
                if (iconStr.split("-")[0] == 'icon') {
                    openTitle += '<i class="' + iconStr + '" class="iconfont ' + iconStr + '"></i>';
                } else {
                    if (iconStr && (iconStr.length > 3) && (iconStr.indexOf('&') === -1)) {
                        openTitle += '<i class="' + iconStr + '"></i>';
                    } else {
                        openTitle += '<i class="' + iconStr + '"></i>';
                    }
                }
            } else {
                openTitle += '<i class="layui-icon"></i>';
            }
            return openTitle;
        }


        //递归获取子菜单html
        var getChildrenHtml = function (item,index) {       
            if (item.children != undefined && item.children.length > 0) {
		        var ulHtml = '<li class="layui-nav-item ">';
	                ulHtml += '<a title="' + item.name + '" href="javascript:void(0)">';
	
	                if(index>0){
	                	var s="";
	                	for(var ii=0;ii<index;ii++){
	                		s=s+"-";
	                	}
	                	 ulHtml +="|"+s;
	                }

	                ulHtml += convertIconHtml(item.icon);
	                ulHtml += '<cite>' + item.name + '</cite>';
	                ulHtml += '<span class="layui-nav-more"></span>';
	                ulHtml += '</a>';
	                ulHtml += '<dl class="layui-nav-child">';
	
	                for (var i = 0; i < item.children.length; i++) {
	                    ulHtml += getChildrenHtml(item.children[i],index+1);
	                }
	
	                ulHtml += "</dl>";
	                ulHtml += '</li>';
	                return ulHtml;
            }
            else {
	        var ulHtml = '<dd>';
            	var itemUrl="";
            	if(item.url!=null){
            		if(item.url.indexOf("http")==0){
            			itemUrl=item.url;
	            	}else{
	            		itemUrl=appendCtx(item.url);
	            	}
            	}
                if (item.target) {
                    ulHtml += '<a title="' + item.name + '" href="' + itemUrl + '" data-url="' + itemUrl + '" target="' + item.target + '">';
                } else {
                    ulHtml += '<a title="' + item.name + '" href="' + itemUrl + '" data-url="' + itemUrl + '">';
                }
                if(index>1){
                	var s="";
                	for(var ii=0;ii<index+1;ii++){
                		s=s+"&nbsp;";
                	}
                    ulHtml += s;
                }
                ulHtml += convertIconHtml(item.icon);
                ulHtml += '<cite>' + item.name + '</cite></a>';
                ulHtml += '</dd>';
            }
            return ulHtml;
        }


        //菜单解析
        var data;
        if (typeof (strData) == "string") {
            var data = eval("(" + strData + ")"); //部分用户解析出来的是字符串，转换一下
        } else {
            data = strData;
        }
        var ulHtml = '<ul class="layui-nav layui-nav-tree"  lay-filter="magetree">';
        for (var i = 0; i < data.length; i++) {
            ulHtml += getChildrenHtml(data[i],0);
        }
        ulHtml += '</ul>';
        return ulHtml;
    }


    //获取二级菜单数据
    Tab.prototype.render = function () {

        var _this = this;
        var ajaxSettings = _this.tabConfig.ajaxSettings;

        ajaxSettings.success = function (res) {

            if (typeof (res) == "string") {
                //如果是字符格式则转为js对象
            	res = JSON.parse(res);
            }
            if(res.code===-2){
            	window.location=appendCtx("/eui/login.html");
            	return;
            }
            if (res.ok) {
                //显示左侧菜单
                if ($(".navBar").html() == '') {
                    var htmlyem = navBar(res.data);
                    $(".navBar").html(htmlyem);
                    element.render();  //初始化页面元素

                    var openTabTitle = $('#top_tabs li.layui-this').first().find('cite').text();
                    if (openTabTitle) {
                        _this.selectedMenu(openTabTitle);
                    }
                }
            } else {
                layer.msg((res.msg || '加载错误'), { anim: 10, icon: 0 });
            }

        }

        ajaxSettings.error = function (ex) {

            console.warn(ex.responseText);

        }
        
        $.ajax(ajaxSettings);
    }

    //参数设置
    Tab.prototype.set = function (option) {
        var _this = this;
        $.extend(true, _this.tabConfig, option);

        //渲染左侧菜单
        _this.render();

        return _this;
    };

    //通过title选中菜单
    Tab.prototype.selectedMenu = function (title) {
        $(".navBar a.layui-this,.navBar dd.layui-this,.navBar li.layui-this").removeClass('layui-this');
        $('.navBar li.layui-nav-item').removeClass('layui-nav-itemed');
        $('.navBar a[title="' + title + '"]').first().addClass('layui-this').parent().addClass('layui-this').parents('li.layui-nav-item').addClass('layui-nav-itemed');
    }

    //通过title获取lay-id
    Tab.prototype.getLayId = function (title) {
        $(".layui-tab-title.top_tab li").each(function () {
            if ($(this).find("cite").text() == title) {
                layId = $(this).attr("lay-id");
                return false;
            }
        })
        return layId;
    }
    //通过title判断tab是否存在
    Tab.prototype.hasTab = function (title) {
        var tabIndex = -1;
        $(".layui-tab-title.top_tab li").each(function () {
            if ($(this).find("cite").text() == title) {
                tabIndex = 1;
                return false;
            }
        })
        return tabIndex;
    }

    //右侧内容tab操作
    var tabIdIndex = 0;
    Tab.prototype.tabAdd = function (_this) {
        if (window.sessionStorage.getItem("menu")) {
            menu = JSON.parse(window.sessionStorage.getItem("menu"));
        }
        var that = this;
        var openTabNum = that.tabConfig.openTabNum;
        tabFilter = that.tabConfig.tabFilter;

        if (_this.attr("target")) {

            //创建一个a标签然后js执行点击跳转
            var aElemant = document.createElement('a');
            aElemant.href = _this.attr("data-url");
            aElemant.target = _this.attr("target");
            aElemant.style.display = 'none';
            aElemant.onclick = function (event) { event.stopPropagation(); }
            document.body.appendChild(aElemant);
            //document.querySelector("body").appendChild(aElemant);
            aElemant.click();
            //document.querySelector("body").removeChild(aElemant);
            document.body.removeChild(aElemant);

        } else if (_this.attr("data-url") != undefined) {
        	var sessionId=sessionStorage.getItem("_SSOID");
            var __url = _this.attr("data-url");
            if(__url.indexOf("http")==0){
         	   if(__url.indexOf("?")>0){
             	   __url=__url+"&_SSOID="+sessionId;
         	   }else{
             	   __url=__url+"?_SSOID="+sessionId;
         	   }
            }
        	
            var title = '';
            if (_this.find("i")) {
                title += '<i class="'+_this.find("i").attr('class')+'" style="height: 28px"></i>';
            } else {
                title += '<i class="layui-icon"></i>';
            }
            //已打开的窗口中不存在
            if (that.hasTab(_this.find("cite").text()) == -1 && _this.siblings("dl.layui-nav-child").length == 0) {
                if ($(".layui-tab-title.top_tab li").length == openTabNum) {
                    layer.msg('只能同时打开' + openTabNum + '个选项卡哦。不然系统会卡的！');
                    return;
                }
                tabIdIndex++;
                title += '<cite>' + _this.find("cite").text() + '</cite>';
                title += '<i class="layui-icon layui-unselect layui-tab-close" data-id="' + tabIdIndex + '">&#x1006;</i>';

                var getTimeNew = (new Date().getTime());
                element.tabAdd(tabFilter, {
                    title: title,
                    content: "<iframe id='laftMenu" + getTimeNew + "' frameborder='0' src='" + __url + "' data-id='" + tabIdIndex + "' style='padding: 0px; margin: 0px; border-width: 0px;' ></frame>",
                    id: getTimeNew
                })
                //当前窗口内容
                var curmenu = {
                    "icon": _this.find("i").attr('class'),
                    "title": _this.find("cite").text(),
                    "href": __url,
                    "layId": getTimeNew
                }
                menu.push(curmenu);
                window.sessionStorage.setItem("menu", JSON.stringify(menu)); //打开的窗口
                window.sessionStorage.setItem("curmenu", JSON.stringify(curmenu));  //当前的窗口
                element.tabChange(tabFilter, that.getLayId(_this.find("cite").text()));
                that.tabMove(); //顶部窗口是否可滚动

                //刷新iframe
                var __thisIframe = document.getElementById("laftMenu" + getTimeNew);
                __thisIframe.src = __thisIframe.src;
                // __thisIframe.contentWindow.location.reload(true);
            } else {
                //当前窗口内容
                var curmenu = {
                    "icon": _this.find("i").attr('class'),
                    "title": _this.find("cite").text(),
                    "href": __url
                }
                window.sessionStorage.setItem("curmenu", JSON.stringify(curmenu));  //当前的窗口
                element.tabChange(tabFilter, that.getLayId(_this.find("cite").text()));
                that.tabMove(); //顶部窗口是否可滚动
            }
        }
    }

    //监听切换tab设置当前选中tab
    Tab.prototype.monitorSwitchTab = function (htmlElement) {
        //切换后获取当前窗口的内容
        var curmenu = '';
        var menu = JSON.parse(window.sessionStorage.getItem("menu"));
        tabFilter = this.tabConfig.tabFilter;

        curmenu = menu[$(htmlElement).index() - 1];
        if ($(htmlElement).index() == 0) {
            window.sessionStorage.setItem("curmenu", '');
        } else {
            window.sessionStorage.setItem("curmenu", JSON.stringify(curmenu));
            if (window.sessionStorage.getItem("curmenu") == "undefined") {
                //如果删除的不是当前选中的tab,则将curmenu设置成当前选中的tab
                if (curNav != JSON.stringify(delMenu)) {
                    window.sessionStorage.setItem("curmenu", curNav);
                } else {
                    window.sessionStorage.setItem("curmenu", JSON.stringify(menu[liIndex - 1]));
                }
            }
        }
        element.tabChange(tabFilter, $(htmlElement).attr("lay-id")).init();
    }

    //关闭tab监听
    Tab.prototype.monitorCloseTab = function (htmlElement) {
        //如果变量里和缓存里都没有菜单则直接刷新页面
        if ((menu === undefined || menu.length === 0) && (window.sessionStorage.getItem("menu") === null || window.sessionStorage.getItem("menu") === undefined)) {
            window.location.reload();
            return;
        }
        tabFilter = this.tabConfig.tabFilter;

        //删除tab后重置session中的menu和curmenu
        liIndex = $(htmlElement).parent("li").index();
        var menu = JSON.parse(window.sessionStorage.getItem("menu"));
        //获取被删除元素
        delMenu = menu[liIndex - 1];
        var curmenu = window.sessionStorage.getItem("curmenu") == "undefined" ? undefined : window.sessionStorage.getItem("curmenu") == "" ? '' : JSON.parse(window.sessionStorage.getItem("curmenu"));
        if (JSON.stringify(curmenu) != JSON.stringify(menu[liIndex - 1])) {  //如果删除的不是当前选中的tab
            // window.sessionStorage.setItem("curmenu",JSON.stringify(curmenu));
            curNav = JSON.stringify(curmenu);
        } else {
            if ($(htmlElement).parent("li").length > liIndex) {
                window.sessionStorage.setItem("curmenu", curmenu);
                curNav = curmenu;
            } else {
                window.sessionStorage.setItem("curmenu", JSON.stringify(menu[liIndex - 1]));
                curNav = JSON.stringify(menu[liIndex - 1]);
            }
        }
        menu.splice((liIndex - 1), 1);
        window.sessionStorage.setItem("menu", JSON.stringify(menu));
        element.tabDelete(tabFilter, $(htmlElement).parent("li").attr("lay-id")).init();
        this.tabMove();
    }

    //刷新还原tab
    Tab.prototype.refreshRestoreTab = function () {
        tabFilter = this.tabConfig.tabFilter;

        //是否需要还原tab
        if (this.tabConfig.isRefreshCacheTab === false) {
            window.sessionStorage.removeItem("menu");
            return;
        }
        menu = JSON.parse(window.sessionStorage.getItem("menu"));
        //不为空的时候
        if (menu.length > 0) {
            //主页必须每次还原都要刷新
            $('#top_tabs i').attr('layuiTabTypeOpen', 'notNewTab');

            curmenu = window.sessionStorage.getItem("curmenu");
            var openTitle = '';
            for (var i = 0; i < menu.length; i++) {
                openTitle = '';
                if (menu[i].icon) {
                    if (menu[i].icon.split("-")[0] == 'icon') {
                        openTitle += '<i class="iconfont ' + menu[i].icon + '"></i>';
                    } else {
                        var layuiIconStr = menu[i].icon;
                        // if (layuiIconStr && (layuiIconStr.length > 3) && (layuiIconStr.indexOf('&') === -1)) {
                        //     openTitle += '<i class="layui-icon ' + layuiIconStr + '"></i>';
                        // } else {
                        //     openTitle += '<i class="layui-icon">' + menu[i].icon + '</i>';
                        // }
                        openTitle += '<i class="' + menu[i].icon + '" style="height: 28px"></i>';
                    }
                } else {
                    openTitle += '<i class="layui-icon"></i>';
                }
                openTitle += '<cite>' + menu[i].title + '</cite>';
                openTitle += '<i class="layui-icon layui-unselect layui-tab-close" layuiTabTypeOpen="notNewTab" data-id="' + menu[i].layId + '">&#x1006;</i>';
                element.tabAdd(tabFilter, {
                    title: openTitle,
                    content: "<iframe src='" + menu[i].href + "' data-id='" + menu[i].layId + "'></iframe>",
                    id: menu[i].layId
                });
                //定位到刷新前的窗口
                if (curmenu != "undefined") {
                    if (curmenu == '' || curmenu == "null" || JSON.parse(curmenu).title == '主页') {  //定位到后台首页
                        element.tabChange(tabFilter, '');
                        $('#top_tabs i').removeAttr('layuiTabTypeOpen');
                    } else if (JSON.parse(curmenu).title == menu[i].title) {  //定位到刷新前的页面
                        element.tabChange(tabFilter, menu[i].layId);
                    }
                    $("[data-id='" + menu[i].layId + "']").removeAttr('layuiTabTypeOpen');
                } else {
                    var lastMenu = menu[menu.length - 1];
                    element.tabChange(tabFilter, lastMenu.layId);
                    $("[data-id='" + lastMenu.layId + "']").removeAttr('layuiTabTypeOpen');
                }
            }
            //渲染顶部窗口
            this.tabMove();
        }
    }

    //关闭其他tab
    Tab.prototype.CloseOtherTab = function () {
        tabFilter = this.tabConfig.tabFilter;

        if ($("#top_tabs li").length > (reservedIndex+1) && $("#top_tabs li.layui-this cite").text() != "主页") {
            menu = JSON.parse(window.sessionStorage.getItem("menu"));
            $("#top_tabs li").each(function () {
                if ($(this).attr("lay-id") != '' && !$(this).hasClass("layui-this")) {
                    element.tabDelete(tabFilter, $(this).attr("lay-id")).init();
                    //此处将当前窗口重新获取放入session，避免一个个删除来回循环造成的不必要工作量
                    for (var i = 0; i < menu.length; i++) {
                        if ($("#top_tabs li.layui-this cite").text() == menu[i].title) {
                            menu.splice(0, menu.length, menu[i]);
                        }
                    }
                }
            });
            window.sessionStorage.setItem("menu", JSON.stringify(menu));
        } else if ($("#top_tabs li.layui-this cite").text() == "主页" && $("#top_tabs li").length > reservedIndex) {
            $("#top_tabs li").each(function () {
                if ($(this).attr("lay-id") != '' && !$(this).hasClass("layui-this")) {
                    element.tabDelete(tabFilter, $(this).attr("lay-id")).init();
                    window.sessionStorage.removeItem("menu");
                    window.sessionStorage.removeItem("curmenu");
                    menu = [];
                    return false;
                }
            });
        } else {
            layer.msg("没有可以关闭的窗口了@_@");
        }
        //渲染顶部窗口
        this.tabMove();
    }

    //关闭当前窗口
    Tab.prototype.CloseCurrentTab = function () {
        tabFilter = this.tabConfig.tabFilter;
        var menu = JSON.parse(window.sessionStorage.getItem("menu"));
        var layId;
        var ltitle;
        $("#top_tabs li").each(function () {
                if ($(this).attr("lay-id") != '' && $(this).hasClass("layui-this")) {
                	layId=$(this).attr("lay-id");
                	ltitle=$("#top_tabs li.layui-this cite").text();
                }
            });
        element.tabDelete(tabFilter, layId).init();
        //此处将当前窗口重新获取放入session，避免一个个删除来回循环造成的不必要工作量
        var nmenu=[];
        var x=0;
        var nextMenu;
        for (var i = 0; i < menu.length; i++) {
            if (ltitle != menu[i].title) {
            	nmenu[x++]=menu[i];
            }else{
            	if(i==menu.length-1){//如果关闭元素是最后一个，则下一个元素取上一个元素
            		if(menu.length==1){
            			//如果没有上一个元素，则为空
            			nextMenu=null;
            		}else{
            			nextMenu=menu[i-1];
            		}
            	}else{
            		//如果不是最后一个元素，则取下一个元素
            		nextMenu=menu[i+1];
            	}
            }
        }
        window.sessionStorage.setItem("menu", JSON.stringify(nmenu));
        if(nextMenu==null){
            sessionStorage.removeItem("curmenu");
        }else{
        	sessionStorage.setItem("curmenu",JSON.stringify(nextMenu));
        }
        //渲染顶部窗口
        this.tabMove();
    }
    
    //关闭全部tab
    Tab.prototype.CloseAllTab = function () {
        tabFilter = this.tabConfig.tabFilter;

        if ($("#top_tabs li").length > reservedIndex) {
            $("#top_tabs li").each(function () {
                if ($(this).attr("lay-id") != '') {
                    element.tabDelete(tabFilter, $(this).attr("lay-id")).init();
                }
            });
            window.sessionStorage.removeItem("menu");
            window.sessionStorage.removeItem("curmenu");
            menu = [];
        } else {
            layer.msg("没有可以关闭的窗口了@_@");
        }
        //渲染顶部窗口
        this.tabMove();
    }

    //顶部窗口移动
    Tab.prototype.tabMove = function () {
        $(window).on("resize", function () {
            var topTabsBox = $("#top_tabs_box"),
                topTabsBoxWidth = $("#top_tabs_box").width(),
                topTabs = $("#top_tabs"),
                topTabsWidth = $("#top_tabs").width(),
                tabLi = topTabs.find("li.layui-this"),
                top_tabs = document.getElementById("top_tabs");;

            if (topTabsWidth > topTabsBoxWidth) {
                if (tabLi.position().left > topTabsBoxWidth || tabLi.position().left + topTabsBoxWidth > topTabsWidth) {
                    topTabs.css("left", topTabsBoxWidth - topTabsWidth);
                } else {
                    topTabs.css("left", -tabLi.position().left);
                }
                //拖动效果
                var flag = false;
                var cur = {
                    x: 0,
                    y: 0
                }
                var nx, dx, x;
                function down(event) {
                    flag = true;
                    var touch;
                    if (event.touches) {
                        touch = event.touches[0];
                    } else {
                        touch = event;
                    }
                    cur.x = touch.clientX;
                    dx = top_tabs.offsetLeft;
                }
                function move(event) {
                    var self = this;
                    window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
                    if (flag) {
                        var touch;
                        if (event.touches) {
                            touch = event.touches[0];
                        } else {
                            touch = event;
                        }
                        nx = touch.clientX - cur.x;
                        x = dx + nx;
                        if (x > 0) {
                            x = 0;
                        } else {
                            if (x < topTabsBoxWidth - topTabsWidth) {
                                x = topTabsBoxWidth - topTabsWidth;
                            } else {
                                x = dx + nx;
                            }
                        }
                        top_tabs.style.left = x + "px";
                        //阻止页面的滑动默认事件
                        document.addEventListener("touchmove", function (event) {
                            event.preventDefault();
                        }, false);
                    }
                }
                //鼠标释放时候的函数
                function end() {
                    flag = false;
                }
                //pc端拖动效果
                topTabs.on("mousedown", down);
                topTabs.on("mousemove", move);
                $(document).on("mouseup", end);
                //移动端拖动效果
                topTabs.on("touchstart", down);
                topTabs.on("touchmove", move);
                topTabs.on("touchend", end);
            } else {
                //移除pc端拖动效果
                topTabs.off("mousedown", down);
                topTabs.off("mousemove", move);
                topTabs.off("mouseup", end);
                //移除移动端拖动效果
                topTabs.off("touchstart", down);
                topTabs.off("touchmove", move);
                topTabs.off("touchend", end);
                topTabs.removeAttr("style");
                return false;
            }
        }).resize();

    }

    var bodyTab = new Tab();

    //将bodyTab给layui模块化加载
    layui.bodyTab = bodyTab;
    drdrdr('bodyTab', bodyTab);

});//layui.define的结尾


function dictionaryInit() {
    var DICT_PREFIX="$global_dictionary_";
    $.ajax({
        url: appendCtx("/ep/sys/parentCode/dictionaries")
        ,type: "get"
        ,sync: false
        ,success: function(res){
            if(res.ok){
                for(var i in res.data) {
                    var dic = res.data[i];
                    var datalist = JSON.stringify(dic.list);
                    window.sessionStorage.setItem(DICT_PREFIX + dic.parentCode, datalist);
                }
            }
        }
    });
}
