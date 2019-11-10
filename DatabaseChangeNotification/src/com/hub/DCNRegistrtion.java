package sfmshub.dbChangeNotification;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleStatement;
import oracle.jdbc.dcn.DatabaseChangeRegistration;



public class DCNRegistrtion {
	
	private HashMap<String,ConcurrentHashMap<String,NotifyObject>> tcache = null;
	private DatabaseChangeRegistration dcr=null;
	private OracleConnection conn = null;
	public DBListener listener =null;
	private HashMap<String,String[]> regQueries=null;
/*	  static RowChangeDescription[] rcd=null;
	  static ROWID rowid=null;
	  static TableChangeDescription[] tcd =null;
	  static QueryChangeDescription[] qcd=null;*/
/*	  public static ConcurrentHashMap<String,String> IDI_IFSC_values = new ConcurrentHashMap<String, String>();
*/
	
	public DCNRegistrtion(OracleConnection con,HashMap<String,ConcurrentHashMap<String,NotifyObject>> TCache,HashMap<String,String[]> RegQueries) {
		super();
		this.tcache = TCache;
		this.conn=con;
		this.regQueries=RegQueries;
	}
	
	public void startListening() throws Exception {
		Properties prop = new Properties();
	    prop.setProperty(OracleConnection.DCN_NOTIFY_ROWIDS,"true");
	    prop.setProperty(OracleConnection.DCN_QUERY_CHANGE_NOTIFICATION,"true");
	    DatabaseChangeRegistration dcr = conn.registerDatabaseChangeNotification(prop);
	    
	    try {
	    	listener= new DBListener(tcache,regQueries,conn);
	    	dcr.addListener(listener);
	    	String Query=null;
	    	Statement stmt = conn.createStatement();
	    	((OracleStatement)stmt).setDatabaseChangeRegistration(dcr);
	    	/*Entry<String,HashMap<String,String>> entry : cache.entrySet()*/
	    	/*HashMap<String,String> columns = entry.getValue();
    		for(Entry<String,String> entry2 : columns.entrySet()) {
    			
    		}*/
	    	for(Entry<String,String[]> entry : regQueries.entrySet()) {
	    		
	    		String[] Colums= entry.getValue();
	    		
	    		String[] Keycolums = Colums[0].split("#");
	    		String[] Valuescolums = Colums[1].split("#");
	    		String qcols=Keycolums[0];
	    		for(String col:Keycolums) {
	    			if(col!=Keycolums[0]) {
	    			qcols=qcols+","+col;
	    			}
	    		}
	    		for(String col:Valuescolums) {
	    			qcols=qcols+","+col;
	    		}
	    		Query="Select "+qcols+ " from "+entry.getKey().toString();
	    		//stmt.addBatch("select MQD_MSG_SEQ from MQD_MQ_QUEUE_DEF");
//int[] s= stmt.executeBatch();
	    		System.out.println("Query"+Query);

		    	stmt.executeQuery(Query);
	    	}
	         String[] tableNames = dcr.getTables();
	         for(int i=0;i<tableNames.length;i++) {
	         System.out.println(tableNames[i]+" is part of the registration.");
	         }
	         stmt.close();
	         System.out.println("DB listener started.........");
	    }catch(Exception ex) {
	    	ex.printStackTrace();
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

/*	public static OracleConnection connect() throws SQLException
	  {
	    OracleDriver dr = new OracleDriver();
	    Properties prop = new Properties();
	    prop.setProperty("user",DCNRegistrtion.USERNAME);
	    prop.setProperty("password",DCNRegistrtion.PASSWORD);
	    return (OracleConnection)dr.connect(DCNRegistrtion.URL,prop);
	  }*/

}


