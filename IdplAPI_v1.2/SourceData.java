package cn.edu.buaa.jsi.portal;

/**
 * 
 * @author yyq @{2014/10/23}
 *
 */
public class SourceData {
	public String node_name;
	public String source_dataDirectory;
	public int ddClusterID;
	public String user_time;

	/**
	 * Returns name of the node where the data belongs to.
	 * 
	 * @return name of the node where the data belongs to
	 */
	public String getNode_name() {
		return node_name;
	}

	/**
	 * Sets name of the node where the data belongs to.
	 * 
	 * @param node_name
	 *            name of the node where the data belongs to
	 */
	public void setNode_name(String node_name) {
		this.node_name = node_name;
	}

	/**
	 * Returns the whole directory of the data.
	 * 
	 * @return the whole directory of the data
	 */
	public String getSource_dataDirectory() {
		return source_dataDirectory;
	}

	/**
	 * Sets the directory of the data.
	 * 
	 * @param source_dataDirectory
	 *            the whole directory of the data
	 */
	public void setSource_dataDirectory(String source_dataDirectory) {
		this.source_dataDirectory = source_dataDirectory;
	}

	/**
	 * Returns the clusterId of condor job when generated source data.This
	 * number is also used as the name of data so it's necessary to record this.
	 * 
	 * @return the clusterId of condor job when generated source data
	 */
	public int getDdClusterID() {
		return ddClusterID;
	}

	/**
	 * Sets the clusterId of condor job when generated source data.
	 * 
	 * @param ddClusterID
	 *            the clusterId of condor job when generated source data
	 */
	public void setDdClusterID(int ddClusterID) {
		this.ddClusterID = ddClusterID;
	}

	/**
	 * Returns name of the folder where the data belongs to.The folder was named
	 * like "username_timestamp" to distinguish from the others.
	 * 
	 * @return name of the folder where the data belongs to
	 */
	public String getUser_time() {
		return user_time;
	}

	/**
	 * Sets name of the folder where the data belongs to.The folder was named
	 * like "username_timestamp" to distinguish from the others.
	 * 
	 * @param user_time
	 *            name of the folder where the data belongs to
	 */
	public void setUser_time(String user_time) {
		this.user_time = user_time;
	}

}
