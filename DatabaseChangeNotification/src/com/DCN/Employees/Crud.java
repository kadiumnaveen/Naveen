package com.DCN.Employees;
import java.sql.*; 
  
public class Crud
{ 
	 public static String IDI_IFSC_CODE ="SUMA0123564";
	 public static String IDI_BANK_CODE="SUMA";
	 public static Connection con;
	 public static Statement stmt;
	 
    public static void main(String args[]) throws SQLException 
    { 
 
    	try
        {
        con = DriverManager.getConnection("jdbc:oracle:thin:@172.16.105.6:1521:NEWSFMS", "sfmsbr", "sfms");
        stmt = con.createStatement();	
		Class.forName("oracle.jdbc.driver.OracleDriver"); 
            // Establishing Connection 
		int i = 0;
		while(i<5) {
			insertSQL("SUMA0123564","SUMA");
			Thread.sleep(100);
			updateSQL("NIKI0123564");
			Thread.sleep(100);
            deleteSQL("NIKI0123564");
            Thread.sleep(100);
            i++;
		}
            
           
        } 
        catch(Exception e) 
        { 
            System.out.println(e); 
        } finally {
        	try
            {
        	if (con!= null) {
        		con.close(); 
        	}
            }catch(Exception e) 
            { 
                System.out.println(e); 
            }
        	
        }
    }

	
	public static void insertSQL(String IDI_IFSC_CODE,String IDI_BANK_CODE){	 		 
			// Inserting data in database 
		String q1 = "insert into IDI_IFSC_DIR_INFO values('" +IDI_IFSC_CODE +"', '" +IDI_BANK_CODE+ "',0,	065076	,1,	23,	'ABHYUDAYA COOPERATIVE BANK LIMITED',	'KURLA ,W',	'MUMBAI',	'SHOP NO. 1 AND 2, SANGHAVI PALACE, BELGRAMI ROAD, KURLA W, MUMBAI - 400070',	'SYSTEM'	,1,	400065076,	'GREATER BOMBAY',	'MAHARASHTRA',	22	,26503002	,'01-SEP-14') "; 
            try {
				stmt.execute(q1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Succesfully Inserted");
	}
	
	public static  void updateSQL(String idi_ifsc_code) {
		 String q1 = "UPDATE IDI_IFSC_DIR_INFO  SET  IDI_IFSC_CODE ='"+ idi_ifsc_code+"' WHERE IDI_BANK_CODE = 'SUMA'";
         try {
			stmt.executeUpdate(q1);
	         System.out.println("Succesfully Updated");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static  void deleteSQL(String idi_ifsc_code){	
		String q1 = "DELETE from IDI_IFSC_DIR_INFO  WHERE IDI_IFSC_CODE ='"+idi_ifsc_code+"'";
        try {
			stmt.execute(q1);
			System.out.println("Succesfully Deleted");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
} 







 