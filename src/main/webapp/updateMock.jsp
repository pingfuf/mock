<%--
  Created by IntelliJ IDEA.
  User: pingfu
  Date: 2018/6/29
  Time: 下午4:52
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
    <title>更新mock</title>
    <script src="./static/js/jquery.min.js"></script>
    <style type="text/css">
        #container {
            width: 1000px;
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
            width: 160px;
        }
        #mockContent {
            height:800px;
            width:1000px;
            border:1px solid;
            font-size: 15px;
        }
    </style>
</head>

<body>
    <script language="JavaScript">
        function repeat(s, count) {
            return new Array(count + 1).join(s);
        }

        $(document).ready(function () {
            var json = $("#mockContent").val();
            if ( json !== null && json.length > 0) {
                var i           = 0;
                var len          = 0;
                var tab         = "    ";
                var targetJson     = "";
                var indentLevel = 0;
                var inString    = false;
                var currentChar = null;
                for (i = 0, len = json.length; i < len; i += 1) {
                    currentChar = json.charAt(i);

                    switch (currentChar) {
                        case '{':
                        case '[':
                            if (!inString) {
                                targetJson += currentChar + "\n" + repeat(tab, indentLevel + 1);
                                indentLevel += 1;
                            } else {
                                targetJson += currentChar;
                            }
                            break;
                        case '}':
                        case ']':
                            if (!inString) {
                                indentLevel -= 1;
                                targetJson += "\n" + repeat(tab, indentLevel) + currentChar;
                            } else {
                                targetJson += currentChar;
                            }
                            break;
                        case ',':
                            if (!inString) {
                                targetJson += ",\n" + repeat(tab, indentLevel);
                            } else {
                                targetJson += currentChar;
                            }
                            break;
                        case ':':
                            if (!inString) {
                                targetJson += ": ";
                            } else {
                                targetJson += currentChar;
                            }
                            break;
                        case ' ':
                        case "\n":
                        case "\t":
                            if (inString) {
                                targetJson += currentChar;
                            }
                            break;
                        case '"':
                            if (i > 0 && json.charAt(i - 1) !== '\\') {
                                inString = !inString;
                            }
                            targetJson += currentChar;
                            break;
                        default:
                            targetJson += currentChar;
                            break;
                    }
                }

                $("#mockContent").val(targetJson);
                $("#add").val("更新");
            }

            $("#add").click(function () {
                var url = $("#mockName").val();
                var content = $("#mockContent").val();
                if (url === null || url.length === 0 || content === null || content.length === 0) {
                    alert("请输入正确的mock数据");
                    return;
                }

                if (url.startsWith("/", 0)) {
                    url = url.substring(1, content.length);
                }

                $.post("./mock/doUpdate",{
                    url: url,
                    content: content
                }, function (e) {
                    if (e !== null && e.code === 0) {
                        alert("保存成功");
                    } else {
                        alert("保存失败");
                    }
                });
            });
        });
    </script>
    <div id="container">
        <div id="top">
            <label>接口URI:</label>
            <input id="mockName" name="url" class="mockName" value="${mock.url}" type="text" />
            <input id="add" class="button" type="button" value="添加" />
            <input id="format" class="button" type="button" value="格式化DocLevel数据">
        </div>

        <div>
            <label>用户名：</label>
        </div>
        <textarea id="mockContent" name="content" class="iframe">${mock.content}</textarea>
    </div>
</body>
</html>
