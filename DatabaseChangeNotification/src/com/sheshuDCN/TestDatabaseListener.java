package com.sheshuDCN;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.EnumSet;
import java.util.Map;

import oracle.jdbc.dcn.DatabaseChangeEvent;
import oracle.jdbc.dcn.DatabaseChangeListener;
import oracle.jdbc.dcn.QueryChangeDescription;
import oracle.jdbc.dcn.RowChangeDescription;
import oracle.jdbc.dcn.RowChangeDescription.RowOperation;
import oracle.jdbc.dcn.TableChangeDescription;
import oracle.jdbc.dcn.TableChangeDescription.TableOperation;
import oracle.sql.ROWID;

public class TestDatabaseListener<T, K, P, D, E, F> implements DatabaseChangeListener {

	private TableData<T, K, P, D, E, F> tableData = null;

	private Connection con = null;

	public TestDatabaseListener(TableData<T, K, P, D, E, F> tableData, DatabaseChangeConnectionDtls conDetls)
			throws SQLException {
		super();
		this.tableData = tableData;
		this.con = conDetls.getConnection();
	}

	@Override
	public void onDatabaseChangeNotification(DatabaseChangeEvent event) {

		System.out.println("Thread Name " + Thread.currentThread());

		QueryChangeDescription[] qChange = event.getQueryChangeDescription();
		TableChangeDescription[] tchange = qChange[0].getTableChangeDescription();

		System.out.println("tchange " + tchange.length);

		String tableName = null;
		EnumSet<TableOperation> tOperation = null;
		RowChangeDescription[] rowChangedsec = null;
		ROWID rid = null;
		RowOperation rOperation = null;

		for (int i = 0; i < tchange.length; i++) {

			tableName = tchange[i].getTableName();
			tOperation = tchange[i].getTableOperations();
			rowChangedsec = tchange[i].getRowChangeDescription();
		}

		System.out.println("tableName " + tableName);

		if (tOperation != null)
			System.out.println("tOperation " + tOperation.contains(TableOperation.UPDATE));

		if (rowChangedsec != null) {

			for (int j = 0; j < rowChangedsec.length; j++) {

				rid = rowChangedsec[j].getRowid();
				rOperation = rowChangedsec[j].getRowOperation();

			}

			System.out.println("rid " + rid.toString());
			System.out.println("rOperation " + rOperation.name());

			if (rOperation != null) {

				if (tableData != null) {

					Map<T, K> cacheData = tableData.getTcacheDataData();
					

					switch (rOperation.name()) {

					case "INSERT": {
						T mKey=null;
						K mValue=null;
						System.out.println("Insert Operation");
						
						T.class.getD
						
						cacheData.get
						String[] keyval=keyValue(tableName, rid);
						mKey=(T) keyval[0];
						mValue=(K) keyval[1];
						
						cacheData.put(mKey, mValue);
						
						break;
					}

					case "UPDATE": {
						T mKey=null;
						K mValue=null;
						System.out.println("Update Operation");
						
							String[] keyval=keyValue(tableName, rid);
						

						
						break;

					}

					case "DELETE": {

						System.out.println("Delete Operation");
						break;
					}

					default:
						System.out.println("Operation need not be Handled");
					}
				}
			}

		}

		/*
		 * tOperation.forEach(action -> {
		 * 
		 * System.out.println(action);
		 * 
		 * switch (action) {
		 * 
		 * case UPDATE: {
		 * 
		 * System.out.println("Update Operation"); break; }
		 * 
		 * case DELETE: {
		 * 
		 * System.out.println("Delete Operation"); break;
		 * 
		 * }
		 * 
		 * case INSERT: {
		 * 
		 * System.out.println("Insert Operation"); break; }
		 * 
		 * default: System.out.println("Operation need not be Handled"); }
		 * 
		 * });
		 */

		// tOperation.

	}
public String[] keyValue(String tableName,ROWID rid) {
	PreparedStatement stmt = null;
	ResultSet rs = null;
	ResultSetMetaData rsmd = null;
	String[] mapValues=new String[2];
	try {
		
		stmt = con.prepareStatement(tableData.getQuery(tableName) + " where ROWID='" + rid + "'");
		rs = stmt.executeQuery();
		rsmd = rs.getMetaData();
	} catch (SQLException e3) {
		// TODO Auto-generated catch block
		e3.printStackTrace();
	}


	String[] Keycolums = tableData.getKeyStr();
	String[] Valuecolums = tableData.getValStr();

	
//	String a=null;
	
/*	for (int i = 0; i < Keycolums.length; i++) {
		
		try {
			if(rsmd.getColumnLabel(i+1).equals(Keycolums[i]))
			{
				a=rsmd.getColumnClassName(i+1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}					

	}*/
	
	
	/*Class cls = Class.forName(a); 
    T obj = (T)cls.newInstance(); 
*/
/*	T mKey = null;
	Class<? extends Object> mValue = null;
*/
	// a =


	String key= null;
	String value= null;

	if (rs != null) {

		try {
			while (rs.next()) {

				int v = 1;
				for (int l = 1; l <= Keycolums.length; l++) {
					try {
						if (rsmd.getColumnName(l).equals(Keycolums[l - 1])) {
							if (rsmd.getColumnTypeName(l).equals("VARCHAR2")
									|| rsmd.getColumnTypeName(l).equals("VARCHAR")) {
								key = key + rs.getString(Keycolums[l - 1]);
								System.out.println("Keycolums while varchar  :" + key);
							} else if (rsmd.getColumnTypeName(l).equals("NUMBER")) {
								key = key + rs.getInt(Keycolums[l - 1]);
							}
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					v++;
					System.out.println("v value:: " + v);
				//	mValue = tableData.getTcacheDataData().ge;
				}
				for (int l = 1; l <= Valuecolums.length; l++) {
					
					try {
						System.out.println("for values" + rsmd.getColumnTypeName(v));
						if (rsmd.getColumnName(v).equals(Valuecolums[l - 1])) {

							if (rsmd.getColumnTypeName(v).equals("VARCHAR2")
									|| rsmd.getColumnTypeName(v).equals("VARCHAR")) {
								value = value + rs.getString(Valuecolums[l - 1]);
								System.out.println("Valuescolums while varchar  :" + value);
							} else if (rsmd.getColumnTypeName(v).equals("NUMBER")) {
								value = value + rs.getLong(Valuecolums[l - 1]);
								System.out.println("Valuescolums while number  :" + value);
								System.out.println(value);
							}
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					v++;
				}
			//	mKey=(T) key;
			//	mValue=(K) value;
				mapValues[0]= key;
				mapValues[1]=value;
				//cacheData.put(mKey, mValue);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return mapValues;
}
}
