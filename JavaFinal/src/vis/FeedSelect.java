package vis;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Servlet implementation class FeedSelect
 */
@WebServlet("/FeedSelect")
public class FeedSelect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FeedSelect() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* response.getWriter().append("Served at: ").append(request.getContextPath());  */
		response.setContentType("text/html");  
		response.setHeader("Cache-Control", "no-store");  
		response.setHeader("Pragma", "no-cache");  
		response.setDateHeader("Expires", 0);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter errWriter = response.getWriter();
		HashMap<String, String> ma = new HashMap<String, String>();
		ObjectMapper mp = new ObjectMapper();
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
			String sqlExist="SELECT target FROM targets";
	    	ResultSet indexExist= st.executeQuery(sqlExist);
	    	
	    	while(indexExist.next()) {
	    		String res = indexExist.getString(1);
	    		ma.put(res, res);
	    	}
		} catch(SQLException e) { errWriter.println("SQL error"); return; }
		String ret = mp.writeValueAsString(ma);
		errWriter.println(ret);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
