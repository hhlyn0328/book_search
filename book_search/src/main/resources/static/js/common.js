function StringFormat() {
	var expression = arguments[0];
	for (var i = 1; i < arguments.length; i++) {
		var prttern = new RegExp('\\{' + (i - 1) + '\\}', 'gi');
		expression = expression.replace(prttern, arguments[i]);
	}
	return expression;
}