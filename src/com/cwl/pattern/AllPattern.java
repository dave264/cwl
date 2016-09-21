package com.cwl.pattern;

import java.util.regex.Pattern;

public class AllPattern {
	
	/**
	 * 编码格式正则
	 * charset=.*(?=[\"]?)
	 */
	public static Pattern charset = Pattern
			.compile("charset=.*(?=[\\\"]?)");
	
	/**
	 * html 页面 meta
	 * <((meta)|(META)).*? charset=.*>
	 */
	public static Pattern meta = Pattern
			.compile("<((meta)|(META)).*? charset=.*>");

}
