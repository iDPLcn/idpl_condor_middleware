package cn.edu.buaa.jsi.portal;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 
 * @author yyq @{2014/10/23}
 *
 */
public class ReadXML {

	public static ReadXML readXML;
	public static final String XMLpath = "../config/Node.xml";
	SAXReader reader;
	Document document;
	Element rootnode;

	/**
	 * The ReadXML class gets information from Node.xml according to
	 * requirements,and returns the information it gets.
	 * 
	 * @throws DocumentException
	 */
	private ReadXML() throws DocumentException {
		this.reader = new SAXReader();
		this.document = reader.read(new File(ReadXML.XMLpath));
		this.rootnode = document.getRootElement();

	}

	/**
	 * Returns a new instance of class ReadXML.
	 * 
	 * @return a instance of class ReadXML
	 */
	public static synchronized ReadXML getInstance() {
		if (readXML == null) {
			try {
				readXML = new ReadXML();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		return readXML;
	}

	/**
	 * Returns the ipv4 of a node.
	 * 
	 * @param node_name
	 *            BUAA,CNIC,WISC or UCSD.
	 * @return the ipv4 of node described by "node_name"
	 */
	public String getIPV4(String node_name) {
		return this.rootnode.element(node_name).elementText("ipv4");
	}

	/**
	 * Returns the ipv6 of a node.
	 * 
	 * @param node_name
	 *            BUAA,CNIC,WISC or UCSD.
	 * @return the ipv6 of node described by "node_name"
	 */
	public String getIPV6(String node_name) {
		return this.rootnode.element(node_name).elementText("ipv6");
	}

	/**
	 * Returns the http port of a node.
	 * 
	 * @param node_name
	 *            BUAA,CNIC,WISC or UCSD.
	 * @return port,like 11401
	 */
	public String getHTTPPORT(String node_name) {
		return this.rootnode.element(node_name).elementText("httpport");
	}

	/**
	 * Returns the ftp port of a node.
	 * 
	 * @param node_name
	 *            BUAA,CNIC,WISC or UCSD.
	 * @return port,like 21
	 */
	public String getFTPPORT(String node_name) {
		return this.rootnode.element(node_name).elementText("ftpport");
	}

	/**
	 * Returns the http root directory of a node.
	 * 
	 * @param node_name
	 *            BUAA,CNIC,WISC or UCSD.
	 * @return root directory,like"/download/"
	 */
	public String getHTTPSUF(String node_name) {
		return this.rootnode.element(node_name).elementText("httpsuf");
	}

	/**
	 * Returns the ftp username of a node.
	 * 
	 * @param node_name
	 *            BUAA,CNIC,WISC or UCSD.
	 * @return the ftp username of the node described by "node_name"
	 */
	public String getFTPUSER(String node_name) {
		return this.rootnode.element(node_name).elementText("ftpuser");
	}

	/**
	 * Returns the ftp password of a node.
	 * 
	 * @param node_name
	 *            BUAA,CNIC,WISC or UCSD.
	 * @return the ftp password of the node described by "node_name"
	 */
	public String getFTPPASSWD(String node_name) {
		return this.rootnode.element(node_name).elementText("ftppasswd");
	}

	/**
	 * Returns the name of a node.
	 * 
	 * @param node_name
	 *            BUAA,CNIC,WISC or UCSD.
	 * @return the name of the node described by "node_name"
	 */
	public String getMACHINE(String node_name) {
		return this.rootnode.element(node_name).elementText("machine");
	}

	/**
	 * Returns the "ip:port" of the machine where the web service of condor
	 * belongs to
	 * 
	 * @return "ip:port",which can be used to connect with remote condor
	 */
	public String getWS() {
		return this.rootnode.element("ws").elementText("ipandport");
	}

	/**
	 * Returns the whole http directory of the node.
	 * 
	 * @param node_name
	 *            BUAA,CNIC,WISC or UCSD.
	 * @return the whole http directory of the node described by "node_name"
	 */
	public String getHTTPHOME(String node_name) {
		return this.rootnode.element(node_name).elementText("httphome");
	}

	/**
	 * Returns the whole ftp directory of the node.
	 * 
	 * @param node_name
	 *            BUAA,CNIC,WISC or UCSD.
	 * @return the whole ftp directory of the node described by "node_name"
	 */
	public String getFTPHOME(String node_name) {
		return this.rootnode.element(node_name).elementText("ftphome");
	}

	/**
	 * Return the address of database.
	 * 
	 * @return the address of the database
	 */
	public String getDBADDRESS() {
		return this.rootnode.element("database").elementText("address");
	}

	/**
	 * Returns the username of database.
	 * 
	 * @return the username of the database
	 */
	public String getDBUSER() {
		return this.rootnode.element("database").elementText("user");
	}

	/**
	 * Returns the password of database.
	 * 
	 * @return the password of the database
	 */
	public String getDBPASSWD() {
		return this.rootnode.element("database").elementText("passwd");
	}

	/**
	 * Returns the name of table in database.The information about each
	 * experiment will be wrote to this table.
	 * 
	 * @return the table name of database
	 */
	public String getDBTABLE() {
		return this.rootnode.element("database").elementText("table");
	}

	/**
	 * Returns the directory of scripts.
	 * 
	 * @return whole directory of scripts
	 */
	public String getSCRIPTHOME() {
		return this.rootnode.element("script").elementText("homedir");
	}

	/**
	 * Returns the name of the data generator script.
	 * 
	 * @return the script name
	 */
	public String getSCRIPT_GENERATOR() {
		return this.rootnode.element("script").elementText("generator");
	}

	/**
	 * Returns the name of the data transfer script.
	 * 
	 * @return the script name
	 */
	public String getSCRIPT_TRANSFER() {
		return this.rootnode.element("script").elementText("transfer");
	}

	/**
	 * Returns the name of the data remove script.
	 * 
	 * @return the script name
	 */
	public String getSCRIPT_REMOVE() {
		return this.rootnode.element("script").elementText("remove");
	}
}
