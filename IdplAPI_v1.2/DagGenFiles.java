package cn.edu.buaa.jsi.portal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.DocumentException;

/**
 * The DagGenFiles class generates files needed by a DAG job,includes a input
 * file describing the DAG and HTCondor submit description files for this DAG.
 * 
 * @author yyq @ {2014/10/21}
 *
 */
public class DagGenFiles {
	/**
	 * Generates the input file describing the DAG according to the number of
	 * all jobs.
	 * 
	 * @param num_Job
	 *            the number of all jobs
	 * @param dagFilesLocation
	 *            the whole directory of files needed by DAG
	 * @throws IOException
	 */
	public void genDagInput(int num_Job, String dagFilesLocation)
			throws IOException {

		File input_dag = new File(dagFilesLocation + "input.dag");
		input_dag.createNewFile();

		FileWriter fw = new FileWriter(input_dag.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);

		if (num_Job == 1) {
			bw.write("JOB 0 submit.0" + "\n");

		} else {
			for (int i = 0; i < num_Job; i++) {
				bw.write("JOB " + i + " submit." + i + "\n");
				// bw.newLine();
			}
			for (int i = 0; i < num_Job - 1; i++) {
				int j = i + 1;
				bw.write("PARENT " + i + " CHILD " + j + "\n");
				// bw.newLine();
			}
		}
		bw.close();
	}

	/**
	 * Generates the HTCondor submit description files for this job.
	 * 
	 * @param dagFilesLocation
	 *            the location of the file
	 * @param log_name
	 *            the name of this job's log,it's important because of the time
	 *            information in it
	 * @param num_Job
	 *            the number of this job
	 * @param arguments
	 *            the arguments of this job,which will be used by the script
	 *            "idpl_data_transfer"
	 * @param requirements
	 *            the requirements of this job,which specify the machine to
	 *            execute the job
	 * @throws IOException
	 * @throws DocumentException
	 */
	public void genJobSub(String dagFilesLocation, String log_name,
			int num_Job, String arguments, String requirements)
			throws IOException, DocumentException {

		File job_submit = new File(dagFilesLocation + "submit." + num_Job);
		job_submit.createNewFile();

		ReadXML readxml = ReadXML.getInstance();
		String script_home = readxml.getSCRIPTHOME();
		String script_transfer = readxml.getSCRIPT_TRANSFER();
		String BUAA_httphome = readxml.getHTTPHOME("BUAA");

		FileWriter fw = new FileWriter(job_submit.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);

		bw.write("executable = " + script_home + script_transfer + "\n");
		bw.write("arguments = " + arguments + "\n");
		bw.write("universe = vanilla" + "\n");
		// bw.write("Iwd = " + BUAA_httphome + "\n");
		bw.write("log = " + BUAA_httphome + log_name + "\n");
		// bw.write("error = " + BUAA_httphome + log_name + "." + num_Job +
		// ".err"
		// + "\n");
		bw.write("requirements = " + requirements + "\n");
		bw.write("should_transfer_files=YES" + "\n");
		bw.write("when_to_transfer_output=ON_EXIT" + "\n");
		bw.write("transfer_output_files=\"\"" + "\n");
		bw.write("queue" + "\n");
		bw.close();
	}

}
