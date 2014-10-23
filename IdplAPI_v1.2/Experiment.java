package cn.edu.buaa.jsi.portal;

/**
 * 
 * @author yyq @ {2014/10/21}
 *
 */
public class Experiment {
	public long exp_id;
	public String source;
	public String destination;
	public String FilesSize;
	public int FilesNumber;
	public int repeat;
	public String protocol;
	public String v4orv6;
	public String putorget;
	public int parallel;

	/**
	 * The Experiment class contains all information about an experiment of data
	 * placement:source,destination,http or ftp,ipv4 or ipv6,the size of data
	 * and so on.An Experiment class was instanced when user submit a job.
	 * 
	 * @param exp_id
	 *            the exp_id of the experiment
	 * @param source
	 *            the source of the data placement experiment
	 * @param destination
	 *            the destination of the data placement experiment
	 * @param protocol
	 *            http or ftp
	 * @param putorget
	 *            put or get
	 * @param v4orv6
	 *            ipv4 or ipv6
	 * @param FilesSize
	 *            size of one file
	 * @param FilesNumber
	 *            the number of files
	 * @param parallel
	 *            the parallel of this transfer,it must less than or equal to
	 *            FilesNumber
	 * @param repeat
	 *            how many times to repeat this experiment
	 */
	public Experiment(long exp_id, String source, String destination,
			String protocol, String putorget, String v4orv6, String FilesSize,
			int FilesNumber, int parallel, int repeat) {
		this.exp_id = exp_id;
		this.source = source;
		this.destination = destination;
		this.FilesSize = FilesSize;
		this.FilesNumber = FilesNumber;
		this.repeat = repeat;
		this.protocol = protocol;
		this.v4orv6 = v4orv6;
		this.putorget = putorget;
		this.parallel = parallel;
	}

	/**
	 * Returns exp_id of the experiment.
	 * 
	 * @return exp_id of the experiment
	 */
	public long getExp_id() {
		return exp_id;
	}

	/**
	 * Returns source name of the experiment.
	 * 
	 * @return source name of the experiment,BUAA,CNIC,WISC or UCSD
	 */
	public String getSource() {
		return source;
	}

	/**
	 * Returns destination name of the experiment.
	 * 
	 * @return destination name of the experiment,BUAA,CNIC,WISC or UCSD
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * Returns the size of one file in this experiment.
	 * 
	 * @return the size of one file in this experiment,like "1G"
	 */
	public String getFilesSize() {
		return FilesSize;
	}

	/**
	 * Returns the number of files in this experiment.
	 * 
	 * @return the number of files in this experiment
	 */
	public int getFilesNumber() {
		return FilesNumber;
	}

	/**
	 * Returns the number to repeat.
	 * 
	 * @return the number to repeat
	 */
	public int getRepeat() {
		return repeat;
	}

	/**
	 * Returns the protocol used in this experiment.
	 * 
	 * @return the protocol used in this experiment,like http or ftp
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * Returns "ipv4"or"ipv6" to be used in this experiment.
	 * 
	 * @return "ipv4"or"ipv6"
	 */
	public String getV4orv6() {
		return v4orv6;
	}

	/**
	 * Returns the direction:"put"or"get".
	 * 
	 * @return "put"or"get"
	 */
	public String getPutorget() {
		return putorget;
	}

	/**
	 * Returns the parallel in this experiment.
	 * 
	 * @return the number of parallel
	 */
	public int getParallel() {
		return parallel;
	}

}
