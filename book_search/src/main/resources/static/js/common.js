function StringFormat() {
	var expression = arguments[0];
	for (var i = 1; i < arguments.length; i++) {
		var prttern = new RegExp('\\{' + (i - 1) + '\\}', 'gi');
		expression = expression.replace(prttern, arguments[i]);
	}
	return expression;
}

function logout(){
	$.ajax({
		url: "/logout",
		method: "POST",
		success: function(){
			alert("로그아웃 되었습니다.");
			location.reload();
		}
	})
}