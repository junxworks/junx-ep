### junx-ep
现在这些前端开发流行MVVM项目，做了很多优秀的前端框架，像Angularjs、vue、ant-design等等，确实对于前端开发来说是福音，但是这些框架对后端开发人员很不友好，简单的全栈式开发框架似乎成为了历史。因此在目前这个后端开发人员做前端开发越来越难的时候，我决心做一款适合后端开发人员使用的全栈式开发框架，一键式开源Java开发框架junx-ep（简称EP框架），提供了一整套web应用开发技术栈，EP框架是基于springboot+layui做的全栈式开发框架（前后端分离，layui组件可以替换成其他框架，这里只是推荐使用layui），轻巧易用，模块化开发框架，按需加载，只需要几个注解，就能拥有Web应用系统基础功能模块与开发框架，由于本人懒且谨慎，因此设计本框架的原则就是“在安全可靠的情况下，能少写一行代码是一行代码”，在本框架下开发出来的系统，代码量比传统的要少很多（对比过本公司其他项目，整体代码量少三分之一左右，当然还得看程序猿本身素养，素养差的就算把屠龙刀给他，他也只会拿刀背去砍）。框架实现上前后端完全分类，目前提供的前端是基于Layui+JQuery技术栈实现，逻辑简单，方便快捷，是后端开发人员的福音，适合个人和中小型研发团队使用。EP框架本身从2019年开发至今，已经使用了多年，经过公司多个项目考验，在此框架上研发功能也方便快捷，后续会根据常用功能不断优化，通常来说只需要升级jar包即可。    

	EP框架的demo体验地址与git地址如下：
    demo URL：  https://www.junxworks.cn/demo/eui/login.html  帐号：test 密码：123
    demo git URL： https://gitee.com/junxworks/junx-ep-demo
    EP源码git URL： https://gitee.com/junxworks/junx-ep
    
### 种子项目    
    种子项目git地址：https://gitee.com/junxworks/junx-ep-seed
    下载种子项目过后，需要进行以下几处调整：
    1、将项目名修改成自己想要的项目名字，新建自己的包路径，将seed项目下的启动类和配置类拷贝过去
    2、调整pom.xml中的项目配置信息
    3、调整log4j2.xml配置
    4、调整application中的上下文配置以及数据库配置
    以上改动完成后，基本上就可以开始新的项目编写了，是不是很简单？
　　如果说不用种子项目自己创建项目也行，直接引用junx-ep包，开启对应的注解即可（文章后面有讲），EP包在maven中央仓库有发布，引用如下：

	<dependency>
		<groupId>io.github.junxworks</groupId>
		<artifactId>junx-ep-sys</artifactId>
		<version>1.1.4</version>
	</dependency>

注意，EP框架本身依赖mybatis，目前数据库只支持mysql（或者兼容mysql的DB），springboot框架只支持springboot2.0以上。项目启动完毕后，即可通过地址 http://localhost:端口/上下文/eui/login.html 地址进行项目访问了，初始用户名admin，初始密码123456。
### 更多
参考https://blog.junxworks.cn/articles/2021/02/23/1614077567094.html
