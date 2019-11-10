package com.hub;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDriver;

public class Process {
	
	NotifyObject ntfyObj = new NotifyObject("MapValue","");
	private ConcurrentHashMap<String,NotifyObject> cache = null;
	private HashMap<String,ConcurrentHashMap<String,NotifyObject>> tcache=null;
	
	
	public static OracleConnection connect() throws SQLException
	  {
	    OracleDriver dr = new OracleDriver();
	    Properties prop = new Properties();
	    prop.setProperty("user",DCNRegistrtion.USERNAME);
	    prop.setProperty("password",DCNRegistrtion.PASSWORD);
	    return (OracleConnection)dr.connect(DCNRegistrtion.URL,prop);
	  }

}
