package JavaFinal;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestAddlevel1
 */
@WebServlet("/TestAddlevel1Index")
public class TestAddlevel1Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestAddlevel1Index() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");  
		response.setHeader("Cache-Control", "no-store");  
		response.setHeader("Pragma", "no-cache");  
		response.setDateHeader("Expires", 0);
		PrintWriter errWriter = response.getWriter();
		String valueName = request.getParameter("name");
		if((valueName == "")) {
			errWriter.printf("添加的一级指标名不能为空<br/>");
			return;
		}
		
		Connection conn;
		try {
    		Class.forName("com.mysql.jdbc.Driver"); 
		}
        catch (ClassNotFoundException ex) {//找不到数据库驱动程序
        	errWriter.println("找不到数据库驱动程序");    
        	return;
        }
		try {
			conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:8889/JavaFinal?"
				+ "useUnicode=true&characterEncoding=utf-8&useSSL="
				+ "true&serverTimezone=Asia/Shanghai","root","root");		
			Statement st = conn.createStatement();
	    	String sqlExist="SELECT * FROM targets WHERE target = '" + valueName + "'";
	    	ResultSet indexExist= st.executeQuery(sqlExist);
	    	if(indexExist.next()==true) {
	    		errWriter.println("指标" + valueName +"已存在!");
	    		return;
	    	} else {
	    		String sqlAppendIndex="INSERT INTO targets VALUES ('" + valueName + "')";
	    		st.executeUpdate(sqlAppendIndex);
	    	}
		} catch(SQLException e) { errWriter.println("SQL error"); return; }
		errWriter.printf("%s<br/>", valueName);
		errWriter.printf("成功添加一级指标！<br/>");
	}

}
