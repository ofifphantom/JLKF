<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/6/20
  Time: 7:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>君霖食品电话客服</title>
</head>
<body>
<!--sdk样式文件-->
<link rel="stylesheet" type="text/css" href="https://g.alicdn.com/acca/workbench-sdk/0.2.6/main.min.css">
<!--sdk js文件-->
<script type="text/javascript" src="//g.alicdn.com/crm/acc-phone/1.1.0/SIPml-api.js"></script>
<script type="text/javascript" src="//g.alicdn.com/crm/acc-phone/1.1.0/index.js"></script>
<script type="text/javascript" src="https://g.alicdn.com/acca/workbench-sdk/0.2.6/workbenchSdk.min.js"></script>
<!--开发模式时workbench-sdk推荐引入workbenchSdk.js,会有一些打印提示.-->
<script>
    var config;
    // config.dom=1
    // config.instanceId=12314
    window.workbench = new WorkbenchSdk(config)

</script>
</body>
</html>
