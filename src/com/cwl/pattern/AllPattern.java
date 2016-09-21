package com.cwl.pattern;

import java.util.regex.Pattern;

public class AllPattern {
	
	/**
	 * �����ʽ����
	 * charset=.*(?=[\"]?)
	 */
	public static Pattern charset = Pattern
			.compile("charset=.*(?=[\\\"]?)");
	
	/**
	 * html ҳ�� meta
	 * <((meta)|(META)).*? charset=.*>
	 */
	public static Pattern meta = Pattern
			.compile("<((meta)|(META)).*? charset=.*>");

}
