package com.DCN.Employees;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.ibm.mq.MQC;
import com.ibm.mq.MQException;
import com.ibm.mq.MQQueueManager;



public class xaConnection {

	public static oracle.jdbc.xa.client.OracleXADataSource ds = null;
	private static MQQueueManager qMgr = null;
	private static Connection DBConnection = null;
	
	public static void main(String args[]) 
	{
		Hashtable<Object, Object> properties = new Hashtable<Object, Object>();
		properties.put(MQC.THREAD_AFFINITY, new Boolean(true));
		if (ds == null) {
		try {
			
		ds = new oracle.jdbc.xa.client.OracleXADataSource();
		ds.setUser("sfmsbr");
		ds.setPassword("sfms");
		ds.setURL("jdbc:oracle:thin:@172.16.105.6:1521:NEWSFMS");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		try {
			qMgr = new MQQueueManager("NAVNBRQM",properties);
			ds.getXAConnection();
			DBConnection=qMgr.getJDBCConnection(ds);
			Statement stmt = DBConnection.createStatement();
			stmt.execute("Insert into Workers_BANK values(13653,'asdffdgfg','asdf98439','osaijnjnsdjf')");
			
		} catch (MQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
