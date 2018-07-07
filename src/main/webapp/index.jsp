<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="WEB-INF/jsp/common/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>

<script type="text/javascript">

$(function(){
    $.getJSON(
    	"http://192.168.0.103:8090/jsonpLogin?callback=?",
    	{},
		function(result) {  
    		console.log(result);
    	}
	 );}
);

</script>

</head>
<body>
<h2>Hello World!</h2>
<a href="${ctx }/seckill/list/0/10?name=ly">秒杀列表页</a>
</body>
</html>
