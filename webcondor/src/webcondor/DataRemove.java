package webcondor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.dom4j.DocumentException;

import condor.ClassAdAttrType;
import condor.ClassAdStructAttr;
import condor.UniverseType;
import birdbath.Schedd;
import birdbath.Transaction;

public class DataRemove {

	private URL url;
	private String source_machine;
	private String source_DataDirectory;
	private String des_DataDirectory;
	private String destination_machine;
	private int ddClusterID;
	private String FtpPutRm;

	public DataRemove(URL url,String source_machine,String destination_machine,String source_DataDirectory,String des_DataDirectory,int ddClusterID,String FtpPutRm){
		
		this.url = url;
		this.source_machine = source_machine;
		this.source_DataDirectory = source_DataDirectory;
		this.des_DataDirectory = des_DataDirectory;
		this.destination_machine = destination_machine;
		this.ddClusterID = ddClusterID;
		this.FtpPutRm = FtpPutRm;
	}
	
	public void remove() throws ServiceException, FileNotFoundException, IOException, DocumentException{
		
		ReadXML readxml = new ReadXML();
		String script_home = readxml.getSCRIPTHOME();
		
		Schedd schedd = new Schedd(url);
		Transaction xact = schedd.createTransaction();
		xact.begin(30);
		int clusterID = xact.createCluster();
		int jobID0 = xact.createJob(clusterID);
		
		String command = script_home+"/idpl_data_rm";
		String arguments1 = "-d "+source_DataDirectory;
		String arguments2;
		
		if(FtpPutRm!=null)
			arguments2 = FtpPutRm;
		else
			arguments2 = "-d "+des_DataDirectory;
		
		String requirements1 = "machine == \""+source_machine+"\"";
		String requirements2 = "machine == \""+destination_machine+"\"";
		
		ClassAdStructAttr[] extraAttributes ={
//				new ClassAdStructAttr("Iwd",ClassAdAttrType.value3,BUAA_httphome),
//				new ClassAdStructAttr("Out",ClassAdAttrType.value3,clusterID+".out"),
//				new ClassAdStructAttr("Err",ClassAdAttrType.value3,clusterID+".err"),
//				new ClassAdStructAttr("UserLog",ClassAdAttrType.value3,clusterID+".log")
				
		};
	
		xact.submit(clusterID, jobID0,"data-rm", UniverseType.VANILLA, command, arguments1, requirements1,extraAttributes, null);
		int jobID1 = xact.createJob(clusterID);
		xact.submit(clusterID, jobID1,"data-rm", UniverseType.VANILLA, command, arguments2, requirements2,extraAttributes, null);
		xact.commit();
		schedd.requestReschedule();
	}
}
