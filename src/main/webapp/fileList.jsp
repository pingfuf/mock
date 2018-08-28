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
    <title>文件列表</title>
    <script src="static/js/jquery.min.js"></script>
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
    <table>
        <tr class="mockItem">
            <td class="mockName">文件类型</td>
            <td class="mockUser">文件名</td>
            <td class="mockUrl">创建时间</td>
            <td class="change">操作</td>
        </tr>
        <c:forEach items="${files}" var="res">
            <tr class="mockItem">
                <td class="mockName">${res.typeName}</td>
                <td class="mockUser">${res.name}</td>
                <td class="mockUrl">${res.date}</td>
                <td class="change">
                    <a target="_blank" href="./file/downloadInfo?id=${res.id}">下载</a>
                    <a target="_blank" href="###" onclick="deleteMock(${res.id})">删除</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<script language="JavaScript">
    function deleteMock(id) {
        var url = "./file/deleteFile?id=" + id;
        $.get(url, function (e) {
            if (e !== null && e.code == 0) {
                alert("删除成功");
            }
        });
    }
    $(document).ready(function () {
        $(".mockItem:odd").css("background", "#cbcbcb");
    })
</script>
</body>
</html>