package com.DCN.workers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleStatement;
import oracle.jdbc.dcn.DatabaseChangeRegistration;

@SuppressWarnings("unchecked")
public class WorkDCNRegister
{
  private DBConnectionManager connMgr = null;
  //private final String depSQL = "select * from dept";
  private DatabaseChangeRegistration dcr = null;
  private static WorkDCNRegister instance = null;
  
  static
  {
    try
    {
      instance = new WorkDCNRegister();
    }
    catch (Exception e)
    {
      throw new RuntimeException("Error creating instance of DeptDCNRegister", e);
    }
  }
  
  private WorkDCNRegister () throws SQLException
  { 
    connMgr = DBConnectionManager.getInstance();
    OracleConnection conn = (OracleConnection) connMgr.getConnection();
    if (dcr == null)
    {
      registerDCN(conn);
    }
  }
  
  public static WorkDCNRegister getInstance()
  {
    return instance;
  }
    
  private void registerDCN (OracleConnection conn) throws SQLException
  {
    /*
     * register a listener for change notofication to be displayed to standard out
     * for testing purposes
     */  
    Properties props = new Properties();
    props.put(OracleConnection.DCN_NOTIFY_ROWIDS, "true");
    props.put(OracleConnection.NTF_QOS_RELIABLE, "false");
    props.setProperty(OracleConnection.DCN_BEST_EFFORT, "true");
    
    dcr = conn.registerDatabaseChangeNotification(props);
    
    // Add the dummy DCNListener which is DCNListener.java class
    DCNListener list = new DCNListener(this);
    dcr.addListener(list);

    Statement stmt = conn.createStatement();
    // Associate the statement with the registration.
    ((OracleStatement)stmt).setDatabaseChangeRegistration(dcr);
    ResultSet rs = stmt.executeQuery("select * from dept where 1 = 2");
    while (rs.next())
    {
      // do nothing no , need to just need query to register the DEPT table
    }

    String[] tableNames = dcr.getTables();
    for(int i=0; i < tableNames.length; i++)
    {
      System.out.println(tableNames[i]+" successfully registered.");
    }
    
    // close resources
    stmt.close();
    rs.close();

  }
  
  public void closeDCN (OracleConnection conn) throws SQLException
  {
    conn.unregisterDatabaseChangeNotification(dcr);
    conn.close();
  }
}
