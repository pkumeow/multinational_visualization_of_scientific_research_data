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
import java.io.File;
/**
 * Servlet implementation class TestChangeIndexName
 */
@WebServlet("/TestChangeIndexName")
public class TestChangeIndexName extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestChangeIndexName() {
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
		String valueNewName = request.getParameter("new_name");
		if((valueName == "") || (valueNewName == "")) {
			errWriter.printf("指标名不能为空<br/>");
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
			if(valueNewName==valueName) {
	    		errWriter.println("改前改后指标名相同，未修改!");
	    		return;
	    	}
	    	String sqlOldIndexExist="SELECT * FROM targets WHERE target = '" + valueName + "'";
	    	ResultSet oldIndexExist= st.executeQuery(sqlOldIndexExist);
	    	if(oldIndexExist.next()!=true) {
	    		errWriter.println("要修改的指标" + valueName +"不存在!");
	    		return;
	    	}
	    	String sqlNewIndexExist="SELECT * FROM targets WHERE target = '" + valueNewName + "'";
	    	ResultSet newIndexExist= st.executeQuery(sqlNewIndexExist);
	    	if(newIndexExist.next()==true) {
	    		errWriter.println("想要修改得到的指标" + valueNewName +"已存在!");
	    		return;
	    	}
	    	String sqlChangeIndex=
	    				"UPDATE targets SET target ='" + valueNewName + "' WHERE target ='"+
	    				valueName + "';" +
	    				"UPDATE second2first SET firstTarget ='" + valueNewName + "' WHERE firstTarget ='"+
	    				valueName + "';" + 
	    				"UPDATE second2first SET secondTarget ='" + valueNewName + "' WHERE secondTarget ='"+
	    				valueName + "';" +
	    				"UPDATE secondValues SET secondTarget ='" + valueNewName + "' WHERE secondTarget ='"+
	    				valueName + "';" ;
	    	st.executeUpdate(sqlChangeIndex);
	    	
		} catch(SQLException e) { errWriter.println("SQL error"); return; }
		errWriter.printf("%s %s<br/>", valueName, valueNewName);
		errWriter.printf("成功修改指标名<br/>");
	}

}
