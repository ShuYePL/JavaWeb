<%@ page import="bean.Student" %><%--
  Created by IntelliJ IDEA.
  User: jh
  Date: 2022/7/4
  Time: 22:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        * {
            margin: 0;
            padding: 0;
        }

        table {
            margin: 0 auto;
            border-style: double;
            border-color: #099;
            border-width: 2px;
            width: 200px;
        }

        td {
            padding: 6px;
            border-style: solid;
            border-color: #349d6e;
        }
    </style>
    <title>预览页面</title>
</head>
<body>
<h1 align="center">详情页面</h1>
<hr>
<table>
    <%
        Student student = (Student)request.getAttribute("student");
    %>
    <tr>
        <td>学号</td>
        <td><%=student.getSno()%></td>
    </tr>
    <tr>
        <td>姓名</td>
        <td><%=student.getName()%></td>
    </tr>
    <tr>
        <td>性别</td>
        <td><%=student.getSex()%></td>
    </tr>
    <tr>
        <td>年龄</td>
        <td><%=student.getAge()%></td>
    </tr>
    <tr>
        <td>家庭住址</td>
        <td><%=student.getAddr()%></td>
    </tr>
</table>
<hr>
<form action="<%=request.getContextPath()%>/t_student/list">
    <input type="submit" value="返回">
</form>
</body>
</html>
