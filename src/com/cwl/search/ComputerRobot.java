package com.cwl.search;

import com.cwl.parents.Home;

public class ComputerRobot extends Home{

	public ComputerRobot(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}

	/**
	 * ��������
	 * http://cililian.me   						��������
	 * http://gaoqing.la    						�й�����
	 * http://www.dy2018.com/        				��Ӱ����
	 */
	
	public static void main(String[] args) {
		
		BFS.find("http://cililian.me");
		
	}

}
