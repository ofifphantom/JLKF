<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<html lang="en">

	<head>
		<meta charset="utf-8" />
		<title>客户管理</title>
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
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/chat/css/jquery.dataTables.css">

		<script src="${pageContext.request.contextPath}/chat/js/jquery-1.10.2.min.js"></script>
		<script src="${pageContext.request.contextPath}/chat/js/bootstrap.min.js"></script>
		<script src="${pageContext.request.contextPath}/chat/js/typeahead-bs2.min.js"></script>
		<script src="${pageContext.request.contextPath}/chat/js/jquery.dataTables.min.js"></script>
		<script src="${pageContext.request.contextPath}/chat/js/jquery.dataTables.bootstrap.js"></script>
		<script src="${pageContext.request.contextPath}/chat/js/layer/layer.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/chat/js/laydate/laydate.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/chat/script/public.js" type="text/javascript"></script>
		<style>
			
		</style>
	</head>

	<body class="content">
		<div class="page-content clearfix">
			<div id="Member_Ratings">
				<div class="d_Confirm_Order_style" style="margin-top:10px;">
					<h4 class="text-title">客服管理</h4>
					<div class="search-div clearfix">
						<div class="clearfix">
							<span class="l_f"> 
								操作人姓名： <input type="text" id="selectUserName" value="" />
							</span>
							<span class="l_f">
								操作模块：
								<select id="selectModelType">
									<option value="-1">--请选择--</option>
								</select>
							</span>
						</span>
							<span class="r_f"> 
							<input type="button"  class="btncss btn_search" onclick="listModel()" value="搜索" />
						</span>
						</div>

					</div>
					<div class="table_menu_list">
						<table class="table table-striped table-hover col-xs-12" id="datatable">
							<thead class="table-header">
								<tr>
									<th>编号</th>
									<th>操作模块</th>
									<th>操作说明</th>
									<th>操作人姓名</th>
									<th>操作人用户名</th>
									<th>操作时间</th>
								</tr>
							</thead>
							<tbody id="userList">
							</tbody>
						</table>
						<nav aria-label="..." id="dynamic-table_info">
							<ul class="pagination" id="page">
								<li>前往:<input id="fromPage" type="number" min="1" onchange="selectDetail(this)"/></li>
							</ul>
						</nav>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script>
		var table;
		$(document).ready(function () {
            table = $("#datatable").dataTable({
                "language":"${pageContext.request.contextPath}/chat/zh_CN.txt",
                "processing":false,
                "searching":false,
                "info":false,
                "bPaginate" : true,
                "sPaginationType":"full_numbers",
                "bLengthChange":false,
                "bStateSave": false,
                "bFilter": false,
                "iDisplayLength": 10,
                "bServerSide": true,
                "serverSide":true,
                "ajax":{
                    type:"POST",
                    data: function(d) {
                        document.getElementById("fromPage").value = "";
                        return $.extend({}, d, {
                            "userName": document.getElementById("selectUserName").value,
                            "modeTypeId": document.getElementById("selectModelType").value
                        });
                    },
                    url:"<%=request.getContextPath()%>/callback/operatingRecord/listModelByUserNameAndModelType"
                },
                "aoColumns":[
                    {
                        "mData": "showOperatingRecord",
                        "bSortable": false,
                        "sWidth": "10%",
                        "sClass": "center"
                    },
                    {
                        "mData": "operatingModel",
                        "bSortable": false,
                        "sWidth": "10%",
                        "sClass": "center"
                    },
                    {
                        "mData": "operatingIntroduce",
                        "bSortable": false,
                        "sWidth": "10%",
                        "sClass": "center"
                    },
                    {
                        "mData": "providerName",
                        "bSortable": false,
                        "sWidth": "15%",
                        "sClass": "center"
                    },
                    {
                        "mData": "provideRnickName",
                        "bSortable": false,
                        "sWidth": "10%",
                        "sClass": "center"
                    },
                    {
                        "mData": "createTime",
                        "bSortable": false,
                        "sWidth": "15%",
                        "sClass": "center"
                    }
                ]
            });
			$.ajax(
				{
					url:"<%=request.getContextPath()%>/callback/operatingRecord/listModelType",
					type:"POST",
					success:function ($data) {
						var $select = document.getElementById("selectModelType");
						for(var i = 0;i< $data.modelType.length;i++){
						    var $option = $(
                                "<option value='"+$data.modelType[i].id+"'>"+$data.modelType[i].operatingModelName+"</option>"
							)
							$option.appendTo($select);
						}
                    }
				}
			)
        })
        function selectDetail($input) {
            var $number = parseInt($input.value);
            var $a = $("span > a");
            if(null != $number && "" != $number){
                if($number <= 0 || $number > parseInt($a[$a.length - 1].innerText)){
                    laysuccess("输入数字不在有效范围");
                }else {
                    table.fnPageChange($number - 1)
                }
            }
        }
		function listModel() {
            table.fnDraw();
        }
	</script>
	<script src="${pageContext.request.contextPath}/chat/js/dataTables.select.js"></script>
</html>