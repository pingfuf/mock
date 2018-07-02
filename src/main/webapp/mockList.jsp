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
        #top {
            margin-top: 40px;
            margin-bottom: 30px;
            width: 960px;
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
                        <a target="_blank" href="./mock/update?url=${mock.url}">编辑</a>
                        <a target="_blank" href="">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <div>
            <label id="totalPage"></label>
            <button>上一页</button>
            <label>第i页</label>
            <button>下一页</button>
        </div>
    </div>
    <script language="JavaScript">
        function deleteMock(id) {
            alert(id);
            var url = "./mock/deleteMock?url=" + id;
            $.get(url, function () {

            });
        }
        $(document).ready(function () {
            $(".mockItem:odd").css("background", "#cbcbcb")
            var size = $(".mockItem").size();
            var currentPage = 0;
            for (var i = 10; i < size; i ++) {
                $(".mockItem").get(i).css("display", "none");
            }
            var pageSize = 0;
            if (size % 10 === 0) {
                pageSize = size / 10;
            } else {
                pageSize = size / 10 + 1;
            }
            
            $("#totalPage").html('<lable>共'+ pageSize + '页</lable>');
        })
    </script>
</body>
</html>