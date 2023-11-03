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
@WebServlet("/TestModifyLevel2Value")
public class TestModifyLevel2Value extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestModifyLevel2Value() {
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
		String strValue = request.getParameter("value");
		if((valueName == "") || (valueYear == "") || (valueCountry == "") || (strValue == "")) {
			errWriter.printf("输入信息不能为空<br/>");
			return;
		}
		double value = Double.parseDouble(strValue);
		
		
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
			if(value<0) {
    			errWriter.println("值非负!");
	    		return;
    		}
	    	String sqlTobeModifiedExist="SELECT * FROM secondValues WHERE secondTarget = '" + valueName + "' and "
	    			+ "year = '"+valueYear+  "' and "
	    	    			+ "country = '"+valueCountry+ "' ";
	    	ResultSet tobeModifiedExist= st.executeQuery(sqlTobeModifiedExist);
	    	if(tobeModifiedExist.next()!=true) {
	    		errWriter.println("要修改的值(" + valueName+","+ valueYear+","+valueCountry+")不存在!");
	    		return;
	    	}
	    	String sqlTobeModifiedChanged="SELECT * FROM secondValues WHERE secondTarget = '" + valueName + "' and "
	    			+ "year = '"+valueYear+  "' and "
	    	    			+ "country = '"+valueCountry+ "' and value <> "+String.valueOf((int) (value*100))+ "";
	    	ResultSet tobeModifiedChanged= st.executeQuery(sqlTobeModifiedChanged);
	    	if(tobeModifiedChanged.next()!=true) {
	    		errWriter.println("改前改后值相同，未修改!");
	    		return;
	    	}
    		String sqlModifyValue=
    				"UPDATE secondValues SET value =" + String.valueOf((int) (value*100)) + " WHERE secondTarget = '" + valueName + "' and "
    						+ "year = '"+valueYear+  "' and "
    						+ "country = '"+valueCountry+ "' ";
    		st.executeUpdate(sqlModifyValue); 
	    	
		} catch(SQLException e) { errWriter.println("SQL error"); return; }
		errWriter.printf("成功修改二级指标值<br/>");
	}

}
