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
    <style type="text/css">
        #container {
            width: 1200px;
            margin-left: auto;
            margin-right: auto;
        }

        .mockItem {
            width: 1200px;
            height: 38px;
        }

        .mockName {
            height: 38px;
            width: 400px;
        }

        .mockUser {
            height: 38px;
            width: 120px;
        }

        .mockUrl {
            height: 38px;
            width: 480px;
        }

        .change{
            height: 38px;
            width: 200px;
        }

        #bottom {
            text-align: center;
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <div id="container">
        <table>
            <tr class="mockItem">
                <td class="mockName">接口名称</td>
                <td class="mockUser">所属用户</td>
                <td class="mockUrl">接口路径</td>
                <td class="change">操作</td>
            </tr>
            <c:forEach items="${mockList}" var="mock">
                <tr class="mockItem">
                    <td class="mockName">${mock.url}</td>
                    <td class="mockUser">${mock.username}</td>
                    <td class="mockUrl">${mock.path}</td>
                    <td class="change">
                        <a target="_blank" href="./mock/update?id=${mock.id}">编辑</a>
                        <a target="_blank" href="###" onclick="deleteMock(${mock.id})">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <div id="bottom">
            <label>共<label id="totalPage">${pageSize}</label>页</label>
            <button id="prePage" style="margin-left: 10px">上一页</button>
            <label style="margin-left: 10px; margin-right: 10px">第<label id="currentPage">${currentPage}</label>页</label>
            <button id="nextPage">下一页</button>
        </div>
    </div>
    <script language="JavaScript">
        $(document).ready(function () {
            $(".mockItem:odd").css("background", "#cbcbcb");

            var totalPage = parseInt($("#totalPage").text());
            var currentPage = 1;
            var pageSize = 14;
            var $mockItems = $(".mockItem");
            var size = $mockItems.size();
            for (var i = 1; i < size; i ++) {
                if (i > (currentPage - 1) * pageSize && i <= currentPage * pageSize ) {
                    $mockItems.eq(i).show();
                } else {
                    $mockItems.eq(i).hide();
                }
            }

            $("#prePage").click(function(){
               if (currentPage > 1) {
                   currentPage --;
                   for (var i = 1; i < size; i ++) {
                       if (i > (currentPage - 1) * pageSize && i <= currentPage * pageSize) {
                           $mockItems.eq(i).show();
                       } else {
                           $mockItems.eq(i).hide();
                       }
                   }
                   $("#currentPage").text(currentPage);
               }
            });

            $("#nextPage").click(function() {
                if (currentPage < totalPage) {
                    currentPage ++;
                    for (var i = 1; i < size; i ++) {
                        if (i > (currentPage - 1) * pageSize && i <= currentPage * pageSize) {
                            $mockItems.eq(i).show();
                        } else {
                            $mockItems.eq(i).hide();
                        }
                    }
                    $("#currentPage").text(currentPage);
                }
            });
        });

        function deleteMock(id) {
            var url = "./mock/deleteMock?id=" + id;
            $.get(url, function (e) {
                if (e !== null && e.code === 0) {
                    alert("删除成功");
                }
            });
        }
    </script>
</body>
</html>