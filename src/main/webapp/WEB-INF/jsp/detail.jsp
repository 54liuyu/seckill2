<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="common/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>秒杀详情页</title>
<%@ include file="common/head.jsp" %>
</head>
<body>
<div id="container">
	<div class="panel panel-default text-center">
		<div class="panel-heading">
			<h1>${seckill.name }</h1>
		</div>
		<div class="panel-body">
			<h2 class="text-danger">
                <!-- 显示time图标 -->
				<span class="glyphicon glyphicon-time"></span>
                <!-- 展示倒计时 -->
				<span class="glyphicon" id="seckill-box"></span>
			</h2>
		</div>
	</div>
</div>

<!-- 登录弹出层，输入手机 号-->
<div id="killPhoneModal" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h3 class="modal-title text-center">
					<span class="glyphicon glyphicon-phone"></span>秒杀电话：
				</h3>
			</div>
			<div class="modal-body">
				<div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
						<input type="text" id="killPhoneKey" name="killPhone" placeholder="填手机号^o^" class="form-control"/>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<span id="killPhoneMessage" class="glyphicon"></span>                
				<button type="button" id="killPhoneBtn" class="btn btn-success">
                    <span class="glyphicon glyphicon-phone"></span>submit
				</button>
			</div>
		</div>
	</div>
</div>
</body>

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>

<!-- 使用CDN 获取公共js http://www.bootcdn.cn/ -->
<!-- jQuery cookie操作插件 -->
<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<!-- jQuery countDown倒计时插件 -->
<script src="http://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js"></script>

<script type="text/javascript" src="${ctx}/resources/script/seckill.js"></script>
<script type="text/javascript">
	$(function(){
	    //使用EL表达式传入参数
	    seckill.detail.init({
	        seckillId : ${seckill.seckillId},
	        startTime : ${seckill.startTime.time},//毫秒
	        endTime : ${seckill.endTime.time}
	    },"${ctx}");
	});
</script>

</html>