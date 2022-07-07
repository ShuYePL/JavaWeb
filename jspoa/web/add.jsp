<%--
  Created by IntelliJ IDEA.
  User: jh
  Date: 2022/7/4
  Time: 22:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>新增页面</title>
</head>
<body>
    <h1 align="center">添加新学生信息</h1>
    <hr>
    <form method="post" action="<%=request.getContextPath()%>/t_student/add">
        学号：<input type="text" name="sno"><br>
        姓名：<input type="text" name="name"><br>
        性别：<input type="text" name="sex"><br>
        年龄：<input type="text" name="age"><br>
        家庭住址：<input type="text" name="addr"><br>
        <input type="submit" value="添加学生信息">
    </form>
</body>
</html>
