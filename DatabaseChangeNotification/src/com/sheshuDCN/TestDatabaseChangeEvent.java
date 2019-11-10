package com.sheshuDCN;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleStatement;
import oracle.jdbc.dcn.DatabaseChangeRegistration;

public class TestDatabaseChangeEvent<T,K,P,D,E,F> {

	private Map<String, TableData<T,K,P,D,E,F>> allTab = null;
	private DatabaseChangeConnectionDtls conDetls = null;

	private Properties dcnProp = new Properties();

	public TestDatabaseChangeEvent(Map<String, TableData<T,K,P,D,E,F>> allTab, DatabaseChangeConnectionDtls conDetls) {
		super();
		this.allTab = allTab;
		this.conDetls = conDetls;
		dcnProp.setProperty(OracleConnection.DCN_NOTIFY_ROWIDS, "true");
	}

	public void enableDatabaseChangeEvents() {

		if (allTab != null) {
			Set<String> allTables = allTab.keySet();

			allTables.forEach(tablename -> {

				System.out.println(tablename);

				TableData<T,K,P,D,E,F> tableData = allTab.get(tablename);

				if (tableData != null) {

					String tableQuery = tableData.getQuery(tablename).toString();

					OracleConnection conn = null;
					DatabaseChangeRegistration dcr = null;
					Statement stmt = null;
					ResultSet rs = null;
					TestDatabaseListener<T,K,P,D,E,F> testDbListener = null;
					try {
						conn = (OracleConnection) conDetls.getConnection();
						dcr = conn.registerDatabaseChangeNotification(dcnProp);
						testDbListener = new TestDatabaseListener<T,K,P,D,E,F>(tableData, conDetls);
						dcr.addListener(testDbListener);
						stmt = conn.createStatement();
						((OracleStatement) stmt).setDatabaseChangeRegistration(dcr);
						rs = stmt.executeQuery(tableQuery);
						rs.close();
						stmt.close();

					} catch (SQLException e) {

						if (conn != null)
							try {
								conn.unregisterDatabaseChangeNotification(dcr);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}

						e.printStackTrace();
					} finally {
						try {
							System.out.println("Closing Connection");
							conn.close();
						} catch (Exception innerex) {
							innerex.printStackTrace();
						}
					}
				}

			});

		}

	}

	/*
	 * public static void main(String[] args) {
	 * 
	 * OracleDriver dr = new OracleDriver(); Properties prop = new Properties();
	 * prop.setProperty("user", "sfmsbr"); prop.setProperty("password", "sfms");
	 * 
	 * Properties prop1 = new Properties();
	 * prop1.setProperty(OracleConnection.DCN_NOTIFY_ROWIDS, "true"); //
	 * prop1.setProperty(OracleConnection.DCN_QUERY_CHANGE_NOTIFICATION, //
	 * "true");
	 * 
	 * OracleConnection conn = null; DatabaseChangeRegistration dcr = null;
	 * 
	 * Statement stmt = null; ResultSet rs = null; try { conn =
	 * (OracleConnection) dr.connect("jdbc:oracle:thin:@172.16.105.8:1521:SFMS",
	 * prop); dcr = conn.registerDatabaseChangeNotification(prop1);
	 * TestDatabaseListener testDbListener = new TestDatabaseListener();
	 * dcr.addListener(testDbListener); stmt = conn.createStatement();
	 * ((OracleStatement) stmt).setDatabaseChangeRegistration(dcr); rs =
	 * stmt.executeQuery("select * from IDI_IFSC_DIR_INFO"); String[] tableNames
	 * = dcr.getTables(); for (int i = 0; i < tableNames.length; i++)
	 * System.out.println(tableNames[i] + " is part of the registration.");
	 * rs.close(); stmt.close();
	 * 
	 * } catch (SQLException e1) {
	 * 
	 * if (conn != null) try { conn.unregisterDatabaseChangeNotification(dcr); }
	 * catch (SQLException e) { e.printStackTrace(); }
	 * 
	 * e1.printStackTrace(); } finally { try {
	 * System.out.println("Closing Connection"); conn.close(); } catch
	 * (Exception innerex) { innerex.printStackTrace(); } }
	 * 
	 * }
	 */

}
