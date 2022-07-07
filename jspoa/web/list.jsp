<%@ page import="bean.Student" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %><%--
  Created by IntelliJ IDEA.
  User: jh
  Date: 2022/7/4
  Time: 21:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<style>
    * {
        margin: 0;
        padding: 0;
    }

    table {
        margin: 10px auto;
        border-style: dotted;
        border-color: #867979
    }

    td {
        border-style: dashed;
        border-width: 2px;
        border-color: #342e2e;
        padding: 6px;
    }
</style>
<head>
    <title>预览页面</title>
</head>
<body>
<h1 align="center">数据预览</h1>
<hr>
<table>
    <tr>
        <td>学号</td>
        <td>姓名</td>
        <td>性别</td>
        <td>年龄</td>
        <td>家庭住址</td>
        <td>操作</td>
    </tr>

    <%
        List<Student> students = (List<Student>)request.getAttribute("students");
        Iterator<Student> iterator = students.iterator();
        while(iterator.hasNext()){
            Student student = iterator.next();
    %>
            <tr>
                <td><%=student.getSno()%></td>
                <td><%=student.getName()%></td>
                <td><%=student.getSex()%></td>
                <td><%=student.getAge()%></td>
                <td><%=student.getAddr()%></td>
                <td>
                    <a href="<%=request.getContextPath()%>/t_student/delete?sno=<%=student.getSno()%>">删除</a>
                    <a href="<%=request.getContextPath()%>/t_student/find?function=modify&sno=<%=student.getSno()%>">修改</a>
                    <a href="<%=request.getContextPath()%>/t_student/find?function=detail&sno=<%=student.getSno()%>">详情</a>
                </td>
            </tr>
    <%
        }
    %>
</table>
<hr>
<a href='<%=request.getContextPath()%>/add.jsp'>增加学生信息</a>
</body>
</html>
