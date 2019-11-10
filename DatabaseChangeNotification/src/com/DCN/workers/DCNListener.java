package com.DCN.workers;

import java.util.concurrent.Executor;

import oracle.jdbc.dcn.DatabaseChangeEvent;
import oracle.jdbc.dcn.DatabaseChangeListener;
import oracle.jdbc.dcn.RowChangeDescription;
import oracle.jdbc.dcn.RowChangeDescription.RowOperation;
import oracle.jdbc.dcn.TableChangeDescription;

public class DCNListener implements DatabaseChangeListener
{
WorkDCNRegister demo;
DCNListener(WorkDCNRegister dem)
{
  demo = dem;
}

public void onDatabaseChangeNotification
(DatabaseChangeEvent databaseChangeEvent)
{
  System.out.println("DCNListener: got an event (" + this + ")");
  System.out.println(databaseChangeEvent.toString());
  TableChangeDescription [] tableChanges =
    databaseChangeEvent.getTableChangeDescription();

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
  }

  synchronized( demo )
  {
    demo.notify();
  }

}
}
