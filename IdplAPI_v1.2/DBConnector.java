package cn.edu.buaa.jsi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import cn.edu.buaa.jsi.portal.ReadXML;

/**
 * 
 * @author yyq @{2014/10/23}
 *
 */
public class DBConnector {
	private static DBConnector dbconnector;
	private static String driver = "com.mysql.jdbc.Driver";
	private static Connection con = null;
	private static Statement stmt = null;
	private String db_address;
	private String db_user;
	private String db_passwd;

	/**
	 * The DBConnector class connects to database and executes sql command.
	 */
	private DBConnector() {
		try {
			ReadXML readxml = ReadXML.getInstance();
			db_address = readxml.getDBADDRESS();
			db_user = readxml.getDBUSER();
			db_passwd = readxml.getDBPASSWD();

			Class.forName(driver).newInstance();
			con = DriverManager.getConnection(db_address, db_user, db_passwd);
			stmt = con.createStatement();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Returns the unique instance of DBConnector.
	 * 
	 * @return the unique instance of DBConnector
	 */
	public static synchronized DBConnector getInstance() {
		if (dbconnector == null) {
			dbconnector = new DBConnector();
		}
		return dbconnector;
	}

	/**
	 * Executes a sql command.
	 * 
	 * @param sql
	 *            the statement
	 */
	public void execute(String sql) {
		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
