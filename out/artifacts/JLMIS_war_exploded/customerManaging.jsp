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
			.box{
				position: relative;
			}
			.box .icon-search{
				position: absolute;
				width: 20px;
				height: 20px;
				top: 10px;
				right: 20px;
				cursor:hand;
				/*background-color: #b0b0b0;*/
			}
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
							用户名： <input type="text" id="selectUserLoginName" value="" />
						</span>
							<span class="l_f"> 
							姓名： <input type="text" id="selectUserName" value="" />
						</span>
						<span class="l_f">
							账号类型：
                            <select id="selectUserType">
								<option value="-1">--请选择--</option>
							</select>
						</span>
							
						</span>
							<span class="r_f"> 
							<input type="button"  class="btncss btn_search" onclick="listUser()" value="搜索" />
						</span>
						</div>

					</div>
					<div class="opration-div clearfix">
						<span class="r_f"> 
							<button type="button" class="btncss jl-btn-defult" onclick="customerAdd()"><i class="fa fa-plus"></i> 新增</button>
						</span>
					</div>
					<div class="table_menu_list">
						<table class="table table-striped table-hover col-xs-12" id="datatable">
							<thead class="table-header">
								<tr>
									<th>编号</th>
									<th>用户名</th>
									<th>姓名</th>
									<th>邮箱</th>
									<th>类型</th>
									<th>创建时间</th>
									<th>操作</th>
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

		<!--新增 编辑 -->
		<div id="editDiv" style="display: none;">
			<form class="container">
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label for="name" class="col-xs-2 control-label"><b class="text-danger"> * </b>用户名</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" id="name" onchange="judgeLoginName(this.value)" onblur="cky(this)"/>
                        <span id="hint" style="color:crimson;"></span>
					</div>
				</div>
				<div class="form-group" id="divPassword">
					<div class="col-xs-2"></div>
					<label for="password" class="col-xs-2 control-label"><b class="text-danger"> * </b>密码</label>
					<div class="col-xs-4 box">
						<i class="fa fa-eye icon-search" aria-hidden="true" onclick="showPassword()"></i>
						<input type="password" class="form-control" id="password" onchange="userpwdCheck(this)"/>
					</div>
					<div class="col-xs-4">
						<input type="button" class="btncss jl-btn-defult" value="生成密码" onclick="makePassword()"/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label for="userName" class="col-xs-2 control-label"><b class="text-danger"> * </b>姓名</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" id="userName"  onblur="cky(this)"/>
					</div>
				</div>
				
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label for="userEmail" class="col-xs-2 control-label">邮箱</label>
					<div class="col-xs-4">
						<input type="email" class="form-control" id="userEmail" onchange="judgeEmail()" onblur="cky(this)"/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-2"></div>
					<label for="updateUserType" class="col-xs-2 control-label"><b class="text-danger"> * </b>账户类型</label>
					<div class="col-xs-4">
						<select class="form-control" id="updateUserType" autocomplete="off">
						</select>
					</div>
				</div>
			</form>

		</div>

	</body>

	<script>
        var table;
			/*加载必要信息*/
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
                                "loginName": $("#selectUserLoginName").val(),
                                "userName": $("#selectUserName").val(),
                                "userTypeId": $("#selectUserType").val()
                            });
                        },
						 url:"<%=request.getContextPath()%>/callback/userManager/listUser"
					},
					"aoColumns":[
						{
							"mData": "showUserId",
							"bSortable": false,
							"sWidth": "10%",
							"sClass": "center",
						},
						{
							"mData": "loginName",
							"bSortable": false,
							"sWidth": "10%",
							"sClass": "center"
						},
						{
							"mData": "userName",
							"bSortable": false,
							"sWidth": "10%",
							"sClass": "center"
						},
						{
							"mData": "userEmail",
							"bSortable": false,
							"sWidth": "15%",
							"sClass": "center"
						},
						{
							"mData": "userType",
							"bSortable": false,
							"sWidth": "10%",
							"sClass": "center"
						},
						{
							"mData": "createTime",
							"bSortable": false,
							"sWidth": "15%",
							"sClass": "center"
						},{
                            "mData": "isForbidden",
                            "bSortable": false,
                            "sWidth": "15%",
                            "sClass": "center",
                            "mRender": function(data, type, row) {
                                var buttons;
                                if(row.isForbidden == 0){
                                    buttons = '<td><input type="button" class="btncss edit"  name='+ row.id +' onclick="customerDisable(this)" value="禁用"/>'
								}else {
                                    buttons = '<td><input type="button" class="btncss edit"  name='+ row.id +' onclick="customerDisable(this)" value="解禁"/>'
								}
								buttons = buttons + '<input type="button" class="btncss edit" onclick="customerEdit('+row.id+')" value="编辑" /></td>';
                                return buttons
							}
                        }
					]
				});
		    $.ajax({
                type:"POST",
                url: "<%=request.getContextPath()%>/callback/userManager/listUserAll",
				success:function () {
					var $userType = document.getElementById("selectUserType");
					$.post("<%=request.getContextPath()%>/callback/userManager/listUserTypeByLoginType",function (data) {
					    var $userTypeAll = data.userType;
						for(var i = 0;i < $userTypeAll.length;i++){
						    var $option = $(
						        "<option value='"+$userTypeAll[i].id+"'>"+$userTypeAll[i].userTypeName+"</option>"
							)
							$option.appendTo($userType);
						}
                    })
                }
				})
            $.ajax(
                {
                    url:"<%=request.getContextPath()%>/callback/userManager/listUserTypeByLoginType",
                    type:"POST",
                    success:function (data) {
                        var $userType = document.getElementById("updateUserType");
                        var $userTypeAll = data.userType;
                        for(var i = 0;i < $userTypeAll.length;i++){
                            var $option = $(
                                "<option value='"+$userTypeAll[i].id+"'>"+$userTypeAll[i].userTypeName+"</option>"
                            )
                            $option.appendTo($userType);
                        }
                    }
                }
            )
        })
		var state=0;
		function showPassword() {
		    if(state==0){
                $("#password").removeAttr("type");
                state=1;
			}else  if(state ==1){
                $("#password").attr("type","password");
                state=0;
			}
        }
        /* 验证密码 */
        function userpwdCheck(thisinput) {
            userpwd = checkPassword($(thisinput).val());
            if(!isnotEmpty($(thisinput).val())) {
                laywarn("密码不能为空");
                userpwd = false;
            } else {
                if(!userpwd) {
                    laywarn("密码为8-16位字母数字下划线组合");
                    userpwd = false;
                }else {
                    userpwd = true;
                }
            }
            return userpwd;
        }
		/*修改*/
		function customerEdit($id) {
			layer.open({
				type: 1,
				title: "编辑客服",
				closeBtn: 1,
				area: ['800px', ''],
				content: $("#editDiv"),
                offset:[0,0],
				btn: ['提交','取消'],
				success:function(){
                    document.getElementById("name").disabled = true;
                    $.ajax(
                        {
                            url:"<%=request.getContextPath()%>/callback/userManager/getUserByUserId",
                            type:"POST",
                            data:{
                                userId : $id
                            },
                            success:function ($data) {
                                $("#divPassword").hide();
                                document.getElementById("name").value = $data.user.loginName;
                                document.getElementById("userName").value = $data.user.userName;
                                document.getElementById("userEmail").value = $data.user.userEmail;
                                var $userTypeId = $data.user.userTypeId;
                                var $select = document.getElementById("updateUserType");
								for(var i = 0;i < $select.children.length;i++){
                                    if($select.children[i].value == $userTypeId){
                                        $select.children[i].selected = true;
                                        break;
                                    }
								}
                            }
                        }
                    )
				},
				yes:function(index){
					if(judgeFromFormat(0)){
                        var $selectUserTypeId = document.getElementById("updateUserType").value;
                        $.ajax(
                            {
                                url:"<%=request.getContextPath()%>/callback/userManager/updateUser",
                                type:"POST",
                                data:{
                                    id:$id,
                                    userName:document.getElementById("userName").value,
                                    loginName:document.getElementById("name").value,
                                    userEmail:document.getElementById("userEmail").value,
                                    userTypeId:$selectUserTypeId
                                },
                                success:function ($data) {
                                    if($data.success){
                                        layer.close(index);
                                        clearForm("editDiv","");
                                        laysuccess("修改成功");
                                        listUser();
                                        var $userTypeId = <%=session.getAttribute("userType")%>;
                                        var $userId = <%=session.getAttribute("userId")%>
                                        if($id == $userId){
                                            if($userTypeId != $selectUserTypeId){
                                                hrefLogin();
											}
                                        }
                                    }
                                }
                            }
                        )
					}
				},
				btn2: function(index, layero){
					layer.close(index);
					clearForm("editDiv","");
  				}
			});
		}
		/*清空文本框*/
		function clearFrom() {
		    document.getElementById("password").value = "";
            document.getElementById("name").value = "";
            document.getElementById("userName").value = "";
            document.getElementById("userEmail").value = "";
            document.getElementById("updateUserType").value = "";
            document.getElementById("updateUserType").innerHTML = "";
        }
		/*新增*/
		function customerAdd() {
            clearFrom();
			layer.open({
				type: 1,
				title: "新增客服",
				closeBtn: 1,
				area: ['800px', ''],
				content: $("#editDiv"),
                offset:[0,0],
				btn: ['提交','取消'],
                success:function(){
                    $("#divPassword").show();
                    document.getElementById("name").disabled = false;
                    $.ajax(
						{
							url:"<%=request.getContextPath()%>/callback/userManager/listUserTypeByLoginType",
							type:"POST",
							success:function ($data) {
                                var $userType = document.getElementById("updateUserType");
                                var $userTypeAll = $data.userType;
                                var $option1 = $("<option value='-1'>请选择</option>");
                                $option1.appendTo($userType);
                                for(var i = 0;i < $userTypeAll.length;i++){
                                    var $option = $(
                                        "<option value='"+$userTypeAll[i].id+"'>"+$userTypeAll[i].userTypeName+"</option>"
                                    )
                                    $option.appendTo($userType);
                                }
                            }
						}
					)
                },
				yes:function(index){
                   if(judgeFromFormat(1)){
                       $.ajax(
                           {
                               url:"<%=request.getContextPath()%>/callback/userManager/insertUser",
                               type:"POST",
                               data:{
                                   userName:document.getElementById("userName").value,
                                   loginName:document.getElementById("name").value,
                                   loginPassword:document.getElementById("password").value,
                                   userEmail:document.getElementById("userEmail").value,
                                   userTypeId:document.getElementById("updateUserType").value
                               },
                               success:function ($data) {
                                   if($data.success){
                                       layer.close(index);
                                       clearForm("editDiv","");
                                       listUser();
                                       laysuccess("新增成功");
                                   }
                               }
                           }
                       )
				   }
				},
				btn2: function(index, layero){
					layer.close(index);
					clearForm("editDiv","");
  				}
			});
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
		/* 判断表单格式*/
		function judgeFromFormat(index) {
            var emailReg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
            var loginNameReg =  /^[0-9a-zA-Z]+$/;
            var userName = /^[\u4e00-\u9fa5]+$/;
            var $userName = document.getElementById("userName").value;
            var $loginName = document.getElementById("name").value;
            var $loginPassword = document.getElementById("password").value;
            var $userType = document.getElementById("updateUserType").value;
            var $userEmail = document.getElementById("userEmail").value;
            if(index == 1){
                if("" == $loginName || null == $loginName){
                    laysuccess("用户名不能为空");
                }else if(($loginName.length < 6 || $loginName.length > 15) || !loginNameReg.test($loginName)){
                    laysuccess("用户名格式为6-15位字母或数字组合");
                    return false;
                } else if(judgeLoginName($loginName) == 1){
                    return false;
                }
                if("" == $loginPassword || null == $loginPassword ){
                    laysuccess("密码不能为空");
                    return false;
                }else if($loginPassword.length < 8 || $loginPassword.length > 16){
                    laysuccess("密码长度为8-16位");
                    return false;
				}
            }
            if("" == $userName || null == $userName){
                laysuccess("姓名不能为空");
                return false;
            }else if(($userName.length < 2 || $userName > 10) || !userName.test($userName)){
                laysuccess("姓名格式为长度为2-10的汉字");
                return false;
            }
            if("" != $userEmail && null != $userEmail){
                if(!emailReg.test($userEmail)){
                    laysuccess("邮箱格式不正确");
                    return false;
                }
            }
			if($userType == -1 && $userType < 0){
                laysuccess("请选择账号类型");
                return false;
			}
           return true;
        }
		function judgeEmail() {
            var $userEmail = document.getElementById("userEmail").value;
            var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
            if("" != $userEmail && null != $userEmail){
                if(!reg.test($userEmail)){
                    laysuccess("邮箱格式不正确");
                }
			}
        }
		/*禁用*/
		function customerDisable($tr) {
		    var $id = $tr.getAttribute("name");
			if($tr.value == "禁用"){
                publicMessageLayer("禁用此客服", function() {
                   var userId = <%=session.getAttribute("userId")%>
					if(userId == $id){
                        publicMessageLayer("禁用自己",function () {
                            $.ajax(
                                {
                                    url:"<%=request.getContextPath()%>/callback/userManager/disableUser",
                                    type:"POST",
                                    data:{
                                        id : $id,
                                        isForbidden:1
                                    },
                                    success:function ($resultData) {
                                        $tr.value = "解禁";
                                        laysuccess("已禁用");
                                        if($resultData.code == -1){
                                            hrefLogin();
                                        }
                                    }
                                }
                            )
                        })
					}else {
                        $.ajax(
                            {
                                url:"<%=request.getContextPath()%>/callback/userManager/disableUser",
                                type:"POST",
                                data:{
                                    id : $id,
                                    isForbidden:1
                                },
                                success:function ($resultData) {
                                    $tr.value = "解禁";
                                    laysuccess("已禁用");
                                    if($resultData.code == -1){
                                        hrefLogin();
                                    }
                                }
                            }
                        )
					}
                })
			}else if($tr.value == "解禁"){
                publicMessageLayer("解除禁用此客服", function() {
                    $.ajax(
                        {
                            url:"<%=request.getContextPath()%>/callback/userManager/disableUser",
                            type:"POST",
                            data:{
                                id : $id,
                                isForbidden:0
                            },
                            success:function () {
                                $tr.value = "禁用";
                                laysuccess("已解除禁用");
                            }
                        }
                    )
                })
			}
		}
		function hrefLogin() {
            top.location.href="<%=request.getContextPath()%>/login";
            laysuccess("你的账号有变动，请重新登录");
        }
        <%--/*根据条件查询客服*/--%>
        function listUser($input) {
			table.fnDraw();
        }
		/*自动生成密码*/
		function makePassword(){
			$.post("<%=request.getContextPath()%>/callback/userManager/getUserPassword",function (data) {
			    var pwd = data.msg;
                $("#password").val(pwd);
            })
		}
		/*判断用户名是否存在*/
		function judgeLoginName($text) {
		    var index = 0;
            $.ajax(
                {
                    url:"<%=request.getContextPath()%>/callback/userManager/judgeLoginName",
                    type:"POST",
                    async:false,
                    data:{
                        userName:$text
                    },
                    success:function ($data) {
                        if($data.success){
                            laysuccess("用户名已存在");
                            index = 1;
                        }
                    }
                }
            )
			return index;
        }
	</script>
	<script src="${pageContext.request.contextPath}/chat/js/dataTables.select.js"></script>
</html>