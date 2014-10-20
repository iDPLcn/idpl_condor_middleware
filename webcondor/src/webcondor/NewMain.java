package webcondor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.apache.axis.components.threadpool.ThreadPool;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import birdbath.Schedd;
import birdbath.Transaction;
import condor.ClassAdAttrType;
import condor.ClassAdStructAttr;
import condor.UniverseType;

public class NewMain {

	public static void main(String[] args) throws IOException, ServiceException{

		Transfer transfer = new Transfer(1,"yyq","WISC","BUAA","10M",1,1,1,"http","get","ipv6",null,null);
		transfer.submit();

	}
	
}
