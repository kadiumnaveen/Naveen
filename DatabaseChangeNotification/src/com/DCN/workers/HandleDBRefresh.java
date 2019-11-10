package com.DCN.workers;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.CacheFactoryBuilder;
import com.tangosol.net.NamedCache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import support.au.coherence.dcn.server.CacheHelper;
import support.au.coherence.dcn.server.Dept;

public class HandleDBRefresh implements Runnable
{
private DBConnectionManager connMgr = null;
private String rowId;
private String action;

public HandleDBRefresh()
{
}

public HandleDBRefresh(String rowId, String action)
{
  super();
  this.rowId = rowId;
  this.action = action;
}

public void run()
{
  PreparedStatement stmt = null;
  ResultSet rset = null;
  Connection conn = null;

  try
  {
    connMgr = DBConnectionManager.getInstance();
    if (!action.toLowerCase().equals("delete"))
    {
      conn = connMgr.getConnection();
      stmt = conn.prepareStatement
        ("select rowid, deptno, dname from dept where rowid = ?");
      stmt.setString(1, rowId);
      rset = stmt.executeQuery();
      rset.next();
    }
  
    CacheHelper cacheHelper = CacheHelper.getInstance();
  
    // check if action
    if (action.toLowerCase().equals("delete"))
    {
      cacheHelper.removeEntry(rowId);
      System.out.println("Cache record delete");
    }
    else if (action.toLowerCase().equals("insert"))
    {
      // add to cache
      if (rset != null)
      {
        Dept d = new Dept(rset.getInt(2), rset.getString(3));
        cacheHelper.updateEntry(rset.getString(1), d);
        System.out.println("Cache updated with new record");
      }
    }
    else if (action.toLowerCase().equals("update"))
    {
      // refresh record in cache
      if (rset != null)
      {
        Dept d = new Dept(rset.getInt(2), rset.getString(3));
        cacheHelper.updateEntry(rset.getString(1), d);
        System.out.println("Cache record updated");
      }
    }
  }
  catch (Exception e)
  {
    throw new RuntimeException
      ("Error updating cache: rowid [" + rowId + "] " + e);
  }
  finally
  {
    if (rset != null)
    {
      try
      {
        rset.close();
      }
      catch (SQLException se)
      {
      }
    }

    if (stmt != null)
    {
      try
      {
        stmt.close();
      }
      catch (SQLException se)
      {
      }
    }

    if (conn != null)
    {
      try
      {
        connMgr.returnConnection(conn);
      }
      catch (SQLException se)
      {
      }
    }
  
  }

}

public void setRowId(String rowId)
{
  this.rowId = rowId;
}

public String getRowId()
{
  return rowId;
}

public void setAction(String action)
{
  this.action = action;
}

public String getAction()
{
  return action;
}
}
