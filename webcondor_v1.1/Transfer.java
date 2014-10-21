package webcondor;

public class Transfer {

	private long id;
	private String source;
	private String destination;
	private String user;
	private String FilesSize;
	private int FilesNumber;
	private int repeat;
	private String protocol;
	private String v4orv6;
	private String putorget;
	private int parallel;
	private String starttime;
	private String stoptime;

	public Transfer(long id,String user,String source,String destination,String size,int number,int repeat,int parallel,
			String protocol,String putorget,String v4orv6,String starttime,String stoptime){
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.user = user;
		this.FilesSize = size;
		this.FilesNumber = number;
		this.repeat = repeat;
		this.protocol = protocol;
		this.v4orv6 = v4orv6;
		this.putorget = putorget;
		this.parallel = parallel;
		this.starttime = starttime;
		this.stoptime = stoptime;
		
	}
	public void submit(){
		DataTransfer dt = new DataTransfer(id,user,source,destination,FilesSize,FilesNumber,repeat,parallel,protocol,putorget,v4orv6,starttime,stoptime);
		Thread th = new Thread(dt);
		th.start();
	}
}
