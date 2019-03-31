<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
<head>
<meta charset="utf-8">
<title>订单选择</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/chat/css/bootstrap.min.css"/>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/chat/font/css/font-awesome.min.css" />
<!--[if IE 7]>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chat/font/css/font-awesome-ie7.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chat/css/ace-ie.min.css"/>
    <![endif]-->

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/chat/css/mine/all.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/chat/css/mine/public.css" />
<script src="${pageContext.request.contextPath}/chat/js/jquery-1.10.2.min.js"></script>
<style>
			body {
				width: 100%;
				height: 100%;
				background: #f0f2f5;
			}
			
			header {
				background: white;
				width: 100%;
				height: 160px;
				line-height: 160px;
				position: relative;
				text-align: center;
			}
			
			header a {
				font-size: 80px;
				margin-top: 42px;
				position: absolute;
				left: 50px;
				color: #555555;
			}
			
			header span {
				font-size: 50px;
				letter-spacing: 1px;
			}
			
			section .order-div {
				margin: 30px;
				padding: 15px;
				height: 200px;
				background: white;
				font-size: 16px;
			}
			
			.order-div img {
				width: 165px;
				height: 165px;
			}
			
			.order-div>div{
				float: left;
				font-size: 35px;
			}
			.order-div ul{
				margin-left: 15px;
			}
			.order-div li{
				font-size: 35px;
				line-height: 57px;
			}
			.order-state{
				float: right;
				line-height: 165px;
				text-align: right;
			}
		</style>

	</head>

	<body>
		<header class="col-xs-12">
			<a href="javascript:history.back(-1);">
				<i class="fa fa-angle-left" aria-hidden="true"></i>
			</a>
			<span>选择商品</span>
		</header>
		<section>
			<article onclick="checkOrder(this)" data-id="1234567812345678" class="order-div">
				<div>
					<img src="../img/kf.jpg" />
				</div>
				<div>
					<ul>
						<li>订单号：1234567812345678</li>
						<li>订单金额：123</li>
						<li>下单时间：2018-05-14 11:12:00</li>
					</ul>
				</div>
				<div class="order-state">
					待支付
				</div>
				<div style="clear: both;"></div>
			</article>
			<article onclick="checkOrder(this)" data-id="1234567812345678" class="order-div">
				<div>
					<img src="../img/kf.jpg" />
				</div>
				<div>
					<ul>
						<li>订单号：1234567812345123</li>
						<li>订单金额：123</li>
						<li>下单时间：2018-05-14 11:12:00</li>
					</ul>
				</div>
				<div class="order-state">
					待支付
				</div>
				<div style="clear: both;"></div>
			</article>

		</section>
		<script>
			function checkOrder(thisa){
				console.log($(thisa).attr("data-id"));
				
			}
		</script>

	</body>

</html>