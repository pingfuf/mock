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
    <script src="./static/js/jquery.form.js" ></script>
    <title>上传文件</title>

    <style type="text/css">
        #container {
            width: 1200px;
            margin-left: auto;
            margin-right: auto;
        }

        #type {
            margin-top: 30px;
            width: 160px;
            height:30px;
        }

        #file {
            width: 260px;
            height:25px;
            margin-left: 10px;
        }
    </style>

    <script language="JavaScript">
        $(document).ready(function() {
            $("#submit").click(function () {
                var type = $("#type").val();
                var file = $("#file").val();
                alert("type = " + type + ", file=" + file.getName());
                $("#form").ajaxForm(function(){
                    alert("lalal");
                });
            })
        });

        function showRequest(formData, jqForm, options) {
            alert(formData);
            return true;
        }

        function showResponse(responseText,statusText) {
            alert(responseText)
        }
    </script>

</head>
<body>
<div id="container">
    <H1 style="margin-top: 30px; text-align: center">上传File</H1>
    <form id="form" enctype="multipart/form-data" action="./file/upload" method="post" target="_self">
        <label>文件类型:</label>
        <select id="type" name="type">
            <option value ="1" selected>android安装包</option>
            <option value ="2">IOS安装包</option>
            <option value="3">学生端h5资源包</option>
            <option value="4">教师端h5资源包</option>
        </select>
        <br>
        <br>
        <label>选择文件:</label><input id="file" type="file" name="file" value="上传文件">
        <br>
        <input type="submit" style="height: 25px; width: 100px" value="提交">
    </form>
    <input id="submit" type="button" style="height: 25px; width: 100px; display: none" value="上传">
</div>
</body>
</html>