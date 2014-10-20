package webcondor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

import condor.ClassAdAttrType;
import condor.ClassAdStructAttr;
import condor.UniverseType;
import birdbath.Schedd;
import birdbath.Transaction;

public class DataGenerator {
	
	private String directory;
	private String FileSize;
	private int number;
	private String machine;
	private URL ws_url;

	public DataGenerator(URL ws_url,String directory,String FileSize,int number,String machine){
	
		this.ws_url = ws_url;
		this.directory = directory;
		this.FileSize = FileSize;
		this.number = number;
		this.machine = machine;
	}
	public int generate() throws ParserConfigurationException, SAXException, IOException, ServiceException, DocumentException{
		
		ReadXML readxml = new ReadXML();
		String script_home = readxml.getSCRIPTHOME();
		
		int length = FileSize.length();
		int size = Integer.parseInt(FileSize.substring(0,length-1));
		char unit = FileSize.charAt(length-1);

		Schedd schedd = new Schedd(ws_url);
		Transaction xact = schedd.createTransaction();
		xact.begin(30);
		int clusterID = xact.createCluster();
		int jobID = xact.createJob(clusterID);
		String name = Integer.toString(clusterID);
		
		String command = script_home+"/idpl_data_generator";
		String arguments = "-d "+directory+" -n "+name+" -s "+size+" -u "+unit+" -N "+number;
		String requirements = "machine == \""+machine+"\"";
		
		ClassAdStructAttr[] extraAttributes ={
//				new ClassAdStructAttr("Iwd",ClassAdAttrType.value3,script_home),
				new ClassAdStructAttr("Out",ClassAdAttrType.value3,clusterID+".out"),
				new ClassAdStructAttr("Err",ClassAdAttrType.value3,clusterID+".err"),
				new ClassAdStructAttr("UserLog",ClassAdAttrType.value3,clusterID+".log")
				
		};
		xact.submit(clusterID, jobID,"dd", UniverseType.VANILLA, command, arguments, requirements,extraAttributes, null);
		xact.commit();
		///////////////////////
		System.out.println("DataGenerator transaction commit.");
		
		schedd.requestReschedule();
		
		return clusterID;
	}
}
