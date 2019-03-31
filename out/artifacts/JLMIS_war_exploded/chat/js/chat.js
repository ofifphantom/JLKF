// $(".tabs-selected").onclick(function () {
//     changeColors(titleInfos())
// })

function addTab(title, id, parentid) {

    console.log(parentid);
    console.log(id);
    /*设定button 的是否禁用*/
    if (parentid == 1||parentid == 3) {
        setDisable();
        $('#commonLanguageSettingTree').tree({
            onClick: function(node){
                layfail("不能发送常用语。");  // alert node text property when clicked
            }
        });
    } else if (parentid == 2) {
        removeDisable();
        //toWangEdit(title);
        //toClock(title);
        // Reset(title);
        // start(title);
        $('#commonLanguageSettingTree').tree({
            onClick: function(node){
                var urlobj = node.children;
                if (urlobj == undefined) {
                    let str = "<div class=\'row clearfix\'><ul class=\'text-right messageul-right\'></li><li class=\'message-img\'>" + node.text + "</li></ul></div>";
                    $("#" + thisTas() + "").append(str);
                    var text = "<li class=\'message-text\'>" + node.text + "</li>";
                    rollingbottomn("" + thisTas() + "");
                    if (titleInfos() != "About") {
                        if (websocket.readyState == 1) {  //0-CONNECTING;1-OPEN;2-CLOSING;3-CLOSED
                            websocket.send("{\"identifying\": 1,\"loginName\": \"" + titleInfos() + "\", \"textMessage\":\"" + text + "\"}");
                        } else {
                            layfail("聊天关闭,请刷新页面");
                        }
                    }
                }
            }
        });
    }

    if ($('#chatTabs').tabs('exists', title)) {
        $('#chatTabs').tabs('select', title);
        $('#chatTabs').tabs('close', $('#chatTabs').tabs('getTabIndex', $('#chatTabs').tabs('getSelected')));
        var content;
        if(parentid == 2){
            content = getChatTabs(title);
            toWangEdit(title);
            toClock(title);
            Reset(title);
            start(title);
        }else if(parentid == 3){
            content = getChatTabs1(title);
        }
        $('#chatTabs').tabs('add', {
            title: title,
            content: content,
            closable: false,
        });

    } else {
        var content;
        if (parentid == 1) {

        } else if (parentid == 2) {
            content = getChatTabs(title);
            $('#chatTabs').tabs('add', {
                title: title,
                content: content,
                closable: false,
            });
            toWangEdit(title);
            toClock(title);
            Reset(title);
            start(title);
        } else if (parentid == 3) {
            content = getChatTabs1(title);
            $('#chatTabs').tabs('add', {
                title: title,
                content: content,
                closable: false,
            });
            /*toClock(title);
            Reset(title);
            start(title);*/
        }
    }
    rollingbottomn("" + thisTas() + "");
}

function  setDisable(){
    $(".sendCommodity").removeAttr("onclick","sendGoods(this)");
    $(".sendCommodity").attr("disabled","disabled");
}
function removeDisable(){
    $(".sendCommodity").attr("onclick","sendGoods(this)");
    $(".sendCommodity").attr("disabled",false);
}


function toWangEdit(id) {
    var E = window.wangEditor;
    window["edit" + id] = new E('#WangEdit_toolbar' + id, '#WangEdit_text' + id); // 两个参数也可以传入 elem 对象，class 选择器
    var edit = window["edit" + id];
    edit.create();
}

function toClock(id) {
    window["hour" + id] = 0;
    window["minute" + id] = 0;
    window["second" + id] = 0;
    window["millisecond" + id] = 0;
    window["interval" + id];

    /*var hour, minute, second; //时 分 秒
    hour = minute = second = 0; //初始化
    var millisecond = 0; //毫秒
    var interval;*/
}

function Reset(id) //重置
{
    window.clearInterval(window["interval" + id]);
    window["millisecond" + id] = window["hour" + id] = window["minute" + id] = window["second" + id] = 0;
    document.getElementById('time' + id).innerHTML = '00 : 00 : 00';
}

function start(id) //开始
{

    window["interval" + id] = setInterval(function () {
        timer(id);
    }, 50);
}

function timer(id) //计时
{
    window["millisecond" + id] = window["millisecond" + id] + 50;
    millisecond = window["millisecond" + id];

    if (window["millisecond" + id] >= 1000) {
        window["millisecond" + id] = 0;
        window["second" + id] = window["second" + id] + 1;
    }
    if (window["second" + id] >= 60) {
        window["second" + id] = 0;
        window["minute" + id] = window["minute" + id] + 1;
    }

    if (window["minute" + id] >= 60) {
        window["minute" + id] = 0;
        window["hour" + id] = window["hour" + id] + 1;
    }

    document.getElementById('time' + id).innerHTML = checkZero(window["hour" + id]) + ' : ' + checkZero(window["minute" + id]) + ' : ' + checkZero(window["second" + id]);

}

function stop(id) //暂停
{
    window.clearInterval(window["interval" + id]);
}

function checkZero(count) {
    return count >= 10 ? count : "0" + count;
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

function thisTimes(str) {
    var oDate = new Date(str),
        oHour = oDate.getHours(),
        oMin = oDate.getMinutes(),
        oSen = oDate.getSeconds(),
        oTime =  getzf(oHour) + ':' +
            getzf(oMin) + ':' + getzf(oSen);//最后拼接时间
    return oTime;
};
//补0操作
function getzf(num) {
    if (parseInt(num) < 10) {
        num = '0' + num;
    }
    return num;
}


