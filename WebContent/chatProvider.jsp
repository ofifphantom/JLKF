<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <meta charset="utf-8"/>
    <title>chat</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chat/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chat/css/ace.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chat/css/style.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chat/css/style.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chat/css/style_i.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chat/font/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chat/js/layer/skin/layer.css"/>
    <!--[if IE 7]>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chat/font/css/font-awesome-ie7.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chat/css/ace-ie.min.css"/>
    <![endif]-->--/
    <!--引入JQuery EasyUI  核心UI 文件 CSS-->
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/chat/js/easyui-1.3.6/themes/default/easyui.css">
    <!--引入EasyUI图标文件-->
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/chat/js/easyui-1.3.6/themes/icon.css">
    <!--引入JQuery 核心库-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chat/css/mine/all.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chat/css/mine/public.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chat/css/mine/chat.css"/>
    <script src="${pageContext.request.contextPath}/chat/js/jquery-1.10.2.min.js"></script>
    <script src="${pageContext.request.contextPath}/chat/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/chat/js/typeahead-bs2.min.js"></script>
    <script src="${pageContext.request.contextPath}/chat/js/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/chat/js/jquery.dataTables.bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/chat/js/layer3.1.1/layer.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/chat/js/laydate/laydate.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/chat/js/jquery1.8.3.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/chat/script/public.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/chat/js/chat.js" type="text/javascript"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/chat/js/easyui-1.3.6/jquery.min.js"></script>
    <!--引入JQuery EasyUI  核心库 1.3.6版本-->
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/chat/js/easyui-1.3.6/jquery.easyui.min.js"></script>
    <!--进入 EasyUI中文提示信息-->
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/chat/js/easyui-1.3.6/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/chat/js/editor/wangEditor.min.js"></script>
    <style>
        .tab-star a {
            background: #ff9610 !important;
        }

        .tab-star-color a {
            background: #eaf2ff !important;
        }
        .order-div {
            width: 450px;
            margin: 15px 0;
            padding: 1.5%;
            background: white;
            font-size: 14px;
            letter-spacing: 1px;
            line-height: 35px;
            background: #61b747;
            color: #fff;
            border-radius: 5px;

        }

        .order-div img {
            width: 68px;
            height: 68px;
        }

        .order-div>div {
            float: left;
            font-size: 13px;
        }

        .order-div ul {
            padding: 0;
            margin: 0;
            margin-left:5px;
        }

        .order-div li {
            list-style: none;
            font-size: 13px;
            line-height: 20px;
        }

        .order-state {
            float: right!important;
            line-height: 60px;
            text-align: right;
        }
        .tree-title {
 width: 260px!important;
 overflow: hidden!important;
 white-space: nowrap!important;
 text-overflow: ellipsis!important;
}
       
    </style>

</head>
<body class="easyui-layout" id="mainLayout">
<div class="top" data-options="region:'north'" style="height:80px;background:#fff;">
    <nav class="top-left col-xs-6">
        <ul>
            <li>
                <img alt="Brand" src="${pageContext.request.contextPath}/chat/img/logo.png" width="100px"/>
            </li>
            <li>
                <h4>君霖食品客服系统</h4>
            </li>
            <li class="clearfix"></li>
        </ul>
    </nav>
    <nav class="top-right col-xs-6">
        <ul>
            <li>
                <button type="button" class="btncss jl-btn-defult" onclick="offLine()">下线</button>
            </li>
            <li>
                ${sessionScope.loginName} | 售前客服
            </li>
            <li class="clearfix"></li>
        </ul>
    </nav>

</div>
<div data-options="region:'west',split:true,title:'对话列表'" style="width:150px;">
    <ul class="easyui-tree" id="dialogListTree">
    </ul>
</div>
<div data-options="region:'east',split:true,title:'商品'" style="width:350px;">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north',border:false" style="height:301px;padding:15px;border-bottom:1px solid #ccc;">
            <div class="search-goods">
                <input type="text" id="seek" class="form-control" onkeydown="" placeholder="搜索商品">
                <i class="fa fa-search" aria-hidden="true" onclick="serchGoods()"></i>
            </div>
            <div id="goos-list">
                <section class="oneGoods">
                    <div class="col-xs-8">
                        <article class="good-title">
                            <span class="goodsName">阿根廷牛肉</span>
                            <span class="goodsType">500g/块</span>
                            <span class="goodsMoney">￥50</span>
                        </article>
                        <article class="good-id">
                            <span>商品编号：</span>
                            <span class="goodsId">123123</span>
                        </article>
                    </div>
                    <div class="col-xs-4 text-right">
                        <input type="button" class="btncss jl-btn-defult sendCommodity" value="发送"
                               onclick="sendGoods(this)"/>
                    </div>
                    <div class="clearfix"></div>
                </section>
                <section class="oneGoods">
                    <div class="col-xs-8">
                        <article class="good-title">
                            <span class="goodsName">阿根廷牛肉</span>
                            <span class="goodsType">500g/块</span>
                            <span class="goodsMoney">￥50</span>
                        </article>
                        <article class="good-id">
                            <span>商品编号：</span>
                            <span class="goodsId">123123</span>
                        </article>
                    </div>
                    <div class="col-xs-4 text-right">
                        <input type="button" class="btncss jl-btn-defult" value="发送" onclick="sendGoods(this)"/>
                    </div>
                    <div class="clearfix"></div>
                </section>
                <section class="oneGoods">
                    <div class="col-xs-8">
                        <article class="good-title">
                            <span class="goodsName">阿根廷牛肉</span>
                            <span class="goodsType">500g/块</span>
                            <span class="goodsMoney">￥50</span>
                        </article>
                        <article class="good-id">
                            <span>商品编号：</span>
                            <span class="goodsId">123123</span>
                        </article>
                    </div>
                    <div class="col-xs-4 text-right">
                        <input type="button" class="btncss jl-btn-defult" value="发送" onclick="sendGoods(this)"/>
                    </div>
                    <div class="clearfix"></div>
                </section>
            </div>
        </div>
        <div data-options="region:'center',title:'常用语',border:false,iconCls:'commonLanguageSetting',tools:'#tools'">
            <div id="tools">
                <i class="fa fa-cog commonLanguageSetting" aria-hidden="true" onclick="commonLanguageSetting()"></i>
            </div>
            <!--<div class="panel-header panel-header-noborder">
                <div class="panel-title">
                    <span>常用语</span>
                    <span class="r_f">
                        <i class="fa fa-cog" aria-hidden="true" onclick="commonLanguageSetting()"></i>
                    </span>
                </div>
            </div>-->

            <ul class="easyui-tree" id="commonLanguageSettingTree">
            </ul>
        </div>
    </div>
</div>
<div data-options="region:'center',title:'聊天',border:false">
    <div id="chatTabs" class="easyui-tabs" data-options="fit:true,border:false,plain:true">

        <div title="About" style="padding: 10px;">
            请点击左边对话列表进行聊天
        </div>
    </div>
</div>


<div id="commonLanguageSettingDiv" style="display:none;">
    <div class="container">
        <h5 class="jl-title">
            <span>常用语分类</span>
            <i class="r_f" style="margin-top: 14px;">
                <input type="button" class="btncss jl-btn-defult" value="添加分类"
                       onclick="commonLanguageClassificationAdd()"/>
            </i>
        </h5>
        <div id="">
            <select name="" id="classify" onchange="isDisable()">
                <option value="-1">---请选择分类---</option>
            </select>
            <i class="r_f" style="margin-top: 6px;">
                <input type="button" id="insert_useful_expressions" class="btncss jl-btn-defult" value="添加常用语"
                       onclick="commonLanguageAdd()"/>
                <input type="button" id="delClassifiy" class="jl-btn-warm" value="删除分类"
                       onclick="commonLanguageClassificationDel()"/>
            </i>
        </div>
        <table class="table table-bordered text-center" id="useful_expressions" border="" cellspacing="" cellpadding="">
            <tr>
                <th>常用语</th>
                <th>操作</th>
            </tr>

        </table>

    </div>
</div>

</body>

<script>


    window.onbeforeunload=function(a){    //页面跳转走之前的操作，(return false;)可阻断关闭和跳转

        //return false;
    }
    var DataAddress=null;
    $(function() {

        if (window.history && window.history.pushState) {
            $(window).on('popstate', function () {
                window.history.pushState('forward', null, '#');
                window.history.forward(1);
            });
        }
        window.history.pushState('forward', null, '#'); //在IE中必须得有这两行
        window.history.forward(1);
    })
    /*远程调用*/
    $(function () {
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
    	
        setInterval("showClientTree()", "3000");
        $("#mainLayout>.panel:last").css("margin-top", "1px");

        //商品信息
        $.ajax({
            url: "http://"+DataAddress+"/JLMIS/customService/goodsInformation/getGoodsMsg?goodsName=",
            type: "GET",
            dataType: "json",
            success: function (data) {
                if (data.length == 0) {
                    $("#goos-list").empty();
                } else {
                    $("#goos-list").empty();
                    for (var i = 0; i < data.length; i++) {
                        var option = `<section class="oneGoods">
							<div class="col-xs-8">
								<article class="good-title">
									<span class="goodsName">` + data[i].goodsName + `</span>
									<span class="goodsType">` + data[i].goodsSpecificationName + `</span>
									<span class="goodsMoney">￥` + data[i].goodsPrice + `</span>
									<span class="goodsIds" style="display:none">`+data[i].goodsId + `</span>
									<span class="goodsSpecificationId" style="display:none">`+data[i].goodsSpecificationId + `</span>
								</article>
								<article class="good-id">
									<span>商品编号：</span>
									<span class="goodsId">` + data[i].isPresell + `</span>
								</article>
							</div>
							<div class="col-xs-4 text-right">
								<input type="button"  	 class="btncss jl-btn-defult sendCommodity" value="发送" onclick="sendGoods(this)"/>
							</div>
							<div class="clearfix"></div>
						</section>`
                        $("#goos-list").append(option);
                    }
                }
            }
        });
        $("#dialogListTree").tree({
            url: "${pageContext.request.contextPath}/seeClient", //获取远程数据URL
            loadFile: function (data) {
                return data;
            },
            lines: true,
            checkbox: false,
            onlyLeafCheck: false, //定义是否只在末节点之前显示复选框
            dnd: false, //定义是否启动拖拽功能
            onClick: function (node) {
                var urlobj = node.attributes;
                if (urlobj != undefined) {
                    //添加tab
                    addTab(urlobj["customerName"], urlobj["customerId"]);


                    /*changeTitle(urlobj["customerName"])*/
                }
            },
            onLoadSuccess: function (node, param) {

            },
            onLoadError: function (arguments) {
                console.log(arguments);
            }
        })
        //获取数据显示在界面中常用语
        var result;
        $.ajax({
            url: '${pageContext.request.contextPath}/selectHabitLanguageType',
            type: "POST",
            dataType: "json",
            async: false,
            cache: false,
            success: function (data) {
                if (data.length == 0) {
                } else {
                    for (var i = 0; i < data.length; i++) {
                        var option = $("<option onclick=\'queryPhrases(" + data[i].id + ")\'  value='" + data[i].id + "'>"
                            + data[i].habitLanguageTypeName + "</option>");
                        $("#classify").append(option);
                    }

                }
            }
        });
        //查看当前用户下的所有常用语
        $.ajax({
            url: '${pageContext.request.contextPath}/selectHabitLanguageRelation',
            type: "POST",
            dataType: "json",
            async: false,
            cache: false,
            success: function (data) {
                if (data.length == 0) {
                } else {
                    for (var i = 0; i < data.length; i++) {
                        var option = "<tr><td id='" + data[i].id + "'>" + data[i].habitlanguageContent + "</td><td><input type=\"button\" class=\"jl-btn-warm\" value=\"删除\" onclick=\"commonLanguageDel(" + data[i].id + ")\"/></td></tr>";
                        $("#useful_expressions").append(option);
                    }

                }
                isDisable();
            }
        });
        $("#commonLanguageSettingTree").tree({
            url: "${pageContext.request.contextPath}/selectHabitLanguageRelationTree", //获取远程数据URL
            loadFile: function (data) {
                return data;
            },
            lines: true,
            checkbox: false,
            onlyLeafCheck: false, //定义是否只在末节点之前显示复选框
            dnd: false, //定义是否启动拖拽功能
            onClick: function (node) {
                var urlobj = node.children;
                if (urlobj == undefined) {
                    let str = "<div class=\'row clearfix\'><ul class=\'text-right messageul-right\'></li><li class=\'message-img\'>" + node.text + "</li></ul></div>";
                    $("#" + thisTas() + "").append(str);
                    var text = "<li class=\'message-text\'>" + node.text + "</li>";
                    rollingbottomn("" + thisTas() + "");
                    if (titleInfos() != "About") {
                        if (websocket.readyState == 1) {  //0-CONNECTING;1-OPEN;2-CLOSING;3-CLOSED
                            websocket.send("{\"identifying\": 1,\"clientLoginName\": \"${sessionScope.loginName}\",\"loginName\": \"" + titleInfos() + "\", \"textMessage\":\"" + text + "\"}");

                        } else {
                            layfail("聊天关闭,请刷新页面");
                        }
                    }
                }
            },
            onLoadSuccess: function (node, param) {
            },
            onLoadError: function (arguments) {
                console.log(arguments);
            }
        })
        var E = window.wangEditor;
        var editor1 = new E('#WangEdit_toolbar', '#WangEdit_text');  // 两个参数也可以传入 elem 对象，class 选择器
        editor1.create();

    })

    /*图片上传*/
    function picUpLoadFun(thisfile) {
        let files = thisfile.files[0];//上传的图片的所有信息

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
        let str = `<div class="row clearfix">
						<ul class="text-right messageul-right">
							<li class="message-img"><img src="` + mysrc + `" onclick="look_photo('` + mysrc + `')"/></li>
						</ul>
					</div>`
        if (titleInfos() != "About") {
            if (websocket.readyState == 1) {  //0-CONNECTING;1-OPEN;2-CLOSING;3-CLOSED
                var id = "picUpLoad" + titleInfos()
                var inputElement = document.getElementById(id);
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
                    websocket.send("{\"identifying\": 1,\"clientLoginName\": \"${sessionScope.loginName}\",\"loginName\": \"" + titleInfos() + "\", \"textMessage\": \"" + file.name + "\"}" + ";fileFinishSingle");

                    console.log("finnish");
                }
                inputElement.outerHTML = inputElement.outerHTML; //清空<input type="file">的值

            } else {
                layfail("聊天关闭，请刷新页面");
            }
        }
        $("#" + thisTas() + "").append(str);
        rollingbottomn("" + thisTas() + "");


    }

    /*文件上传*/
    function fileUpLoadFun(thisfile) {
        var f = thisfile.files;
        if (thisfile.files && thisfile.files[0]) {
            customerServiceDialogueAppendFile(f[0].name, f[0].size);
        }

    }

    function customerServiceDialogueAppendFile(name, size) {
        console.log(size);
        var fileName = "\"" + name + "\"";
        let str = "<div class=\'row clearfix\'><ul class=\'text-right messageul-right\'><li onclick='sendURL(\"" + name + "\")' class=\'message-text\'><span>" + name + "</span>&nbsp;<span>" + ((size / 1024) / 1024).toFixed(2) + "M</span>请点击下载</li></ul></div>"
        var text = "<li onclick='sendURL(" + name + ")' class=\'message-text\'><span>" + name + "</span>&nbsp;<span>" + ((size / 1024) / 1024).toFixed(2) + "M</span>请点击下载</li>"
        $("#" + thisTas() + "").append(str);
        if (titleInfos() != "About") {
            if (websocket.readyState == 1) {  //0-CONNECTING;1-OPEN;2-CLOSING;3-CLOSED
                sendFile("fileUpLoad" + titleInfos())
                websocket.send("{\"identifying\": 1,\"clientLoginName\": \"${sessionScope.loginName}\",\"loginName\": \"" + titleInfos() + "\", \"textMessage\":\"" + text + "\"}");
            } else {
                layfail("聊天处于关闭状态，请刷新页面")
            }
        }
        rollingbottomn("" + thisTas() + "");
    }

    <%--/*添加客户对话*/--%>
    <%--function customerDialogueAppendImg(obj){--%>
    <%--let str=`<div class="row clearfix">--%>
    <%--<ul class="text-right messageul-right">--%>
    <%--<li class="messgae-img"><img src="${pageContext.request.contextPath}/chat/img/kf.jpg"></li>--%>
    <%--<li class="message-arrow message-arrow-right"></li>--%>
    <%--<li class="message-text">`+obj+`</li>--%>
    <%--</ul>--%>
    <%--</div>`;--%>
    <%--$("#chatMessageShow").append(str);--%>
    <%--let div=document.getElementById("chatMessageShow");--%>
    <%--$('#chatMessageShow').animate({scrollTop:div.scrollHeight},1000);--%>
    <%----%>
    <%--}--%>

    <%--/*添加客服对话*/--%>
    <%--function customerServiceDialogueAppendImg(mysrc){--%>
    <%--let str=`<div class="row clearfix">--%>
    <%--<ul class="text-right messageul-right">--%>
    <%--<li class="messgae-img"><img src="${pageContext.request.contextPath}/chat/img/kf.jpg"></li>--%>
    <%--<li class="message-arrow message-arrow-right"></li>--%>
    <%--<li class="message-img"><img src="`+mysrc+`" onclick="look_photo('`+mysrc+`')"/></li>--%>
    <%--</ul>--%>
    <%--</div>`;--%>
    <%--$("#chatMessageShow").append(str);--%>
    <%----%>
    <%----%>
    <%--rollingbottomn("chatMessageShow");--%>
    <%--}--%>

    <%--function customerServiceDialogueAppendFile(name,size){--%>
    <%--console.log(size);--%>
    <%----%>
    <%--let str=`<div class="row clearfix">--%>
    <%--<ul class="text-right messageul-right">--%>
    <%--<li class="messgae-img"><img src="${pageContext.request.contextPath}/chat/img/kf.jpg"></li>--%>
    <%--<li class="message-arrow message-arrow-right"></li>--%>
    <%--<li class="message-text">--%>
    <%--<span>`+name+`</span>&nbsp;<span>`+((size/1024)/1024).toFixed(2)+`M</span>	--%>
    <%--</li>--%>
    <%--</ul>--%>
    <%--</div>`;--%>
    <%--$("#chatMessageShow").append(str);--%>
    <%----%>
    <%----%>
    <%--rollingbottomn("chatMessageShow");--%>
    <%--}--%>


    function look_photo(mysrc) {
        console.log(mysrc)
        layer.photos({
            photos: {
                "title": "", //相册标题
                "id": "photo", //相册id
                "start": 0, //初始显示的图片序号，默认0
                "data": [   //相册包含的图片，数组格式
                    {
                        "alt": "聊天记录",
                        "pid": 666, //图片id
                        "src": mysrc, //原图地址
                        "thumb": "" //缩略图地址
                    }
                ]
            }
            , anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机
        });
    }

    /*搜索商品*/
    function serchGoods() {
        let goods = $("#seek").val();
        //商品信息
        $.ajax({
            url: "http://"+DataAddress+"/JLMIS/customService/goodsInformation/getGoodsMsg?goodsName=" + goods,
            type: "GET",
            dataType: "json",
            success: function (data) {
                if (data.length == 0) {
                    $("#goos-list").empty();
                } else {
                    $("#goos-list").empty();
                    for (var i = 0; i < data.length; i++) {
                        var option = `<section class="oneGoods">
							<div class="col-xs-8">
								<article class="good-title">
									<span class="goodsName">` + data[i].goodsName + `</span>
									<span class="goodsType">` + data[i].goodsSpecificationName + `</span>
									<span class="goodsMoney">￥` + data[i].goodsPrice + `</span>
									<span class="goodsIds" style="display:none">`+data[i].goodsId + `</span>
									<span class="goodsSpecificationId" style="display:none">`+data[i].isPresell + `</span>
								</article>
								<article class="good-id">
									<span>商品编号：</span>
									<span class="goodsId">` + data[i].goodsIdentifier + `</span>
								</article>
							</div>
							<div class="col-xs-4 text-right">
								<input type="button"  class="btncss jl-btn-defult sendCommodity" value="发送" onclick="sendGoods(this)"/>
							</div>
							<div class="clearfix"></div>
						</section>`
                        $("#goos-list").append(option);
                    }

                }
            }
        });
    }

    /*修改聊天框标题的方法*/
    /*function changeTitle(str){
        $("#mainLayout").layout('panel', 'center').panel({
            title: str
        });}*/

    /*常用语设置*/
    function commonLanguageSetting() {
        layer.open({
            type: 1,
            title: "设置常用语", //不显示标题
            area: ['800px', ''],
            content: $('#commonLanguageSettingDiv'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
            closeBtn: 1,
            yes: function () {

            },
            end: function () {

            }
        });
    }


    /*添加常用语分类*/
    function commonLanguageClassificationAdd() {
        layer.prompt({title: '添加常用语分类', formType: 2}, function (pass, index) {

            $.ajax({
                url: '${pageContext.request.contextPath}/insertHabitLanguageType',
                type: "POST",
                dataType: "json",
                data: {HabitLanguageTypeName: pass},
                async: false,
                cache: false,
                success: function (data) {
                    if (data.length == 0) {
                        $("#classify").empty();
                        $("#classify").append("<option  value=\"-1\">---请选择分类---</option>");
                        $("#classify").find("option[value='-1']").attr("selected", true);
                    } else {
                        $("#classify").empty();
                        $("#classify").append("<option  value=\"-1\">---请选择分类---</option>");
                        for (var i = 0; i < data.length; i++) {
                            var option = $("<option  onclick=\'queryPhrases(" + data[i].id + ")\'  value='" + data[i].id + "'>"
                                + data[i].habitLanguageTypeName + "</option>");
                            $("#classify").append(option);
                        }
                        $("#classify").find("option[value='-1']").attr("selected", true);

                    }
                }
            });
            refreshTable();
            refeshuUseful();
            isDisable();
            console.log(pass)
            layer.close(index);
        });
    }

    /*添加常用语*/
    function commonLanguageAdd() {
        layer.prompt({title: '添加常用语', formType: 2, id: "insertUseful"}, function (pass, index) {
            $.ajax({
                url: '${pageContext.request.contextPath}/insertHabitLanguage',
                type: "POST",
                dataType: "json",
                data: {typeId: $("#classify").find("option:selected").val(), habitLanguageText: pass},
                async: false,
                cache: false,
                success: function (data) {
                    if (data.length == 0) {
                        $("#useful_expressions").empty();
                        $("#useful_expressions").append("<tr><th>常用语</th><th>操作</th></tr>");
                    } else {
                        $("#useful_expressions").empty();
                        $("#useful_expressions").append("<tr><th>常用语</th><th>操作</th></tr>");
                        for (var i = 0; i < data.length; i++) {
                            var option = "<tr><td id='" + data[i].id + "'>" + data[i].habitlanguageContent + "</td><td><input type=\"button\" class=\"jl-btn-warm\" value=\"删除\" onclick=\"commonLanguageDel(" + data[i].id + ")\"/></td></tr>";
                            $("#useful_expressions").append(option);
                        }
                    }

                }
            });
            $("#classify").find("option[value='-1']").attr("selected", true);
            refeshuUseful();
            refreshTable();
            isDisable();

            layer.close(index);

        });

    }


    /*保存信息并提交*/
    function save() {
        var tab1 = $('#chatTabs').tabs('getSelected');
        var titleInfo = tab1.panel('options').title;
        var tab = $('#chatTabs').tabs('getSelected');
        var index = $('#chatTabs').tabs('getTabIndex', tab);
        var id = "#remark" + titleInfo
        $.ajax({
            url: '${pageContext.request.contextPath}/SaveAndsubmit',
            type: "POST",
            dataType: "json",
            data: {remark: $("" + id + "").text(), id: titleInfo},
            async: false,
            cache: false,
            success: function (data) {
            }
        });
        $('#chatTabs').tabs('close', index);
    }

    /*发送商品*/
    function sendGoods(thisinput) {
        let parentDiv = $(thisinput).parents(".oneGoods");
        let name = parentDiv.find(".goodsName").html();
        let type = parentDiv.find(".goodsType").html();
        let money = parentDiv.find(".goodsMoney").html();
        let id = parentDiv.find(".goodsIds").html();
        let goodsSpecificationId = parentDiv.find(".goodsSpecificationId").html();
        var msg = "<div class=\'row clearfix\'><ul class=\'text-right messageul-right\'><li class=\'message-goods\'><article class=\'text-left\'><div>" + name + "&nbsp;&nbsp;" + type + "</div><div>" + money + "</div></article></li></ul></div>"
        $("#" + thisTas() + "").append(msg);
        var text = "<li class=\\'message-goods\\' onclick='getId(this)' title="+goodsSpecificationId+" id='"+id+"'><article class=\'text-left\'><div>" + name + "&nbsp;&nbsp;" + type + "</div><div>" + money + "</div></article></li>"
        if (titleInfos() != "About") {
            if (websocket.readyState == 1) {  //0-CONNECTING;1-OPEN;2-CLOSING;3-CLOSED
                websocket.send("{\"identifying\": 1,\"clientLoginName\": \"${sessionScope.loginName}\",\"loginName\": \"" + titleInfos() + "\", \"textMessage\":\"" + text + "\"}");
            } else {
                layfail("聊天处于关闭状态，请刷新页面")
            }
        }
        rollingbottomn("" + thisTas() + "");
    }

    function rollingbottomn(id) {
        let div = document.getElementById(id);
        $('#' + id).animate({scrollTop: div.scrollHeight}, 1000);
    }
    function getId(ob) {
        var id=$(ob).attr("id");
    }
    //jquery
    /*删除常用语分类*/
    function commonLanguageClassificationDel() {
        layer.confirm('您确定要删除这个常用语分类吗？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            $.ajax({
                url: '${pageContext.request.contextPath}/delHabitLanguageType',
                type: "POST",
                dataType: "json",
                data: {id: $("#classify").find("option:selected").val()},
                async: false,
                cache: false,
                success: function (data) {
                    if (data.length == 0) {
                        $("#classify").empty();
                        $("#classify").append("<option  value=\"-1\">---请选择分类---</option>");
                        $("#classify").find("option[value='-1']").attr("selected", true);
                    } else {
                        $("#classify").empty();
                        $("#classify").append("<option  value=\"-1\">---请选择分类---</option>");
                        for (var i = 0; i < data.length; i++) {
                            var option = $("<option  onclick=\'queryPhrases(" + data[i].id + ")\'  value='" + data[i].id + "'>"
                                + data[i].habitLanguageTypeName + "</option>");
                            $("#classify").append(option);
                        }
                        $("#classify").find("option[value='-1']").attr("selected", true);
                    }
                    refeshuUseful();
                    refreshTable();
                }
            });
            isDisable();
            layer.msg('删除成功', {icon: 1});
        });
    }
    /*删除常用语*/
    function commonLanguageDel(id) {
        layer.confirm('您确定要删除这个常用语吗？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            $.ajax({
                url: '${pageContext.request.contextPath}/delHabitLanguage',
                type: "POST",
                dataType: "json",
                data: {id: id},
                async: false,
                cache: false,
                success: function (data) {
                    if (data.length == 0) {
                        $("#useful_expressions").empty();
                        $("#useful_expressions").append("<tr><th>常用语</th><th>操作</th></tr>");
                    } else {
                        $("#classify").find("option[value='-1']").attr("selected", true);
                        $("#useful_expressions").empty();
                        $("#useful_expressions").append("<tr><th>常用语</th><th>操作</th></tr>");
                        for (var i = 0; i < data.length; i++) {
                            var option = "<tr><td id='" + data[i].id + "'>" + data[i].habitlanguageContent + "</td><td><input type=\"button\" class=\"jl-btn-warm\" value=\"删除\" onclick=\"commonLanguageDel(" + data[i].id + ")\"/></td></tr>";
                            $("#useful_expressions").append(option);
                        }
                    }
                    refeshuUseful();
                    layer.msg('删除成功', {icon: 1});
                }
            });
        });
    }

    /* 是否禁用按钮 */
    function isDisable() {
            if ($("#classify").find("option:selected").val() == -1) {
            $("#insert_useful_expressions").attr("disabled", "disabled")
            $("#delClassifiy").attr("disabled", "disabled")
        } else {
            $("#delClassifiy").attr("disabled", false)
            $("#insert_useful_expressions").attr("disabled", false)
        }
    }

    /*查询常用语*/
    function queryPhrases(id) {
        $.ajax({
            url: '${pageContext.request.contextPath}/selectHabitLanguageToType',
            type: "POST",
            dataType: "json",
            data: {id: id},
            async: false,
            cache: false,
            success: function (data) {
                if (data.length == 0) {
                    $("#useful_expressions").empty();
                    $("#useful_expressions").append("<tr><th>常用语</th><th>操作</th></tr>");
                } else {
                    $("#useful_expressions").empty();
                    $("#useful_expressions").append("<tr><th>常用语</th><th>操作</th></tr>");
                    for (var i = 0; i < data.length; i++) {
                        var option = "<tr><td id='" + data[i].id + "'>" + data[i].habitlanguageContent + "</td><td><input type=\"button\" class=\"jl-btn-warm\" value=\"删除\" onclick=\"commonLanguageDel(" + data[i].id + ")\"/></td></tr>";
                        $("#useful_expressions").append(option);
                    }
                }
            }
        });
    }

    <!-- 刷新设置中的常用语列表 -->
    function refreshTable() {

        //查看当前用户下的所有常用语
        $.ajax({
            url: '${pageContext.request.contextPath}/selectHabitLanguageRelation',
            type: "POST",
            dataType: "json",
            async: false,
            cache: false,
            success: function (data) {
                if (data.length == 0) {
                    $("#useful_expressions").empty();
                    $("#useful_expressions").append("<tr><th>常用语</th><th>操作</th></tr>");
                } else {
                    $("#useful_expressions").empty();
                    $("#useful_expressions").append("<tr><th>常用语</th><th>操作</th></tr>");
                    for (var i = 0; i < data.length; i++) {
                        var option = "<tr><td id='" + data[i].id + "'>" + data[i].habitlanguageContent + "</td><td><input type=\"button\" class=\"jl-btn-warm\" value=\"删除\" onclick=\"commonLanguageDel(" + data[i].id + ")\"/></td></tr>";
                        $("#useful_expressions").append(option);
                    }
                }
            }
        });
    }

    // //查看当前用户下的所有常用语
    // $.ajax({
    //     url: '/selectHabitLanguageRelation',
    //     type: "POST",
    //     dataType: "json",
    //     async: false,
    //     cache: false,
    //     success: function(data) {
    //         if (data.length==0){
    //             $("#useful_expressions").empty();
    //             $("#useful_expressions").append("<tr><th>常用语</th><th>操作</th></tr>");
    //         }else{
    //             $("#useful_expressions").empty();
    //             $("#useful_expressions").append("<tr><th>常用语</th><th>操作</th></tr>");
    //             for(var i=0;i<data.length;i++){
    //                 var option="<tr><td id='"+data[i].id+"'>"+data[i].habitlanguageContent+"</td><td><input type=\"button\" class=\"jl-btn-warm\" value=\"删除\" onclick=\"commonLanguageDel("+data[i].id+")\"/></td></tr>";
    //                 $("#useful_expressions").append(option);
    //             }
    //         }
    //     }
    // });

    <!-- 刷新常用语树形菜单 -->
    function refeshuUseful() {
        $("#commonLanguageSettingTree").tree({
            url: "${pageContext.request.contextPath}/selectHabitLanguageRelationTree", //获取远程数据URL
            loadFile: function (data) {
                return data;
            },
            lines: true,
            checkbox: false,
            onlyLeafCheck: false, //定义是否只在末节点之前显示复选框
            dnd: false, //定义是否启动拖拽功能
            onClick: function (node) {
                var urlobj = node.children;
                if (urlobj == undefined) {
                    let str = "<div class=\'row clearfix\'><ul class=\'text-right messageul-right\'><li class=\'message-img\'>" + node.text + "</li></ul></div>";
                    $("#" + thisTas() + "").append(str);
                    var text = "<li class=\'message-text\'>" + node.text + "</li>"
                    if (titleInfos() != "About") {
                        if (websocket.readyState == 1) {  //0-CONNECTING;1-OPEN;2-CLOSING;3-CLOSED
                            websocket.send("{\"identifying\": 1,\"clientLoginName\": \"${sessionScope.loginName}\",\"loginName\": \"" + titleInfos() + "\", \"textMessage\":\"" + text + "\"}");

                        }
                    }
                    rollingbottomn("" + thisTas() + "");
                }
            },
            onLoadSuccess: function (node, param) {

            },
            onLoadError: function (arguments) {
                console.log(arguments);
            }
        })
    }

    <!-- 下线 -->
    function offLine() {
        $.ajax({
            url: '${pageContext.request.contextPath}/offLine',
            type: "POST",
            dataType: "json",
            async: false,
            cache: false,
            success: function (data) {
                if (data == true) {
                    window.location.href = "${pageContext.request.contextPath}/"
                } else {
                }
            }
        });
    }

    function thisTas() {
        var tab = $('#chatTabs').tabs('getSelected');
        var titleInfo = tab.panel('options').title;
        var id = "chatMessageShow" + titleInfo
        return id;
    }

    function sendMessage() {
        var tab = $('#chatTabs').tabs('getSelected');
        var titleInfo = tab.panel('options').title;
        var id = "chatMessageShow" + titleInfo
        var WangEdit_textids = "WangEdit_text" + titleInfo
        var text = $("#" + WangEdit_textids + "").text();
        var msg = "<div class=\'row clearfix\'><ul class=\'text-right messageul-right\'><li class=\'message-goods\'><a href=\'\'><article class=\'text-left\'>" + text + "</article></a></li></ul></div>"
        $("#" + id + "").append(msg)
        var text = "<li class=\'message-goods\'><article class=\'text-left\'>" + text + "</article></li>"
        if (titleInfos() != "About") {
            if (websocket.readyState == 1) {  //0-CONNECTING;1-OPEN;2-CLOSING;3-CLOSED
                websocket.send("{\"identifying\":1,\"clientLoginName\": \"${sessionScope.loginName}\",\"loginName\": \"" + titleInfo + "\", \"textMessage\":\"" + text + "\"}");

            } else {
                layfail("聊天处于关闭状态,请刷新页面");
            }
        }
        var edit = window["edit" + titleInfo];
        edit.txt.html('')
        rollingbottomn("" + thisTas() + "");
    }

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
        websocket = new SockJS("http://${sessionScope.webSocketAddress}/JLKF/sockjs/socketServer.do");    //SockJS对应的地址
    }
    websocket.onopen = onOpen;
    websocket.onmessage = onMessage;
    websocket.onerror = onError;
    websocket.onclose = onClose;

    function onOpen(openEvent) {
        laysuccess("开始聊天")
    }


    var loginNames;
    //收到信息
    function onMessage(event) {
        if (typeof event.data == 'string') {
            if (event.data.indexOf("^BinarySystem")!=-1){
                loginNames=event.data.split('^BinarySystem')[0];
            }else{
                let msg;
                 loginNames = $.parseJSON(event.data).loginName
                if (loginNames!=null){
                    if ($.parseJSON(event.data).text.split('~')[1] != "img") {
                        var loginName = $.parseJSON(event.data).loginName
                        //获得发信息的人登陆的账号
                        msg = $.parseJSON(event.data).text;
                        var result = $.parseJSON(event.data).text;
                        if (result.indexOf("sendURL") != -1) {
                            var fileName = result.substring(result.indexOf("(") + 1, result.indexOf(")"));
                            msg = result.replace(fileName, "\"" + fileName + "\"");
                        }
                        msg = `<div class="row clearfix">
                <ul class="text-left messageul-left">
                ` + msg + `
            </ul>
            </div>`
                        $("#chatMessageShow" + loginName + "").append(msg);
                        rollingbottomn("" + thisTas() + "");
                        thisTas()
                        var title = titleInfos();
                        if (title != loginName) {
                            changeColor(loginName)
                        }
                    }
                }
            }

        }else {
            var reader = new FileReader();
            reader.onload = function (eve) {
                if (eve.target.readyState == FileReader.DONE) {
                    let str = `<div class="row clearfix">
						<ul class="text-left messageul-left">
							<li class="message-img"><img src="` + this.result + `" onclick="look_photo('` + this.result + `')"/></li>
						</ul>
					</div>`
                    //图片信息
                    $("#chatMessageShow" +  loginNames + "").append(str);
                    rollingbottomn("" + thisTas() + "");
                }

            };

            reader.readAsDataURL(event.data);
            if (title != loginNames) {
                changeColor(loginNames)
            }
        }
        }

    function onError() {
        offLine()


    }

    function onClose(event) {
        console.log(event.reason)
        offLine()

        layfail("聊天关闭")
    }


    function disconnect() {
        if (websocket != null) {
            websocket.close();
            websocket = null;
        }
    }

    function reconnect() {
        if (websocket != null) {
            websocket.close();
            websocket = null;
        }
        websocket.onopen = onOpen;
        websocket.onmessage = onMessage;
        websocket.onerror = onError;
        websocket.onclose = onClose;
    }

    function clean() {
        document.getElementById("plane").innerHTML = "";
    }

    function titleInfos() {
        var tab = $('#chatTabs').tabs('getSelected');
        var titleInfo = tab.panel('options').title;
        return titleInfo;
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
                    }else{
                        window.location.href = "${pageContext.request.contextPath}/downLoadFile?fileName=" + name;
                    }
                }
            }
        );

    }

    function sendFile(fileURI) {
        var inputElement = document.getElementById(fileURI);
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
            //websocket.send("{\"identifying\": 0,\"loginName\": \"123\", \"textMessage\": \""+file.name+"\"}"+";fileFinishSingle");

            console.log("finnish");
        }
        inputElement.outerHTML = inputElement.outerHTML; //清空<input type="file">的值
    }

    function showClientTree() {
        $("#dialogListTree").tree({
            url: "${pageContext.request.contextPath}/seeClient", //获取远程数据URL
            loadFile: function (data) {
                return data;
            },
            lines: true,
            checkbox: false,
            onlyLeafCheck: false, //定义是否只在末节点之前显示复选框
            dnd: false, //定义是否启动拖拽功能
            onClick: function (node) {

                var checknodes = $('#dialogListTree').tree('getParent', node.target);
                var temp;
                while (checknodes != null) {
                    temp = checknodes;
                    checknodes = $('#dialogListTree').tree('getParent', checknodes.target);//对该节点取父节点
                }
                var urlobj = node.attributes;
                if (urlobj != undefined) {
                    addTab(urlobj["customerName"], urlobj["customerId"], temp.id);
                    /*changeTitle(urlobj["customerName"])*/
                }

            },
            onLoadSuccess: function (node, param) {

            },
            onLoadError: function (arguments) {
                console.log(arguments);
            }
        })
        changeColors(titleInfos())

    }

    function changeColor(title) {
        $(".easyui-tabs .tabs li .tabs-title").each(function (index, obj) {
            if (title == $(obj).html()) {
                $(obj).parents("li").addClass("tab-star");
            }
        });
    }

    function changeColors(title) {
        $(".easyui-tabs .tabs li .tabs-title").each(function (index, obj) {

            if (title == $(obj).html()) {
                $(obj).parents("li").removeClass("tab-star");
            }

        });
    }

    function laysuccess(str) {
        layer.msg(str, {
            icon: 6,
            time: 2000
        });
    }
    //失败
    function layfail(str) {
        layer.msg(str, {
            icon: 5,
            time: 1500
        })
    }
   function getChatTabs1(id) {
        var ids = "chatMessageShow" + id;
        var WangEdit_textids = "WangEdit_text" + id
        //开头
        var titleHtml = "<div  class=\"easyui-layout\" data-options=\"fit:true\" attr-id=\"" + id + "\">\n\t\t\t\t\t\t<div id=\"" + ids + "\" data-options=\"region:'north',border:false\" style=\"height:300px;padding:10px;\">"
        //结尾
        var lastHtml = "<div class=\"row text-center messgae-time\"><span>" + getMyDate(new Date().getTime()) + "</span></div>" + "</div>\n\t\t\t\t\t\t<div data-options=\"region:'center',border:false\" style=\"border-top:1px solid #ccc;border-bottom:1px solid #ccc;\">\n\t\t\t\t\t\t\t<div class=\"easyui-layout\" data-options=\"fit:true\">\n\t\t\t\t\t\t\t\t<div class=\"CenterTools\" data-options=\"region:'north',border:false\" style=\"height:30px;\">\n\t\t\t\t\t\t\t\t\t<label for=\"picUpLoad" + id + "\"><i class=\"fa fa-picture-o\" aria-hidden=\"true\"  ></i></label>\n\t\t\t\t\t\t\t\t\t<label for=\"fileUpLoad" + id + "\"><i class=\"fa fa-file\" aria-hidden=\"true\" ></i></label>\n\t\t\t\t\t\t\t\t\t<span class=\"r_f\">\n\t\t\t\t\t\t\t\t\t\t<input type=\"button\" class=\"btncss jl-btn-warm\" value=\"发送\" onclick=\"\" />\n\t\t\t\t\t\t\t\t\t</span>\n\t\t\t\t\t\t\t\t\t<form>\n\t\t\t\t\t\t\t\t\t\t<input class=\"hidden\" type=\"file\"  id=\"picUpLoad" + id + "\"   accept=\"image/gif,image/jpeg,image/jpg,image/png,image/svg\"/>\n\t\t\t\t\t\t\t\t\t</form>\n\t\t\t\t\t\t\t\t\t<form action=\"\" method=\"post\">\n\t\t\t\t\t\t\t\t\t\t<input class=\"hidden\" type=\"file\"  id=\"fileUpLoad" + id + "\" />\n\t\t\t\t\t\t\t\t\t</form>\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t<div data-options=\"region:'center',border:false\" style=\"height: 30px;\">\n\t\t\t\t\t\t\t\t   \t <!--可使用 min-height 实现编辑区域自动增加高度-->\n\t\t\t\t\t\t\t\t    <div id=\"\" class=\"WangEdit_text text\"></div>\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t<div data-options=\"region:'south',border:false\" style=\"height:120px;\">\n\t\t\t\t\t\t\t<div class=\"remarks\">\n\t\t\t\t\t\t\t\t<textarea disabled='disabled' class=\"form-control\" id=\"remark" + id + "\" name=\"\" rows=\"\" cols=\"\" placeholder=\"备注\"></textarea>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<div class=\"remarks-submission\">\n\t\t\t\t\t\t\t\t<div class=\"time\">\n\t\t\t\t\t\t\t\t\t咨询时长：<span id=\"time" + id + "\">00 : 00 : 00</span>\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t<input type=\"button\" name=\"\" class=\"btncss jl-btn-importent\" value=\"保存并提交\" onclick=\"save(this)\"/>\n\t\t\t\t\t\t\t</div>"

        let $Tab
        $.ajax({
            url: '${pageContext.request.contextPath}/seeChattingRecords',
            type: "POST",
            dataType: "json",
            data: {loginName: id},
            async: false,
            cache: false,
            success: function (data) {

                if (data.length > 0) {
                    var contentHtml = "<div class=\"row text-center messgae-time\"><span>" + getMyDate(data[0].startTime) + "-----" + getMyDate(data[data.length - 1].startTime) + "</span></div>"
                    for (var i = 0; i < data.length; i++) {

                        if (data[i].sender == 0) {
                            //客户
                            contentHtml += "<div class=\"row clearfix\"><ul class=\"text-left messageul-left\"> " + data[i].records + " </ul> </div>";
                        } else {
                            //客服
                            contentHtml += "<div class=\"row clearfix\"><ul class=\"text-right messageul-right\"> " + data[i].records + " </ul> </div>";
                        }
                    }
                    $Tab = titleHtml + contentHtml + lastHtml
                }
                else {
                    $Tab = titleHtml + lastHtml
                }
            }
        });
        return $Tab;
    }

    function notOnClick() {
   $("#commonLanguageSettingTree").tree({
       url: "/selectHabitLanguageRelationTree", //获取远程数据URL
        loadFile: function (data) {
           return data;
        },
        lines: true,
        checkbox: false,
        onlyLeafCheck: false, //定义是否只在末节点之前显示复选框
        dnd: false, //定义是否启动拖拽功能

        onLoadSuccess: function (node, param) {

        },
        onLoadError: function (arguments) {
           console.log(arguments);
        }
    })}




    function getChatTabs(id) {
        var ids = "chatMessageShow" + id;
        var WangEdit_textids = "WangEdit_text" + id
        //开头
        var titleHtml = "<div  class=\"easyui-layout\" data-options=\"fit:true\" attr-id=\"" + id + "\">\n\t\t\t\t\t\t<div id=\"" + ids + "\" data-options=\"region:'north',border:false\" style=\"height:300px;padding:10px;\">"
        //结尾
        var lastHtml = "<div class=\"row text-center messgae-time\"><span>" + getMyDate(new Date().getTime()) + "</span></div>" + "</div>\n\t\t\t\t\t\t<div data-options=\"region:'center',border:false\" style=\"border-top:1px solid #ccc;border-bottom:1px solid #ccc;\">\n\t\t\t\t\t\t\t<div class=\"easyui-layout\" data-options=\"fit:true\">\n\t\t\t\t\t\t\t\t<div class=\"CenterTools\" data-options=\"region:'north',border:false\" style=\"height:30px;\">\n\t\t\t\t\t\t\t\t\t<label for=\"picUpLoad" + id + "\"><i class=\"fa fa-picture-o\" aria-hidden=\"true\"  ></i></label>\n\t\t\t\t\t\t\t\t\t<label for=\"fileUpLoad" + id + "\"><i class=\"fa fa-file\" aria-hidden=\"true\" ></i></label>\n\t\t\t\t\t\t\t\t\t<span class=\"r_f\">\n\t\t\t\t\t\t\t\t\t\t<input type=\"button\" class=\"btncss jl-btn-warm\" value=\"发送\" onclick=\"sendMessage()\" />\n\t\t\t\t\t\t\t\t\t</span>\n\t\t\t\t\t\t\t\t\t<form>\n\t\t\t\t\t\t\t\t\t\t<input class=\"hidden\" type=\"file\"  id=\"picUpLoad" + id + "\" onchange=\"picUpLoadFun(this)\"  accept=\"image/gif,image/jpeg,image/jpg,image/png,image/svg\"/>\n\t\t\t\t\t\t\t\t\t</form>\n\t\t\t\t\t\t\t\t\t<form action=\"\" method=\"post\">\n\t\t\t\t\t\t\t\t\t\t<input class=\"hidden\" type=\"file\"  id=\"fileUpLoad" + id + "\" onchange=\"fileUpLoadFun(this)\"/>\n\t\t\t\t\t\t\t\t\t</form>\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t<div data-options=\"region:'center',border:false\" style=\"height: 30px;\">\n\t\t\t\t\t\t\t\t   \t <!--可使用 min-height 实现编辑区域自动增加高度-->\n\t\t\t\t\t\t\t\t    <div id=\"" + WangEdit_textids + "\" class=\"WangEdit_text text\"></div>\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t<div data-options=\"region:'south',border:false\" style=\"height:120px;\">\n\t\t\t\t\t\t\t<div class=\"remarks\">\n\t\t\t\t\t\t\t\t<textarea class=\"form-control\" id=\"remark" + id + "\" name=\"\" rows=\"\" cols=\"\" placeholder=\"备注\"></textarea>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<div class=\"remarks-submission\">\n\t\t\t\t\t\t\t\t<div class=\"time\">\n\t\t\t\t\t\t\t\t\t咨询时长：<span id=\"time" + id + "\">00 : 00 : 00</span>\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t<input type=\"button\" name=\"\" class=\"btncss jl-btn-importent\" value=\"保存并提交\" onclick=\"save(this)\"/>\n\t\t\t\t\t\t\t</div>"

        let $Tab
        $.ajax({
            url: '${pageContext.request.contextPath}/seeChattingRecords',
            type: "POST",
            dataType: "json",
            data: {loginName: id},
            async: false,
            cache: false,
            success: function (data) {

                if (data.length > 0) {
                    var contentHtml = "<div class=\"row text-center messgae-time\"><span>" + getMyDate(data[0].startTime) + "-----" + getMyDate(data[data.length - 1].startTime) + "</span></div>"
                    for (var i = 0; i < data.length; i++) {

                        if (data[i].sender == 0) {
                            var result = data[i].records;
                            if (result.indexOf("sendURL") != -1) {
                                var fileName = result.substring(result.indexOf("(") + 1, result.indexOf(")"));
                                result = result.replace(fileName, "\"" + fileName + "\"");

                            }
                            //客户
                            contentHtml += "<div class=\"row clearfix\"><ul class=\"text-left messageul-left\"> " + result + " </ul> </div>";
                        } else {
                            var result = data[i].records;
                            if (result.indexOf("sendURL") != -1) {
                                var fileName = result.substring(result.indexOf("(") + 1, result.indexOf(")"));
                                result = result.replace(fileName, "\"" + fileName + "\"");

                            }
                            //客服
                            contentHtml += "<div class=\"row clearfix\"><ul class=\"text-right messageul-right\"> " + result + " </ul> </div>";
                        }
                    }
                    $Tab = titleHtml + contentHtml + lastHtml
                }
                else {
                    $Tab = titleHtml + lastHtml
                }
            }
        });
        return $Tab;

    }

    window.onbeforeunload = function(){
        offLines();
        websocket.close();
    }

</script>
</html>