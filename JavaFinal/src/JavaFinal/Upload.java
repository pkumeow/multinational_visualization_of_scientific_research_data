package JavaFinal;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

//使用到的包
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class Upload
 */
@WebServlet("/Upload")
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Upload() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	static class func {
		static class MinMax{
			public static int[][] minMax(int[][] D){
				for(int i=0;i<3;i++) {
					int min=D[i][0];
					int max=D[i][0];
					for(int j=1;j<5;j++) {
						if (D[i][j]<min){
							min=D[i][j];
						}
						if (D[i][j]>max){
							max=D[i][j];
						}
					}
					for(int j=0;j<5;j++) {
						D[i][j]=(D[i][j]-min)*10000/(max-min);
					}
				}
				return D;
			}
		}
		public static void getDataBase(String pathA, String pathB) throws BiffException, IOException  {
			//创建存储指标数据的数组，初始化为0
			int[][] D111=new int[3][5];
			int[][] D112=new int[3][5];
			for (int i=0;i<3;i++) {
				for (int j=0;j<5;j++) {
					D111[i][j]=0;
					D112[i][j]=0;
				}
			}
			//创建国家对应存储序号的字典{USA=1, China=0, England=2, France=3, Russia=4}
			HashMap<String, Integer> nation2id = new HashMap<>();
			nation2id.put("China",0);
			nation2id.put("USA",1);
			nation2id.put("England",2);
			nation2id.put("France",3);
			nation2id.put("Russia",4);
			//依次填写每年的信息
			for(int yearId=0;yearId<3;yearId++) {
				//维护两个字典，分别是学者名与国家的映射，以及学者名出现次数
				HashMap<String, Integer> name2num = new HashMap<>();
				HashMap<String, String> name2nation = new HashMap<>();
				String nationArr[] = {"China","USA","England","France","Russia"};
				String path = pathA;//Excel文件的路径
				Workbook wrb = Workbook.getWorkbook(new File(path));
				//获取每个Sheet表
				int sheets = wrb.getNumberOfSheets();
				for(int sheet=0;sheet<sheets;sheet++) {
					Sheet rs = wrb.getSheet(sheet);
					//得到一共有多少行
					int rows = rs.getRows();
					//按照行依次遍历表格
					for(int row=1;row<rows;row++) {
						//获取论文发表年份
						String pyInfo=rs.getCell(46,row).getContents();
						if(pyInfo.equals("")) continue;
						int publishYear=Integer.parseInt(pyInfo);
						//不属于当前检查年
						if(publishYear!=(yearId+2019))continue;
						//通讯作者信息
						String rpInfo=rs.getCell(24,row).getContents();
						if(rpInfo.equals(""))continue;
						//先提取第一通讯作者姓名
						String firstAuthor;
						String headInfo=rpInfo.substring(0,rpInfo.indexOf(" ("));
						//Xu, HY; Yang, HJ
						//处理开头多作者的情况
						if(headInfo.contains(";")){
							firstAuthor=headInfo.substring(0,rpInfo.indexOf(";"));
						}else {
							firstAuthor=headInfo;
						}
						//如果还没有这个作者的论文数记录，则向name2num添加作者对应论文数为0
						name2num.putIfAbsent(firstAuthor,0);
						//为这个作者的论文数记录+1
						name2num.compute(firstAuthor, (key, value) -> value+1);
						//如果还没有这个作者的国籍记录，则向name2nation添加
						if(name2nation.containsKey(firstAuthor)==false) {
							//再提取第一通讯作者地址
							String firstAuthorAdress;
							String tailInfo=rpInfo.substring(rpInfo.indexOf(")"));
							//处理多个通讯作者的情况
							if(tailInfo.contains(" (")) {
								firstAuthorAdress=tailInfo.substring(0,tailInfo.indexOf(" ("));
								//)，China Acad Chinese Med Sci, Inst Chinese Mat Med, Beijing 100700, Peoples R China.; Xu, HY; Huang, LQ 
							}else {
								firstAuthorAdress=tailInfo;
							}
							//name2nation填入信息
							for(int nationId=0;nationId<5;nationId++) {
								if(firstAuthorAdress.contains(nationArr[nationId])) {
									name2nation.put(firstAuthor,nationArr[nationId]);
									break;
								}
							}
						}
					}
					//遍历学者姓名
					for(String name: name2num.keySet()) {
						//筛选发表至少2篇论文的作者，对应国家的顶级学者数+1
						if(name2nation.containsKey(name)==false) continue;
						if(name2num.get(name)>1) {
							D111[yearId][nation2id.get(name2nation.get(name))]+=1;
						}
						//该学者对应国家的论文数+该学者发表论文数
						D112[yearId][nation2id.get(name2nation.get(name))]+=(name2num.get(name));
					}
				}
			}
			//创建存储指标数据的数组，初始化为0
			int[][] D121=new int[3][5];
			int[][] D122=new int[3][5];
			for (int i=0;i<3;i++) {
				for (int j=0;j<5;j++) {
					D121[i][j]=0;
					D122[i][j]=0;
				}
			}
			String nationArr[] = {"CN","US","GB","FR","RU"};
			String path = pathB;//Excel文件的路径
			Workbook wrb = Workbook.getWorkbook(new File(path));
			//获取每个Sheet表
			int sheets = wrb.getNumberOfSheets();
			for(int sheet=0;sheet<sheets;sheet++) {
				Sheet rs = wrb.getSheet(sheet);
				//得到一共有多少行
				int rows = rs.getRows();
				//按照行依次遍历表格
				for(int row=1;row<rows;row++) {
					//地址信息
					String adressInfo=rs.getCell(4,row).getContents();
					//先提取申请时间信息
					String applicationTime=rs.getCell(2,row).getContents();
					if(applicationTime.equals("-")==false) {
						//通过截取前4位获得年份
						int applicationYear=Integer.parseInt(applicationTime.substring(0,4));
						if (applicationYear>=2019 & applicationYear<=2021){
							for(int nationId=0;nationId<5;nationId++) {
								//依次修改当年对应国家的数据
								if(adressInfo.contains(nationArr[nationId])){
									D121[applicationYear-2019][nationId]+=1;
								}
							}
						}
					}
					//再获取授权时间信息
					String authorisationTime=rs.getCell(3,row).getContents();
					if(authorisationTime.equals("-")==false) {
						int authorisationYear=Integer.parseInt(authorisationTime.substring(0,4));
						if (authorisationYear>=2019 & authorisationYear<=2021){
							for(int nationId=0;nationId<5;nationId++) {
								if(adressInfo.contains(nationArr[nationId])){
									D122[authorisationYear-2019][nationId]+=1;
								}
							}
						}
					}
				}
			}
			D111=MinMax.minMax(D111);
			D112=MinMax.minMax(D112);
			D121=MinMax.minMax(D121);
			D122=MinMax.minMax(D122);
			//创建序号对应国家缩写的字典
			HashMap<Integer,String> id2nation = new HashMap<>();
			id2nation.put(0,"CN");
			id2nation.put(1,"US");
			id2nation.put(2,"GB");
			id2nation.put(3,"FR");
			id2nation.put(4,"RU");
			Connection conn;
			try {
				Class.forName("com.mysql.jdbc.Driver"); 
			}
	        catch (ClassNotFoundException ex) {//找不到数据库驱动程序
	        	return;
	        }
			try {
				conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:8889/JavaFinal?"
						+ "useUnicode=true&characterEncoding=utf-8&useSSL="
						+ "true&serverTimezone=Asia/Shanghai&allowMultiQueries=true","root","root");
				Statement st = conn.createStatement();
		    	String sqlCreateDatabase=
		    				"DROP TABLE IF EXISTS second2first;DROP TABLE IF EXISTS secondValues;" +
		    	"CREATE TABLE second2first (secondTarget varchar(20) NOT NULL,firstTarget varchar(10) NOT NULL,primary key (secondTarget))"
		    	+ " ENGINE=InnoDB DEFAULT CHARSET=utf8;" +
		    	"INSERT INTO second2first (secondTarget, firstTarget) VALUES ('信息化顶级学者人口数量', '研究情况'),"
		    	+ "('信息化高被引文产出量', '研究情况'),('信息化专利申请量', '应用情况'),('信息化专利授权量', '应用情况');" 
		    	+"CREATE TABLE secondValues (secondTarget varchar(20) NOT NULL,year varchar(5) NOT NULL,"
		    	+ "country varchar(5) NOT NULL,value int(5) NOT NULL,primary key (secondTarget,year,country)) ENGINE=InnoDB DEFAULT CHARSET=utf8;"
		    	+ "DROP TABLE IF EXISTS targets;CREATE TABLE targets(target varchar(20) NOT NULL,primary key (target))"
		    	+ "ENGINE=InnoDB DEFAULT CHARSET=utf8;"
		    	+ "INSERT INTO targets (target)VALUES ('信息化专利授权量'),('信息化专利申请量'),('信息化顶级学者人口数量'),"
		    	+ "('信息化高被引文产出量'),('应用情况'),('研究情况');";
		    	st.executeUpdate(sqlCreateDatabase);
		    	for (int i=0;i<3;i++) {
					for (int j=0;j<5;j++) {
						String sqlInsertValues="INSERT INTO secondValues(secondTarget, year, country, value) VALUES"
								+ "('信息化顶级学者人口数量', '"+String.valueOf(2019+i)+"','"+id2nation.get(j)+"',"+String.valueOf(D111[i][j])+"),"
								+ "('信息化高被引文产出量', '"+String.valueOf(2019+i)+"','"+id2nation.get(j)+"',"+String.valueOf(D112[i][j])+"),"
								+ "('信息化专利申请量', '"+String.valueOf(2019+i)+"','"+id2nation.get(j)+"',"+String.valueOf(D121[i][j])+"),"
								+ "('信息化专利授权量', '"+String.valueOf(2019+i)+"','"+id2nation.get(j)+"',"+String.valueOf(D122[i][j])+");";
						st.executeUpdate(sqlInsertValues);
					}
				}
		    	String sqlCreateView="DROP TABLE IF EXISTS firstvalues;DROP VIEW IF EXISTS firstvalues;"+
				"CREATE VIEW firstvalues AS SELECT a.firstTarget,a.year, a.country,if((b.fenmu = 0),0,(a.fenzi DIV b.fenmu)) AS value "
				+ "FROM ((select c.firstTarget,c.year,c.country,sum(c.value) AS fenzi "
				+ "from (select secondvalues.secondTarget,secondvalues.year,secondvalues.country,secondvalues.value,second2first.firstTarget "
				+ "from (secondvalues left join second2first on(secondvalues.secondTarget = second2first.secondTarget)))c group by year,country,firstTarget)a "
				+ "left join (select firstTarget,count(*) AS fenmu from second2first group by firstTarget)b on(a.firstTarget=b.firstTarget));";
		    	st.executeUpdate(sqlCreateView);
			} catch(SQLException e) { return; }
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");  
		response.setHeader("Cache-Control", "no-store");  
		response.setHeader("Pragma", "no-cache");  
		response.setDateHeader("Expires", 0);
		String path = request.getParameter("path");
		Part Afile = request.getPart("Afile");
        String fileName = Afile.getSubmittedFileName();
        Afile.write(path + "/" + fileName);
        String pathA = path + "/" + fileName;
		Part Bfile = request.getPart("Bfile");
        fileName = Bfile.getSubmittedFileName();
        Bfile.write(path + "/" + fileName);
        String pathB = path + "/" + fileName;
        
        
        try {
        	func.getDataBase(pathA, pathB);
        } catch(BiffException e) {
        	return;
        }
	}

}
