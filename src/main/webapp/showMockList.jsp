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
    <title>mock列表</title>
    <script src="./static/js/jquery.min.js"></script>
    <style type="text/css">
        #container {
            width: 1200px;
            margin-left: auto;
            margin-right: auto;
        }
        #top {
            margin-top: 40px;
            margin-bottom: 30px;
            width: 1200px;
            text-align: center;
        }
        .mockName {
            height: 30px;
            width: 320px;
        }
        .button {
            height: 30px;
            width: 80px;
        }
        .iframe {
            height:1600px;
            width:1200px;
            border-style: none;
        }
        #bottom {
            text-align: center;
            height: 40px;
        }
    </style>
</head>
<body>
    <script language="JavaScript">
        $(document).ready(function () {
            var url;
            var serverUrl = "./mock/showMockList";
            var currentPage = $("#currentPage").val();

            $("#search").click(function(){
                url = $("#mockName").val();
                if (url !== null && url.length > 0) {
                    serverUrl = serverUrl + "&url=" + url;
                }
                currentPage = 1;
                $("#currentPage").text(currentPage);
                $("#iframe").src = serverUrl;
            });

        });
    </script>

    <div id="container">
        <div id="top">
            <label>mock的Url:</label>
            <input id="mockName" type="text"  class="mockName">
            <input id="search" class="button" type="button" value="搜索">
            <input id="add" class="button" type="button" value="添加">
        </div>
        <iframe id="iframe" class="iframe" src="./mock/showMockList?page=0"></iframe>
    </div>
</body>
</html>