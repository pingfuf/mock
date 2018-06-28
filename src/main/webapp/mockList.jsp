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
    <script src="static/js/jquery.min.js"></script>
    <script language="JavaScript">
        $(document).ready(function () {
            alert("this is temp")
        });
    </script>
    <style type="text/css">
        #container {
            width: 960px;
            margin-left: auto;
            margin-right: auto;
        }
        #top {
            margin-top: 40px;
            margin-bottom: 30px;
            width: 960px;
        }
        .mockName {
            height: 40px;
            width: 220px;
        }
        .mockUser {
            height: 40px;
            width: 120px;
        }
        .mockUrl {
            height: 40px;
            width: 420px;
        }
        .change{
            height: 40px;
            width: 200px;
        }
    </style>
</head>
<body>
    <div id="container">
        <table>
            <tr>
                <td class="mockName">接口名称</td>
                <td class="mockName">所属用户</td>
                <td class="mockUrl">接口路径</td>
                <td class="change">操作</td>
            </tr>
            <c:forEach items="${mockList}" var="mock">
                <tr>
                    <td class="mockName">${mock.path}</td>
                    <td class="mockUser">${mock.username}</td>
                    <td class="mockUrl">${mock.url}</td>
                    <td class="change">
                        <a target="_blank" href="">编辑</a>
                        <a target="_blank" href="">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>