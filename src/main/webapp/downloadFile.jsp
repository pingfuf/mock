<%--
  Created by IntelliJ IDEA.
  User: fupingfu
  Date: 2017/11/15
  Time: 18:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <base href="<%=basePath %>" />
    <title>文件信息</title>
    <script src="./static/js/jquery.min.js"></script>
    <script src="./static/js/qrcode.js"></script>
    <style type="text/css">
        #container {
            width: 1200px;
            margin-left: auto;
            margin-right: auto;
        }

        .mockName {
            height: 40px;
            width: 400px;
        }

        .mockUser {
            height: 40px;
            width: 120px;
        }

        .mockUrl {
            height: 40px;
            width: 480px;
        }
        .change{
            height: 40px;
            width: 200px;
        }
    </style>
</head>
<body>
<div id="container">
    <label>文件名称：</label>
    <label>${file.name}</label>
    <br>
    <label>创建时间：</label>
    <label>${file.date}</label>
    <br>
    <label>下载地址</label>
    <label id="url">${url}</label>
    <br>
    <label>二维码</label><br>
    <div id="qrcode" style="width:200px; height:200px; margin-top:15px;"></div>
</div>

<script language="JavaScript">
   $(document).ready(function () {
       var url = $("#url").text();
       var qr = new QRCode(document.getElementById("qrcode"), {
           width:200,
           height:200
       });
       qr.makeCode(url);
   })
</script>

</body>
</html>