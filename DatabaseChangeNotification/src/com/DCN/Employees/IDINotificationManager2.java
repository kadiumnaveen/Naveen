package com.DCN.Employees;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDriver;
import oracle.jdbc.OracleStatement;
import oracle.jdbc.dcn.DatabaseChangeRegistration;
import oracle.jdbc.dcn.QueryChangeDescription;
import oracle.jdbc.dcn.RowChangeDescription;
import oracle.jdbc.dcn.TableChangeDescription;
import oracle.sql.ROWID;



public class IDINotificationManager2 {
	
	  static String USERNAME= "sfmsbr";
	  static String PASSWORD= "sfms";
	  static String URL="jdbc:oracle:thin:@172.16.105.6:1521:NEWSFMS";
	  
	  
	  static DatabaseChangeRegistration dcr=null;
	  static OracleConnection conn = null;
	  public DBListener listener =null;
	  public DBListnershan listener2 =null;
	  static PreparedStatement stmt =null;
	  static ResultSet rs =null;
	  
	  static RowChangeDescription[] rcd=null;
	  static ROWID rowid=null;
	  static TableChangeDescription[] tcd =null;
	  static QueryChangeDescription[] qcd=null;
	  
	  public static ConcurrentHashMap<String,String> IDI_IFSC_values = new ConcurrentHashMap<String, String>();
	  
	public static void main(String[] args) throws Exception {
		IDINotificationManager2 idin = new IDINotificationManager2();
		if(dcr!=null) {
			idin.destroy();
		}
		idin.startListening();
	}
	
	public void startListening() throws Exception {
		conn=connect();
		Properties prop = new Properties();
	    prop.setProperty(OracleConnection.DCN_NOTIFY_ROWIDS,"true");
	    prop.setProperty(OracleConnection.DCN_QUERY_CHANGE_NOTIFICATION,"true");
	    DatabaseChangeRegistration dcr = conn.registerDatabaseChangeNotification(prop);
	    
	    try {
	    	
	    	
	    	
	    	Statement stmt = conn.createStatement();
	    	 ((OracleStatement)stmt).setDatabaseChangeRegistration(dcr);
	    	 
	         ResultSet rs = stmt.executeQuery("select IDI_IFSC_CODE from IDI_IFSC_DIR_INFO");
	         
	         listener= new DBListener(this);
		    	listener2= new DBListnershan(this);
		    	dcr.addListener(listener);
		    	dcr.addListener(listener2);
		    	
		    	
	         String[] tableNames = dcr.getTables();
	         for(int i=0;i<tableNames.length;i++) {
	         System.out.println(tableNames[i]+" is part of the registration.");
	         }
	         rs.close();
	         stmt.close();
	         System.out.println("DB listener started.........");
	    }catch(Exception ex) {
	    	if(conn!=null) {
	    		conn.unregisterDatabaseChangeNotification(dcr);
	    		conn.close();
	    	}
	    	throw ex;
	    }
	    
		
	}
	
	
	
	public void destroy() {
		try {
			dcr.removeListener(listener);
			conn.unregisterDatabaseChangeNotification(dcr);
			conn.close();
		System.out.println("DB Listener Destroyed.........");	
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static OracleConnection connect() throws SQLException
	  {
	    OracleDriver dr = new OracleDriver();
	    Properties prop = new Properties();
	    prop.setProperty("user",IDINotificationManager2.USERNAME);
	    prop.setProperty("password",IDINotificationManager2.PASSWORD);
	    return (OracleConnection)dr.connect(IDINotificationManager2.URL,prop);
	  }

}


