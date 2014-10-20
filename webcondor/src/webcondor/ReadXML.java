package webcondor;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ReadXML {

	String XMLpath = "../config/Node.xml";
	SAXReader reader = new SAXReader();
	Document document;
	Element rootnode;
	
	public ReadXML() throws DocumentException{
		
		this.document = reader.read(new File("../config/Node.xml"));
		this.rootnode = document.getRootElement();
		
	}
	
	public String getIPV4(String node_name){
		return this.rootnode.element(node_name).elementText("ipv4");
	}
	
	public String getIPV6(String node_name){
		return this.rootnode.element(node_name).elementText("ipv6");
	}
	
	public String getHTTPPORT(String node_name){
		return this.rootnode.element(node_name).elementText("httpport");
	}
	
	public String getFTPPORT(String node_name){
		return this.rootnode.element(node_name).elementText("ftpport");
	}
	public String getHTTPSUF(String node_name){
		return this.rootnode.element(node_name).elementText("httpsuf");
	}
	
	public String getMACHINE(String node_name){
		return this.rootnode.element(node_name).elementText("machine");
	}
	
	public String getWS(){
		return this.rootnode.element("ws").elementText("ipandport");
	}
	
	public String getHTTPHOME(String node_name){
		return this.rootnode.element(node_name).elementText("httphome");
	}
	
	public String getFTPHOME(String node_name){
		return this.rootnode.element(node_name).elementText("ftphome");
	}
	
	public String getFTPUSER(){
		return this.rootnode.element("ftpinf").elementText("user");
	}
	
	public String getFTPPASSWD(){
		return this.rootnode.element("ftpinf").elementText("passwd");
	}
	
	public String getWORKSPACE(String node_name){
		return this.rootnode.element(node_name).elementText("workspace");
	}
	
	public String getDBADDRESS(){
		return this.rootnode.element("database").elementText("address");
	}
	
	public String getDBUSER(){
		return this.rootnode.element("database").elementText("user");
	}
	
	public String getDBPASSWD(){
		return this.rootnode.element("database").elementText("passwd");
	}
	
	public String getDBTABLE(){
		return this.rootnode.element("database").elementText("table");
	}
	
	public String getSCRIPTHOME(){
		return this.rootnode.element("script").elementText("homedir");
	}
}
