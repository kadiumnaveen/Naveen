package com.DCN.Employees;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import oracle.jdbc.dcn.DatabaseChangeEvent;

import oracle.jdbc.dcn.DatabaseChangeListener;
import oracle.jdbc.dcn.QueryChangeDescription;
import oracle.jdbc.dcn.RowChangeDescription;
import oracle.jdbc.dcn.TableChangeDescription;
import oracle.jdbc.dcn.RowChangeDescription.RowOperation;
import oracle.sql.ROWID;



public class DBListnershan implements DatabaseChangeListener
{
	static ResultSet rs =null;
	static RowChangeDescription[] rcd=null;
	static ROWID rowid=null;
	static TableChangeDescription[] tcd =null;
	static QueryChangeDescription[] qcd=null;
	IDINotificationManager2 demo;
	static PreparedStatement stmt =null;
	public static ConcurrentHashMap<String,String> IDI_IFSC_values = new ConcurrentHashMap<String, String>();
	
  DBListnershan(IDINotificationManager2 idiNotificationManager2)
  {
    demo = idiNotificationManager2;
  }
  public void onDatabaseChangeNotification(DatabaseChangeEvent dce) {
		System.out.println("-------------------------------------------------------------------------------");
		System.out.println("-------------------------------------------------------------------------------");
try {
		System.out.println("DCNListener: got an event (" + this + ")");
		//System.out.println("DB Table updated");
		System.out.println(dce.toString());
		qcd =dce.getQueryChangeDescription();
	         tcd=qcd[0].getTableChangeDescription();
	         rcd=tcd[0].getRowChangeDescription();
					rowid=rcd[0].getRowid();
					System.out.println(rowid.stringValue());
					RowOperation ro=rcd[0].getRowOperation();
					System.out.println("shanmukha rao");
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
					synchronized( demo )
					  {
					    demo.notify();
					  }
}catch(Exception ex) {
	ex.printStackTrace();
}
					

  }
  
  public static void deletecatche() {
		try {
			//stmt=conn.prepareStatement("select WORKER_ID,ROWID from Workers_BANK where ROWID='"+rowid+"'");
			//rs=stmt.executeQuery();
	        //while(rs.next()) {
			IDI_IFSC_values.remove(rowid.toString());
			System.out.println("Deleted data related to RowID : "+rowid.toString());
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
			System.out.println(rowid.toString());
			stmt=IDINotificationManager2.conn.prepareStatement("select IDI_IFSC_CODE,ROWID from IDI_IFSC_DIR_INFO where ROWID='"+rowid+"'");
			rs=stmt.executeQuery();
			if(rs!=null) {
	        while(rs.next()) {
	        	IDI_IFSC_values.put(rs.getString("ROWID"),rs.getString("IDI_IFSC_CODE"));
	        	System.out.println("Updated data related to IDI_IFSC_CODE : "+rs.getString(1));
	        }
		}else {
			System.out.println("resultset in null on updateing catche while modifying");
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
			System.out.println(rowid.toString());
			stmt=IDINotificationManager2.conn.prepareStatement("select IDI_IFSC_CODE,ROWID from IDI_IFSC_DIR_INFO where ROWID='"+rowid+"'");
			rs=stmt.executeQuery();
			if(rs!=null) {
	        while(rs.next()) {
	        	IDI_IFSC_values.putIfAbsent(rs.getString("ROWID"),rs.getString("IDI_IFSC_CODE"));
	        	System.out.println("Loaded the data related to IDI_IFSC_CODE : "+rs.getString(1));
	        }
			}else {
				System.out.println("resultset in null on loading catche while inserting");
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
		for (Entry<String, String> entry : IDI_IFSC_values.entrySet()) 
		{ String key = entry.getKey().toString();
		String value = entry.getValue();
		System.out.println("key: " + key + " value: " + value); 
		}
	}
}

