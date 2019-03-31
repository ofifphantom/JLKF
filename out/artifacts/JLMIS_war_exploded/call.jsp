<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<head>
    <title>君霖客服云呼叫中心</title>
    <link rel="stylesheet" type="text/css" href="//g.alicdn.com/acca/workbench-sdk/0.2.2/main.min.css" />
    <style>
        *{
        margin: 0;
        padding: 0;
        }
        .mini-workbench {
            display: none;
        position: fixed;
        border-radius: 100%;
        height: 50px;
        line-height: 50px;
        width: 50px;
        text-align: center;
        background: #00c1de;
        color: white;
        cursor: pointer;
        bottom: 44px;
        right: 100px;
        }

        .test-call {
        cursor: pointer;
        color: #00c1de;
        }

        #workbench {
        font-size: 14px;
        color: #4F5357;
        width: inherit;
        height:inherit;
        background: #FFFFFF;
        transition: all .3s;
        }
        #workbench>div{
	       width:inherit !important;
	       line-height: 48px;
	       text-align: center;
        }

        .workbench_sdk_components-OnAndOff-___style__OnAndOff-box___trIVs .workbench_sdk_components-OnAndOff-___style__content-box___3GGza .workbench_sdk_components-OnAndOff-___style__off-btn-group___iDcXM{
            margin-top: 20px;
        }
        #workbench>div img{
            width: 250px;
		    margin: 30px;
		    left: 0;
        }
        .workbench_sdk_components-MicrophoneFailure-___style__microphone_box___1eNOY .workbench_sdk_components-MicrophoneFailure-___style__error_left___13-qy,
        .workbench_sdk_components-MicrophoneFailure-___style__microphone_box___1eNOY .workbench_sdk_components-MicrophoneFailure-___style__error_txt___2PUtD{
        float:none;
        }

        #test {
        position: fixed;
        width: 265px;
        background: #FFFFFF;
        top: 150px;
        right: 400px;
        }

        #workbench>div img {
            width: 24px;
            margin: 0;
            top: 18px;
            left: 57%;
        }
        .sdk-container input, .sdk-container select, .sdk-container textarea{
            text-align: center;
        }
        .workbench_sdk_components-Keyboard-___style__Keyboard-box___FjD7T .workbench_sdk_components-Keyboard-___style__not-in-call___13zhB .workbench_sdk_components-Keyboard-___style__call-keyboard___Of6kx .workbench_sdk_components-Keyboard-___style__callee-show___j4LXp input{
            width: 200px;
            margin: 10px;
        }

        .workbench_sdk_components-Keyboard-___style__Keyboard-box___FjD7T .workbench_sdk_components-Keyboard-___style__not-in-call___13zhB .workbench_sdk_components-Keyboard-___style__call-keyboard___Of6kx .workbench_sdk_components-Keyboard-___style__panel-body___3ujQi{
            width: 200px;
            margin: 0 auto;
            margin-top: 28px;
            margin-bottom: 29px;
        }
        .workbench_sdk_components-Keyboard-___style__Keyboard-box___FjD7T .workbench_sdk_components-Keyboard-___style__not-in-call___13zhB .workbench_sdk_components-Keyboard-___style__chose-caller___37X7k>span{
            width: 200px;
            display: block;
            margin: 0 auto;
        }
        .workbench_sdk_components-CallRecords-___style__call-records___EjBj4 .workbench_sdk_components-CallRecords-___style__records-item___1tVZq .workbench_sdk_components-CallRecords-___style__records-item-left___1zVrO h6{
            display: inline-block;
            margin-right: 50px;
        }
        .workbench_sdk_components-Keyboard-___style__Keyboard-box___FjD7T .workbench_sdk_components-Keyboard-___style__not-in-call___13zhB .workbench_sdk_components-Keyboard-___style__call-keyboard___Of6kx>button{
                width:200px;
        }
    </style>
</head>

<body>
<div id="app">
    <div onclick="openWorkbench()" class="mini-workbench">
        云呼
    </div>
    <div id="workbench"></div>
</div>

<h1 th:inline="text"></h1>
<script type="text/javascript" src="//g.alicdn.com/crm/acc-phone/1.0.9/SIPml-api.js"></script>
<script type="text/javascript" src="//g.alicdn.com/crm/acc-phone/1.0.9/index.js"></script>
<script type="text/javascript" src="//g.alicdn.com/acca/workbench-sdk/0.2.2/workbenchSdk.js"></script>
<script src="${pageContext.request.contextPath}/chat/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript">

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
    !(function (win) {
      win.workbench = new win.WorkbenchSdk({
        dom: 'workbench',
        instanceId: '1d429646-34e5-49a1-bbf2-34b183a000c3',
        //instanceId: '52cf1ae3-7400-4382-a528-78c00973042e',
        ajaxPath: '/JLKF/aliyun/ccc/api',
        ajaxMethod: 'post',
        afterCallRule: 10,
        header: true,
        autoAnswerCall: 5,
        // moreActionList: ['break', 'offline'],
        // outsideComponents: [{
        //   componentName: 'transfer',
        //   dom: 'test',
        //   btnStyle: { color: 'pink', background: 'gray' }
        // }],
        onErrorNotify: function (error) {
          console.warn(error)
        },
        onCallComing: function (aa, bb, cc, dd) {
          console.log(aa, bb, cc, dd);
          window.open("https://127.0.0.1:8443/customer/phoneNumber/135012345678")
        },
        onCallDialing: function (aa, bb, cc, dd) {
          console.log(aa, bb, cc, dd);
        },
        onStatusChange: function (code, lastCode, context){
          console.log(code, lastCode, context);
        }
      })
      //面板展示开关
      win.workbenchOpen = true;
      openWorkbench = function () {
        var workbenchWrapper = document.querySelector("#workbench");
        var floater = document.querySelector(".mini-workbench");
        win.workbench.changeVisible(win.workbenchOpen);
        if (!win.workbenchOpen) {
          workbenchWrapper.style.display = 'none';
          floater.innerHTML = '云呼';
        } else {
          workbenchWrapper.style.display = 'block';
          floater.innerHTML = 'X';
        }
        win.workbenchOpen = !win.workbenchOpen
      }

      call = function () {
        win.workbench.call("135012345678")
      }

      applyBreak = function () {
        var yes = prompt("请输入：小休id-小休名称（请不要输入0-default", "1-wc");
        if(yes){
          var id = yes.split("-")[0] || 1;
          var name = yes.split("-")[1] || 'wc';
          win.workbench.applyForBreak(id, name);
        }
      }
    })(window)

</script>
</body>

</html>
