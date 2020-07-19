function isNull(obj){
	if(obj===0){
		return false;
	}
	return obj == null || typeof(obj) == "undefined" || obj == "undefined" || obj=="" || obj=="null";
}

var io={
	/**
	 * 【settings对象属性】
	 * url:String 发送请求的地址，会在请求地址前加入上下文地址appendCtx(url)
	 * type:String 默认值: "GET"。请求方式 ("POST" 或 "GET")， 默认为 "GET"。注意：其它 HTTP 请求方法，如 PUT 和 DELETE 也可以使用，但仅部分浏览器支持。
	 * data:object 数据
	 * contentType:请求的内容格式，get/delete默认是"application/x-www-form-urlencoded"，post/put默认是"application/json;charset=utf-8"
	 * dataType:String 默认"json",预期服务器应答的数据类型。如果不指定，jQuery 将自动根据 HTTP 包 MIME 信息来智能判断，比如 XML MIME 类型就被识别为 XML。
	 * loading:boolean 是否显示加载图标,默认true
	 * success:fcuntion 请求成功后回调方法
	 * alertSuccess:boolean 是否提示操作成功，GET默认false,POST,PUT,DELETE默认是true
	 * error:fcuntion 请求异常时回调方法
	 * async:boolean 是否是异步请求，默认是true
	 */
		ajax: function(settings){
			if(isNull(settings.url)){
				layer.msg("请求地址URL参数不能为空", {icon: 2});
				return;
			}
			var _settings={
				url:appendCtx(settings.url),
			};
			var type = settings.type;
			if(isNull(type)){
				type="GET";
			}
			_settings.type=type;
			if(settings.data!=null){
				_settings.data=settings.data;
			}
			if(!isNull(settings.contentType)){
				_settings.contentType=settings.contentType;
			}else{
				if(type=="POST"||type=="PUT"){
					_settings.contentType="application/json;charset=utf-8";
				}else{
					_settings.contentType="application/x-www-form-urlencoded";
				}
			}
			if(isNull(settings.dataType)){
				_settings.dataType="json";
			}else{
				_settings.dataType=settings.dataType;
			}
			_settings.async=settings.async;
			_settings.success=function (result){
				layer.closeAll('loading');
  		        if (result.ok) {
  		        	if(settings.alertSuccess){
	  		        	layer.msg("操作成功",{icon:1});
  		        	}
  		        	if(settings.success){
  		        		settings.success(result);
  		        	}
  		        }
  		        else {
  		        	layer.alert(result.msg, {icon: 2});
  		        }
  		    };
  		    _settings.beforeSend=function() {
  		    	if(settings.loading){
  		    		layer.load(2);
  				}
			};
			if(settings.error){
				_settings.error=settings.error;
			}else{
				_settings.error=function(error){
					layer.closeAll('loading');
					layer.alert("\""+settings.url+"\" request error:"+error.responseText, {icon: 2});
				};
			}
			if(settings.needConfirm==true){
				var alertMsg = "是否要进行操作？";
				if(settings.type=="DELETE"){
					alertMsg="是否要进行删除操作？";
				}
				layer.confirm(alertMsg, {icon: 3, title:'提示'}, function(index){
						$.ajax(_settings); 
						layer.close(index);
					});
			}else{
				$.ajax(_settings); 
			}
		},
	get: function(url,successCallback,data,loading){
		this.send(url,"GET",data,successCallback,false,false,loading);
	},
	delete: function(url,successCallback,data,alertSuccess,needConfirm,loading){
		this.send(url,"DELETE",data,successCallback,alertSuccess,needConfirm,loading);
	},
	post: function(url,data,successCallback,alertSuccess,loading){
		this.send(url,"POST",data,successCallback,alertSuccess,false,loading);
	},
	put: function(url,data,successCallback,alertSuccess,loading){
		this.send(url,"PUT",data,successCallback,alertSuccess,false,loading);
	},
	/**
	 * url:String 发送请求的地址，会在请求地址前加入上下文地址appendCtx(url)
	 * data:object 数据
	 * successCallback:fcuntion 请求成功后回调方法
	 * alertSuccess:boolean 是否提示操作成功，GET默认false,POST,PUT,DELETE默认是true
	 * loading:boolean 是否显示加载图标,默认true
	 * async:是否异步请求，默认true
	 */
	send: function(url,type,data,successCallback,alertSuccess,needConfirm,loading,async){
		var settings = {};
		settings.url = url;
		settings.type=type;
		if(data){
			settings.data=data;
		}
		if(successCallback){
			settings.success=successCallback;
		}
		if(typeof(alertSuccess) == "undefined" || alertSuccess == "undefined"){
			if(type=="GET"){
				settings.alertSuccess=false;
			}else{
				settings.alertSuccess=true;
			}
		}else{
			settings.alertSuccess=alertSuccess;
		}
		if(typeof(needConfirm) == "undefined" || needConfirm == "undefined"){
			if(type=="DELETE"){
				settings.needConfirm=true;
			}else{
				settings.needConfirm=false;
			}
		}else{
			settings.needConfirm=needConfirm;
		}
		if(typeof(loading) == "undefined" || loading == "undefined"){
			settings.loading=true;
		}else{
			settings.loading=loading;
		}
		if(typeof(async) == "undefined" || async == "undefined"){
			settings.async=true;
		}else{
			settings.async=async;
		}
		this.ajax(settings);
	}
};

/**
 * 获取cookie
 * 
 * @param {string}
 *            key cookie键
 */
function getCookie(key) {
	var e = key;
	for (var t = e + "=", n = document.cookie.split(";"), o = 0; o < n.length; o++) {
		for (var i = n[o]; " " == i.charAt(0);)
			i = i.substring(1, i.length);
		if (0 == i.indexOf(t))
			return unescape(i.substring(t.length, i.length))
	}
	return "";
}

/**
 * 删除指定cookie
 * 
 * @param {string}
 *            key cookie键
 */
function clearCookie(key) {
	var e = key;
	setCookie(e, "", -1);
}

/**
 * 设置cookie
 * 
 * @param {string}
 *            key cookie键
 * @param {string}
 *            value cookie值
 * @param {number}
 *            time cookie过期时间
 */
function setCookie(key, value, time) {
	var e = key, t = value, n = time;
	n = n || 0;
	var o = "";
	if (0 != n) {
		var i = new Date;
		i.setTime(i.getTime() + 1e3 * n), o = "; expires=" + i.toGMTString()
	}
	document.cookie = e + "=" + escape(t) + o + "; path=/";
}

/**
 * 清除所有cookie
 */
function clearAllCookie() {
	var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
	if (keys) {
		for (var i = keys.length; i--;)
			document.cookie = keys[i] + '=0;expires='
					+ new Date(0).toUTCString()
	}
}

/**
 * 生成最小值和最大值之间的随机数(包含最小值最大值)
 * 
 * @param {number}
 *            min 最小值
 * @param {number}
 *            max 最大值
 * @param {boolean}
 *            isDecimal 是否是小数,默认生成整数
 */
function randomNum(min, max, isDecimal) {
	var num = Math.random() * (max - min + 1) + min;
	if (isDecimal === true) {
		return num;
	}
	return parseInt(num, 10);
}

/**
 * 时间戳转换为时间字符串
 * 
 * @param {object}
 *            timestamp 时间戳
 */
function formatDate(timestamp) {
	function add0(m) {
		m = parseInt(m);
		return ((m < 10) ? ('0' + m) : m);
	}
	function format(shijianchuo) {

		if ((typeof shijianchuo) !== "number") {
			shijianchuo = parseInt(shijianchuo);
		}

		if (Date.prototype.getTimezoneOffset.call(new Date) === 0) {
			// 如果是UTC时间，代表用户可能没设置时区，则转换为北京时间。如果不需要请去除该if
			shijianchuo += (8 * 60 * 60 * 1000);
		}

		var time = new Date(shijianchuo);
		var y = time.getFullYear();
		var m = time.getMonth() + 1;
		var d = time.getDate();
		var h = time.getHours();
		var mm = time.getMinutes();
		var s = time.getSeconds();
		return y + '-' + add0(m) + '-' + add0(d) + ' ' + add0(h) + ':'
				+ add0(mm) + ':' + add0(s);
	}
	return format(timestamp);
}

/**
 * 让页面始终显示在最最上层，而不是在iframe中
 */
function pageKeepTop() {
	if (window && top) {
		// 如果window和top对象存在，但是他们不相等
		if (window !== top) {
			// 把当前页面显示在最外面
			top.location.href = window.location.href;
		}
	}
}

/**
 * MD5加密
 * 
 * @param {string}
 *            string 要加密的字符串
 */
function createMD5(string) {

	function RotateLeft(lValue, iShiftBits) {
		return (lValue << iShiftBits) | (lValue >>> (32 - iShiftBits));
	}

	function AddUnsigned(lX, lY) {
		var lX4, lY4, lX8, lY8, lResult;
		lX8 = (lX & 0x80000000);
		lY8 = (lY & 0x80000000);
		lX4 = (lX & 0x40000000);
		lY4 = (lY & 0x40000000);
		lResult = (lX & 0x3FFFFFFF) + (lY & 0x3FFFFFFF);
		if (lX4 & lY4) {
			return (lResult ^ 0x80000000 ^ lX8 ^ lY8);
		}
		if (lX4 | lY4) {
			if (lResult & 0x40000000) {
				return (lResult ^ 0xC0000000 ^ lX8 ^ lY8);
			} else {
				return (lResult ^ 0x40000000 ^ lX8 ^ lY8);
			}
		} else {
			return (lResult ^ lX8 ^ lY8);
		}
	}

	function F(x, y, z) {
		return (x & y) | ((~x) & z);
	}
	function G(x, y, z) {
		return (x & z) | (y & (~z));
	}
	function H(x, y, z) {
		return (x ^ y ^ z);
	}
	function I(x, y, z) {
		return (y ^ (x | (~z)));
	}

	function FF(a, b, c, d, x, s, ac) {
		a = AddUnsigned(a, AddUnsigned(AddUnsigned(F(b, c, d), x), ac));
		return AddUnsigned(RotateLeft(a, s), b);
	}
	;

	function GG(a, b, c, d, x, s, ac) {
		a = AddUnsigned(a, AddUnsigned(AddUnsigned(G(b, c, d), x), ac));
		return AddUnsigned(RotateLeft(a, s), b);
	}
	;

	function HH(a, b, c, d, x, s, ac) {
		a = AddUnsigned(a, AddUnsigned(AddUnsigned(H(b, c, d), x), ac));
		return AddUnsigned(RotateLeft(a, s), b);
	}
	;

	function II(a, b, c, d, x, s, ac) {
		a = AddUnsigned(a, AddUnsigned(AddUnsigned(I(b, c, d), x), ac));
		return AddUnsigned(RotateLeft(a, s), b);
	}
	;

	function ConvertToWordArray(string) {
		var lWordCount;
		var lMessageLength = string.length;
		var lNumberOfWords_temp1 = lMessageLength + 8;
		var lNumberOfWords_temp2 = (lNumberOfWords_temp1 - (lNumberOfWords_temp1 % 64)) / 64;
		var lNumberOfWords = (lNumberOfWords_temp2 + 1) * 16;
		var lWordArray = Array(lNumberOfWords - 1);
		var lBytePosition = 0;
		var lByteCount = 0;
		while (lByteCount < lMessageLength) {
			lWordCount = (lByteCount - (lByteCount % 4)) / 4;
			lBytePosition = (lByteCount % 4) * 8;
			lWordArray[lWordCount] = (lWordArray[lWordCount] | (string
					.charCodeAt(lByteCount) << lBytePosition));
			lByteCount++;
		}
		lWordCount = (lByteCount - (lByteCount % 4)) / 4;
		lBytePosition = (lByteCount % 4) * 8;
		lWordArray[lWordCount] = lWordArray[lWordCount]
				| (0x80 << lBytePosition);
		lWordArray[lNumberOfWords - 2] = lMessageLength << 3;
		lWordArray[lNumberOfWords - 1] = lMessageLength >>> 29;
		return lWordArray;
	}
	;

	function WordToHex(lValue) {
		var WordToHexValue = "", WordToHexValue_temp = "", lByte, lCount;
		for (lCount = 0; lCount <= 3; lCount++) {
			lByte = (lValue >>> (lCount * 8)) & 255;
			WordToHexValue_temp = "0" + lByte.toString(16);
			WordToHexValue = WordToHexValue
					+ WordToHexValue_temp.substr(
							WordToHexValue_temp.length - 2, 2);
		}
		return WordToHexValue;
	}
	;

	function Utf8Encode(string) {
		string = string.replace(/\r\n/g, "\n");
		var utftext = "";

		for (var n = 0; n < string.length; n++) {

			var c = string.charCodeAt(n);

			if (c < 128) {
				utftext += String.fromCharCode(c);
			} else if ((c > 127) && (c < 2048)) {
				utftext += String.fromCharCode((c >> 6) | 192);
				utftext += String.fromCharCode((c & 63) | 128);
			} else {
				utftext += String.fromCharCode((c >> 12) | 224);
				utftext += String.fromCharCode(((c >> 6) & 63) | 128);
				utftext += String.fromCharCode((c & 63) | 128);
			}

		}

		return utftext;
	}
	;

	var x = Array();
	var k, AA, BB, CC, DD, a, b, c, d;
	var S11 = 7, S12 = 12, S13 = 17, S14 = 22;
	var S21 = 5, S22 = 9, S23 = 14, S24 = 20;
	var S31 = 4, S32 = 11, S33 = 16, S34 = 23;
	var S41 = 6, S42 = 10, S43 = 15, S44 = 21;

	string = Utf8Encode(string);

	x = ConvertToWordArray(string);

	a = 0x67452301;
	b = 0xEFCDAB89;
	c = 0x98BADCFE;
	d = 0x10325476;

	for (k = 0; k < x.length; k += 16) {
		AA = a;
		BB = b;
		CC = c;
		DD = d;
		a = FF(a, b, c, d, x[k + 0], S11, 0xD76AA478);
		d = FF(d, a, b, c, x[k + 1], S12, 0xE8C7B756);
		c = FF(c, d, a, b, x[k + 2], S13, 0x242070DB);
		b = FF(b, c, d, a, x[k + 3], S14, 0xC1BDCEEE);
		a = FF(a, b, c, d, x[k + 4], S11, 0xF57C0FAF);
		d = FF(d, a, b, c, x[k + 5], S12, 0x4787C62A);
		c = FF(c, d, a, b, x[k + 6], S13, 0xA8304613);
		b = FF(b, c, d, a, x[k + 7], S14, 0xFD469501);
		a = FF(a, b, c, d, x[k + 8], S11, 0x698098D8);
		d = FF(d, a, b, c, x[k + 9], S12, 0x8B44F7AF);
		c = FF(c, d, a, b, x[k + 10], S13, 0xFFFF5BB1);
		b = FF(b, c, d, a, x[k + 11], S14, 0x895CD7BE);
		a = FF(a, b, c, d, x[k + 12], S11, 0x6B901122);
		d = FF(d, a, b, c, x[k + 13], S12, 0xFD987193);
		c = FF(c, d, a, b, x[k + 14], S13, 0xA679438E);
		b = FF(b, c, d, a, x[k + 15], S14, 0x49B40821);
		a = GG(a, b, c, d, x[k + 1], S21, 0xF61E2562);
		d = GG(d, a, b, c, x[k + 6], S22, 0xC040B340);
		c = GG(c, d, a, b, x[k + 11], S23, 0x265E5A51);
		b = GG(b, c, d, a, x[k + 0], S24, 0xE9B6C7AA);
		a = GG(a, b, c, d, x[k + 5], S21, 0xD62F105D);
		d = GG(d, a, b, c, x[k + 10], S22, 0x2441453);
		c = GG(c, d, a, b, x[k + 15], S23, 0xD8A1E681);
		b = GG(b, c, d, a, x[k + 4], S24, 0xE7D3FBC8);
		a = GG(a, b, c, d, x[k + 9], S21, 0x21E1CDE6);
		d = GG(d, a, b, c, x[k + 14], S22, 0xC33707D6);
		c = GG(c, d, a, b, x[k + 3], S23, 0xF4D50D87);
		b = GG(b, c, d, a, x[k + 8], S24, 0x455A14ED);
		a = GG(a, b, c, d, x[k + 13], S21, 0xA9E3E905);
		d = GG(d, a, b, c, x[k + 2], S22, 0xFCEFA3F8);
		c = GG(c, d, a, b, x[k + 7], S23, 0x676F02D9);
		b = GG(b, c, d, a, x[k + 12], S24, 0x8D2A4C8A);
		a = HH(a, b, c, d, x[k + 5], S31, 0xFFFA3942);
		d = HH(d, a, b, c, x[k + 8], S32, 0x8771F681);
		c = HH(c, d, a, b, x[k + 11], S33, 0x6D9D6122);
		b = HH(b, c, d, a, x[k + 14], S34, 0xFDE5380C);
		a = HH(a, b, c, d, x[k + 1], S31, 0xA4BEEA44);
		d = HH(d, a, b, c, x[k + 4], S32, 0x4BDECFA9);
		c = HH(c, d, a, b, x[k + 7], S33, 0xF6BB4B60);
		b = HH(b, c, d, a, x[k + 10], S34, 0xBEBFBC70);
		a = HH(a, b, c, d, x[k + 13], S31, 0x289B7EC6);
		d = HH(d, a, b, c, x[k + 0], S32, 0xEAA127FA);
		c = HH(c, d, a, b, x[k + 3], S33, 0xD4EF3085);
		b = HH(b, c, d, a, x[k + 6], S34, 0x4881D05);
		a = HH(a, b, c, d, x[k + 9], S31, 0xD9D4D039);
		d = HH(d, a, b, c, x[k + 12], S32, 0xE6DB99E5);
		c = HH(c, d, a, b, x[k + 15], S33, 0x1FA27CF8);
		b = HH(b, c, d, a, x[k + 2], S34, 0xC4AC5665);
		a = II(a, b, c, d, x[k + 0], S41, 0xF4292244);
		d = II(d, a, b, c, x[k + 7], S42, 0x432AFF97);
		c = II(c, d, a, b, x[k + 14], S43, 0xAB9423A7);
		b = II(b, c, d, a, x[k + 5], S44, 0xFC93A039);
		a = II(a, b, c, d, x[k + 12], S41, 0x655B59C3);
		d = II(d, a, b, c, x[k + 3], S42, 0x8F0CCC92);
		c = II(c, d, a, b, x[k + 10], S43, 0xFFEFF47D);
		b = II(b, c, d, a, x[k + 1], S44, 0x85845DD1);
		a = II(a, b, c, d, x[k + 8], S41, 0x6FA87E4F);
		d = II(d, a, b, c, x[k + 15], S42, 0xFE2CE6E0);
		c = II(c, d, a, b, x[k + 6], S43, 0xA3014314);
		b = II(b, c, d, a, x[k + 13], S44, 0x4E0811A1);
		a = II(a, b, c, d, x[k + 4], S41, 0xF7537E82);
		d = II(d, a, b, c, x[k + 11], S42, 0xBD3AF235);
		c = II(c, d, a, b, x[k + 2], S43, 0x2AD7D2BB);
		b = II(b, c, d, a, x[k + 9], S44, 0xEB86D391);
		a = AddUnsigned(a, AA);
		b = AddUnsigned(b, BB);
		c = AddUnsigned(c, CC);
		d = AddUnsigned(d, DD);
	}

	var temp = WordToHex(a) + WordToHex(b) + WordToHex(c) + WordToHex(d);

	return temp.toLowerCase();
}

/**
 * 生成Guid
 */
function createGuid() {
	return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
		var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
		return v.toString(16);
	});
}

/**
 * 下载文件
 * 
 * @param {any}
 *            url 要下载的地址
 * @param {any}
 *            saveName 文件保存的名字 可选参数
 */
function openDownloadDialog(url, saveName) {
	// url = ((window.location.href.toLowerCase().indexOf('probation') == -1) ?
	// '' : '/probation') + url;
	if (typeof url == 'object' && url instanceof Blob) {
		url = URL.createObjectURL(url); // 创建blob地址
	}
	var aLink = document.createElement('a');
	aLink.target = "_blank";
	aLink.href = url;
	aLink.download = saveName || ''; // HTML5新增的属性，指定保存文件名，可以不要后缀
	var event = document.createEvent('MouseEvents');
	event.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false,
			false, false, false, 0, null);
	aLink.dispatchEvent(event);
}

/**
 * base64加密
 * 
 * @param {any}
 *            string
 */
function base64Encode(string) {
	var Base64 = {
		_keyStr : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",
		encode : function(e) {
			var t = "";
			var n, r, i, s, o, u, a;
			var f = 0;
			e = Base64._utf8_encode(e);
			while (f < e.length) {
				n = e.charCodeAt(f++);
				r = e.charCodeAt(f++);
				i = e.charCodeAt(f++);
				s = n >> 2;
				o = (n & 3) << 4 | r >> 4;
				u = (r & 15) << 2 | i >> 6;
				a = i & 63;
				if (isNaN(r)) {
					u = a = 64
				} else if (isNaN(i)) {
					a = 64
				}
				t = t + this._keyStr.charAt(s) + this._keyStr.charAt(o)
						+ this._keyStr.charAt(u) + this._keyStr.charAt(a)
			}
			return t
		},
		_utf8_encode : function(e) {
			e = e.replace(/rn/g, "n");
			var t = "";
			for (var n = 0; n < e.length; n++) {
				var r = e.charCodeAt(n);
				if (r < 128) {
					t += String.fromCharCode(r)
				} else if (r > 127 && r < 2048) {
					t += String.fromCharCode(r >> 6 | 192);
					t += String.fromCharCode(r & 63 | 128)
				} else {
					t += String.fromCharCode(r >> 12 | 224);
					t += String.fromCharCode(r >> 6 & 63 | 128);
					t += String.fromCharCode(r & 63 | 128)
				}
			}
			return t
		}
	}
	return Base64.encode(string);
}

/**
 * base64解密
 * 
 * @param {any}
 *            string
 */
function base64Decode(string) {
	var Base64 = {
		_keyStr : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",
		decode : function(e) {
			var t = "";
			var n, r, i;
			var s, o, u, a;
			var f = 0;
			e = e.replace(/[^A-Za-z0-9+/=]/g, "");
			while (f < e.length) {
				s = this._keyStr.indexOf(e.charAt(f++));
				o = this._keyStr.indexOf(e.charAt(f++));
				u = this._keyStr.indexOf(e.charAt(f++));
				a = this._keyStr.indexOf(e.charAt(f++));
				n = s << 2 | o >> 4;
				r = (o & 15) << 4 | u >> 2;
				i = (u & 3) << 6 | a;
				t = t + String.fromCharCode(n);
				if (u != 64) {
					t = t + String.fromCharCode(r)
				}
				if (a != 64) {
					t = t + String.fromCharCode(i)
				}
			}
			t = Base64._utf8_decode(t);
			return t
		},
		_utf8_decode : function(e) {
			var t = "";
			var n = 0;
			var r = c1 = c2 = 0;
			while (n < e.length) {
				r = e.charCodeAt(n);
				if (r < 128) {
					t += String.fromCharCode(r);
					n++
				} else if (r > 191 && r < 224) {
					c2 = e.charCodeAt(n + 1);
					t += String.fromCharCode((r & 31) << 6 | c2 & 63);
					n += 2
				} else {
					c2 = e.charCodeAt(n + 1);
					c3 = e.charCodeAt(n + 2);
					t += String.fromCharCode((r & 15) << 12 | (c2 & 63) << 6
							| c3 & 63);
					n += 3
				}
			}
			return t
		}
	}
	return Base64.decode(string);
}

/**
 * 网页全屏
 */
function fullscreen() {
	var docElm = document.documentElement;
	if (docElm.requestFullscreen) {
		docElm.requestFullscreen();
	} else if (docElm.mozRequestFullScreen) {
		docElm.mozRequestFullScreen();
	} else if (docElm.webkitRequestFullScreen) {
		docElm.webkitRequestFullScreen();
	} else if (docElm.msRequestFullscreen) {
		docElm.msRequestFullscreen();
	}
}

/**
 * 退出全屏
 */
function exitFullscreen() {
	if (document.exitFullscreen) {
		document.exitFullscreen();
	} else if (document.mozCancelFullScreen) {
		document.mozCancelFullScreen();
	} else if (document.webkitCancelFullScreen) {
		document.webkitCancelFullScreen();
	} else if (document.msExitFullscreen) {
		document.msExitFullscreen();
	}
}

/**
 * 全局上下文对象ctx
 */
var ctx = {
	/**
	 * 获取上下文路径
	 * 
	 * @returns
	 */
	rootPath : function() {
		var ectx=sessionStorage.getItem("epCtx");
		if(!isNull(ectx)){
			return ectx;
		}
		var path = window.document.location.pathname;
		var index = path.substr(0).indexOf("/eui");
		if(index>0){
			ectx=path.substr(0, index)
		}else {
			ectx= "";
		}
		sessionStorage.setItem("epCtx",ectx);
		return ectx;
	}
};

function appendCtx(p) {
	if(p.indexOf("http")==0){
		return p;
	}else{
		return ctx.rootPath() + p;
	}
}

/**
 * 判断一个对象是否在数组中
 * @param arr
 * @param obj
 * @returns
 */
function containsInArray(arr, obj) {
    var i = arr.length;
    while (i--) {
        if (arr[i] === obj) {
            return true;
        }
    }
    return false;
}


/**
 * 获取url参数
 */
function getParam(name) {
	var param_part_arr = decodeURI(window.location.href).split('?');
	if (param_part_arr.length == 1) {
		return "";
	}
	var param_part = param_part_arr[1];
	var paramArr = param_part.split('&');
	for (var i = 0; i < paramArr.length; i++)
	{
		var temp_item = paramArr[i];
		var paramItemArr = temp_item.split('=');
		if (paramItemArr[0] == name)
		{
			return paramItemArr[1];
		}
	}
	return "";
}
/**
 * 日期格式化
 * @param datetime
 * @param fmt
 * @returns
 */
function dateFormat(datetime,fmt) {
	if (parseInt(datetime)==datetime) {
		if (datetime.length==10) {
			datetime=parseInt(datetime)*1000;
		} else if(datetime.length==13) {
			datetime=parseInt(datetime);
		}
	}
	datetime=new Date(datetime);
	var o = {
		"M+" : datetime.getMonth()+1,                 //月份
		"d+" : datetime.getDate(),                    //日
		"h+" : datetime.getHours(),                   //小时
		"m+" : datetime.getMinutes(),                 //分
		"s+" : datetime.getSeconds(),                 //秒
		"q+" : Math.floor((datetime.getMonth()+3)/3), //季度
		"S"  : datetime.getMilliseconds()             //毫秒
	};
	if(/(y+)/.test(fmt))
		fmt=fmt.replace(RegExp.$1, (datetime.getFullYear()+"").substr(4 - RegExp.$1.length));
	for(var k in o)
		if(new RegExp("("+ k +")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
	return fmt;
}

function numberFormat(money, decimals) {
	var tpMoney = '0.00';
	if (money != null) {
		tpMoney = money;
	}
	tpMoney = new Number(tpMoney);
	if (isNaN(tpMoney)) {
		return '0.00';
	}
	tpMoney = tpMoney.toFixed(decimals) + '';
	var re = /^(-?\d+)(\d{3})(\.?\d*)/;
	while (re.test(tpMoney)) {
		tpMoney = tpMoney.replace(re, "$1,$2$3")
	}
	return tpMoney;
}

var DICT_PREFIX="$global_dictionary_";

/**
 * 计算两个数值之和，支持浮点数
 * @param num1
 * @param num2
 * @returns
 */
function numberAdd(num1, num2) { 
	   var baseNum, baseNum1, baseNum2; 
	   try { 
	      baseNum1 = num1.toString().split(".")[1].length; 
	   } catch (e) {  
	     baseNum1 = 0;
	   } 
	   try {
	       baseNum2 = num2.toString().split(".")[1].length; 
	   } catch (e) {
	     baseNum2 = 0; 
	   } 
	   baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));
	   var precision = (baseNum1 >= baseNum2) ? baseNum1 : baseNum2;//精度
	   return ((num1 * baseNum + num2 * baseNum) / baseNum).toFixed(precision);; 
};

/**
 * 计算两个数值之差，支持浮点数
 * @param num1
 * @param num2
 * @returns
 */
function numberSub(num1, num2) {
		var baseNum, baseNum1, baseNum2;
		var precision;// 精度
		try {
		baseNum1 = num1.toString().split(".")[1].length;
		} catch (e) {
		baseNum1 = 0;
		}
		try {
		baseNum2 = num2.toString().split(".")[1].length;
		} catch (e) {
		baseNum2 = 0;
		}
		baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));
		precision = (baseNum1 >= baseNum2) ? baseNum1 : baseNum2;
		return ((num1 * baseNum - num2 * baseNum) / baseNum).toFixed(precision);
		};
		
/**
 * 计算两个数的积
 * @param num1
 * @param num2
 * @returns
 */
function numberMulti(num1, num2) {
	var baseNum = 0;
	try {
	baseNum += num1.toString().split(".")[1].length;
	} catch (e) {
	}
	try {
	baseNum += num2.toString().split(".")[1].length;
	} catch (e) {
	}
	return Number(num1.toString().replace(".", "")) * Number(num2.toString().replace(".", "")) / Math.pow(10, baseNum);
};
		
function numberDiv(num1, num2) {
	var baseNum1 = 0, baseNum2 = 0;
	var baseNum3, baseNum4;
	try {
	baseNum1 = num1.toString().split(".")[1].length;
	} catch (e) {
	baseNum1 = 0;
	}
	try {
	baseNum2 = num2.toString().split(".")[1].length;
	} catch (e) {
	baseNum2 = 0;
	}
	with (Math) {
	baseNum3 = Number(num1.toString().replace(".", ""));
	baseNum4 = Number(num2.toString().replace(".", ""));
	return (baseNum3 / baseNum4) * pow(10, baseNum2 - baseNum1);
	}
}

/**
 * 通过父类型与数据字典值取对应的数据字典翻译，
 * 如果dataCode为空，则返回parentCode对应的数据字典
 * 
 * @param parentCode
 * @returns
 */
function dictionary(parentCode){
	var key = DICT_PREFIX+parentCode;
	var data=sessionStorage.getItem(key);
	if(isNull(data)){
		$.ajax({
			url: appendCtx("/ep/sys/dictionaries?parentCode="+parentCode+"&pageSize=-1&pageNo=0")
			,type: "get"
			,sync: false
			,success: function(res){
				if(res.ok){
					data = JSON.stringify(res.data.list);
					sessionStorage.setItem(key,data);
				}
			}
		});
	}
	if(!isNull(data)){
		var array=JSON.parse(data);
		var map= new Map();
		for(var i = 0; i < array.length; i++){
			var item = array[i];
			map.set(String(item["dataCode"]),String(item["dataLabel"]));
		}
		return map;
	}else{
		return null;
	}
}

/**
 * 数据字典翻译
 * parentCode:类型
 * dataCode:值
 * @returns
 */
function translate(parentCode,dataCode){
	if(isNull(dataCode)){
		return "";
	}
	var dict = dictionary(String(parentCode));
	if(dict!=null){
		return dict.get(String(dataCode));
	}
	return null;
}

/**
 * 数据字典翻译
 * parentCode:类型
 * dataCodes: 数据编码，用逗号隔开
 * @returns
 */
function translateMulti(parentCode,dataCodes){
	if(isNull(dataCodes)){
		return "";
	}
	var res="";
	var strs= new Array();
	strs= dataCodes.split(",");
	for (i=0;i<strs.length ;i++ ) 
	{ 
		if(i>0){
			res =res+","+translate(parentCode,strs[i]);
		}else{
			res=translate(parentCode,strs[i]);
		}
	} 
	return res;
}

/**
 * 将数据字典值设置进下拉组件
 * @param parentCode
 * @param selectorId select组件的id
 * @param exclude 排除在外的编码正则表达式
 * @returns
 */
function dict2Select(parentCode,selectorId,exclude){
	var dict = dictionary(String(parentCode));
	map2Select(dict,selectorId,exclude);
}
/**
 * 
 * @param map key,value的键值对，key表示编码，value表示显示名
 * @param selectorId select组件的id
 * @param exclude 排除在外的编码正则表达式
 * @returns
 */
function map2Select(dataMap,selectorId,exclude){
	if(dataMap!=null){
		$("#"+selectorId).empty();
		$("#"+selectorId).append("<option value=''></option>");
		dataMap.forEach(function(v,k,map){
			if(exclude!=null&&typeof(exclude) != "undefined" ){
				//if(exclude.indexOf(k)<0){
				//console.log("k:"+k+" reg:"+exclude+" res:"+k.match(exclude))
				if(!exclude.test(k)){
					$("#"+selectorId).append("<option value='"+k+"'>"+v+"</option>");
				}
			}else{
				$("#"+selectorId).append("<option value='"+k+"'>"+v+"</option>");
			}
			
		});
	}
}

/**
 * 将数据字典渲染到多选框
 * @param parentCode
 * @param mxSelect select组件的xm-select属性值
 * @param selectedKeys 选中的选项key
 * @param exclude 排除在外的编码正则表达式
 * @returns
 */
function dict2MSelect(parentCode,mxSelect,selectedKeys,exclude){
	var dict = dictionary(String(parentCode));
	map2MSelect(dict,mxSelect,selectedKeys,exclude);
}

/**
 * 将map渲染到多选框
 * @param parentCode
 * @param mxSelect select组件的xm-select属性值
 * @param selectedKeys 选中的选项key
 * @param exclude 排除在外的编码正则表达式
 * @returns
 */
function map2MSelect(dataMap,mxSelect,selectedKeys,exclude){
	var options= new Array();
	if(dataMap!=null){
		dataMap.forEach(function(v,k,map){
			if(exclude!=null&&typeof(exclude) != "undefined" ){
				//if(exclude.indexOf(k)<0){
				//console.log("k:"+k+" reg:"+exclude+" res:"+k.match(exclude))
				if(!exclude.test(k)){
					options.push({"name":v,"value":k});
				}
			}else{
				options.push({"name":v,"value":k});
			}
		});
	}
	if(!isNull(selectedKeys)){
		var selctedValues = new Array();
		selctedValues=selectedKeys.split(",");
		for(var i in options){
			var oi = options[i];
			if(selctedValues.includes(oi.value)){
				oi.selected=true;
			}
		}
	}
	mxSelect.data=options;
	layui.xmSelect.render(mxSelect);
}

/**
 * 将数据字典渲染成文本
 */
function dict2Text(parentCode, itemId, selectedKeys) {
	var dataMap = dictionary(String(parentCode));
	map2Text(dataMap, itemId, selectedKeys)
}

function map2Text(dataMap, itemId, selectedKeys) {
	var text = '';
	if (dataMap != null && !isNull(selectedKeys)) {
		if (selectedKeys.toString().indexOf(',') > -1) {
			var selctedValues = selectedKeys.split(",");
			$.each(selctedValues, function (i, selctedValue) {
				var mapName = dataMap.get(selctedValue);
				text = text + mapName + ',';
			});
		} else {
			text = dataMap.get(selectedKeys.toString());
		}
	}
	$('#' + itemId).text(text);
}

function json2Url(data) {
    try {
        var tempArr = [];
        for (var i in data) {
            var key = encodeURIComponent(i);
            var value = encodeURIComponent(data[i]);
            if(!isNull(value)){
                tempArr.push(key + '=' + value);
            }
        }
        var urlParamsStr = tempArr.join('&');
        return urlParamsStr;
    } catch (err) {
        return '';
    }
}

function url2Json(url) {
    try {
        var index = url.indexOf('?');
        url = url.match(/\?([^#]+)/)[1];
        var obj = {}, arr = url.split('&');
        for (var i = 0; i < arr.length; i++) {
            var subArr = arr[i].split('=');
            var key = decodeURIComponent(subArr[0]);
            var value = decodeURIComponent(subArr[1]);
            obj[key] = value;
        }
        return obj;
        
    } catch (err) {
        return null;
    }
}

/**
 * 将kvMap里面中的key和value（label）初始化到复选框list中
 * @param elementId
 * @param kvMap
 * @param name
 * @param checkList
 * @returns
 */
function arraysToCheckbox(elementId,kvMap,name,checkList){
	var innerHtml="";
	
	kvMap.forEach(function(v,k,map){
		 var checked="";
			if(checkList!=null&&checkList.indexOf(k)>-1){
				checked="checked";
            }
			innerHtml = innerHtml + '<input type="checkbox" name="'+name+'" title="'+v+'" value="'+k+'" '+checked+'>';
	});
	$("#"+elementId).html(innerHtml);
}
/**
 * 将kvMap里面中的key和value（label）初始化到单选框中
 * @param elementId
 * @param kvMap
 * @param name
 * @param checkList
 * @returns
 */
function arraysToRadios(elementId,kvMap,name,checkedItem){
	var innerHtml="";
	kvMap.forEach(function(v,k,map){
		 var checked="";
			if(checkedItem!=null&&checkedItem==k){
				checked="checked";
            }
			innerHtml = innerHtml + '<input type="radio" name="'+name+'" title="'+v+'" value="'+k+'" '+checked+'>';
	});
	$("#"+elementId).html(innerHtml);
}

/**
 * checkbox的值用逗号分隔
 * @param elementName
 * @returns
 */
function checkboxToArray(elementName){
	var boxes=$("[name="+elementName+"]");
	var res="";
	for(var i=0;i<boxes.length;i++){
		if(boxes[i].checked){
			var value=boxes[i].value;
			if(isNull(res)){
				res=value;
			}else{
				res=res+","+value;
			}
		}
	}
	return res;
}

/**
 * layui弹层，弹出单个图片
 * @param src
 * @returns
 */
function showPhoto(src){
    layer.photos({
        photos: { "data": [{"src":src}] }
    });
}

/**
 * layui弹层，关闭当前窗口
 * @param src
 * @returns
 */
function closePage(){
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}

/**返回上一页面*/
function returnPreviousPage(){
	var re=document.referrer;
	if(re==""){
		window.close();
	}else{
		window.location.href=re;
	}
}

/**
 * 在工作区菜单tab中新打开指定的tab
 * @param url
 * @param title
 * @param iconCss
 * @returns
 */
function addMenuTab(url,title,iconCss){
	window.parent.addTabByUrlTitleIcon(url,title,iconCss);
}

/**
 * 关闭当前tab页
 * @param url
 * @param title
 * @param iconCss
 * @returns
 */
function closeCurrentTab(){
	window.parent.closeCurrentTab();
}


/**
 * 权限校验，如果当前用户没有权限，则返回false
 * @param authorize 权限标识
 * @returns 有权限返回true，没有权限返回false
 */
function checkAuth(authorize){
	if(isNull(authorize)){
		return false;
	}
	var authorizations = sessionStorage.getItem("authorizations");
	if(!isNull(authorizations)){
		var authorizationArray=JSON.parse(authorizations);
		return containsInArray(authorizationArray,authorize);
	}
	return false;
}
/**
 * 权限校验，如果当前用户没有权限，则隐藏指定的页面元素element
 * @param elementId 元素ID
 * @param authorize 权限标识
 */
function auth(authorize,elementId){
	if(!checkAuth(authorize)){
		$("#"+elementId).hide();
	}
}

/**
 * 权限校验，如果当前用户没有权限，则隐藏指定的页面元素class
 * @param elementId class
 * @param authorize 权限标识
 */
function authByClass(authorize,className){
	if(!checkAuth(authorize)){
		$("."+className).hide();
	}
}

/**
 * 将所有页面元素设置成不可编辑
 * @returns
 */
function disableAllElements(){
    $("input").attr("readOnly",true);
    $(".checkbox").attr("disabled","disabled");
    $("select").attr("disabled",true);
    $("textarea").attr("readOnly",true);
}

/**
 * 刷新菜单tab页面
 * @param menuName 菜单名
 */
function refreshMenuTab(menuName){
	var tabId = '';
	$(parent.document).find('#top_tabs').find('li').each(function() {
		if($(this).find('cite').text() == menuName){
			tabId = $(this).find('.layui-tab-close').attr('data-id');
		}
	});
	$(parent.document).find('#top_tabs_pages').find('iframe').each(function() {
		if($(this).attr('data-id') == tabId){
			$(this).attr('src',$(this).attr('src'));
		}
	})
}

/**
 * 初始化xmSelect的下拉树选项
 * @param treeData
 * @param selectId
 * @returns
 */
function initXMTreeSelect(treeData,selectId){
	if(isNull(selectId)){
		return;
	}
	for(var i in treeData){
		var item = treeData[i];
		if(item.value==selectId){
			item.selected=true;
			return;
		}
		if(item.children!=null){
			initXMTreeSelect(item.children,selectId);
		}
	}
}
