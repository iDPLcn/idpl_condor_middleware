package webcondor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;


public class DataTransfer implements Runnable{
	
	

	private String source;
	private String destination;
	private String user;
	private String source_DataDirectory;
	private String des_DataDirectory;
	private String FilesSize;
	private int FilesNumber;
	private int repeat;
	private String protocol;
	private String v4orv6;
	private String putorget;
	private int parallel;
	private String FTP_USER;
	private String FTP_PASSWD;
	private String starttime;
	private String stoptime;
	
	private static String db_address;
	private static String driver = "com.mysql.jdbc.Driver";
	private static Connection con = null;
	private static Statement stmt = null;
	private static String db_table_name;
	private static String db_user;
	private static String db_passwd;
	private long id;

	public DataTransfer(long id,String user,String source,String destination,String size,int number,int repeat,int parallel,
			String protocol,String putorget,String v4orv6,String starttime,String stoptime){
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.user = user;
		this.FilesSize = size;
		this.FilesNumber = number;
		this.repeat = repeat;
		this.protocol = protocol;
		this.v4orv6 = v4orv6;
		this.putorget = putorget;
		this.parallel = parallel;
		this.starttime = starttime;
		this.stoptime = stoptime;
	}
	@Override
	public void run() {
		
		int ddclusterID;
		final int dagclusterID;
		final URL webservice_url;
		long time;
		long SubmitTime;
//		BufferedReader br;
//		String line = null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String executable;
		String requirements;
		String arguments;
		String source_ip;
		String destination_ip;
		String source_machine;
		String destination_machine;
		String source_httpport;
		String source_httpsuf;
		String webservice_ipandport;
		String usertime;
		String script_home;
		String FtpPutRm=null;
		String source_ftpport;
		String destination_ftpport;
		
		try {
			ReadXML readxml = new ReadXML();
			db_address = readxml.getDBADDRESS();
			db_table_name = readxml.getDBTABLE();
			db_user = readxml.getDBUSER();
			db_passwd = readxml.getDBPASSWD();
			
			connect();
			stmt.execute("insert into "+db_table_name+" (id,State) values("+id+",'Waiting');");

			
			source_machine = readxml.getMACHINE(source);
			destination_machine = readxml.getMACHINE(destination);
			webservice_ipandport = readxml.getWS();
			webservice_url = new URL(webservice_ipandport);
			source_httpport = readxml.getHTTPPORT(source);
			source_httpsuf = readxml.getHTTPSUF(source);
			source_ftpport = readxml.getFTPPORT(source);
			destination_ftpport = readxml.getFTPPORT(destination);
			FTP_USER = readxml.getFTPUSER();
			FTP_PASSWD = readxml.getFTPPASSWD();
			script_home = readxml.getSCRIPTHOME();
			
			time = new Date().getTime();
			usertime = user+"_"+time+"/";
			
	        if(protocol.equalsIgnoreCase("http")){
				source_DataDirectory = readxml.getHTTPHOME(source)+usertime;
				des_DataDirectory = readxml.getHTTPHOME(destination)+usertime;
			}else{
				source_DataDirectory = readxml.getFTPHOME(source)+"download/"+usertime;
				if(putorget.equalsIgnoreCase("put"))
					des_DataDirectory = readxml.getFTPHOME(destination)+"upload/"+usertime;
				else
					des_DataDirectory = readxml.getFTPHOME(destination)+"download/"+usertime;
			}
			//////////////////////////////////////////////
			System.out.println("source_DataDirectory="+source_DataDirectory);
			System.out.println("des_DataDirectory="+des_DataDirectory);
			
			if(v4orv6.equalsIgnoreCase("ipv4")){
				source_ip = readxml.getIPV4(source);
				destination_ip = readxml.getIPV4(destination);
			}else{
				source_ip = readxml.getIPV6(source);
				destination_ip = readxml.getIPV6(destination);
			}
			
			DataGenerator files = new DataGenerator(webservice_url,source_DataDirectory,FilesSize,FilesNumber,source_machine);
			ddclusterID = files.generate();
			
			executable = script_home+"idpl_data_transfer";
			
			if(protocol.equalsIgnoreCase("http")){
				requirements = "machine == \""+destination_machine+"\"";
				arguments = "-t "+source_ip+":"+source_httpport+" -s "+source_httpsuf+usertime+" -d "+des_DataDirectory+" -n "+ddclusterID+" -p http -D get -N "+FilesNumber+" -P "+parallel;
				if(v4orv6.equalsIgnoreCase("ipv6")){
					arguments = arguments +" -6";
				}
			}
			else{
				if(putorget.equalsIgnoreCase("put")){
					requirements = "machine == \""+source_machine+"\"";
					arguments = "-t "+destination_ip+":"+destination_ftpport+" -s "+source_DataDirectory+" -d "+"/upload/"+usertime+" -n "+ddclusterID+" -p ftp -D put -N "+FilesNumber+" -P "+parallel+" FTP_USER="+FTP_USER+" FTP_PASSWD="+FTP_PASSWD;
					if(v4orv6.equalsIgnoreCase("ipv6")){
						arguments = arguments +" -6";
					}
					FtpPutRm = "-t "+destination_ip+" -d "+"/upload/"+usertime+" -n "+ddclusterID+" -p ftp -N "+FilesNumber+" FTP_USER="+FTP_USER+" FTP_PASSWD="+FTP_PASSWD;
					
				}else{
					requirements = "machine == \""+destination_machine+"\"";
					arguments = "-t "+source_ip+":"+source_ftpport+" -s /download/"+usertime+" -d "+des_DataDirectory+" -n "+ddclusterID+" -p ftp -D get -N "+FilesNumber+" -P "+parallel+" FTP_USER="+FTP_USER+" FTP_PASSWD="+FTP_PASSWD;
					if(v4orv6.equalsIgnoreCase("ipv6")){
						arguments = arguments +" -6";
					}
				}
			}
			System.out.println("arguments = "+arguments);
	
			SubmitTime = df.parse(df.format(new Date())).getTime()/1000;
			stmt.executeUpdate("UPDATE "+db_table_name+" SET SubmitTime='"+SubmitTime+"' WHERE id="+id+";");
			
			DagSubmit ds = new DagSubmit(id,webservice_url,user,source_machine,destination_machine,source_DataDirectory,des_DataDirectory,starttime,
					stoptime,repeat,executable,arguments,requirements,ddclusterID,FtpPutRm);
			ds.submit();
			
			con.close();
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean connect()
	{		
		try{
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
