//事件总线
function EventBus(){
	this.channels=new Map();
	this.register=function(topic,cmpName,listener){
		var channel=this.channels.get(topic);
		if(channel!=null&&channel!=undefined){
			channel.set(cmpName,listener);
		}else{
			channel = new Map();
			channel.set(cmpName,listener);
			this.channels.set(topic,channel);
		}
	};
	this.broadcast=function(topic,eventContext){
		var channel=this.channels.get(topic);
		if(channel!=null&&channel!=undefined){
			channel.forEach(function(v,k,map){
				v.onEvent(eventContext);
			});
		}
	}
};

/**
 事件总线，用于跨页面通信
 */
var eventBus = new EventBus();

/**
 注册事件监听
 topic：事件主题
 cmpName：主键名称
 listener：监听处理器
 */
function register(topic,cmpName,listener){
	eventBus.register(topic,cmpName,listener);
}

/**
 事件广播
 topic：事件主题
 eventContext: 事件上下文对象
 */
function broadcast(topic,eventContext){
	eventBus.broadcast(topic,eventContext);
}