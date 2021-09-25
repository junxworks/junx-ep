function _epUpdateSystemTime(){
	$("#EPSystemTime").html(dateFormat(new Date(),'yyyy-MM-dd hh:mm:ss')) ;
}
setInterval("_epUpdateSystemTime()",1000);
