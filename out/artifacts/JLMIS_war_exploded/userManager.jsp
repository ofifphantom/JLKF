<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>君霖食品客服系统</title>

		<link href="${pageContext.request.contextPath}/chat/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/chat/css/ace.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/chat/css/ace-rtl.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/chat/css/ace-skins.min.css" />
		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="${pageContext.request.contextPath}/chat/css/ace-ie.min.css" />
		<![endif]-->
		<link href="${pageContext.request.contextPath}/chat/css/mine/all.css" rel="stylesheet" />
		<link href="${pageContext.request.contextPath}/chat/css/mine/index.css" rel="stylesheet" />

		<script src="${pageContext.request.contextPath}/chat/js/ace-extra.min.js"></script>
		<!--[if lt IE 9]>
		<script src="${pageContext.request.contextPath}/chat/js/html5shiv.js"></script>
		<script src="${pageContext.request.contextPath}/chat/js/respond.min.js"></script>
		<![endif]-->
		<script src="${pageContext.request.contextPath}/chat/js/jquery-1.10.2.min.js"></script>

		<script type="text/javascript">
			if("ontouchend" in document)
				document.write("<script src='${pageContext.request.contextPath}/chat/js/jquery.mobile.custom.min.js'>" +
					"<" + "script>");
		</script>
		<script src="${pageContext.request.contextPath}/chat/js/bootstrap.min.js"></script>
		<!--自动补全插件-->
		<script src="${pageContext.request.contextPath}/chat/js/typeahead-bs2.min.js"></script>
		<!--[if lte IE 8]>
		  <script src="${pageContext.request.contextPath}/chat/js/excanvas.min.js"></script>
		<![endif]-->
		<script src="${pageContext.request.contextPath}/chat/js/ace-elements.min.js"></script>
		<script src="${pageContext.request.contextPath}/chat/js/ace.min.js"></script>
		<script src="${pageContext.request.contextPath}/chat/js/layer3.1.1/layer.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/chat/js/laydate/laydate.js" type="text/javascript"></script>
		<!--menu.js用于动态加载左侧导航栏-->
		<!--<script src="../js/menu.js"></script>-->

		<!--引入JQuery EasyUI  核心UI 文件 CSS-->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/chat/js/easyui-1.3.6/themes/default/easyui.css">
		<!--引入EasyUI图标文件-->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/chat/js/easyui-1.3.6/themes/icon.css">
		<!--引入JQuery 核心库-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/chat/js/easyui-1.3.6/jquery.min.js"></script>
		<!--引入JQuery EasyUI  核心库 1.3.6版本-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/chat/js/easyui-1.3.6/jquery.easyui.min.js"></script>
		<!--进入 EasyUI中文提示信息-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/chat/js/easyui-1.3.6/locale/easyui-lang-zh_CN.js"></script>
		<style>
			#sidebar {
				width: 190px;
				padding-top: 20px;
			}
			.tree-node {
				height: 40px;
				padding-top: 11px;
			}
			#sidebar ul li>div {
				padding-left: 15px;
			}
		</style>

	</head>

	<body>

		<div class="navbar navbar-default" id="navbar">
			<div class="navbar-container" id="navbar-container">
				<div class="navbar-header pull-left">
					<a href="#" class="navbar-brand">
						<ul>
							<li>
								<img alt="Brand" src="${pageContext.request.contextPath}/chat/img/logo.png" width="100px" />
							</li>
							<li style="margin-top: 5px;margin-left: 5px;">
								<span>君霖食品客服系统</span>
							</li>
							<li class="clearfix"></li>
						</ul>
					</a>
				</div>
				<div class="navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">
						<li>
							<a href="login.html" id="quit" role="button" class="btn">退出
							</a>
						</li>
						<!--<li>
							<a href="javascript:modifyPwd()" role="button" class="btn">修改密码
							</a>
						</li>-->
						<li>
							<span>admin 	/	超级管理员</span>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="main-container" id="main-container">
			<div class="main-container-inner">
				<a class="menu-toggler" id="menu-toggler" href="#">
					<span class="menu-text"></span>
				</a>
				<div class="sidebar" id="sidebar">
					<!-- #sidebar-shortcuts -->
					<ul class="easyui-tree" id="nav_list">

					</ul>
					<div class="sidebar-collapse" id="sidebar-collapse">
						<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
					</div>
					<script type="text/javascript">
						try {
							ace.settings.check('sidebar', 'collapsed')
						} catch(e) {}
					</script>
				</div>
				<div class="main-content">
						<iframe id="iframe" style="border: 0; width: 100%;background-color: #f7f6f6!important;" name="iframe" frameborder="0" src="/customerManaging.jsp"></iframe>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
		<!--底部样式-->
		<!--<div class="footer_style" id="footerstyle">
			<p class="r_f">Copyright 2014-2016 洛阳小蚂蚁网络科技有限公司版权所有 豫ICP备15003737号</p>
		</div>-->
	</body>
	<script>
		var url = "/customerManaging.jsp";

		jQuery(document).ready(function() {
			$.each($(".submenu"), function() {
				var $aobjs = $(this).children("li");
				var rowCount = $aobjs.size();
				var divHeigth = $(this).height();
				$aobjs.height(divHeigth / rowCount);
			});
			//初始化宽度、高度    
			$("#main-container").height($(window).height() - 75);
			$("#iframe").height($(window).height() - 81);
			$(".sidebar").height($(window).height() - 95);
			var thisHeight = $("#nav_list").height($(window).outerHeight() - 124);
			//当文档窗口发生改变时 触发  
			$(window).resize(function() {
				$("#main-container").height($(window).height() - 75);
				$("#iframe").height($(window).height() - 81);
				$(".sidebar").height($(window).height() - 95);
				var thisHeight = $("#nav_list").height($(window).outerHeight() - 124);
				$("#iframe").attr("src", url).ready();
			});

		});
		/*********************点击事件*********************/

		$(document).ready(function() {
			$('#Exit_system').on('click', function() {
				layer.confirm('是否确定退出系统？', {
					btn: ['是', '否'], //按钮
					icon: 2,
				}, function() {
					location.href = "<%=request.getContextPath() %>/com/tyzd/tenement/loginMain/logout1";
				});
			});
		})

		$("#nav_list").tree({
			url: null, //获取远程数据URL
			data: [
				{
					"id":1,
					"text":"客服管理",
					"state": "open",
					"children": [{
						"id":2,
						"text": "客服管理",
						"iconCls": "icon-no",
						"attributes": {
							"name": "/customerManaging.jsp"
						},
					},{
						"id":2,
						"text": "操作日志",
						"iconCls": "icon-no",
						"attributes": {
							"name": ""
						},
					}]
				},{
					"id":1,
					"text":"售前咨询",
					"state": "closed",
					"children": [{
						"id":2,
						"text": "chat",
						"iconCls": "icon-no",
						"attributes": {
							"name": "/chatIn.jsp"
						},
					},{
						"id":2,
						"text": "咨询记录",
						"iconCls": "icon-no",
						"attributes": {
							"name": ""
						},
					}]
				},{
					"id":1,
					"text":"售后咨询",
					"state": "closed",
					"children": [{
						"id":2,
						"text": "接线",
						"iconCls": "icon-no",
						"attributes": {
							"name": ""
						},
					},{
						"id":2,
						"text": "咨询记录",
						"iconCls": "icon-no",
						"attributes": {
							"name": ""
						},
					},{
						"id":2,
						"text": "队列/坐席",
						"iconCls": "icon-no",
						"attributes": {
							"name": ""
						},
					}]
				},{
					"id":1,
					"text":"账户咨询",
					"state": "closed",
					"children": [{
						"id":2,
						"text": "账户咨询",
						"iconCls": "icon-no",
						"attributes": {
							"name": ""
						},
					}]
				},

			], //节点数据加载
			lines: false,
			checkbox: false,
			onlyLeafCheck: false, //定义是否只在末节点之前显示复选框
			dnd: false, //定义是否启动拖拽功能
			onClick: function(node) {
				var urlobj = node.attributes;
				if(urlobj != undefined) {
					for(var Key in urlobj) {
						url = urlobj[Key];
						$("#iframe").attr("src", url).ready();
					}
				}

			},
			onLoadSuccess: function(node, param) {
				//					console.log(param);
				//var aa=getfirstChildren(param);
				getfirstChildren(param);
				var urlobj = obj0.attributes;
				if(urlobj != undefined) {
					for(var Key in urlobj) {
						url = urlobj[Key];
						$("#iframe").attr("src", url).ready();
					}
				}
//				 makeTreeOpen(param); 
			},
			onLoadError: function(arguments) {
				console.log(arguments);
			}
		})

		function getChildren(obj) {
			return obj[0].children;
		}

		function getfirstChildren(obj) {

			if(getChildren(obj) != undefined) {
				obj0 = getChildren(obj);
				getfirstChildren(obj0);
			} else {
				obj0 = obj[0];
				var node = $("#nav_list").tree('find', obj0.id - 0);
				$('#nav_list').tree('select', node.target);
			}
			//return obj;
		}

		function OpenNode(nodeId) {
			var node = $("#nav_list").tree('find', nodeId);
			$('#nav_list').tree('expand', node.target);

		}

		function makeTreeOpen(arr) {
			arr = arr[0];
			OpenNode(arr["id"] - 0);
			var res = getChildren(arr);
			if(res != undefined) {
				makeTreeOpen(res);
			}
		}

		function modifyPwd() {
			$("#iframe").attr("src", "backgroundManagement/background/changePwd.html").ready();
		}
	</script>

</html>