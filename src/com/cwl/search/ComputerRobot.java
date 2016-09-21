package com.cwl.search;

import com.cwl.parents.Home;

public class ComputerRobot extends Home{

	public ComputerRobot(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 网络爬虫
	 * http://cililian.me   						磁力链接
	 * http://gaoqing.la    						中国高清
	 * http://www.dy2018.com/        				电影天堂
	 */
	
	public static void main(String[] args) {
		
		BFS.find("http://cililian.me");
		
	}

}
