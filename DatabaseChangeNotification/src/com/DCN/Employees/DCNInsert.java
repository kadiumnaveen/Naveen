package com.DCN.Employees;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.OracleConnection;

public class DCNInsert 
{ 
	public static Connection conn=null;
	public static Statement stmt=null;
	public static PreparedStatement prprdstmt=null;
	
	
    public static void main(String args[]) throws SQLException 
    {  
    	
    	int nbThreads =  Thread.getAllStackTraces().keySet().size();
		System.out.println("number of threads at starting : "+nbThreads);
    try
    	{ 
    	getconnnection();
    	Employee e1 = new Employee(5, "Navfghenfgfsg", "NarSDFSGum", "Hyderabad");
        
            if (InsertEmployee(e1)== 1) 
                System.out.println("inserted successfully"); 
            else
                System.out.println("insertion failed"); 
        } 
        catch(Exception ex) 
        { 
        	ex.printStackTrace();
            System.err.println(ex); 
        } finally {
        	conn.close(); 
        }
    } 
    
    
    public void updateEmployee(Employee ae,Employee ue){
    	
    }
    
    public static int InsertEmployee(Employee e){
    	int m= 0;
    	try {
    	  
        //Inserting data using SQL query 
        String sql = "insert into Employees values(?,?,?,?)";
        
        System.out.println(sql);
        
         prprdstmt = conn.prepareStatement(sql);
         prprdstmt.setInt(1, e.getEmploy_ID());
         prprdstmt.setString(2, e.getFirst_Name());
         prprdstmt.setString(3, e.getLast_Name());
         prprdstmt.setString(4, e.getCity_Name());

         m = prprdstmt.executeUpdate(); 
         
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	return m; 
    }
    
    public int  DeleteEmployee(Employee e){
		return 0;
    	
    }		
    
 static void getconnnection() throws SQLException {
    	try
        { 
    		
    		//Creating the connnection 
            String url = "jdbc:oracle:thin:@172.16.105.6:1521:NEWSFMS"; 
            String user = "sfmsbr"; 
            String pass = "sfms"; 
            
        	 DriverManager.registerDriver(new oracle.jdbc.OracleDriver()); 
        	  
             //Reference to connnection interface 
             conn =DriverManager.getConnection(url,user,pass); 
             conn.setAutoCommit(false);
             
             System.out.println(conn);
             
        }catch(Exception e) {
        	e.printStackTrace();
        }
    }
    
    
} 
