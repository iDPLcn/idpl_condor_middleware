package cn.edu.buaa.jsi.portal;

import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import birdbath.ClassAd;
import birdbath.Schedd;
import birdbath.Transaction;

/**
 * 
 * @author yyq @{2014/10/23}
 *
 */
public class QueryJob {

	/**
	 * The queryJob method returns the state of a job using the clusterId and jobId of it.
	 * 
	 * @param url
	 *            the web service URL of condor
	 * @param clusterID
	 *            the clusterId of the job
	 * @param jobID
	 *            the jobId of the job
	 * @return the state of the job:"Idle","Running","Removed","Completed" or
	 *         "Held".
	 * @throws ServiceException
	 * @throws RemoteException
	 */
	public static String queryJob(URL url, int clusterID, int jobID)
			throws ServiceException, RemoteException

	{
		String[] statusName = { "", "Idle", "Running", "Removed", "Completed",
				"Held" };
		Schedd schedd = new Schedd(url);
		Transaction xact = schedd.createTransaction();
		xact.begin(30);

		ClassAd ad = new ClassAd(schedd.getJobAd(clusterID, jobID));
		int status = Integer.valueOf(ad.get("JobStatus"));
		String JobStatus = statusName[status];

		xact.commit();
		return JobStatus;
	}

}
