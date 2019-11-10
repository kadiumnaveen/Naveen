package sfmshub.dbChangeNotification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.dcn.DatabaseChangeEvent;
import oracle.jdbc.dcn.DatabaseChangeListener;
import oracle.jdbc.dcn.QueryChangeDescription;
import oracle.jdbc.dcn.RowChangeDescription;
import oracle.jdbc.dcn.RowChangeDescription.RowOperation;
import oracle.jdbc.dcn.TableChangeDescription;
import oracle.sql.ROWID;
import sfmshub.senderreceiver.SndrRcvrUtils;



public class DBListener implements DatabaseChangeListener
{
	static ResultSet rs =null;
	private RowChangeDescription[] rcd=null;
	private ROWID rowid=null;
	private TableChangeDescription[] tcd =null;
	private QueryChangeDescription[] qcd=null;
	private HashMap<String, ConcurrentHashMap<String, NotifyObject>> tcacheMap = null;
	
	private HashMap<String, String[]> qrycolms=null;
	static PreparedStatement stmt =null;
	private OracleConnection conn=null;
	private String tableName=null;
	private String[] colums=null;
	private ResultSetMetaData rsmd=null;
//	public static ConcurrentHashMap<String,String> IDI_IFSC_values = new ConcurrentHashMap<String, String>();
	
  DBListener( HashMap<String, ConcurrentHashMap<String, NotifyObject>> tcache, HashMap<String, String[]> regQueries,OracleConnection Conn)
  {
	  this.tcacheMap = tcache;
	  this.qrycolms=regQueries;
	  this.conn=Conn;
	
  }
  public void onDatabaseChangeNotification(DatabaseChangeEvent dce) {
	  	try {
		qcd =dce.getQueryChangeDescription();
		for(int i=0;i<qcd.length;i++) {
			tcd=qcd[i].getTableChangeDescription();
			for(int j=0;j<tcd.length;j++) {
				tableName=tcd[0].getTableName();
				
				String[] tName =tableName.split("\\.");
				
				tableName=tName[1];
				System.out.println("tableName"+tableName);
				rcd=tcd[0].getRowChangeDescription();
				
				
				for(int k=0;k<rcd.length;k++) {
					rowid=rcd[0].getRowid();
					System.out.println("rowid"+rowid);
					
					RowOperation ro=rcd[0].getRowOperation();
			
					//ReloadMap=cacheMap.get(tableName);
					colums=qrycolms.get(tableName);
					String[] Keycolums = colums[0].split("#");
		    		String[] Valuescolums = colums[1].split("#");

		    		String qcols=Keycolums[0];
		    		for(String col:Keycolums) {
		    			if(col!=Keycolums[0]) {
		    				qcols=qcols+","+col;
System.out.println("Keycolums  "+qcols);
		    			}
		    		}
		    		for(String col:Valuescolums) {
		    			qcols=qcols+","+col;
System.out.println("Valuescolums  :"+qcols);
		    			}
		    		
		    		String Query="Select "+qcols+ " from "+tableName;
		    		System.out.println("Query in listner :"+Query);
		    		stmt=conn.prepareStatement(Query+" where ROWID='"+rowid+"'");
					rs=stmt.executeQuery();
					rsmd=rs.getMetaData();
					String mKey="";
					String mValue="";
					
					if(rs!=null) {
						while(rs.next()) {
							for(int l=1;l<=Keycolums.length;l++) {
								if(rsmd.getColumnTypeName(l).equals("VARCHAR2")||rsmd.getColumnTypeName(l).equals("VARCHAR")) {
									mKey=mKey+rs.getString(Keycolums[l-1]);
System.out.println("Keycolums while varchar  :"+mKey);
								}else if(rsmd.getColumnTypeName(l).equals("NUMBER")) {
									mKey=mKey+rs.getInt(Keycolums[l-1]);
								}
								
							}	
							for(int l=1;l<=Valuescolums.length;l++) {
								if(rsmd.getColumnTypeName(l).equals("VARCHAR2")||rsmd.getColumnTypeName(l).equals("VARCHAR")) {
									mValue=mValue+rs.getString(Valuescolums[l-1]);
System.out.println("Valuescolums while varchar  :"+mValue);
								}else if(rsmd.getColumnTypeName(l).equals("NUMBER")) {
									mValue=mValue+rs.getInt(Valuescolums[l-1]);
System.out.println("Valuescolums while number  :"+mValue);
									System.out.println(mValue);
								}
								
							}
							
							System.out.println("Row Operation performed is "+ro);
							
							if(ro.toString().equals("INSERT") || (ro.toString().equals(("UPDATE")))) {
								System.out.println("operation INSERT");
System.out.println("value"+mValue+"mKey"+mKey);
                                NotifyObject ntfyObj =new NotifyObject(mValue,rowid.toString());
								tcacheMap.get(tableName).put(mKey,ntfyObj);
System.out.println("cache map value and rowid value "+tcacheMap.get(tableName).get(mKey).getRowId()+"      ......."+tcacheMap.get(tableName).get(mKey).getMapValue());
							}	
							
							SndrRcvrUtils.reLoadCacheCheck();
						}

					}
if(ro.toString().equals("DELETE")) {
								System.out.println("operation DELETE");
								for (Entry<String, NotifyObject> entry :tcacheMap.get(tableName).entrySet()) 
								{ String key = entry.getKey().toString();
								if(tcacheMap.get(tableName).get(key).getRowId().equals(rowid)) {
									tcacheMap.get(tableName).remove(key);
									System.out.println("Deleted the Map with rowid = "+ tcacheMap.get(tableName).get(key).getRowId());
								}
								//System.out.println("key: " + key + " value: " + value); 
								}
							}
				}
			}
		}
}	catch(Exception ex) {
	ex.printStackTrace();
}
					

  }
  
  
  
  
  
  /*public static void deletecatche() {
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
			stmt=DCNRegistrtion.conn.prepareStatement("select IDI_IFSC_CODE,ROWID from IDI_IFSC_DIR_INFO where ROWID='"+rowid+"'");
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
			stmt=DCNRegistrtion.conn.prepareStatement("select IDI_IFSC_CODE,ROWID from IDI_IFSC_DIR_INFO where ROWID='"+rowid+"'");
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
	}*/
}

