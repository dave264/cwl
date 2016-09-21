package com.cwl.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cwl.factory.DAOFactory;
import com.cwl.tool.Tools;

public class ThreadLogService implements Runnable {

	public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);//多线程并发
	public int index = 0;
	public int page =1;
	public int li =0;
	public String url = "";
	public String fileName = "";
	
	public ThreadLogService() {}
	
	public ThreadLogService(int index,int page,int li,String url,String fileName) {
		
		this.index = index;
		this.page = page;
		this.li = li;
		this.url = url;
		this.fileName = fileName;
		ThreadLogService log = new ThreadLogService();
		fixedThreadPool.execute(log);
	}

	@Override
	public void run() {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("index", index);
		map.put("page", page);
		map.put("li", li);
		map.put("url", url);
		String json = Tools.toJson(map);
		DAOFactory.getIFileDAOInstance(this.fileName).FileOutputStream(json+"\r\n", true);
		
	}

}
