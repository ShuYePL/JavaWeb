package jspoa;

import bean.Student;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// 使用模板类的方法构建一个处理请求的Servlet对象
@WebServlet({"/t_student/list","/t_student/delete","/t_student/find","/t_student/modify","/t_student/add"})
public class T_studentServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 获取访问路径
        String servletPath = request.getServletPath();
        // 根据访问路径的不同，执行不同的程序，这就是一个模板类里面的各种模板了
        if("/t_student/list".equals(servletPath)){
            doList(request, response);
        }else if("/t_student/delete".equals(servletPath)){
            doDelete(request, response);
        }else if("/t_student/find".equals(servletPath)){
            doFind(request, response);
        }else if("/t_student/modify".equals(servletPath)){
            doModify(request, response);
        }else if("/t_student/add".equals(servletPath)){
            doAdd(request, response);
        }
    }

    /**
     * 向数据库中添加数据
     * @param request
     * @param response
     * @throws IOException
     */
    private void doAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Connection conn = null;
        PreparedStatement ps = null;
        // 记录被修改数据的数据数量
        int count;
        try {
            conn = DButil.getConnection();
            conn.setAutoCommit(false);
            String sql = "insert into t_student values(?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, request.getParameter("sno"));
            ps.setString(2, request.getParameter("name"));
            ps.setString(3, request.getParameter("sex"));
            ps.setString(4, request.getParameter("age"));
            ps.setString(5, request.getParameter("addr"));
            count = ps.executeUpdate();
            // 根据修改的数量初步判断操作是否正确
            if(count == 1){
                // 操作正确
                conn.commit();
                response.sendRedirect(request.getContextPath() + "/t_student/list");
            } else {
                // 操作错误
                conn.rollback();
                out.print("出错了(⊙o⊙)…");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DButil.close(conn,ps,null);
        }
    }

    /**
     * 对修改页面提交过来的数据进行处理，修改数据库中对应的数据
     * @param request
     * @param response
     */
    private void doModify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Connection conn = null;
        PreparedStatement ps = null;
        // 记录修改数据库中数据的数量
        int count;
        try {
            conn = DButil.getConnection();
            // 关闭自动提交
            conn.setAutoCommit(false);
            String sql = "update t_student set name = ?, sex = ?, age = ?, addr = ? where sno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, request.getParameter("name"));
            ps.setString(2, request.getParameter("sex"));
            ps.setString(3, request.getParameter("age"));
            ps.setString(4, request.getParameter("addr"));
            ps.setString(5, request.getParameter("sno"));
            count = ps.executeUpdate();
            if(count == 1){
                // 提交并重定向
                conn.commit();
                response.sendRedirect(request.getContextPath() + "/t_student/list");
            } else {
                // 回滚并输出操作错误信息
                conn.rollback();
                out.print("出错了(⊙o⊙)…");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DButil.close(conn,ps,null);
        }
    }

    /**
     * 查找指定学号学生的方法
     * @param request
     * @param response
     */
    private void doFind(HttpServletRequest request, HttpServletResponse response) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 根据传送过来的学号查找数据库中对应的数据
            conn = DButil.getConnection();
            String sql = "select sno,name,sex,age,addr from t_student where sno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, request.getParameter("sno"));
            rs = ps.executeQuery();
            // 封装对象并绑定数据
            while(rs.next()){
                Student student = new Student();
                student.setSno(rs.getString("sno"));
                student.setName(rs.getString("name"));
                student.setSex(rs.getString("sex"));
                student.setAge(rs.getString("age"));
                student.setAddr(rs.getString("addr"));
                request.setAttribute("student", student);
            }
            // 转发，这个要根据是修改操作访问这个方法还是查看详情操作访问这个方法具体选择适合的转发路径
            String function = request.getParameter("function");
            if("modify".equals(function)){
                request.getRequestDispatcher("/edit.jsp").forward(request,response);
            } else if("detail".equals(function)) {
                request.getRequestDispatcher("/detail.jsp").forward(request,response);
            }

        } catch (SQLException | ServletException | IOException e) {
            e.printStackTrace();
        } finally {
            DButil.close(conn,ps,rs);
        }
    }

    /**
     * 删除操作
     * @param request
     * @param response
     * @throws IOException
     */
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Connection conn = null;
        PreparedStatement ps = null;
        // 记录最后改变了多少条数据的结果，如果改变的数据条数过了，就证明可能语句有错，可以回滚
        int count = 0;
        try {
            conn = DButil.getConnection();
            conn.setAutoCommit(false);
            String sql = "delete from t_student where sno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, request.getParameter("sno"));
            count = ps.executeUpdate();
            if(count == 1){
                // 数据库操作结果正确，可以提交，并跳转到预览页面
                conn.commit();
                response.sendRedirect(request.getContextPath() + "/t_student/list");
            } else if(count > 1 || count < 1){
                conn.rollback();
                // 这里显示出错信息，之后有时间可以改成一个特定的出错页面
                out.print("出错了~");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DButil.close(conn,ps,null);
        }
    }

    /**
     * 查找所有的数据库数据，并转发到展示页面的jsp
     * @param request
     * @param response
     */
    private void doList(HttpServletRequest request, HttpServletResponse response){
        // 数据库连接对象
        Connection conn = null;
        // 数据库操作对象
        PreparedStatement ps = null;
        // 数据库操作得到的结果集
        ResultSet rs = null;
        try {
            // 连接数据库并得到结果集
            conn = DButil.getConnection();
            String sql = "select sno, name, sex, age, addr from t_student";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            // 创建一个学生队列，用于将得到的数据进行封装
            List<Student> students = new ArrayList<>();
            // 封装数据
            while(rs.next()){
                Student student = new Student();
                student.setSno(rs.getString("sno"));
                student.setName(rs.getString("name"));
                student.setSex(rs.getString("sex"));
                student.setAge(rs.getString("age"));
                student.setAddr(rs.getString("addr"));
                students.add(student);
            }
            request.setAttribute("students", students);
            request.getRequestDispatcher("/list.jsp").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            DButil.close(conn,ps,rs);
        }
    }
}
