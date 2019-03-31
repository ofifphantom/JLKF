<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<html lang="en">

	<head>
		<meta charset="utf-8" />
		<title>咨询记录</title>
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
			#editDiv{
			padding:20px;
			}
			.jl-panel{
				padding:10px;
			    line-height: 25px;
			}
			li>ol{
			margin:0;
			}
			.msg{
			margin-left:5px;
			}
			.custom .title{
			color:green;
			}
			.ceremony .title{
			color:blue;
			}
		</style>
	</head>

	<body class="content">
		<div class="page-content clearfix">
			<div id="Member_Ratings">
				<div class="d_Confirm_Order_style" style="margin-top:10px;">
					<h4 class="text-title">咨询记录</h4>
                            <div class="search-div clearfix">
                                <div class="clearfix">
                                    <span class="l_f">
                                    咨询ID： <input type="text" id="selectConsultationID" value="" />
                                </span>
                                    <span class="l_f">
                                    用户名： <input type="text" id="selectUserPhone" value="" />
                                </span>
                                    <span class="l_f">
                                    客服： <input type="text" id="selectServiceName"  value="" />
                                </span>
                                    <span class="l_f">
                                    开始时间：
                                    <input type="date" id="selectCreateTime" />
                                </span>
                                    </span>
                                    <span class="r_f">
                                    <input type="button"  class="btncss btn_search" onclick="listClient()" value="搜索" />
                                </span>
                                </div>

                            </div>
					<%
						Integer userTypeId = (Integer) request.getSession().getAttribute("userType");
						if(userTypeId != Integer.valueOf(5)){
					%>
                            <div class="opration-div clearfix">
                                <span class="r_f">
                                    <button type="button" class="btncss jl-btn-defult" onclick="outPutExcel()"><i class="fa fa-plus"></i> 导出报表</button>
                                </span>
                            </div>
                    <%
                        }
                    %>
					<div class="table_menu_list">
						<table class="table table-striped table-hover col-xs-12" id="datatable">
							<thead class="table-header">
								<tr>
									<th>咨询ID</th>
									<th>用户名</th>
									<th>VIP</th>
									<th>客服</th>
									<th>开始时间</th>
									<th>咨询时长</th>
									<th>操作</th>
								</tr>

							</thead>
							<tbody id="clientList">

							</tbody>
						</table>
						<nav aria-label="..." id="dynamic-table_info">
							<ul class="pagination" id="page">
								<li>前往:<input id="fromPage" type="number" min="1" onchange="selectDetail(this)"/></li>
							</ul>
						</nav>
					</div>
					<div id="editDiv" style="display: none;">
						<div class="container">
							<div id="talkBeginTime"></div>
							<div class="row">
								<div class="row jl-title">
									<span>聊天内容</span>
								</div>
								<div class="row jl-panel">
									<ul id="talkList"></ul>
								</div>
							</div>
						</div>
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
                "bPaginate" : true,
                "bLengthChange":false,
                "info":false,
                "bStateSave": false,
                "sPaginationType":"full_numbers",
                "bFilter": false,
                "iDisplayLength": 10,
                "bServerSide": true,
                "serverSide":true,
                "ajax":{
                    type:"POST",
                    data: function(d) {
                        document.getElementById("fromPage").value = "";
                        return $.extend({}, d, {
                            "consultNumber": $("#selectConsultationID").val(),
                            "userName": $("#selectUserPhone").val(),
                            "serviceName": $("#selectServiceName").val(),
                            "startTime": $("#selectCreateTime").val()
                        });
                    },
                    url:"<%=request.getContextPath()%>/callback/clientRecords/listClient"
                },
                "aoColumns":[
                    {
                        "mData": "consultNumber",
                        "bSortable": false,
                        "sWidth": "10%",
                        "sClass": "center",
                    },
                    {
                        "mData": "userName",
                        "bSortable": false,
                        "sWidth": "10%",
                        "sClass": "center"
                    },
                    {
                        "mData": "isVip",
                        "bSortable": false,
                        "sWidth": "10%",
                        "sClass": "center"
                    },
                    {
                        "mData": "serviceName",
                        "bSortable": false,
                        "sWidth": "15%",
                        "sClass": "center"
                    },
                    {
                        "mData": "createTime",
                        "bSortable": false,
                        "sWidth": "15%",
                        "sClass": "center"
                    },
                    {
                        "mData": "consultTime",
                        "bSortable": false,
                        "sWidth": "10%",
                        "sClass": "center"
                    }, {
                        "mData": "consultTime",
                        "bSortable": false,
                        "sWidth": "15%",
                        "sClass": "center",
                        "mRender": function(data, type, row) {
                            return '<td><input type="button" name= '+row.id+' class="btncss" onclick="selectDetails(this)" value="查看详情"/></td>'
                        }
                    }
                ]
            });
        })
        function listClient() {
            table.fnDraw();
        }
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
        function selectDetails($td) {
            $.ajax(
                {
                    url:"<%=request.getContextPath()%>/callback/clientRecords/listChattingRecords",
                    type:"POST",
                    data:{
                        clientId:$td.getAttribute("name")
                    },
                    success:function ($data) {
                        if($data.success && (null != $data.data.chattingRecordsEntityList && $data.data.chattingRecordsEntityList.length > 0)){
                        	var $talkDivList = document.getElementById("talkList");
                            var $talkBeginTime = document.getElementById("talkBeginTime");
                            var $talkList = $data.data.chattingRecordsEntityList;
                            $talkDivList.innerHTML = "";
                            $talkBeginTime.innerHTML = "<ul><li>咨询ID:"+$data.data.consultNumber+"</li><li>用户名:"+$data.data.userName+"</li><li>是否VIP:"+$data.data.isVip+"</li>" +
								"<li>客服用户名:"+$data.data.serviceLoginName+"</li><li>客服姓名:"+$data.data.serviceName+"</li>" +
								"<li>开始时间: "+ $data.data.entranceTime+ "</li><li>结束时间: " + $data.data.finishTime +"</li><li> 聊天时长: " + $data.data.consultTime+"</li></ul>";
                            for(var i = 0;i < $talkList.length;i++){
                                var $div = $();
								if($talkList[i].sender == 0){
                                    $div = $(
                                        "<li class='custom'><ol><li class='title'>客户：</li><li class='msg'>"+$talkList[i].records+"</li></li>"
									);
								}else if($talkList[i].sender == 1){
                                    $div = $(
                                        "<li class='ceremony'><ol><li class='title'>客服：</li><li class='msg'>"+$talkList[i].records+"</li></li>"
									)
								}
								$div.appendTo($talkDivList);
							}
                        	
                        	
                            layer.open({
                                type: 1,
                                title: "聊天记录",
                                closeBtn: 1,
                                area: ['800px', ''],
                                content: $("#editDiv")
                            })
						}else {
                            laysuccess("该用户没有聊天记录");
						}
                    }
                }
            )
        }
        function outPutExcel() {
            window.location.href = "<%=request.getContextPath()%>/callback/clientRecords/outPutExcel";
        }
	</script>
	<script src="${pageContext.request.contextPath}/chat/js/dataTables.select.js"></script>
</html>