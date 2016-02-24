$(document).ready(function() {
	$("#loginButton").click(function() {
		 $('#logintext').html('登陆中');
		 $('#loginButton').buttonMarkup({ theme: "f" });
    var uname =$("#email").val(); 
	if(!uname){
		$("#showMes").show();
        $("#showMes").html("<font color=red>登录失败，邮箱不能为空!</font>");
        $("#email").focus();
        $('#logintext').html('登陆');
        $('#loginButton').buttonMarkup({ theme: "e" });
		return false;
	}
	  //对电子邮件的验证
	             var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	              if(!myreg.test(uname))
	              {
	          		$("#showMes").show();
	                $("#showMes").html("<font color=red>邮箱格式不正确!</font>");
	                 $("#email").focus();
	                 $('#logintext').html('登陆');
	                 $('#loginButton').buttonMarkup({ theme: "e" });
	                 return false;
	            }
	var psd = $("#password").val(); 
	if(!psd){
		$("#showMes").show();
        $("#showMes").html("<font color=red>登录失败，密码不能为空!</font>");
        $("#password").focus();
        $('#logintext').html('登陆');
        $('#loginButton').buttonMarkup({ theme: "e" });
		return false;
	}
	  var pathName=window.document.location.pathname;
	  var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);  
		$.ajax({
			type : "GET",
			url : projectName+"/wlp/login",
			data :{userName:$("#email").val(),password:$("#password").val()},
			success : function(data) {
				console.log(projectName)
				   $('#loginButton').buttonMarkup({ theme: "e" });
				   $('#logintext').html('登陆');
				if(data&&data.userName){
			      location.href=projectName+'/memCenter.html';
				}
				else{
				    $("#showMes").html("<font color=red>登录失败，邮箱或密码错误!</font>");
				}
			},
			 dataType: 'json',
			error : function(xmlhttprequest, errorinfo) {
				   $('#loginButton').buttonMarkup({ theme: "e" });
				   $('#logintext').html('登陆');
			    $("#showMes").html("<font color=red>登录失败，邮箱或密码错误!</font>");

				}
		});
	});

	$("#regButton").live('click', function() {
		 $('#regtext').html('注册中');
		 $('#regButton').buttonMarkup({ theme: "f" });
		 
		    var uname =$("#username5").val(); 
			if(!uname){
				$("#showRegMes").show();
		        $("#showRegMes").html("<font color=red>注册失败，昵称不能为空!</font>");
		        $("#username5").focus();
		        $('#regtext').html('注册');
		        $('#regButton').buttonMarkup({ theme: "e" });
				return false;
			}
			 var telphone =$("#telphone").val(); 
			 if(!telphone){
					$("#showRegMes").show();
			        $("#showRegMes").html("<font color=red>注册失败，手机号码不能为空!</font>");
			        $("#telphone").focus();
			        $('#regtext').html('注册');
			        $('#regButton').buttonMarkup({ theme: "e" });
					return false;
				}
			 var treg = /^0?1[3|4|5|8][0-9]\d{8}$/;
			 if (!treg.test(telphone)) {
					$("#showRegMes").show();
			        $("#showRegMes").html("<font color=red>注册失败，手机号码格式错误!</font>");
			        $("#telphone").focus();
			        $('#regtext').html('注册');
			        $('#regButton').buttonMarkup({ theme: "e" });
					return false;
				}
			 var email2 =$("#email2").val(); 
			 if(!email2){
					$("#showRegMes").show();
			        $("#showRegMes").html("<font color=red>注册失败，登陆邮箱不能为空!</font>");
			        $("#email2").focus();
			        $('#regtext').html('注册');
			        $('#regButton').buttonMarkup({ theme: "e" });
					return false;
				}
			   var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	              if(!myreg.test(email2))
	              {
	          		$("#showRegMes").show();
			        $("#showRegMes").html("<font color=red>注册失败，登陆邮箱格式不正确!</font>");
	                 $("#email2").focus();
	                 $('#regtext').html('注册');
				      $('#regButton').buttonMarkup({ theme: "e" });
	                 return false;
	            }
			 var password3 =$("#password3").val(); 
			 if(!password3){
					$("#showRegMes").show();
			        $("#showRegMes").html("<font color=red>注册失败，登陆密码不能为空!</font>");
			        $("#password3").focus();
			        $('#regtext').html('注册');
			        $('#regButton').buttonMarkup({ theme: "e" });
					return false;
				}
			 
			 if(password3.length<6||password3.length>20){				 
				    $("#showRegMes").show();
			        $("#showRegMes").html("<font color=red>注册失败，登陆密码6-20位字母数字组合!</font>");
			        $("#password3").focus();
			        $('#regtext').html('注册');
			        $('#regButton').buttonMarkup({ theme: "e" });
					return false;
			 }
			 var password4 =$("#password4").val(); 
			 if(!password4){
					$("#showRegMes").show();
			        $("#showRegMes").html("<font color=red>注册失败， 确认登陆密码不能为空!</font>");
			        $("#password4").focus();
			        $('#regtext').html('注册');
			        $('#regButton').buttonMarkup({ theme: "e" });
					return false;
				}
			 if(password4.length<6||password4.length>20){				 
				    $("#showRegMes").show();
			        $("#showRegMes").html("<font color=red>注册失败，登陆密码6-20位字母数字组合!</font>");
			        $("#password4").focus();
			        $('#regtext').html('注册');
			        $('#regButton').buttonMarkup({ theme: "e" });
					return false;
			 }
			 if(password4!=password3){
					$("#showRegMes").show();
			        $("#showRegMes").html("<font color=red>注册失败， 确认登陆密码和登陆密码不一致!</font>");
			        $("#password4").focus();
			        $('#regtext').html('注册');
			        $('#regButton').buttonMarkup({ theme: "e" });
					return false;
				}
			 var password5 =$("#password5").val(); 
			 if(!password5){
					$("#showRegMes").show();
			        $("#showRegMes").html("<font color=red>注册失败，交易密码不能为空!</font>");
			        $("#password5").focus();
			        $('#regtext').html('注册');
			        $('#regButton').buttonMarkup({ theme: "e" });
					return false;
				}	
			 if(password5.length<6||password5.length>20){				 
				    $("#showRegMes").show();
			        $("#showRegMes").html("<font color=red>注册失败，交易密码6-20位字母数字组合!</font>");
			        $("#password5").focus();
			        $('#regtext').html('注册');
			        $('#regButton').buttonMarkup({ theme: "e" });
					return false;
			 }
			 var password6 =$("#password6").val(); 
			 if(!password6){
					$("#showRegMes").show();
			        $("#showRegMes").html("<font color=red>注册失败， 确认交易密码不能为空!</font>");
			        $("#password6").focus();
			        $('#regtext').html('注册');
			        $('#regButton').buttonMarkup({ theme: "e" });
					return false;
				}
			 if(password5!=password6){
					$("#showRegMes").show();
			        $("#showRegMes").html("<font color=red>注册失败， 确认交易密码和交易密码不一致!</font>");
			        $("#password6").focus();
			        $('#regtext').html('注册');
			        $('#regButton').buttonMarkup({ theme: "e" });
					return false;
				}
			 var email4 =$("#email4").val(); 
			 if(!email4){
					$("#showRegMes").show();
			        $("#showRegMes").html("<font color=red>注册失败，介绍人邮箱不能为空!</font>");
			        $("#email4").focus();
			        $('#regtext').html('注册');
			        $('#regButton').buttonMarkup({ theme: "e" });
					return false;
				}
			   var myreg2 = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
			  if(!myreg2.test(email4))
              {
          		$("#showRegMes").show();
		        $("#showRegMes").html("<font color=red>注册失败，介绍人邮箱格式不正确!</font>");
                 $("#email4").focus();
                 $('#regtext').html('注册');
			      $('#regButton').buttonMarkup({ theme: "e" });
                 return false;
            }
			  var pathName=window.document.location.pathname;
			  var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);  
		$.ajax({
		
			type : 'POST',
			url : projectName+'/wlp/regUser',
			data : {
				username : $("#username5").val(),telphone : $("#telphone").val(),email : $("#email2").val(),
				password : $("#password3").val(),paypassword : $("#password5").val(),introemail : $("#email4").val()},
			dataType : 'json',
			success : function(data, type, request) {
				console.log(data)
				if(data){
	                location.href=projectName+"/memCenter.html";   	           
	            }else {
	               	$("#showRegMes").show(); 
			        $("#showRegMes").html("<font color=red>注册失败，介绍人不存在!</font>");
	                 $("#email4").focus();
	                 $('#regtext').html('注册');
				      $('#regButton').buttonMarkup({ theme: "e" });
	            }    
			},
			error : function(xmlhttprequest, errorinfo) {
				 $('#regButton').buttonMarkup({ theme: "e" });
			}
		});

	});

});