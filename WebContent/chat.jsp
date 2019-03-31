<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<meta name = "format-detection" content = "telephone=no">


<html>
	<head>
		<style>
			li{list-style: none;}/*这里设置*/
		</style>
		<meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/chatClient/font_Icon/iconfont.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/chatClient/css/chat.css">
		<script src="${pageContext.request.contextPath}/chat/js/layer3.1.1/layer.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/chat/js/jquery-1.10.2.min.js"></script>
		<%--<link rel="stylesheet" href="${pageContext.request.contextPath}/chat/css/mine/chat.css"/>--%>
		<script src="${pageContext.request.contextPath}/static/js/date.js"></script>
		<script src="${pageContext.request.contextPath}/chat/js/bootstrap.min.js"></script>
		<script src="${pageContext.request.contextPath}/chat/js/typeahead-bs2.min.js"></script>
		<script src="${pageContext.request.contextPath}/chat/js/jquery.dataTables.min.js"></script>
		<script src="${pageContext.request.contextPath}/chat/js/jquery.dataTables.bootstrap.js"></script>
		<script src="${pageContext.request.contextPath}/chat/js/layer3.1.1/layer.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/chat/js/laydate/laydate.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/chat/script/public.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/chat/js/chat.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/chatClient/js/Singleton.js"></script>
	</head>
	<body>
		<div class="chatContainer">
			<div class="chatBtn">
				<i class="iconfont icon-xiaoxi1"></i>
			</div>
			<div class="chat-message-num">10</div>
			<div class="chatBox" ref="chatBox">
				<div class="chatBox-head">
					<div class="chatBox-head-one">
						<div class="chat-return chat-return0"><i class="iconfont icon-houtui"></i></div>
						客户服务
						<!--<div class="chat-close" style="margin: 10px 10px 0 0;font-size: 14px">关闭</div>-->
					</div>
					<div class="chatBox-head-two">
						<div class="chat-return chat-return1"><i class="iconfont icon-houtui"></i></div>
						客户服务
						<!--<div class="chat-people">
                    <div class="ChatInfoHead">
                        <img src="" alt="头像"/>
                    </div>
                    <div class="ChatInfoName">这是用户的名字，看看名字到底能有多长</div>
                </div>-->
						<div class="chat-sendOrder">发送订单</div>
					</div>
					<div class="chatBox-head-three">
						<div class="chat-return chat-return2"><i class="iconfont icon-houtui"></i></div>
						选择订单
					</div>
				</div>
				<div class="chatBox-info">
					<div class="chatBox-list" ref="chatBoxlist">
						<div class="chat-list-people">
							<div><img src="${pageContext.request.contextPath}/chatClient/img/icon01.png" alt="头像" /></div>
							<div class="chat-name">
								<p>客服</p>
							</div>
						</div>
						<!--<div class="chat-list-people">
                    <div><img src="img/icon02.png" alt="头像"/></div>
                    <div class="chat-name">
                        <p>葡萄酒</p>
                    </div>
                    <div class="message-num"></div>
                </div>
                
                <div class="chat-list-people">
                    <div><img src="img/icon03.png" alt="头像"/></div>
                    <div class="chat-name">
                        <p>樱花酒</p>
                    </div>
                    <div class="message-num"></div>
                </div>-->
					</div>
					<div class="chatBox-kuang" ref="chatBoxkuang">
						<div class="chatBox-content">
							<div class="chatBox-content-demo" id="chatBox-content-demo">


							</div>
						</div>
						<div class="chatBox-send">
							<div class="div-textarea" contenteditable="true"></div>
							<div>
								<label id="chat-tuxiang" title="发送图片" for="inputImage" class="btn-default-styles">
                            <input type="file" onchange="picUpLoadFun(this)" accept="image/jpg,image/jpeg,image/png"
                                   name="file" id="inputImage" class="hidden">
                            <i class="iconfont icon-tuxiang1"></i>
                        </label>
								<button id="chat-fasong" class="btn-default-styles"><i class="iconfont icon-fasong"></i>
                        </button>
							</div>

						</div>
					</div>
					<div class="chatBox-order" ref="chatBoxorder">
						<section id="order">

						</section>
					</div>
				</div>
			</div>
		</div>
		<script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
		<script>

            window.onload=function(){
                document.documentElement.addEventListener('touchstart', function (event) {
                    if (event.touches.length > 1) {
                        event.preventDefault();
                    }
                }, false);

                var lastTouchEnd = 0;
                document.documentElement.addEventListener('touchend', function (event) {
                    var now = Date.now();
                    if (now - lastTouchEnd <= 300) {
                        event.preventDefault();
                    }
                    lastTouchEnd = now;
                }, false);
            }
            var DataAddress=null;
            /*远程调用*/
            $(function () {
                $.ajax({
                    url: '${pageContext.request.contextPath}/seeChattingRecords',
                    type: "POST",
                    dataType: "json",
                    data: {loginName:${sessionScope.clientLoginName}},
                    async: false,
                    cache: false,
                    success: function (data) {
                        var contentHtml="";
                        var contentHtmlLast=0;
                        var state=0;
                        $("#chatBox-content-demo").html("");
                        if (data.length > 0) {
                            contentHtml+= " <div><span style='margin-left:30%;background-color: #e6e6e6;color: white; border-radius: 5px'>" + getMyDate(data[0].startTime) +"</span></div>"
                            for (var i = 0; i < data.length; i++) {
                                state=i;
                                if (data[i].sender == 0) {
                                    var msg = data[i].records;
                                    var result = data[i].records;
                                    if (result.indexOf("sendURL") != -1) {
                                        var b = result.substring(result.indexOf("(") + 1, result.indexOf(")"));
                                        msg = result.replace(b, "\"" + b + "\"");
                                    }
                                    //客户
                                    contentHtml += "<div class=\"clearfloat\">\n" +
                                        "\t\t\t\t\t\t\t\t\t<div class=\"right\">\n" +
                                        "\t\t\t\t\t\t\t\t\t\t<div class=\"chat-message\">"+msg+"</div>\n" +
                                        "\t\t\t\t\t\t\t\t\t\t<div class=\"chat-avatars\"><img src=\"${pageContext.request.contextPath}/chatClient/img/icon02.png\" alt=\"头像\" /></div>\n" +
                                        "\t\t\t\t\t\t\t\t\t</div>\n" +
                                        "\t\t\t\t\t\t\t\t</div>";
                                } else {
                                    var msg = data[i].records;
                                    var result = data[i].records;
                                    if (result.indexOf("sendURL") != -1) {
                                        var b = result.substring(result.indexOf("(") + 1, result.indexOf(")"));
                                        msg = result.replace(b, "\"" + b + "\"");
                                    }
                                    //客服
                                    contentHtml += "<div class=\"clearfloat\">\n" +
                                        "\t\t\t\t\t\t\t\t\t<div class=\"left\">\n" +
                                        "\t\t\t\t\t\t\t\t\t\t<div class=\"chat-avatars\"><img src=\"${pageContext.request.contextPath}/chatClient/img/icon01.png\" alt=\"头像\" /></div>\n" +
                                        "\t\t\t\t\t\t\t\t\t\t<div class=\"chat-message\">\n" +
                                        "\t\t\t\t\t\t\t\t\t\t\t"+msg+"\n" +
                                        "\t\t\t\t\t\t\t\t\t\t</div>\n" +
                                        "\t\t\t\t\t\t\t\t\t</div>\n" +
                                        "\t\t\t\t\t\t\t\t</div>";
                                }
                                if (i+1==data.length) {
									contentHtmlLast=1;
								}
                            }
                            $("#chatBox-content-demo").append(contentHtml)

                        }
                        if (contentHtmlLast==1||state==0) {
                            contentHtml="";
                            contentHtml= " <div><span style='margin-left: 45%;background-color: #e6e6e6;color: white; border-radius: 5px'>"+ thisTimes(new Date().getTime())+"</span></div>"
                            contentHtml+="<div onClick=\"call()\" class=\"clearfloat\">\n" +
                                "\t\t\t\t\t\t\t\t\t<div class=\"left\">\n" +
                                "\t\t\t\t\t\t\t\t\t\t<div class=\"chat-avatars\"><img src=\"${pageContext.request.contextPath}/chatClient/img/icon01.png\" alt=\"头像\" /></div>\n" +
                                "\t\t\t\t\t\t\t\t\t\t<div class=\"chat-message\">\n" +
                                "\t\t\t\t\t\t\t\t\t\t\t"+"我们的客服电话是：01086488182"+"\n" +
                                "\t\t\t\t\t\t\t\t\t\t</div>\n" +
                                "\t\t\t\t\t\t\t\t\t</div>\n" +
                                "\t\t\t\t\t\t\t\t</div>";
                            $("#chatBox-content-demo").append(contentHtml)
                        }
                    }
                });
                $.ajax({
                        url: '${pageContext.request.contextPath}/getDataAddress',
                        type: "get",
                        dataType: "json",
                        async: false,
                        cache: false,
                        success: function (data) {
                            DataAddress= data.dataAddress;
                        }
                    }
                );

                $.ajax({
               //     http://117.158.178.202:8000/JLMIS/user/getOrderMsgByUserId?userId=7
                    url: "http://"+DataAddress+"/JLMIS/user/getOrderMsgByUserId?userId="+${sessionScope.clientId},
                    type: "GET",
                    dataType: "json",
                    async: false,
                    cache: false,
                    success: function (data) {
                        if (data.code==200){
                            //清空订单
                            $("#order").html("");
                            for (var i = 0; i < data.resultData.length; i++) {
                                $("#order").append("<article onclick=\"checkOrder(this)\" class=\"order-div clearfloat\">\n" +
                                    "\t\t\t\t\t\t\t\t<div>\n" +
                                    "\t\t\t\t\t\t\t\t\t<img src=\""+data.resultData[i].goodsCoverUrl+"\" class=\"order-img\" />\n" +
                                    "\t\t\t\t\t\t\t\t</div>\n" +
                                    "\t\t\t\t\t\t\t\t<div>\n" +
                                    "\t\t\t\t\t\t\t\t\t<ul>\n" +
                                    "\t\t\t\t\t\t\t\t\t\t<li>订单号：<span class=\"order-num\">"+data.resultData[i].orderNo+"</span></li>\n" +
                                    "\t\t\t\t\t\t\t\t\t\t<li>订单金额：<span class=\"order-price\">"+data.resultData[i].orderPrice+"</span></li>\n" +
                                    "\t\t\t\t\t\t\t\t\t\t<li>下单时间：<span class=\"order-time\">"+timestampToTime(data.resultData[i].placeOrderTime)+"</span></li>\n" +
                                    "\t\t\t\t\t\t\t\t\t</ul>\n" +
                                    "\t\t\t\t\t\t\t\t</div>\n" +
                                    "\t\t\t\t\t\t\t\t<div class=\"order-state\">"+orderType(data.resultData[i].orderState)+"</div><div style=\"clear: both;\"></div></article>");
							}
						}
                    }
                });
            })
			function call() {

                try {
                    window.demo.callDial("01086488182");
                }catch (e) {

                }
                try {
                    window.webkit.messageHandlers.showPhoneCall.postMessage("01086488182")
                }catch (e) {

                }
            }
            function timestampToTime(timestamp) {
                return getSmpFormatDateByLong(timestamp, true);
            }
            function orderType(state) {
                switch (state){
					case 0:
					    return "待支付";
						break;
                    case 1:
                        return "待发货";
                        break;
                    case 2:
                        return "已完成";
                        break;
                    case 3:
                        return "已完成";
                        break;
                    case 4:
                        return "已取消";
                        break;
                    case 5:
                        return "已关闭";
                        break;
                    case 6:
                        return "售后中";
                        break;
                    case 7:
                        return "已退货退款";
                        break;
                    case 8:
                        return "已换货";
                        break;
                    case 9:
                        return "线下支付审核中";
                        break;
                    case 10:
                        return "已完成";
                        break;

				}

            }
		</script>
		<script>
			screenFuc();
			function screenFuc() {
				var topHeight = $(".chatBox-head").innerHeight(); //聊天头部高度
				//屏幕小于768px时候,布局change
				var winWidth = $(window).innerWidth();
				if(winWidth <= 768) {
					var totalHeight = $(window).height(); //页面整体高度
					$(".chatBox-info").css("height", totalHeight - topHeight);
					var infoHeight = $(".chatBox-info").innerHeight(); //聊天头部以下高度
					//中间内容高度
					$(".chatBox-content").css("height", infoHeight - 46);
					$(".chatBox-content-demo").css("height", infoHeight - 46);
					$(".chatBox-list,.chatBox-order").css("height", totalHeight - topHeight);
					$(".chatBox-kuang").css("height", totalHeight - topHeight);

                    $(".div-textarea").css("width", winWidth - 75);
				} else {
					$(".chatBox-info").css("height", 495);
					$(".chatBox-content").css("height", 448);
					$(".chatBox-content-demo").css("height", 448);
					$(".chatBox-list,.chatBox-order").css("height", 495);
					$(".chatBox-kuang").css("height", 495);
					$(".div-textarea").css("width", 300);
				}
			}
			(window.onresize = function() {
				screenFuc();
			})();
			//未读信息数量为空时
			var totalNum = $(".chat-message-num").html();
			if(totalNum == "") {
				$(".chat-message-num").css("padding", 0);
			}
			$(".message-num").each(function() {
				var wdNum = $(this).html();
				if(wdNum == "") {
					$(this).css("padding", 0);
				}
			});

			//打开/关闭聊天框
			$(".chatBtn").click(function() {
				$(".chatBox").toggle(10);
			})
			$(".chat-close").click(function() {
				$(".chatBox").toggle(10);
			})
			//进聊天页面
			$(".chat-list-people").each(function() {
				$(this).click(function() {
					var n = $(this).index();
					$(".chatBox-head-one").toggle(false);
					$(".chatBox-head-two").toggle(true);
					$(".chatBox-list").fadeToggle(false);
					$(".chatBox-kuang").fadeToggle(true);

					//传名字
					$(".ChatInfoName").text($(this).children(".chat-name").children("p").eq(0).html());

					//传头像
					$(".ChatInfoHead>img").attr("src", $(this).children().eq(0).children("img").attr("src"));

					//聊天框默认最底部
					$(document).ready(function() {
						$("#chatBox-content-demo").scrollTop($("#chatBox-content-demo")[0].scrollHeight);
					});
				})
			});

			//返回列表
			$(".chat-return1").click(function() {
				$(".chatBox-head-one").toggle(1);
				$(".chatBox-head-two").toggle(1);
				$(".chatBox-list").fadeToggle(1);
				$(".chatBox-kuang").fadeToggle(1);
			});
			//返回聊天窗口
			$(".chat-return2").click(function() {
				$(".chatBox-head-two").toggle(1);
				$(".chatBox-head-three").toggle(1);
				$(".chatBox-kuang").fadeToggle(1);
				$(".chatBox-order").fadeToggle(1);
			});

			$(".chatBox-head-one").click(function () {
                window.demo.backFunction();
            })
			//发送订单
			$(".chat-sendOrder").click(function() {
				$(".chatBox-head-two").toggle();
				$(".chatBox-head-three").toggle();
				$(".chatBox-kuang").fadeToggle();
				$(".chatBox-order").fadeToggle();
			})

			//      发送信息
			$("#chat-fasong").click(function() {
                if (websocket.readyState == 1) {  //0-CONNECTING;1-OPEN;2-CLOSING;3-CLOSED
				var textContent = $(".div-textarea").html().replace(/[\n\r]/g, '<br>')
				if(textContent != "") {
					$(".chatBox-content-demo").append("<div class=\"clearfloat\">" +
						"<div class=\"right\"> " +
						"<div class=\"chat-message\"> " + textContent + " </div> " +
						"<div class=\"chat-avatars\"><img src=\"${pageContext.request.contextPath}/chatClient/img/icon02.png\" alt=\"头像\" /></div> </div> </div>");
					var text="<li class='message-text'>"+textContent+"</li>"
                    websocket.send("{\"identifying\":0,\"loginName\": \"" + ${sessionScope.clientLoginName} + "\", \"textMessage\":\"" + text + "\"}");
					//发送后清空输入框
					$(".div-textarea").html("");
					//聊天框默认最底部
					$(document).ready(function() {
						$("#chatBox-content-demo").scrollTop($("#chatBox-content-demo")[0].scrollHeight);
					});
				}
				}else{
                    layfail("聊天意外关闭，请重新进入该页面")
				}
			});
            <!-- WebSocket聊天 -->
            var websocket = null;
            if ('WebSocket' in window) {
                //Websocket的连接
                websocket = new WebSocket("ws://${sessionScope.webSocketAddress}/JLKF/websocket/socketServer.do");//WebSocket对应的地址
            }
            else if ('MozWebSocket' in window) {
                //Websocket的连接
                websocket = new MozWebSocket("ws://${sessionScope.webSocketAddress}/JLKF/websocket/socketServer.do");//SockJS对应的地址
            }
            else {
                //SockJS的连接
                websocket = new SockJS("http://${sessionScope.webSocketAddress}/JLKF/sockjs/JLKsocketServer.do");    //SockJS对应的地址
            }
            websocket.onopen = onOpen;
            websocket.onmessage = onMessage;
            websocket.onerror = onError;
            websocket.onclose = onClose;

            function onOpen(openEvent) {
                laysuccess("开始聊天")
            }
            //失败
            function layfail(str) {
                layer.msg(str, {
                    icon: 5,
                    time: 1500
                })
            }
            function laysuccess(str) {
                layer.msg(str, {
                    icon: 6,
                    time: 2000
                });
            }

            function sendURL(name) {
                $.ajax({
                        url: '${pageContext.request.contextPath}/downLoadFile',
                        data:{fileName:name},
                        type: "get",
                        async: false,
                        success: function (data) {
                            if (data==false){
                                layfail("地址已经过期")
                            }else {
                                window.location.href = "${pageContext.request.contextPath}/downLoadFile?fileName=" + name;
							}
                        }
                    }
                );
            }
            function getId(ob) {
                var id=$(ob).attr("id");
                var isPresell=$(ob).attr("title");
                try {
                    window.demo.clickOnAndroid(id,isPresell);
                }catch (e) {

                }

                try {
                    window.webkit.messageHandlers.getGoodsDetailId.postMessage(id)
                }catch (e) {
                }
               // 方法名：getGoodsDetailId
            }
            /*图片上传*/
            function picUpLoadFun(thisfile) {
                var files = thisfile.files[0];//上传的图片 的所有信息
                console.log(thisfile.files[0]);
                if (thisfile.files && thisfile.files[0]) {
                    //判断file不为空，上传图片至后台
                    var reader = new FileReader();
                    reader.onload = function (evt) {
                        customerServiceDialogueAppendImg(evt.target.result);
                    }
                    reader.readAsDataURL(thisfile.files[0]);
                } else {
                    layfail("图片上传失败")
                }
            }

            /*添加客服对话*/
            function customerServiceDialogueAppendImg(mysrc) {
                var str =  "<div class=\"clearfloat\">" +
                    "<div class=\"author-name\"> </div> " +
                    "<div class=\"right\"> <div class=\"chat-message\"><img ontouchend=\"Singleton.getInstance(this.src)\" src=" + mysrc + "></div> " +
                    "<div class=\"chat-avatars\"><img src=\"${pageContext.request.contextPath}/chatClient/img/icon02.png\" alt=\"头像\" /></div> </div> </div>";
                    if (websocket.readyState == 1) {  //0-CONNECTING;1-OPEN;2-CLOSING;3-CLOSED
                        var inputElement = document.getElementById("inputImage");
                        var fileList = inputElement.files;
                        var file = fileList[0];
                        if (!file) return;
                        websocket.send(file.name + ":fileStart");
                        var reader = new FileReader();
                        //以二进制形式读取文件
                        reader.readAsArrayBuffer(file);
                        //文件读取完毕后该函数响应
                        reader.onload = function loaded(evt) {
                            var blob = evt.target.result;
                            //发送二进制表示的文件
                            websocket.send(blob);
                            websocket.send("{\"identifying\": 0,\"loginName\": \"" + ${sessionScope.clientLoginName} + "\", \"textMessage\": \"" + file.name + "\"}" + ";fileFinishSingle");
                            console.log("finnish");
                        }
                    } else {
                        layfail("聊天关闭，请刷新页面");
                    }

                $(".chatBox-content-demo").append(str);
                //聊天框默认最底部
                $(document).ready(function () {
                    $("#chatBox-content-demo").scrollTop($("#chatBox-content-demo")[0].scrollHeight);
                });
            }

			<%--//      发送图片--%>
			<%--function selectImg(pic) {--%>
                <%--if (websocket.readyState == 1) {  //0-CONNECTING;1-OPEN;2-CLOSING;3-CLOSED--%>


                    <%--if (!pic.files || !pic.files[0]) {--%>
                        <%--return;--%>
                    <%--}--%>
                    <%--var inputElement = document.getElementById("inputImage");--%>
                    <%--var fileList = inputElement.files;--%>
                    <%--var file = fileList[0];--%>
                    <%--websocket.send(file.name + ":fileStart");--%>
                    <%--//获得图片路径--%>
                    <%--var reader = new FileReader();--%>
                    <%--reader.onload = function (evt) {--%>
                        <%--var images = evt.target.result;--%>
                        <%--websocket.send("{\"identifying\": 0,\"loginName\": \"" + ${sessionScope.clientLoginName} + "\", \"textMessage\": \"" + file.name + "\"}" + ";fileFinishSingle");--%>
                        <%--$(".chatBox-content-demo").append("<div class=\"clearfloat\">" +--%>
                            <%--"<div class=\"author-name\"> </div> " +--%>
                            <%--"<div class=\"right\"> <div class=\"chat-message\"><img src=" + images + "></div> " +--%>
                            <%--"<div class=\"chat-avatars\"></div> </div> </div>");--%>

                        <%--//聊天框默认最底部--%>
                        <%--$(document).ready(function () {--%>
                            <%--$("#chatBox-content-demo").scrollTop($("#chatBox-content-demo")[0].scrollHeight);--%>
                        <%--});--%>
                    <%--};--%>
                    <%--reader.readAsDataURL(pic.files[0]);--%>
                <%--}else{--%>
                    <%--layfail("聊天意外关闭,请重新进入聊天")--%>
				<%--}--%>
			<%--}--%>
            function onError() {

            }
            function onClose(event) {
                console.log(event.reason)
                layfail("聊天关闭")
            }
			//发送订单
			function checkOrder(thisDiv){
				$(".chatBox-head-two").toggle(1);
				$(".chatBox-head-three").toggle(1);
				$(".chatBox-kuang").fadeToggle(1);
				$(".chatBox-order").fadeToggle(1);
				var src=$(thisDiv).find(".order-img").attr("src");
				var num=$(thisDiv).find(".order-num").html();
				var price=$(thisDiv).find(".order-price").html();
				var time=$(thisDiv).find(".order-time").html();
				var state=$(thisDiv).find(".order-state").html();
				$(".chatBox-content-demo").append("<div class=\"clearfloat\">\n" +
                    "                            <div class=\"author-name\">\n" +
                    "                                <small class=\"chat-date\"></small>\n" +
                    "                            </div>\n" +
                    "                            <div class=\"right\">\n" +
                    "                                <div class=\"chat-message\">\n" +
                    "                                <article  class=\"order-div clearfloat\">\n" +
                    "<div>\n" +
                    "<img src=\""+src+"\"/>\n" +
                    "</div>\n" +
                    "<div>\n" +
                    "<ul>\n" +
                    "<li>订单号：<span>"+num+"</span></li>\n" +
                    "<li>订单金额：<span>"+price+"</span></li>\n" +
                    "<li>下单时间：<span>"+time+"</span></li>\n" +
                    "</ul>\n" +
                    "</div>\n" +
                    "<div class=\"order-state\">订单状态:"+state+"</div>\n" +
                    "<div style=\"clear: both;\"></div>\n" +
                    "</article>\n" +
                    " \n" +
                    "</div>\n" +
                    "<div class=\"chat-avatars\"><img src=\"${pageContext.request.contextPath}/chatClient/img/icon02.png\" alt=\"头像\"></div>\n" +
                    "</div>\n" +
                    "</div>");
                /**
				  <li class="message-goods">
				     <img src='http://117.158.178.202:8000/JLMIS/GoodsDisplayPicture/0a68764291834267a7399b07dfa70dc7.jpg'>
				     <article class="text-left">
				         <div>订单号：<span>JLORDER20180620154503</span></div>
				         <div>订单金额：<span>0.1</span></div>
				         <div>下单时间：<span>50437-04-25 6:50:0</span></div>
				      <div>订单状态:已完成</div>
				    <div style='clear: both;'></div>
				     </article></a>
				     </li>
                 * @type {string}
                 */

				var msg="<article  class='order-div clearfix'><div><img src='"+src+"'/></div><div><ul><li>订单号：<span>"+num+"</span></li><li>订单金额：<span>"+price+"</span></li><li>下单时间：<span>"+time+"</span></li></ul></div><div class='order-state'>订单状态:"+state+"</div><div style='clear: both;'></div></article>"

                websocket.send("{\"identifying\": 0,\"loginName\": \"" + ${sessionScope.clientLoginName} + "\", \"textMessage\": \"" + msg +"\"}");

                //聊天框默认最底部
                $(document).ready(function() {
                    $("#chatBox-content-demo").scrollTop($("#chatBox-content-demo")[0].scrollHeight);
                });
			}
            //收到信息
            function onMessage(event) {
                if (typeof event.data == 'string') {
                    if (event.data.indexOf("^BinarySystem")!=-1){
                        //loginNames=event.data.split('^BinarySystem')[0];
					}else{
                    var msg;
                    //获得发信息的人登陆的账号
                    msg = event.data;
                    var result = event.data;
                    if (result.indexOf("sendURL") != -1) {
                        var b = result.substring(result.indexOf("(") + 1, result.indexOf(")"));
                        msg = result.replace(b, "\"" + b + "\"");
                    }
                    msg = "<div class=\"clearfloat\">\n" +
                        "\t\t\t\t\t\t\t\t\t<div class=\"left\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t<div class=\"chat-avatars\"><img src=\"${pageContext.request.contextPath}/chatClient/img/icon01.png\" alt=\"头像\" /></div>\n" +
                        "\t\t\t\t\t\t\t\t\t\t<div class=\"chat-message\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t"+msg+"\n" +
                        "\t\t\t\t\t\t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t\t\t\t</div>"

                    $("#chatBox-content-demo").append(msg);

                    //聊天框默认最底部
                    $(document).ready(function() {
                        $("#chatBox-content-demo").scrollTop($("#chatBox-content-demo")[0].scrollHeight);
                    });
                    }
                } else {
                    var reader = new FileReader();
                    reader.onload = function (eve) {
                        if (eve.target.readyState == FileReader.DONE) {
                            var str = "<div class=\"clearfloat\">\n" +
                                "\t\t\t\t\t\t\t\t\t<div class=\"left\">\n" +
                                "\t\t\t\t\t\t\t\t\t\t<div class=\"chat-avatars\"><img src=\"${pageContext.request.contextPath}/chatClient/img/icon01.png\" alt=\"头像\" /></div>\n" +
                                "\t\t\t\t\t\t\t\t\t\t<div class=\"chat-message\">\n" +
                                "\t\t\t\t\t\t\t\t\t\t\t<img ontouchend=\"Singleton.getInstance(this.src)\" src=\""+this.result+"\" alt=\"\">\n" +
                                "\t\t\t\t\t\t\t\t\t\t</div>\n" +
                                "\t\t\t\t\t\t\t\t\t</div>\n" +
                                "\t\t\t\t\t\t\t\t</div>"
                            //图片信息
                            $("#chatBox-content-demo").append(str);

                            //聊天框默认最底部
                            $(document).ready(function() {
                                $("#chatBox-content-demo").scrollTop($("#chatBox-content-demo")[0].scrollHeight);
                            });
                        }

                    };
                    reader.readAsDataURL(event.data);
                    if (title != loginNames) {
                        changeColor(loginNames)
                    }
                }

            }
            function getMyDate(str) {
                var oDate = new Date(str),
                    oYear = oDate.getFullYear(),
                    oMonth = oDate.getMonth() + 1,
                    oDay = oDate.getDate(),
                    oHour = oDate.getHours(),
                    oMin = oDate.getMinutes(),
                    oSen = oDate.getSeconds(),
                    oTime = oYear + '-' + getzf(oMonth) + '-' + getzf(oDay) + ' ' + getzf(oHour) + ':' +
                        getzf(oMin) + ':' + getzf(oSen);//最后拼接时间
                return oTime;
            };

		</script>
	</body>
</html>