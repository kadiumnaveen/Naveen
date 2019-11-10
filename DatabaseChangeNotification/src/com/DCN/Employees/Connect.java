package com.DCN.Employees;
import java.sql.*; 
  
public class Connect  
{ 
    public static void main(String args[]) 
    { 
	String IDI_IFSC_CODE ="SUMA0123564";
	
 String IDI_BANK_CODE="SUMA";
        try
        { 
            Class.forName("oracle.jdbc.driver.OracleDriver"); 
              
            // Establishing Connection 
            Connection con = DriverManager.getConnection( 
             "jdbc:oracle:thin:@172.16.105.6:1521:NEWSFMS", "sfmsbr", "sfms");
            Statement stmt = con.createStatement();			 
  // Inserting data in database 
            String q1 = "insert into IDI_IFSC_DIR_INFO values('" +IDI_IFSC_CODE +"', '" +IDI_BANK_CODE+ "',0,	065076	,1,	23,	'ABHYUDAYA COOPERATIVE BANK LIMITED',	'KURLA ,W',	'MUMBAI',	'SHOP NO. 1 AND 2, SANGHAVI PALACE, BELGRAMI ROAD, KURLA W, MUMBAI - 400070',	'SYSTEM'	,1,	400065076,	'GREATER BOMBAY',	'MAHARASHTRA',	22	,26503002	,'01-SEP-14') "; 
            int x = stmt.executeUpdate(q1); 
            
            con.close(); 
        } 
        catch(Exception e) 
        { 
            System.out.println(e); 
        } 
    } 
} 
