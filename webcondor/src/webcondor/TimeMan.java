package webcondor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class TimeMan implements Runnable{

	private String db_address;
	private String driver = "com.mysql.jdbc.Driver";
	private Connection con = null;
	private Statement stmt = null;
	public static String db_table_name;
	private String db_user;
	private String db_passwd;
	
	private int DagClusterID;
	private int jobID;
	private URL url;
	private long id;
	private String source_machine;
	private String destination_machine;
	private String source_DataDirectory;
	private String des_DataDirectory;
	private int ddClusterID;
	private String FtpPutRm;
	
	public TimeMan(long id,URL url,int clusterID, int jobID,String source_machine,String destination_machine,
			String source_DataDirectory,String des_DataDirectory,int ddClusterID,String FtpPutRm) {
		super();
		this.id = id;
		this.url = url;
		this.DagClusterID = clusterID;
		this.jobID = jobID;
		this.source_machine = source_machine;
		this.source_DataDirectory = source_DataDirectory;
		this.des_DataDirectory = des_DataDirectory;
		this.destination_machine = destination_machine;
		this.ddClusterID = ddClusterID;
		this.FtpPutRm = FtpPutRm;
	}	
	
	public void run(){
		// TODO Auto-generated method stub
		
		try{
			ReadXML readxml = new ReadXML();
			db_address = readxml.getDBADDRESS();
			db_table_name = readxml.getDBTABLE();
			db_user = readxml.getDBUSER();
			db_passwd = readxml.getDBPASSWD();
			
			String laststate="Idle";
			String state = null;
			String line = null;
			String[] tokens;
			String RunningTime;
			String CompletedTime;
			String SubmitTime;
			BufferedReader br;
			long longtime = 0;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			int JobClusterID = DagClusterID+1;
			String log_ip = "http://"+readxml.getIPV4("BUAA")+":"+readxml.getHTTPPORT("BUAA")+readxml.getHTTPSUF("BUAA")+JobClusterID+".log";
			URL log_url = new URL(log_ip);
			
			connect();
			
			stmt.executeUpdate("UPDATE "+db_table_name+" SET ClusterID="+DagClusterID+" WHERE id="+id+";");
			stmt.executeUpdate("UPDATE "+db_table_name+" SET JobID="+jobID+" WHERE id="+id+";");
			stmt.executeUpdate("UPDATE "+db_table_name+" SET State='Idle' WHERE id="+id+";");
			
			while(true){
				state =querysuc(url,DagClusterID,jobID);
				while(state.equals("fail")){
					Thread.sleep(1000);
					state =querysuc(url,DagClusterID,jobID);
				}
				if(state.equals(laststate)){
					Thread.sleep(1000);
				}
				else if(state.equals("Running")){
					laststate = "Running";
						
//					while(!existfile(log_url));
//						
//					br = new BufferedReader(new InputStreamReader(log_url.openStream()));
//					line = br.readLine();
//					while(line==null){
//						br = new BufferedReader(new InputStreamReader(log_url.openStream()));
//						line = br.readLine();
//					}
//					if(line.substring(0,3).equals("000")){
//						tokens = line.split(" ");
//						SubmitTime = df.format(new Date()).substring(0, 10)+" "+tokens[3];
//						longtime = df.parse(SubmitTime).getTime()/1000;
//						stmt.executeUpdate("UPDATE "+table_name+" SET SubmitTime='"+longtime+"' WHERE id="+id+";");
//					}
//					while(!line.substring(0,3).equals("001")){
//						line = br.readLine();
//					}
//					tokens = line.split(" ");
//					RunningTime = df.format(new Date()).substring(0, 10)+" "+tokens[3];
					RunningTime = df.format(new Date());
					longtime = df.parse(RunningTime).getTime()/1000;
						
					//write to mysql-->State,StartRunning
					stmt.executeUpdate("UPDATE "+db_table_name+" SET State='Running' WHERE id="+id+";");
					stmt.executeUpdate("UPDATE "+db_table_name+" SET StartRunning='"+longtime+"' WHERE id="+id+";");
						
				}
					
				else if(state.equals("Completed")){					
						
//					br = new BufferedReader(new InputStreamReader(log_url.openStream()));
//					line = br.readLine();
//					line = br.readLine();
//					line = br.readLine();
//					while(line==null){
//						br = new BufferedReader(new InputStreamReader(log_url.openStream()));
//						line = br.readLine();
//						System.out.println(line);
//						line = br.readLine();
//						line = br.readLine();
//							
//					}
//					while(!line.substring(0,3).equals("005")){
//						line = br.readLine();
//					}
//					
//					tokens = line.split(" ");
//					CompletedTime = df.format(new Date()).substring(0, 10)+" "+tokens[3];
					CompletedTime = df.format(new Date());
					longtime = df.parse(CompletedTime).getTime()/1000;
						
					
					//write to mysql-->State,CompletedTime
					stmt.executeUpdate("UPDATE "+db_table_name+" SET State='Completed' WHERE id="+id+";");
					stmt.executeUpdate("UPDATE "+db_table_name+" SET CompletedTime='"+longtime+"' WHERE id="+id+";");	
					
					////////////////////////////////////////////
					System.out.println("Call DataRemove.");
					DataRemove dr = new DataRemove(url,source_machine,destination_machine,source_DataDirectory,des_DataDirectory,ddClusterID,FtpPutRm);
					dr.remove();
					break;
				}
					
				else if(state.equals("Held")){
					JobMan.removejob(url, DagClusterID, jobID, "Time is up!");
					stmt.executeUpdate("UPDATE "+db_table_name+" SET State='Stop' WHERE id="+id+";");
//					stmt.executeUpdate("UPDATE "+db_table_name+" SET State='Held' WHERE id="+id+";");
					
					////////////////////////////////////////////
					System.out.println("Call DataRemove.");
					DataRemove dr = new DataRemove(url,source_machine,destination_machine,source_DataDirectory,des_DataDirectory,ddClusterID,FtpPutRm);
					dr.remove();
					break;
				}
				
				
			
			}
			con.close();
			stmt.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	private boolean existfile(URL log_url) {
		// TODO Auto-generated method stub
		try{
			log_url.openStream();
			return true;
		}catch(Exception e){
			return false;
		}
		
	}

	private String querysuc(URL url,int clusterID,int jobID){
		try{
			String state = QueryJob.queryjob(url, clusterID, jobID);
			return state;
		}catch(Exception e){
			System.out.println("fail");
			return "fail";
		}
	}
	
	public boolean connect()
	{		
		try 
		{
			Class.forName(driver).newInstance();
			con = DriverManager.getConnection(db_address, db_user, db_passwd);
			
			stmt = con.createStatement();
			return true;
			
		} catch (Exception e){			
			System.out.println(e.getMessage());
		}
		return false;
	}
}
