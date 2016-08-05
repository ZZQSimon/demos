$(function() {
	
	//var datetimeboxclass = "aaaa";
	//自动加载datatimebox
	//$('.' + datetimeboxclass).datetimebox({
		// value: '3/4/2010 2:3',   
	//	required : true,
	//	showSeconds : false
	//});

	//验证用户名
	$('.vali_username').validatebox({
		required : true,
		missingMessage : '请填写用户名!'
	});

	$('.vali_password').validatebox({
		required : true,
		missingMessage : '请填写密码!'
	});

	//执行一下本地化?？？？
	//		执行初始化方法
	//		DCIM.ready();
	
	$(".button").click( function() {
		DCIM.post(WEB_APP +'/j_spring_security_check', "", function(data) {
			$(".loginmsg").text(data.message);
			if(data.returnUrl){
				location.href = "../" + data.returnUrl;
			}
		});
	});
		
	document.onkeydown = function(e) {
		var ev = document.all ? window.event : e;
		if (ev.keyCode == 13) {
			$(".button").click();
		}
	};
	
	//登录页高度自适应,登录表单水平垂直居中的方法调用
    loginHeight();
    $(window).resize(function(){
	loginHeight();
    });
});

	//登录页高度自适应,登录表单水平垂直居中的方法
	function loginHeight(){
		var loginw = window.innerWidth
		|| document.documentElement.clientWidth
		|| document.body.clientWidth;
        var loginh = window.innerHeight
		|| document.documentElement.clientHeight
		|| document.body.clientHeight;
		$('.login').width(loginw);
		$('.login').height(loginh);
		var formw=$('.login-bg').width();
		var formh=$('.login-bg').height();
		var left=(loginw-formw)/2;
		var top=(loginh-formh)/2;
		$('.login-bg').css({top: (top > 0 ? top : 0)+'px', left: (left > 0 ? left : 0)+'px'});
	}
