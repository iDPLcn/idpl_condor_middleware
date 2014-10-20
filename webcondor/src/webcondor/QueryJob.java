package webcondor;

import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import birdbath.ClassAd;
import birdbath.Schedd;
import birdbath.Transaction;

public class QueryJob {
	
	public static String queryjob(URL url,int clusterID,int jobID) 
			throws ServiceException, RemoteException 
		
	{
		String[] statusName = { "", "Idle", "Running", "Removed", "Completed", "Held"};		
		Schedd schedd = new Schedd(url);
		Transaction xact = schedd.createTransaction();
		xact.begin(30);
		
		ClassAd ad = new ClassAd(schedd.getJobAd(clusterID,jobID));
		int status = Integer.valueOf(ad.get("JobStatus"));
		String JobStatus = statusName[status];
		
		xact.commit();
		return JobStatus;
	}
	
}
