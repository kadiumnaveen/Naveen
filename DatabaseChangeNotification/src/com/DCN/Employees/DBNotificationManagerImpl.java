package com.DCN.Employees;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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

public class DBNotificationManagerImpl {
	
	  private static String USERNAME= "sfmsbr";
	  private static String PASSWORD= "sfms";
	  private static String URL="jdbc:oracle:thin:@172.16.105.6:1521:NEWSFMS";
	  
	  
	  static DatabaseChangeRegistration dcr=null;
	  static OracleConnection conn = null;
	  static DatabaseChangeListener listener =null;
	  static PreparedStatement stmt =null;
	  static ResultSet rs =null;
	  
	public static void main(String[] args) throws Exception {
		if(dcr!=null) {
			destroy();
		}
		startListening();
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
					// TODO Auto-generated method stub
					System.out.println("DCNListener: got an event (" + this + ")");
					System.out.println("DB Table updated");
					System.out.println(dce.toString());
					QueryChangeDescription[] qcd =dce.getQueryChangeDescription();
					for(int i=0;i<qcd.length;i++){
				         System.out.println(qcd[i]+" is QueryChangeDescription.");
				         System.out.println("lkfdjgf;jjsfdakgfdg;kjnb;kj");
				         }
					System.out.println(dce.getQueryChangeDescription());
					
					List<Employee> emps = getDBData();
					System.out.println("Size of the list : "+emps.size());
					for(Employee e:emps) {
						System.out.println("Employee_ID : "+e.getEmploy_ID()+" with first name "+e.getFirst_Name());
					}
				}
			};
	    	dcr.addListener(listener);
	    	
	    	Statement stmt = conn.createStatement();
	    	 ((OracleStatement)stmt).setDatabaseChangeRegistration(dcr);
	         ResultSet rs = stmt.executeQuery("select * from Employees");
	         int count =0;
	         while(rs.next()) {
	        	 count++;
	         }
	         System.out.println("No of Employees : "+count);
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
	
	public static ArrayList<Employee> getDBData() {
		// TODO Auto-generated method stub
		ArrayList<Employee> employees = new ArrayList<Employee>();
		
		try {
			stmt=conn.prepareStatement("select * from Employees");
			rs=stmt.executeQuery();
			int count =0;
	         while(rs.next()) {
	        	 Employee emp = new Employee();
	        	 count++;
	        	 emp.setEmploy_ID(rs.getInt(1));
	        	 emp.setFirst_Name(rs.getString(2));
	        	 emp.setLast_Name(rs.getString(3));
	        	 emp.setCity_Name(rs.getString(4));
	        	 employees.add(emp);
	         }
	         System.out.println("No of Employees : "+count);
	         count =0;
	         /*stmt=conn.prepareStatement("select * from Workers");
				rs=stmt.executeQuery();
		         while(rs.next()) {
		        	 count++;
		        	 
		         }
		         System.out.println("No of Workers : "+count);
		         */
	         
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
		return employees;
	}

	static OracleConnection connect() throws SQLException
	  {
	    OracleDriver dr = new OracleDriver();
	    Properties prop = new Properties();
	    prop.setProperty("user",DBNotificationManagerImpl.USERNAME);
	    prop.setProperty("password",DBNotificationManagerImpl.PASSWORD);
	    return (OracleConnection)dr.connect(DBNotificationManagerImpl.URL,prop);
	  }

}
