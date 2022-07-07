<%@ page import="bean.Student" %>
<%--
  Created by IntelliJ IDEA.
  User: jh
  Date: 2022/7/4
  Time: 22:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改页面</title>
</head>
<body>
<h1 align="center">修改页面</h1>
<hr>
<form method="psot" action="<%=request.getContextPath()%>/t_student/modify">
    <%--  获取转发过来的数据（学生对象）  --%>
    <%
        Student student = (Student)request.getAttribute("student");
    %>
    学号：<input type="text" value="<%=student.getSno()%>" name="sno" readonly><br>
    姓名：<input type="text" value="<%=student.getName()%>" name="name"><br>
    性别：<input type="text" value="<%=student.getSex()%>" name="sex"><br>
    年龄：<input type="text" value="<%=student.getAge()%>" name="age"><br>
    家庭住址：<input type="text" value="<%=student.getAddr()%>" name="addr"><br>
    <input type="submit" value="修改学生信息">
</form>
</body>
</html>
