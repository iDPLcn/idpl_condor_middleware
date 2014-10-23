package cn.edu.buaa.jsi.portal;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * 
 * @author yyq @{2014/10/23}
 *
 */
public class Transfer {

	private static Transfer transfer;
	private List<Experiment> expList;
	private String user;
	private String starttime;
	private String stoptime;
	private int id;
	ExecutorService pool;

	private Transfer() {
		// TODO:thread pool!
		this.pool = Executors.newCachedThreadPool();
	}

	public static synchronized Transfer getInstance() {
		if (transfer == null) {
			transfer = new Transfer();
		}
		return transfer;
	}

	public void submit() throws IOException {

		ExpAnalyze expAnalyze = new ExpAnalyze(expList, user, id, starttime,
				stoptime);
		Thread th = new Thread(expAnalyze);
		pool.execute(th);
	}

	public void setExpList(List<Experiment> expList) {
		this.expList = expList;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public void setStoptime(String stoptime) {
		this.stoptime = stoptime;
	}

	public void setId(int id) {
		this.id = id;
	}

}
