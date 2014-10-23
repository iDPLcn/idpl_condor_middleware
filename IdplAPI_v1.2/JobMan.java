package cn.edu.buaa.jsi.portal;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import condor.CondorScheddLocator;
import condor.CondorScheddPortType;
import condor.Transaction;
import condor.TransactionAndStatus;

/**
 * The JobMan class can remove/hold/release a job using its clusterId and jobId.
 * 
 * @author yyq @ {2014/10/23}
 */
public class JobMan {

	/**
	 * Removes a job from queue using its clusterId and jobId.
	 * 
	 * @param url
	 *            the web service url of condor
	 * @param clusterID
	 *            the clusterId of the job
	 * @param jobID
	 *            the jobId of the job
	 * @param reason
	 *            the reason to remove the job
	 * @throws MalformedURLException
	 * @throws ServiceException
	 * @throws RemoteException
	 */
	public static void removeJob(URL url, int clusterID, int jobID,
			String reason) throws MalformedURLException, ServiceException,
			RemoteException {
		CondorScheddLocator scheddLocator = new CondorScheddLocator();
		CondorScheddPortType schedd = scheddLocator.getcondorSchedd(url);
		TransactionAndStatus transactionAndStatus = schedd.beginTransaction(30);
		Transaction transaction = transactionAndStatus.getTransaction();
		// remove job
		schedd.removeJob(transaction, clusterID, jobID, reason, false);
		schedd.commitTransaction(transaction);
	}

	/**
	 * Holds a job using its clusterId and jobID.
	 * 
	 * @param url
	 *            the web service url of condor
	 * @param clusterID
	 *            the clusterId of the job
	 * @param jobID
	 *            the jobId of the job
	 * @param reason
	 *            the reason to hold the job
	 * @throws MalformedURLException
	 * @throws ServiceException
	 * @throws RemoteException
	 */
	public static void holdjob(URL url, int clusterID, int jobID, String reason)
			throws MalformedURLException, ServiceException, RemoteException {
		CondorScheddLocator scheddLocator = new CondorScheddLocator();
		CondorScheddPortType schedd = scheddLocator.getcondorSchedd(url);
		TransactionAndStatus transactionAndStatus = schedd.beginTransaction(30);
		Transaction transaction = transactionAndStatus.getTransaction();
		// hold job
		schedd.holdJob(transaction, clusterID, jobID, reason, false, false,
				false);
		schedd.commitTransaction(transaction);
	}

	/**
	 * Releases a job using its clusterId and jobID.
	 * 
	 * @param url
	 *            the web service url of condor
	 * @param clusterID
	 *            the clusterId of the job
	 * @param jobID
	 *            the jobId of the job
	 * @param reason
	 *            the reason to release the job
	 * @throws MalformedURLException
	 * @throws ServiceException
	 * @throws RemoteException
	 */
	public static void releasejob(URL url, int clusterID, int jobID,
			String reason) throws MalformedURLException, ServiceException,
			RemoteException {
		CondorScheddLocator scheddLocator = new CondorScheddLocator();
		CondorScheddPortType schedd = scheddLocator.getcondorSchedd(url);
		TransactionAndStatus transactionAndStatus = schedd.beginTransaction(30);
		Transaction transaction = transactionAndStatus.getTransaction();
		// release job
		schedd.releaseJob(transaction, clusterID, jobID, reason, false, false);
		schedd.commitTransaction(transaction);
	}

}
