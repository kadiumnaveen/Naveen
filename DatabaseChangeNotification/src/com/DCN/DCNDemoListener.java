package com.DCN;

import java.util.concurrent.Executor;

import com.DCN.workers.DBExecutor;
import com.DCN.workers.HandleDBRefresh;
import oracle.jdbc.dcn.DatabaseChangeEvent;

import oracle.jdbc.dcn.DatabaseChangeListener;
import oracle.jdbc.dcn.RowChangeDescription;
import oracle.jdbc.dcn.TableChangeDescription;
import oracle.jdbc.dcn.RowChangeDescription.RowOperation;

public class DCNDemoListener implements DatabaseChangeListener
{
  DBChangeNotification demo;
  DCNDemoListener(DBChangeNotification dem)
  {
    demo = dem;
  }
  public void onDatabaseChangeNotification(DatabaseChangeEvent e)
  { 
	  System.out.println("DCNListener: got an event (" + this + ")");
	  System.out.println(e.toString());
	  //TableChangeDescription [] tableChanges = e.getTableChangeDescription();
	  
	  System.out.println("Changed row id : "+e.getTableChangeDescription()[0].getRowChangeDescription()[0].getRowid().stringValue());
	  
	  /*System.out.println(tableChanges.length);

	  for (TableChangeDescription tableChange : tableChanges)
	  {
	    RowChangeDescription[] rcds = tableChange.getRowChangeDescription();
	    for (RowChangeDescription rcd : rcds)
	    {
	      System.out.println("Affected row -> " +
	                         rcd.getRowid().stringValue());
	      RowOperation ro = rcd.getRowOperation();
	    
	      Executor executor = new DBExecutor();
	      String rowid = rcd.getRowid().stringValue();
	    
	      if (ro.equals(RowOperation.INSERT))
	      {
	      
	        System.out.println("INSERT occurred");
	        executor.execute(new HandleDBRefresh(rowid, "insert"));
	      }
	      else if (ro.equals(RowOperation.UPDATE))
	      {
	        System.out.println("UPDATE occurred");
	        executor.execute(new HandleDBRefresh(rowid, "update"));
	      }
	      else if (ro.equals(RowOperation.DELETE))
	      {
	        System.out.println("DELETE occurred");
	        executor.execute(new HandleDBRefresh(rowid, "delete"));
	      }
	      else
	      {
	        System.out.println("Only handling INSERT/DELETE/UPDATE");
	      }
	    }
	  }*/

	  synchronized( demo )
	  {
	    demo.notify();
	  }
/*
    Thread t = Thread.currentThread();
    System.out.println("DCNDemoListener: got an event ("+this+" running on thread "+t+")");
    System.out.println(e.toString());
    synchronized( demo ){ 
    	System.out.println("lister function");
    	demo.notify();
    	}*/
  }
}
