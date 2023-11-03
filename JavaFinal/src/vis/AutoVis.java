package vis;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vis.SingleValueGenerator;
import vis.MultiValueGenerator;



/**
 * Servlet implementation class TestYearIndexBar
 */
@WebServlet("/AutoVis")
public class AutoVis extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AutoVis() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	static class visfunc {
		static String[] countryList = {"CN", "US", "GB", "FR", "RU"};
		static String[] yearList = {"2019","2020","2021"};
		static ArrayList<String> nameList;
		static int countName;
		public static void doSingleBar(HttpServletRequest request, HttpServletResponse response) throws IOException {
			SingleValueGenerator svg = new SingleValueGenerator(5, 0);
			
			String valueName = request.getParameter("name");
			String valueYear = request.getParameter("year");
			
			PrintWriter errWriter = response.getWriter();
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
				String sqlJudge12="SELECT * FROM secondValues WHERE secondTarget = '" + valueName + "'";
				ResultSet judge12= st.executeQuery(sqlJudge12);
				if(judge12.next()==true) {
					for(String country:countryList) {
						String sqlGetValue="SELECT value FROM secondValues WHERE secondTarget = '" + valueName + "' and "
								+ "year = '"+valueYear+  "' and " + "country = '"+country+ "' ";
						ResultSet getValue= st.executeQuery(sqlGetValue);
						getValue.next();
						svg.appendValue(Integer.parseInt(getValue.getString(1)));
					}
				}else {
					for(String country:countryList) {
						String sqlGetValue="SELECT value FROM firstValues WHERE firstTarget = '" + valueName + "' and "
								+ "year = '"+valueYear+  "' and " + "country = '"+country+ "' ";
						ResultSet getValue= st.executeQuery(sqlGetValue);
						getValue.next();
						svg.appendValue(Integer.parseInt(getValue.getString(1)));
					}
				}
			} catch(SQLException e) { errWriter.println("SQL error"); return; }
			String ret = svg.makejson();
			response.getWriter().println(ret);
		}
		public static void doSingleLine(HttpServletRequest request, HttpServletResponse response) throws IOException {
			SingleValueGenerator svg = new SingleValueGenerator(3, 0);
			String valueName = request.getParameter("name");
			String valueCountry = request.getParameter("country");
			
			PrintWriter errWriter = response.getWriter();
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
				String sqlJudge12="SELECT * FROM secondValues WHERE secondTarget = '" + valueName + "'";
				ResultSet judge12= st.executeQuery(sqlJudge12);
				if(judge12.next()==true) {
					for(String year:yearList) {
						String sqlGetValue="SELECT value FROM secondValues WHERE secondTarget = '" + valueName + "' and "
								+ "year = '"+ year+  "' and " + "country = '"+ valueCountry+ "' ";
						ResultSet getValue= st.executeQuery(sqlGetValue);
						getValue.next();
						svg.appendValue(Integer.parseInt(getValue.getString(1)));
					}
				}else {
					for(String year:yearList) {
						String sqlGetValue="SELECT value FROM firstValues WHERE firstTarget = '" + valueName + "' and "
								+ "year = '"+ year+  "' and " + "country = '"+valueCountry+ "' ";
						ResultSet getValue= st.executeQuery(sqlGetValue);
						getValue.next();
						svg.appendValue(Integer.parseInt(getValue.getString(1)));
					}
				}
			} catch(SQLException e) { errWriter.println("SQL error"); return; }
			String ret = svg.makejson();
			response.getWriter().println(ret);
		}
		public static void doMultBar(HttpServletRequest request, HttpServletResponse response) throws IOException {
			MultiValueGenerator mvg = new MultiValueGenerator(3, 5, 0);
			String valueName = request.getParameter("name");
			
			PrintWriter errWriter = response.getWriter();
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
				String sqlJudge12="SELECT * FROM secondValues WHERE secondTarget = '" + valueName + "'";
				ResultSet judge12= st.executeQuery(sqlJudge12);
				if(judge12.next()==true) {
					for(String year:yearList)for(String country:countryList)  {
						String sqlGetValue="SELECT value FROM secondValues WHERE secondTarget = '" + valueName + "' and "
								+ "year = '"+ year+  "' and " + "country = '"+ country+ "' ";
						ResultSet getValue= st.executeQuery(sqlGetValue);
						getValue.next();
						mvg.appendValue(Integer.parseInt(getValue.getString(1)));
					}
				}else {
					 for(String year:yearList)for(String country:countryList) {
						String sqlGetValue="SELECT value FROM firstValues WHERE firstTarget = '" + valueName + "' and "
								+ "year = '"+ year+  "' and " + "country = '"+country+ "' ";
						ResultSet getValue= st.executeQuery(sqlGetValue);
						getValue.next();
						mvg.appendValue(Integer.parseInt(getValue.getString(1)));
					}
				}
			} catch(SQLException e) { errWriter.println("SQL error"); return; }
			String ret = mvg.makejson();
			response.getWriter().println(ret);
		}
		public static void doMultLine(HttpServletRequest request, HttpServletResponse response) throws IOException {
			MultiValueGenerator mvg = new MultiValueGenerator(5, 3, 0);
			String valueName = request.getParameter("name");
			
			PrintWriter errWriter = response.getWriter();
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
				String sqlJudge12="SELECT * FROM secondValues WHERE secondTarget = '" + valueName + "'";
				ResultSet judge12= st.executeQuery(sqlJudge12);
				if(judge12.next()==true) {
					for(String country:countryList)for(String year:yearList) {
						String sqlGetValue="SELECT value FROM secondValues WHERE secondTarget = '" + valueName + "' and "
								+ "year = '"+ year+  "' and " + "country = '"+ country+ "' ";
						ResultSet getValue= st.executeQuery(sqlGetValue);
						getValue.next();
						mvg.appendValue(Integer.parseInt(getValue.getString(1)));
					}
				}else {
					for(String country:countryList)for(String year:yearList) {
						String sqlGetValue="SELECT value FROM firstValues WHERE firstTarget = '" + valueName + "' and "
								+ "year = '"+ year+  "' and " + "country = '"+country+ "' ";
						ResultSet getValue= st.executeQuery(sqlGetValue);
						getValue.next();
						mvg.appendValue(Integer.parseInt(getValue.getString(1)));
					}
				}
			} catch(SQLException e) { errWriter.println("SQL error"); return; }
			String ret = mvg.makejson();
			response.getWriter().println(ret);
		}
		public static void doCountry(HttpServletRequest request, HttpServletResponse response) throws IOException {
			String valueCountry = request.getParameter("country");
			Connection conn;
			PrintWriter errWriter = response.getWriter();
			MultiValueGenerator mvg = new MultiValueGenerator(3, countName, 1);
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
				for(String name: nameList) {
					Statement st = conn.createStatement();
					String sqlJudge12="SELECT * FROM secondValues WHERE secondTarget = '" + name + "'";
					ResultSet judge12= st.executeQuery(sqlJudge12);
					if(judge12.next()==true) {
						for(String year:yearList) {
							String sqlGetValue="SELECT value FROM secondValues WHERE secondTarget = '" + name + "' and "
									+ "year = '"+ year+  "' and " + "country = '"+ valueCountry+ "' ";
							ResultSet getValue= st.executeQuery(sqlGetValue);
							getValue.next();
							mvg.appendValue(Integer.parseInt(getValue.getString(1)), name);
						}
					}else {
						for(String year:yearList) {
							String sqlGetValue="SELECT value FROM firstvalues WHERE firstTarget = '" + name + "' and "
									+ "year = '"+ year+  "' and " + "country = '"+valueCountry+ "' ";
							ResultSet getValue= st.executeQuery(sqlGetValue);
							getValue.next();
							mvg.appendValue(Integer.parseInt(getValue.getString(1)), name);
						}
					}
				}
				
			} catch(SQLException e) { errWriter.println("SQL error"); return; }
			String ret = mvg.makejson();
			response.getWriter().println(ret);
		}
		public static void doYear(HttpServletRequest request, HttpServletResponse response) throws IOException {
			String valueYear = request.getParameter("year");
			Connection conn;
			PrintWriter errWriter = response.getWriter();
			MultiValueGenerator mvg = new MultiValueGenerator(5, countName, 1);
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
				for(String name: nameList) {
					Statement st = conn.createStatement();
					String sqlJudge12="SELECT * FROM secondValues WHERE secondTarget = '" + name + "'";
					ResultSet judge12= st.executeQuery(sqlJudge12);
					if(judge12.next()==true) {
						for(String country:countryList) {
							String sqlGetValue="SELECT value FROM secondValues WHERE secondTarget = '" + name + "' and "
									+ "year = '"+ valueYear+  "' and " + "country = '"+ country+ "' ";
							ResultSet getValue= st.executeQuery(sqlGetValue);
							getValue.next();
							mvg.appendValue(Integer.parseInt(getValue.getString(1)), name);
						}
					}else {
						for(String country:countryList) {
							String sqlGetValue="SELECT value FROM firstvalues WHERE firstTarget = '" + name + "' and "
									+ "year = '"+ valueYear+  "' and " + "country = '"+country+ "' ";
							ResultSet getValue= st.executeQuery(sqlGetValue);
							getValue.next();
							mvg.appendValue(Integer.parseInt(getValue.getString(1)), name);
						}
					}
				}
				
			} catch(SQLException e) { errWriter.println("SQL error"); return; }
			String ret = mvg.makejson();
			response.getWriter().println(ret);
		}
		public static void doAllName(HttpServletRequest request, HttpServletResponse response) throws IOException {
			String valueYear = request.getParameter("year");
			String valueCountry = request.getParameter("country");
			Connection conn;
			PrintWriter errWriter = response.getWriter();
			SingleValueGenerator svg = new SingleValueGenerator(countName, 1);
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
				for(String name: nameList) {
					Statement st = conn.createStatement();
					String sqlJudge12="SELECT * FROM secondValues WHERE secondTarget = '" + name + "'";
					ResultSet judge12= st.executeQuery(sqlJudge12);
					if(judge12.next()==true) {
							String sqlGetValue="SELECT value FROM secondValues WHERE secondTarget = '" + name + "' and "
									+ "year = '"+ valueYear+  "' and " + "country = '"+ valueCountry+ "' ";
							ResultSet getValue= st.executeQuery(sqlGetValue);
							getValue.next();
							svg.appendValue(Integer.parseInt(getValue.getString(1)), name);
					}else {
							String sqlGetValue="SELECT value FROM firstvalues WHERE firstTarget = '" + name + "' and "
									+ "year = '"+ valueYear+  "' and " + "country = '"+valueCountry+ "' ";
							ResultSet getValue= st.executeQuery(sqlGetValue);
							getValue.next();
							svg.appendValue(Integer.parseInt(getValue.getString(1)), name);
					}
				}
				
			} catch(SQLException e) { errWriter.println("SQL error"); return; }
			String ret = svg.makejson();
			response.getWriter().println(ret);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");  
		response.setHeader("Cache-Control", "no-store");  
		response.setHeader("Pragma", "no-cache");  
		response.setDateHeader("Expires", 0);
		
		Connection conn;
		visfunc.nameList = new ArrayList<String>();
		PrintWriter errWriter = response.getWriter();
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
			String sqlTargetCount="SELECT count(*) FROM targets";
			ResultSet targetCount= st.executeQuery(sqlTargetCount);
			targetCount.next();
			visfunc.countName=targetCount.getInt(1);
			String sqlTargets="SELECT target FROM targets";
			ResultSet targets= st.executeQuery(sqlTargets);
			while(targets.next()) {
				visfunc.nameList.add(targets.getString(1));
;			}
			conn.close();
		} catch(SQLException e) { errWriter.println("SQL error"); return; }
		String typestr = request.getParameter("type");
		int type = Integer.parseInt(typestr);
		if(type == 1) visfunc.doSingleBar(request, response);
		else if(type == 2) visfunc.doSingleLine(request, response);
		else if(type == 3) visfunc.doMultBar(request, response);
		else if(type == 4) visfunc.doMultLine(request, response);
		else if(type == 5) visfunc.doCountry(request, response);
		else if(type == 6) visfunc.doYear(request, response);
		else if(type == 7) visfunc.doAllName(request, response);
		else response.getWriter().printf("error!");
	}
}
