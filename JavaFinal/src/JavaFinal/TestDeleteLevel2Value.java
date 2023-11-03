package JavaFinal;

import java.io.*;
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
 * Servlet implementation class TestDel
 */
@WebServlet("/TestDeleteLevel2Value")
public class TestDeleteLevel2Value extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestDeleteLevel2Value() {
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
		String valueYear = request.getParameter("year");
		String valueCountry = request.getParameter("country");
		if((valueName == "") || (valueYear == "") || (valueCountry == "")) {
			errWriter.printf("输入信息不能为空<br/>");
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
				+ "true&serverTimezone=Asia/Shanghai&allowMultiQueries=true","root","root");		
			Statement st = conn.createStatement();
			String sqlOldValueExist="SELECT * FROM secondValues WHERE secondTarget = '" + valueName + "' and "
	    			+ "year = '"+valueYear+  "' and "
	    	    			+ "country = '"+valueCountry+ "' ";
	    	ResultSet oldValueExist= st.executeQuery(sqlOldValueExist);
	    	if(oldValueExist.next()!=true) {
	    		errWriter.println("要删除的值(" + valueName+","+ valueYear+","+valueCountry+")不存在!");
	    		return;
	    	}else {
	    		String sqlDeleteValue=
	    				"DELETE FROM secondValues WHERE secondTarget = '" + valueName + "' and "
	    						+ "year = '"+valueYear+  "' and "
	    				+ "country = '"+valueCountry+ "' ";
	    		st.executeUpdate(sqlDeleteValue);
	    	}
	    	
		} catch(SQLException e) { errWriter.println("SQL error"); return; }
		errWriter.printf("%s %s %s<br/>", valueName, valueYear, valueCountry);
		errWriter.printf("成功删除二级指标值<br/>");
	}

}
