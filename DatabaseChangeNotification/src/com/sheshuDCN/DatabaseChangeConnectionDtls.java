package com.sheshuDCN;



import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import oracle.jdbc.OracleDriver;

public class DatabaseChangeConnectionDtls {

	private String user = null;
	private String password = null;
	private String url = null;
	private Properties conprop = null;

	public DatabaseChangeConnectionDtls(String user, String password, String url) {
		super();
		this.user = user;
		this.password = password;
		this.url = url;

		conprop = new Properties();
		conprop.setProperty("user", getUser());
		conprop.setProperty("password", getPassword());
	}

	public Connection getConnection() throws SQLException {

		OracleDriver dr = new OracleDriver();
		Connection conn = dr.connect(getUrl(), conprop);

		return conn;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
