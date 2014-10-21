/**
 * This class can remove/hold/release a job.
 */
package webcondor;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import condor.CondorScheddLocator;
import condor.CondorScheddPortType;
import condor.Transaction;
import condor.TransactionAndStatus;

/**
 * This class can remove/hold/release a job.
 *
 */
public class JobMan {
	
	/**
	 * This method can remove a job.
	 * @param url The web service url of condor.
	 * @param clusterID
	 * @param jobID
	 * @param reason Reason to remove the job.
	 * @throws MalformedURLException
	 * @throws ServiceException
	 * @throws RemoteException
	 */
	public static void removejob(URL url,int clusterID,int jobID,String reason) 
			throws MalformedURLException, ServiceException, RemoteException
	{
		CondorScheddLocator scheddLocator = new CondorScheddLocator();
        CondorScheddPortType schedd = scheddLocator.getcondorSchedd(url);
        TransactionAndStatus transactionAndStatus = schedd.beginTransaction(30);
        Transaction transaction = transactionAndStatus.getTransaction();
        //remove job
        schedd.removeJob(transaction, clusterID, jobID, reason,false);
        schedd.commitTransaction(transaction);
	}
	
	/**
	 * This method can hold a job.
	 * @param url The web service url of condor.
	 * @param clusterID
	 * @param jobID
	 * @param reason Reason to hold the job.
	 * @throws MalformedURLException
	 * @throws ServiceException
	 * @throws RemoteException
	 */
	public static void holdjob(URL url,int clusterID,int jobID,String reason) 
			throws MalformedURLException, ServiceException, RemoteException
	{
		CondorScheddLocator scheddLocator = new CondorScheddLocator();
        CondorScheddPortType schedd = scheddLocator.getcondorSchedd(url);
        TransactionAndStatus transactionAndStatus = schedd.beginTransaction(30);
        Transaction transaction = transactionAndStatus.getTransaction();
        //hold job
        schedd.holdJob(transaction, clusterID, jobID,reason,false,false ,false);
        schedd.commitTransaction(transaction);
	}
	
	/**
	 * This method can release a job.
	 * @param url The web service url of condor.
	 * @param clusterID
	 * @param jobID
	 * @param reason Reason to release the job.
	 * @throws MalformedURLException
	 * @throws ServiceException
	 * @throws RemoteException
	 */
	public static void releasejob(URL url,int clusterID,int jobID,String reason) 
			throws MalformedURLException, ServiceException, RemoteException
	{
		CondorScheddLocator scheddLocator = new CondorScheddLocator();
        CondorScheddPortType schedd = scheddLocator.getcondorSchedd(url);
        TransactionAndStatus transactionAndStatus = schedd.beginTransaction(30);
        Transaction transaction = transactionAndStatus.getTransaction();
        //release job
        schedd.releaseJob(transaction, clusterID, jobID,reason,false,false);
        schedd.commitTransaction(transaction);
	}

}
