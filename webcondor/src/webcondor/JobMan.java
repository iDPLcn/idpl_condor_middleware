package webcondor;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import condor.CondorScheddLocator;
import condor.CondorScheddPortType;
import condor.Transaction;
import condor.TransactionAndStatus;

public class JobMan {
	
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
