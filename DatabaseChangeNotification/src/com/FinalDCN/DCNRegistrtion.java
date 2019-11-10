package sfmshub.dbChangeNotification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.sheshuDCN.DatabaseChangeConnectionDtls;
import com.sheshuDCN.TableData;
import com.sheshuDCN.TestDatabaseListener;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleStatement;
import oracle.jdbc.dcn.DatabaseChangeRegistration;



public class DCNRegistrtion <T,K,P,D,E,F> {

	private Map<String, TableData<T,K,P,D,E,F>> allTab = null;
	private DatabaseChangeConnectionDtls conDetls = null;

	private Properties dcnProp = new Properties();

	public DCNRegistrtion(Map<String, TableData<T,K,P,D,E,F>> allTab, DatabaseChangeConnectionDtls conDetls) {
		super();
		this.allTab = allTab;
		this.conDetls = conDetls;
		dcnProp.setProperty(OracleConnection.DCN_NOTIFY_ROWIDS, "true");
	}
	
	public void startListening() throws Exception {if (allTab != null) {
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
	
	
	
/*	public static OracleConnection connect() throws SQLException
	  {
	    OracleDriver dr = new OracleDriver();
	    Properties prop = new Properties();
	    prop.setProperty("user",DCNRegistrtion.USERNAME);
	    prop.setProperty("password",DCNRegistrtion.PASSWORD);
	    return (OracleConnection)dr.connect(DCNRegistrtion.URL,prop);
	  }*/

}


