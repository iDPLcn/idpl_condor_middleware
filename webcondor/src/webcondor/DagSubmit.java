package webcondor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.rpc.ServiceException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import birdbath.Schedd;
import birdbath.Transaction;
import condor.ClassAdAttrType;
import condor.ClassAdStructAttr;
import condor.UniverseType;

public class DagSubmit {
	
	private String user;
	private long id;
	private URL ws_url;
	private String source_machine;
	private String destination_machine;
	private String source_DataDirectory;
	private String des_DataDirectory;
	private String starttime;
	private int repeat;
	private String executable;
	private String arguments;
	private String requirements;
	private int ddClusterID;
	private String stoptime;
	private String FtpPutRm;
	
	public DagSubmit(long id,URL ws_url,String user,String source_machine,String destination_machine,
			String source_DataDirectory,String des_DataDirectory,String starttime,String stoptime,
			int repeat,String executable,String arguments,String requirements,int ddClusterID,String FtpPutRm){
		this.ws_url = ws_url;
		this.id = id;
		this.user = user;
		this.source_machine = source_machine;
		this.destination_machine = destination_machine;
		this.source_DataDirectory = source_DataDirectory;
		this.des_DataDirectory = des_DataDirectory;
		this.starttime = starttime;
		this.stoptime = stoptime;
		this.repeat = repeat;
		this.executable = executable;
		this.arguments = arguments;
		this.requirements = requirements;
		this.ddClusterID = ddClusterID;
		this.FtpPutRm = FtpPutRm;
	}
	
	public void submit() throws ServiceException, FileNotFoundException, IOException, DocumentException, InterruptedException, ParseException{
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ReadXML readxml = new ReadXML();
		String machine = readxml.getMACHINE("BUAA");
		
		Schedd schedd = new Schedd(ws_url);
		Transaction xact = schedd.createTransaction();
		xact.begin(30);
		final int clusterID = xact.createCluster();
		int jobID = xact.createJob(clusterID);
		
		DagGenFiles dgf = new DagGenFiles(user,repeat,executable, arguments,requirements,clusterID);
		File[] DagFiles = dgf.genfiles();
		
		String data_state = querysuc(ws_url,ddClusterID,0);
		while(!data_state.equals("Completed")){
			Thread.sleep(1000);
			data_state = querysuc(ws_url,ddClusterID,0);
		}
		////////////////////////////////////////////////////
		System.out.println("Data Generator Completed.");
		
		String command = "/usr/bin/condor_dagman";
		String arguments = "-f -l . -Lockfile diamond.dag.lock -AutoRescue 1 -DoRescueFrom 0 -Dag input.dag -CsdVersion $CondorVersion:' '7.6.3' 'Oct' '14' '2011' '$ -Dagman /usr/local/bin/condor_dagman";
		String requirements = "machine == \""+machine+"\"";
		
		ClassAdStructAttr[] Attributes;
		
		if(starttime!=null){
			
			long time1 = df.parse("1970-01-01 08:00:00").getTime();
			long time2 = df.parse(starttime).getTime();
			long DeferralTime = (time2 - time1)/1000;
			
			ClassAdStructAttr[] extraAttributes ={
					new ClassAdStructAttr("DeferralTime",
							ClassAdAttrType.value4,String.valueOf(DeferralTime)),
					new ClassAdStructAttr("Out",ClassAdAttrType.value3,clusterID+".out"),
					new ClassAdStructAttr("Err",ClassAdAttrType.value3,clusterID+".err"),
					new ClassAdStructAttr("UserLog",ClassAdAttrType.value3,clusterID+".log"),
					new ClassAdStructAttr("should_transfer_files",ClassAdAttrType.value3,"YES"),
					new ClassAdStructAttr("when_to_transfer_output",ClassAdAttrType.value3,"ON_EXIT"),
					new ClassAdStructAttr("transfer_output_files",ClassAdAttrType.value3,"\"\"")
					
			};
			Attributes = extraAttributes;
			
		}else{
			
			ClassAdStructAttr[] extraAttributes ={
					new ClassAdStructAttr("Out",ClassAdAttrType.value3,clusterID+".out"),
					new ClassAdStructAttr("Err",ClassAdAttrType.value3,clusterID+".err"),
					new ClassAdStructAttr("UserLog",ClassAdAttrType.value3,clusterID+".log"),
					new ClassAdStructAttr("should_transfer_files",ClassAdAttrType.value3,"YES"),
					new ClassAdStructAttr("when_to_transfer_output",ClassAdAttrType.value3,"ON_EXIT"),
					new ClassAdStructAttr("transfer_output_files",ClassAdAttrType.value3,"\"\"")
					
			};
			Attributes = extraAttributes;
		}
		xact.submit(clusterID, jobID,"root", UniverseType.VANILLA, command, arguments, requirements,Attributes, DagFiles);
		xact.commit();
		schedd.requestReschedule();
		
		/////////////////////////////////////////////////////////
		System.out.println("Dag submit.");
		System.out.println("Call TimeMan");
		TimeMan tm = new TimeMan(id,ws_url, clusterID, jobID,source_machine,destination_machine,source_DataDirectory,des_DataDirectory,ddClusterID,FtpPutRm);
		Thread th = new Thread(tm);
		th.start();
		
		if(stoptime!=null){
			System.out.println("stoptime!=null");
			Date stop = df.parse(stoptime);
			TimerTask timetask = new TimerTask(){				
				public void run(){
					try {
						String status = querysuc(ws_url,clusterID,0);
						while(status.equals("fail")){
							status = querysuc(ws_url,clusterID,0);
						}
						if(!status.equals("Completed")){
							JobMan.holdjob(ws_url, clusterID,0,"Time's up.");
						}
					} catch (Exception e) {
					
					}
				}
			};
			Timer timer = new Timer();
			timer.schedule(timetask,stop);
		}
	}
	
	private String querysuc(URL url,int clusterID,int jobID){
		try{
			String state = QueryJob.queryjob(url, clusterID, jobID);
			return state;
		}catch(Exception e){
			System.out.println("fail to query111");
			return "fail";
		}
	}
}
