/**
 * QueryJob.java
 */
package webcondor;

import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import birdbath.ClassAd;
import birdbath.Schedd;
import birdbath.Transaction;

/**
 * Query the state of a job.
 *
 */
public class QueryJob {
	
	/**
	 * Query the state of a job.
	 * @param url The web service URL of condor.
	 * @param clusterID
	 * @param jobID
	 * @return state of the job:"Idle","Running","Removed","Completed" or "Held".
	 * @throws ServiceException
	 * @throws RemoteException
	 */
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
