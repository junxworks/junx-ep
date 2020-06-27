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
	this.broadcast=function(topic,obj){
		var channel=this.channels.get(topic);
		//console.log(channel)
		if(channel!=null&&channel!=undefined){
			channel.forEach(function(v,k,map){
				v.onEvent(obj);
			});
		}
	}
};

