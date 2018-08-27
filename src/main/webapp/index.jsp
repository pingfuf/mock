<%--
  Created by IntelliJ IDEA.
  User: fupingfu
  Date: 2017/11/15
  Time: 18:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <base href="<%=basePath %>" />
    <link rel="stylesheet" type="text/css" href="./static/css/style.css"/>
    <script language="JavaScript" src="./static/js/jquery.min.js" />
    <title>首页</title>

    <style type="text/css">
        .menu {

        }
    </style>

    <script language="JavaScript">

    </script>

</head>
<body>
    <div id="container">
        <H1 style="margin-top: 30px; text-align: center">Mock服务</H1>

        <table style="margin-top: 30px; border-style: solid; border-color: beige">
            <tr class="item">
                <td>Mock项目</td>
                <td>
                    <a href="./mock/showMockList" target="_blank">查看mock接口列表</a>
                </td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr class="item">
                <td>文件</td>
                <td></td>
            </tr>
        </table>
    </div>


    <a href=""></a>
</body>
</html>