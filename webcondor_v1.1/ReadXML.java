/**
 * ReadXML.java
 */
package webcondor;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * This class can get information from the config file--Node.xml.
 *
 */
public class ReadXML {

	String XMLpath = "../config/Node.xml";
	SAXReader reader = new SAXReader();
	Document document;
	Element rootnode;
	
	public ReadXML() throws DocumentException{
		
		this.document = reader.read(new File("../config/Node.xml"));
		this.rootnode = document.getRootElement();
		
	}
	
	/**
	 * This method gets the ipv4 of a node.
	 * @param node_name BUAA,CNIC,WISC or UCSD.
	 * @return ip
	 */
	public String getIPV4(String node_name){
		return this.rootnode.element(node_name).elementText("ipv4");
	}
	
	/**
	 * This method gets the ipv6 of a node.
	 * @param node_name BUAA,CNIC,WISC or UCSD.
	 * @return ip
	 */
	public String getIPV6(String node_name){
		return this.rootnode.element(node_name).elementText("ipv6");
	}
	
	/**
	 * This method gets the httpport of a node.
	 * @param node_name BUAA,CNIC,WISC or UCSD.
	 * @return port,like 11401.
	 */
	public String getHTTPPORT(String node_name){
		return this.rootnode.element(node_name).elementText("httpport");
	}
	
	/**
	 * This method gets the ftpport of a node.
	 * @param node_name BUAA,CNIC,WISC or UCSD.
	 * @return port,like 21.
	 */
	public String getFTPPORT(String node_name){
		return this.rootnode.element(node_name).elementText("ftpport");
	}
	
	/**
	 * This method gets the http root directory of a node.
	 * @param node_name BUAA,CNIC,WISC or UCSD.
	 * @return root directory,like"/download/".
	 */
	public String getHTTPSUF(String node_name){
		return this.rootnode.element(node_name).elementText("httpsuf");
	}
	
	/**
	 * This method gets the ftp username of a node.
	 * @param node_name BUAA,CNIC,WISC or UCSD.
	 * @return ftp username.
	 */
	public String getFTPUSER(String node_name){
		return this.rootnode.element(node_name).elementText("ftpuser");
	}
	
	/**
	 * This method gets the ftp password of a node.
	 * @param node_name BUAA,CNIC,WISC or UCSD.
	 * @return ftp password.
	 */
	public String getFTPPASSWD(String node_name){
		return this.rootnode.element(node_name).elementText("ftppasswd");
	}
	/**
	 * This method gets the machine name of a node.
	 * @param node_name BUAA,CNIC,WISC or UCSD.
	 * @return name of the machine.
	 */
	public String getMACHINE(String node_name){
		return this.rootnode.element(node_name).elementText("machine");
	}
	
	/**
	 * This method gets the "ip:port" of the condor web wervice.
	 * @return
	 */
	public String getWS(){
		return this.rootnode.element("ws").elementText("ipandport");
	}
	
	/**
	 * This method gets the whole http directory of the node.
	 * @param node_name BUAA,CNIC,WISC or UCSD.
	 * @return whole directory.
	 */
	public String getHTTPHOME(String node_name){
		return this.rootnode.element(node_name).elementText("httphome");
	}
	
	/**
	 * This method gets the whole ftp directory of the node.
	 * @param node_name BUAA,CNIC,WISC or UCSD.
	 * @return whole directory.
	 */
	public String getFTPHOME(String node_name){
		return this.rootnode.element(node_name).elementText("ftphome");
	}
	
	/**
	 * This method gets the workspace of the node.
	 * @param node_name BUAA,CNIC,WISC or UCSD.
	 * @return whole directory.
	 */
	public String getWORKSPACE(String node_name){
		return this.rootnode.element(node_name).elementText("workspace");
	}
	
	/**
	 * This method gets the address of database.
	 * @return address of the database.
	 */
	public String getDBADDRESS(){
		return this.rootnode.element("database").elementText("address");
	}
	
	/**
	 * This method gets the username of database.
	 * @return username of the database.
	 */
	public String getDBUSER(){
		return this.rootnode.element("database").elementText("user");
	}
	
	/**
	 * This method gets the password of database.
	 * @return password of the database.
	 */
	public String getDBPASSWD(){
		return this.rootnode.element("database").elementText("passwd");
	}
	
	/**
	 * This method gets the table of database.
	 * @return name of the table.
	 */
	public String getDBTABLE(){
		return this.rootnode.element("database").elementText("table");
	}
	
	/**
	 * This method gets the directory of scripts.
	 * @return whole directory of scripts.
	 */
	public String getSCRIPTHOME(){
		return this.rootnode.element("script").elementText("homedir");
	}
	
	/**
	 * This method gets the name of the data generator script.
	 * @return the script name.
	 */
	public String getSCRIPT_GENERATOR(){
		return this.rootnode.element("script").elementText("generator");
	}
	
	/**
	 * This method gets the name of the data transfer script.
	 * @return the script name.
	 */
	public String getSCRIPT_TRANSFER(){
		return this.rootnode.element("script").elementText("transfer");
	}
	
	/**
	 * This method gets the name of the data remove script.
	 * @return the script name.
	 */
	public String getSCRIPT_REMOVE(){
		return this.rootnode.element("script").elementText("remove");
	}
}
