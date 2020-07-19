var NumEvent = {
	    // 例 510,085.00
	    number: function(digit){
	      var num_arr=[];
	      for(var i = 0;i<digit.length;i++){
	        num_arr.push(digit.charAt(i));
	      }
	 
	      return num_arr;
	    },
	    dom: function(arr,classId){
	      var str = '';
	      for(var i = 0;i<arr.length;i++){
	        if(parseInt(arr[i])>=0){
	          str += '<div class="l '+classId+'-l digit-container" data-show='+arr[i]+'>\
	                  <span>0</span>\
	                  <span>1</span>\
	                  <span>2</span>\
	                  <span>3</span>\
	                  <span>4</span>\
	                  <span>5</span>\
	                  <span>6</span>\
	                  <span>7</span>\
	                  <span>8</span>\
	                  <span>9</span>\
	                </div>';
	        }else{
	           str += '<div class="sign-box l"><span>'+arr[i]+'</span></div>';
	        }
	      }
	      return str;
	    },
	    animation: function(classId){
	      var height = $("."+classId).height();
	      $("."+classId+"-l").each(function(i){
	        var num = parseInt($(this).data("show"));
	        var scrollTop = 0;
	          var scrollTop = height * num;
	          $(this).css("margin-top",0);
	          $(this).animate({marginTop: -scrollTop},1500);
	      });
	    }
	  };
