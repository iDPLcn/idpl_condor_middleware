package webcondor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.dom4j.DocumentException;

public class DagGenFiles {
	
	private String user;
	private int repeat;
	private String executable;
	private String arguments;
	private String requirements;
	private int TransferClusterID;

	private static String IDPLpath = "../tmp/";
	public DagGenFiles(String user,int repeat,String executable,String arguments,String requirements,int DagClusterID){
		
		this.user = user;
		this.repeat = repeat;
		this.executable = executable;
		this.arguments = arguments;
		this.requirements = requirements;
		this.TransferClusterID = DagClusterID+1;
	}
	
	public File[] genfiles() throws IOException, DocumentException{
		
		File[] files = createFile();
		writeDAG(files[0]);
		writeJOB(files[1]);
		return files;
	} 

	public File[] createFile() throws IOException{
		
		long time = new Date().getTime();
		String JOBpath = IDPLpath+user+"_"+time;
		
		new File(JOBpath).mkdirs();//create directory for this user and this job
		
		File input_dag = new File(JOBpath+"/input.dag");//create DAG input file
		input_dag.createNewFile();
		
		File submit_condor = new File(JOBpath+"/submit.condor");//create HTCondor submit description file for this DAG
		submit_condor.createNewFile();
		
		File[] dagfiles= new File[]{input_dag,submit_condor};
		return dagfiles;
	}
	
	public void writeDAG(File input_dag) throws IOException{
		
		FileWriter fw = new FileWriter(input_dag.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		   
//		if(repeat == 1){
			bw.write("JOB 1 submit.condor");
			bw.newLine();
//		}else{
//			for(int i=1;i<=repeat;i++){
//				bw.write("JOB "+i+" submit.condor");
//				bw.newLine();
//			}
//			for(int i=1;i<repeat;i++){
//				int j=i+1;
//				bw.write("PARENT "+i+" CHILD "+ j);
//				bw.newLine();
//			}
//		}
		bw.close();
	}
	
	private void writeJOB(File submit_condor) throws IOException, DocumentException {
		ReadXML readxml = new ReadXML();
		String script_home = readxml.getSCRIPTHOME();
		String BUAA_httphome = readxml.getHTTPHOME("BUAA");
		
		FileWriter fw = new FileWriter(submit_condor.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("executable = "+executable);
		bw.newLine();
		bw.write("arguments = "+arguments);
		bw.newLine();
		bw.write("universe =vanilla");
		bw.newLine();
		bw.write("Iwd = "+BUAA_httphome);
		bw.newLine();
		bw.write("log = "+BUAA_httphome+TransferClusterID+".log");
		bw.newLine();
		bw.write("error = "+BUAA_httphome+TransferClusterID+".err");
		bw.newLine();
		bw.write("requirements = "+requirements);
		bw.newLine();
		bw.write("should_transfer_files=YES");
		bw.newLine();
		bw.write("when_to_transfer_output=ON_EXIT");
		bw.newLine();
		bw.write("transfer_output_files=\"\"");
		bw.newLine();
		if(repeat==1)
			bw.write("queue");
		else
			bw.write("queue "+repeat);
		bw.newLine();
		bw.close();
	}
}
