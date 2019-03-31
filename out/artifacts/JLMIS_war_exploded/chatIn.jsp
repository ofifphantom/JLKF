<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>chat</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/chat/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/chat/css/ace.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/chat/css/style.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/chat/css/style_i.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/chat/font/css/font-awesome.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/chat/js/layer/skin/layer.css" />
		<!--[if IE 7]>
		  <link rel="stylesheet" href="${pageContext.request.contextPath}/chat/font/css/font-awesome-ie7.min.css" />
		  <link rel="stylesheet" href="${pageContext.request.contextPath}/chat/css/ace-ie.min.css" />
		<![endif]-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/chat/css/mine/all.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/chat/css/mine/public.css" />
		<script src="${pageContext.request.contextPath}/chat/js/jquery-1.10.2.min.js"></script>
		<script src="${pageContext.request.contextPath}/chat/js/bootstrap.min.js"></script>
		<script src="${pageContext.request.contextPath}/chat/js/typeahead-bs2.min.js"></script>
		<script src="${pageContext.request.contextPath}/chat/js/jquery.dataTables.min.js"></script>
		<script src="${pageContext.request.contextPath}/chat/js/jquery.dataTables.bootstrap.js"></script>
		<script src="${pageContext.request.contextPath}/chat/js/layer/layer.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/chat/js/laydate/laydate.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/chat/script/public.js" type="text/javascript"></script>
		<style>
			.div-table{
				width: 100%;
				height: 500px;
				line-height: 40px;
			}
		</style>
	</head>
	<body class="content">
		<div class="page-content clearfix">
			<div id="Member_Ratings">
				<div class="d_Confirm_Order_style" style="margin-top:10px;">
					<h4 class="text-title">chat</h4>
					<div class="opration-div div-table">
						<div class="div-middle text-center">
							<article>当前为离线状态，请点击下方按钮上线。</article>
							<button type="button" class="btncss jl-btn-defult" onclick="online()">上线</button>
						</div>
					</div>
					</div>

				</div>
			</div>
		</div>
	</body>
	<script>
		/*上线*/
		function online(){
			parent.location.href="${pageContext.request.contextPath}/onLine";
		}
	</script>
</html>