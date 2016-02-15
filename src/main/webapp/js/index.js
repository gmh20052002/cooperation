$(document).ready(function() {
	$("#loginButton").click(function() {
		cache: false,
		$.ajax({
			type : "GET",
			url : "/wlp/login",
			data :{userName:$("#email").val(),password:$("#password").val()},
			success : function(data) {
				console.log(data)
				if(data&&data.userName){
			location.href='memCenter.html';
				}
				else{
					alert(1)
				}
			},
			 dataType: 'json'
		});
	});

});