
	function myFunction(x)
	{
		  var pathName=window.document.location.pathname;
		  var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);  
		 		if($("#email2").val()){
		 		$.ajax({
		 			type : 'POST',
					data:{ 'username':$("#email2").val() },
					url :  projectName+"/wlp/checkUsernameIfExist",
					success : function(data) {
						  console.log(data)
						if(data){
						 	$("#showRegMes").hide();  
						 }else{				
							 	$("#showRegMes").show(); 
							      $("#showRegMes").html("<font color=red>注册失败，用户账号已存在!</font>");
					           $("#email2").focus();

						}
					}
				});
		 		}else{
		 		 	$("#showRegMes").show(); 
				      $("#showRegMes").html("<font color=red>用户账号不能为空!</font>");
		 		}
	}
	function myFunction2(x)
	{
		 	
	}
$(document).ready(function() {
	$("#loginButton").click(function() {
		 $('#logintext').html('登陆中');
		 $('#loginButton').buttonMarkup({ theme: "e" });
    var uname =$("#email").val(); 
	if(!uname){
		$("#showMes").show();
        $("#showMes").html("<font color=red>登录失败，用户名不能为空!</font>");
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
	var randomCode = $("#randomCode").val(); 
	if(!randomCode){
		$("#showMes").show();
        $("#showMes").html("<font color=red>登录失败，注册码不能为空!</font>");
        $("#randomCode").focus();
        $('#logintext').html('登陆');
        $('#loginButton').buttonMarkup({ theme: "e" });
		return false;
	}
	  var pathName=window.document.location.pathname;
	  var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);  
		$.ajax({
			type : "GET",
			url : projectName+"/wlp/login",
			data :{userName:$("#email").val(),password:$("#password").val(),validateCode:$("#randomCode").val()},
			success : function(data) {
			console.log(data)
				   $('#loginButton').buttonMarkup({ theme: "e" });
				   $('#logintext').html('登陆');
				   if(data&&data.id=="123456789"){
						$("#showMes").show();
						var imgNode = document.getElementById("vimg");
						imgNode.src = "servlet/AuthImageServlet?t=" + Math.random();
				        $("#showMes").html("<font color=red>登录失败，验证码输入错误!</font>");
						}
				   else if(data&&data.userName){
			      location.href=projectName+'/memCenter.html';
				}
				else{
					$("#showMes").show();
					var imgNode = document.getElementById("vimg");
					imgNode.src = "servlet/AuthImageServlet?t=" + Math.random();
				    $("#showMes").html("<font color=red>登录失败，用户名或密码输入错误!</font>");
				}
			},
			 dataType: 'json',
			error : function(xmlhttprequest, errorinfo) {
				   $('#loginButton').buttonMarkup({ theme: "e" });
				   $('#logintext').html('登陆');
					var imgNode = document.getElementById("vimg");
					imgNode.src = "servlet/AuthImageServlet?t=" + Math.random();
			    $("#showMes").html("<font color=red>登录失败，用户名或密码错误!</font>");

				}
		});
	});

	$("#regButton").live('click', function() {
		 $('#regtext').html('注册中');
		 $('#regButton').buttonMarkup({ theme: "e" });
		 
		 /*   var uname =$("#username5").val(); 
			if(!uname){
				$("#showRegMes").show();
		        $("#showRegMes").html("<font color=red>注册失败，昵称不能为空!</font>");
		        $("#username5").focus();
		        $('#regtext').html('注册');
		        $('#regButton').buttonMarkup({ theme: "e" });
				return false;
			}
			if(uname.length>16){
				$("#showRegMes").show();
		        $("#showRegMes").html("<font color=red>注册失败，用户昵称长度不能超过16!</font>");
		        $("#username5").focus();
		        $('#regtext').html('注册');
		        $('#regButton').buttonMarkup({ theme: "e" });
				return false;
			}*/
			
			 var email2 =$("#email2").val(); 
			 if(!email2){
					$("#showRegMes").show();
			        $("#showRegMes").html("<font color=red>注册失败，登陆账号不能为空!</font>");
			        $("#email2").focus();
			        $('#regtext').html('注册');
			        $('#regButton').buttonMarkup({ theme: "e" });
					return false;
				}
			 if(email2.length>20){
					$("#showRegMes").show();
			        $("#showRegMes").html("<font color=red>登陆账号长度不能超过20!</font>");
			        $("#email2").focus();
			        $('#regtext').html('注册');
			        $('#regButton').buttonMarkup({ theme: "e" });
					return false;
				}
			 var treg2 =  /^[a-zA-Z0-9_@.]{1,20}$/;
			 if (!treg2.test(email2)) {
					$("#showRegMes").show();
			        $("#showRegMes").html("<font color=red>注册失败，登陆账号只能是字母数字下划线或邮箱格式!</font>");
			        $("#email2").focus();
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
			        $("#showRegMes").html("<font color=red>注册失败， 确认登陆密码和登陆密码不一致!</font>");
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
			   var myreg24 =   /^[a-zA-Z0-9_@.]{1,20}$/;
			 if(email4){
			         if(!myreg24.test(email4))
                   {
          		     $("#showRegMes").show();
		             $("#showRegMes").html("<font color=red>注册失败，介绍人账号只能是字母数字下划线或邮箱格式!</font>");
                     $("#email4").focus();
                    $('#regtext').html('注册');
			        $('#regButton').buttonMarkup({ theme: "e" });
                      return false;
                  }
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
				 	$("#showRegMes").show(); 
				      $("#showRegMes").html("<font >恭喜您注册成功，系统将自动跳转到平安互助主页!</font>");
	                location.href=projectName+"/memCenter.html";   	           
	            }else {
	         	$("#showRegMes").show(); 
			      $("#showRegMes").html("<font color=red>注册失败，用户名已存在!</font>");
	           $("#email2").focus();
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