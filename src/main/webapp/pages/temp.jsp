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
    <script src="./static/js/jquery.min.js" ></script>
    <title>首页</title>

    <style type="text/css">
        #container {
            width: 1200px;
            margin-left: auto;
            margin-right: auto;
        }

        #table {
            margin-top: 30px;
            width: 1200px;
        }
        .line {
            width: 1200px;
            background: seashell;
        }
        .menu {
            height: 60px;
            width: 200px;
            background: deepskyblue;
            text-align: center;
        }
        .item {
            height: 60px;
            width: 250px;
        }
    </style>

    <script language="JavaScript">
        $(document).read(function() {

        });
    </script>

</head>
<body>
<div id="container">
    <H1 style="margin-top: 30px; text-align: center">Mock服务</H1>

    <table id="table">
        <tr class="line">
            <td class="menu">Mock项目</td>
            <td class="item">
                <a href="./mock/showMockList" target="_blank">查看mock接口列表</a>
            </td>
            <td class="item">
                <a href="./updateMock.jsp" target="_blank">添加Mock</a>
            </td>
            <td class="item">b</td>
            <td class="item">c</td>
        </tr>
        <tr class="line">
            <td class="menu">资源包</td>
            <td class="item">安装包上传</td>
            <td class="item">安装包下载</td>
            <td class="item">H5包下载</td>
            <td class="item">c</td>
        </tr>
    </table>
</div>
</body>
</html>