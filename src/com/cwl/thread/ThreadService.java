package com.cwl.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import com.cwl.factory.DAOFactory;

public class ThreadService implements Callable<Integer> {

	public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);//多线程并发
	public static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();//可缓存线程池
	public static ExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(5);//定长线程池，支持定时及周期任务
	public static ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();//单线程化的线程池
	
	public String json = "";
	public String fileName = null;
	
	public ThreadService() {}
	
	public ThreadService(String json,String fileName) {
		
		this.json = json;
		this.fileName = fileName;
		
	}

	@Override
	public Integer call() throws Exception {
	
		DAOFactory.getIFileDAOInstance(fileName).FileOutputStream(json,true);
		
		return 1;
	}
	
	public void run(ThreadService ts) {
		
		FutureTask<Integer> futureTask = new  FutureTask<Integer>(ts);
//		fixedThreadPool.execute(futureTask);
		fixedThreadPool.submit(futureTask);
		
	}


}
