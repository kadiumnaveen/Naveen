package com.DCN.workers;

import java.util.concurrent.Executor;

public class DBExecutor implements Executor
{
public void execute(Runnable command)
{
  new Thread(command).run();
}
}
