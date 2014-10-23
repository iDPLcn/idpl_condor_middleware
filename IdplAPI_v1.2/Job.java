package cn.edu.buaa.jsi.portal;

/**
 * 
 * @author yyq @{2014/10/23}
 *
 */
public class Job {
	public long id;
	public long exp_id;
	public int repeat_id;
	public String log_name;

	/**
	 * Returns id of the job.
	 * 
	 * @return id of the job
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets id of the job.
	 * 
	 * @param id
	 *            id of the job
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns exp_id of the job
	 * 
	 * @return exp_id of the job
	 */
	public long getExp_id() {
		return exp_id;
	}

	/**
	 * Sets exp_id of the job.
	 * 
	 * @param exp_id
	 *            exp_id of the job
	 */
	public void setExp_id(long exp_id) {
		this.exp_id = exp_id;
	}

	/**
	 * Returns repeat_id of the job.
	 * 
	 * @return repeat_id of the job
	 */
	public int getRepeat_id() {
		return repeat_id;
	}

	/**
	 * Sets repeat_id of the job.
	 * 
	 * @param repeat_id
	 *            repeat_id of the job
	 */
	public void setRepeat_id(int repeat_id) {
		this.repeat_id = repeat_id;
	}

	/**
	 * Returns log_name(the name of log) of the job.
	 * 
	 * @return log_name(the name of log) of the job
	 */
	public String getLog_name() {
		return log_name;
	}

	/**
	 * Sets log_name(the name of log) of the job.
	 * 
	 * @param log_name
	 *            the name of log
	 */
	public void setLog_name(String log_name) {
		this.log_name = log_name;
	}

}
