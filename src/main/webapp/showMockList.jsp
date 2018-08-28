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
            margin-left: 10px;
            width: 960px;
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
    </style>
</head>
<body>
    <script language="JavaScript">
        $(document).ready(function () {
            $("#search").click(function(){
                var url = "./mock/showMockListInfo";
                var mockName = $("#mockName").val();
                if (mockName != null && mockName.length > 0) {
                    url = url + "&name=" + mockName;
                }
                $("#iframe").src = url;
            });
            
            $("#add").click(function () {
                window.open("./mock/update");
            })
        });
    </script>

    <div id="container">
        <div id="top">
            <label>mock名称:</label>
            <input id="mockName" type="text"  class="mockName">
            <input id="search" class="button" type="button" value="搜索">
            <input id="add" class="button" type="button" value="添加">
        </div>
        <iframe id="iframe" class="iframe" src="./mock/showMockListInfo"></iframe>
    </div>
</body>
</html>