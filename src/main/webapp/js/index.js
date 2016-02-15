$(document).ready(function() {
	$("#loginButton").click(function() {
		cache: false,
		$.ajax({
			type : "GET",
			url : "/wlp/login",
			data :{userName:$("#email").val(),password:$("#password").val()},
			success : function(data) {
				goTo('content.html');
			},
			 dataType: 'json'
		});
	});

});