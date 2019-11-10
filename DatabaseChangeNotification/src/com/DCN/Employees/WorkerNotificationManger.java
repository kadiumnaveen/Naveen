package com.DCN.Employees;

import java.sql.SQLException;
import java.util.Map.Entry;
/*import java.util.ArrayList;
import java.util.List;*/
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDriver;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import oracle.jdbc.OracleStatement;
import oracle.jdbc.dcn.DatabaseChangeEvent;
import oracle.jdbc.dcn.DatabaseChangeListener;
//import oracle.jdbc.dcn.DatabaseChangeEvent;
//import oracle.jdbc.dcn.DatabaseChangeListener;
import oracle.jdbc.dcn.DatabaseChangeRegistration;
import oracle.jdbc.dcn.QueryChangeDescription;
import oracle.jdbc.dcn.RowChangeDescription;
import oracle.jdbc.dcn.TableChangeDescription;
import oracle.sql.ROWID;
import java.util.concurrent.ConcurrentHashMap;
import oracle.jdbc.dcn.RowChangeDescription.RowOperation;



public class WorkerNotificationManger {
	
	  static String USERNAME= "sfmsbr";
	  static String PASSWORD= "sfms";
	  static String URL="jdbc:oracle:thin:@172.16.105.6:1521:NEWSFMS";
	  
	  
	  static DatabaseChangeRegistration dcr=null;
	  static OracleConnection conn = null;
	  static DatabaseChangeListener listener =null;
	  static PreparedStatement stmt =null;
	  static ResultSet rs =null;
	  
	  static RowChangeDescription[] rcd=null;
	  static ROWID rowid=null;
	  static TableChangeDescription[] tcd =null;
	  static QueryChangeDescription[] qcd=null;
	  
	  public static ConcurrentHashMap<String,String> Worker_values = new ConcurrentHashMap<String, String>();
	  
	public static void main(String[] args) throws Exception {
		if(dcr!=null) {
			destroy();
		}
		startListening();
	}
	
	public static void startListening() throws Exception {
		conn=connect();
		Properties prop = new Properties();
	    prop.setProperty(OracleConnection.DCN_NOTIFY_ROWIDS,"true");
	    prop.setProperty(OracleConnection.DCN_QUERY_CHANGE_NOTIFICATION,"true");
	    DatabaseChangeRegistration dcr = conn.registerDatabaseChangeNotification(prop);
	    
	    try {
	    	listener= new DatabaseChangeListener() {
				
				@Override
				public void onDatabaseChangeNotification(DatabaseChangeEvent dce) {
					System.out.println("DCNListener: got an event (" + this + ")");
					System.out.println("DB Table updated");
					System.out.println(dce.toString());
					qcd =dce.getQueryChangeDescription();
				         tcd=qcd[0].getTableChangeDescription();
				         rcd=tcd[0].getRowChangeDescription();
								rowid=rcd[0].getRowid();
								System.out.println(rowid.stringValue());
								RowOperation ro=rcd[0].getRowOperation();
								
								System.out.println("Row Operation performer is "+ro);
								
								if(ro.toString().equals("INSERT")) {
									System.out.println("operation INSERT");
									loadcatche();	
								}else if(ro.toString().equals(("UPDATE"))){
									System.out.println("operation UPDATE");
									updatecatche();
								}else if(ro.toString().equals("DELETE")) {
									System.out.println("operation DELETE");
									deletecatche();
								}else {
									System.out.println("operation NOTHING");
								}
								
								
								
				
				}
			};
	    	dcr.addListener(listener);
	    	
	    	Statement stmt = conn.createStatement();
	    	 ((OracleStatement)stmt).setDatabaseChangeRegistration(dcr);
	         ResultSet rs = stmt.executeQuery("select * from Workers_BANK");
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
	
	public static void deletecatche() {
		try {
			//stmt=conn.prepareStatement("select WORKER_ID,ROWID from Workers_BANK where ROWID='"+rowid+"'");
			//rs=stmt.executeQuery();
	        //while(rs.next()) {
	        	Worker_values.remove(rowid.toString());
	        	System.out.println("Deleted data related to Worker_ID : "+rs.getString(1));
	        //}
	        printCatche();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				if(stmt!=null) {
					stmt.close();
				}	
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	protected static void updatecatche() {
		try {
			stmt=conn.prepareStatement("select WORKER_ID,ROWID from Workers_BANK where ROWID='"+rowid+"'");
			rs=stmt.executeQuery();
	        while(rs.next()) {
	        	Worker_values.put(rs.getString("ROWID"),rs.getString("WORKER_ID"));
	        	System.out.println("Update data related to Worker_ID : "+rs.getString(1));
	        }
	        printCatche();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				if(stmt!=null) {
					stmt.close();
				}	
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void loadcatche() {
		
		try {
			stmt=conn.prepareStatement("select WORKER_ID,ROWID from Workers_BANK where ROWID='"+rowid+"'");
			rs=stmt.executeQuery();
	        while(rs.next()) {
	        	Worker_values.putIfAbsent(rs.getString("ROWID"),rs.getString("WORKER_ID"));
	        	System.out.println("Update data related to Worker_ID : "+rs.getString(1));
	        }
	        printCatche();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				if(stmt!=null) {
					stmt.close();
				}	
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public  static void printCatche() {
		System.out.println("Printing all keys and values of ConcurrentHashMap"); 
		for (Entry<String, String> entry : Worker_values.entrySet()) 
		{ String key = entry.getKey().toString();
		String value = entry.getValue();
		System.out.println("key: " + key + " value: " + value); 
		}
	}
	
	
	
	public static void destroy() {
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
	    prop.setProperty("user",WorkerNotificationManger.USERNAME);
	    prop.setProperty("password",WorkerNotificationManger.PASSWORD);
	    return (OracleConnection)dr.connect(WorkerNotificationManger.URL,prop);
	  }

}

