package com.cwl.parse.reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import com.cwl.factory.DAOFactory;
import com.cwl.parse.filter.Filter;
import com.cwl.parse.source.ChuangBang;
import com.cwl.parse.source.EasyPicking;
import com.cwl.parse.source.FirstLogistics;
import com.cwl.parse.source.HuaYunWang;
import com.cwl.parse.source.HuoYunWang;
import com.cwl.parse.source.IntelligentTruck;
import com.cwl.parse.source.LinAnWuLiu;
import com.cwl.parse.source.LuJieBao;
import com.cwl.parse.source.Phone51;
import com.cwl.parse.source.TeYunTong;
import com.cwl.parse.source.TianJiao;
import com.cwl.parse.source.TianXiaTong;
import com.cwl.parse.source.Transport;
import com.cwl.parse.source.WanJiWuLiu;
import com.cwl.parse.source.WirelessPort;
import com.cwl.parse.source.WuLiuXinXi;
import com.cwl.parse.source.WuLiuZhongGuo;
import com.cwl.parse.source.WuTongWang;
import com.cwl.parse.source.YiPeiHuo;
import com.cwl.parse.source.YiWuLiu;
import com.cwl.parse.source._56315;
import com.cwl.parse.tools.Tools;

/**
 * 读取解析文件
 * @author Mr.chen
 *
 */
public class Reader {
	public static String filePath = "";
	public static String source = "";
	public static int index = 0;
	public static List<Map<String,String>> list = null;
	public static long total = 0;
	public static long current = 0; 
	
	/**
	 * 构造方法初始化
	 * @param filePath
	 * @param source
	 */
	public Reader(String filePath,int index) {
		Reader.filePath = filePath;
		Reader.index = index;
		Tools.setPath(filePath);
	}
	
	/**
	 * 货源状态码
	 * @param s
	 * @return
	 */
	public int state(String s) {
		
		String source = "";
		try{
			source = s.split("[$]")[0].trim();
		} catch(Exception e) {
			return 0;
		}
		if("".equals(source)) {
			return 0;
		}
		if("天骄".equals(source)) {
			return 1;
		}
		if("天下通".equals(source)) {
			return 2;
		}
		if("物流信息网".equals(source)) {
			return 3;
		}
		if("中国货运网".equals(source)) {
			return 4;
		}
		if("创邦冷藏物流".equals(source)) {
			return 5;
		}
		if("易物流".equals(source)) {
			return 6;
		}
		if("物通网".equals(source)) {
			return 7;
		}
		if("易配货".equals(source)) {
			return 8;
		}
		if("51手机端".equals(source)) {
			return 9;
		}
		if("特运通".equals(source)) {
			return 10;
		}
		if("林安物流".equals(source)) {
			return 11;
		}
		if("物流中国".equals(source)) {
			return 12;
		}
		if("智慧货车".equals(source)) {
			return 13;
		}
		if("QQ".equals(source)) {
			return 14;
		}
		if("物流QQ".equals(source)) {
			return 15;
		}
		if("56315".equals(source)) {
			return 16;
		}
		if("中华配货网".equals(source)) {
			return 17;
		}
		if("独树物流".equals(source)) {
			return 18;
		}
		if("第一物流".equals(source)) {
			return 19;
		}
		if("平安物流".equals(source)) {
			return 20;
		}
		if("顺达天下".equals(source)) {
			return 21;
		}
		if("掌运通".equals(source)) {
			return 22;
		}
		if("车旺".equals(source)) {
			return 23;
		}
		if("运吧".equals(source)) {
			return 24;
		}
		if("无线货运港".equals(source)) {
			return 25;
		}
		if("配货易".equals(source)) {
			return 26;
		}
		if("万吉物流".equals(source)) {
			return 27;
		}
		if("路捷宝".equals(source)) {
			return 28;
		}
		if("华运网".equals(source)) {
			return 29;
		}
		return 0;
	}
	
	/**
	 * 通过状态码获取来源
	 * @param state
	 * @return
	 */
	public String getSource(int state) {
		
		String source = "";
		if(state == 0) {
			source = "其它";
		}
		if(state ==1 ) {
			source = "天骄";
		}
		if(state ==2) {
			source = "天下通";
		}
		if(state == 3) {
			source = "物流信息网";
		}
		if(state == 4) {
			source = "中国货运网";
		}
		if(state == 5) {
			source = "创邦冷藏物流";
		}
		if(state == 6) {
			source = "易物流";
		}
		if(state == 7) {
			source = "物通网";
		}
		if(state == 8) {
			source = "易配货";
		}
		if(state == 9) {
			source = "51手机端";
		}
		if(state == 10) {
			source = "特运通";
		}
		if(state == 11) {
			source = "林安物流";
		}
		if(state == 12) {
			source = "物流中国";
		}
		if(state == 13) {
			source = "智慧货车";
		}
		if(state == 14) {
			source = "QQ";
		}
		if(state == 15) {
			source = "物流QQ";
		}
		if(state == 16) {
			source = "56315";
		}
		if(state == 17) {
			source = "中华配货网";
		}
		if(state == 18) {
			source = "独树物流";
		}
		if(state == 19) {
			source = "第一物流";
		}
		if(state == 20) {
			source = "平安物流";
		}
		if(state == 21) {
			source = "顺达天下";
		}
		if(state == 22) {
			source = "掌运通";
		}
		if(state == 23) {
			source = "车旺";
		}
		if(state == 24) {
			source = "运吧";
		}
		if(state == 25) {
			source = "无线货运港";
		}
		if(state == 26) {
			source = "配货易";
		}
		if(state == 27) {
			source = "万吉物流";
		}
		if(state == 28) {
			source = "路捷宝";
		}
		if(state == 29) {
			source = "华运网";
		}
		return source;
	}
	
	/**
	 * 解析货源
	 * @param state   货源状态码
	 * @param s       解析数据
	 */
	public void choose(int state,String s) {
		
		List<Map<String,String>> data = null;
		switch(state) {
			case 1:
				data = TianJiao.parse(s, "天骄");
				break;
			case 2:
				data = TianXiaTong.parse(s, "天下通");
				break;
			case 3:
				data = WuLiuXinXi.parse(s, "物流信息网");
				break;
			case 4:
				data = HuoYunWang.parse(s,"中国货运网");
				break;
			case 5:
				data = ChuangBang.parse(s, "创邦冷藏物流");
				break;
			case 6:
				data = YiWuLiu.parse(s, "易物流");
				break;
			case 7:
				data = WuTongWang.parse(s, "物通网");
				break;
			case 8:
				data = YiPeiHuo.parse(s, "易配货");
				break;
			case 9:
				data = Phone51.parse(s, "51手机端");
				break;
			case 10:
				data = TeYunTong.parse(s, "特运通");
				break;
			case 11:
				data = LinAnWuLiu.parse(s, "林安物流");
				break;
			case 12:
				data = WuLiuZhongGuo.parse(s, "物流中国");
				break;
			case 13:
				data = IntelligentTruck.parse(s, "智慧货车");
				break;
			case 14://QQ
				data = null;
				break;
			case 15://物流QQ
				data = null;
				break;
			case 16:
				data = _56315.parse(s, "56315");
				break;
			case 17://中华配货网
				data = null;
				break;
			case 18://独树物流
				data = null;
				break;
			case 19:
				data = FirstLogistics.parse(s, "第一物流");
				break;
			case 20://平安物流
				data = null;
				break;
			case 21://顺达天下
				data = null;
				break;
			case 22://掌运通
				data = null;
				break;
			case 23://车旺
				data = null;
				break;
			case 24:
				data = Transport.parse(s, "运吧");
				break;
			case 25:
				data = WirelessPort.parse(s, "无线货运港");
				break;
			case 26:
				data = EasyPicking.parse(s, "配货易");
				break;
			case 27:
				data = WanJiWuLiu.parse(s, "万吉物流");
				break;
			case 28:
				data = LuJieBao.parse(s, "路捷宝");
				break;
			case 29:
				data = HuaYunWang.parse(s, "华运网");
				break;
			default:
				break;
		}
		list = Filter.filter(list, data);
		try{
			current = list.size();
		} catch(Exception e) {
			current =0;
		}
		
	}
	
	/**
	 * 读取解析文件
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public void reader() throws Exception{
		
		InputStreamReader isr = null;
		FileInputStream fis = null;
		BufferedReader buf = null;
	
		fis = new FileInputStream(filePath);	
		isr = new InputStreamReader(fis, "UTF-8");
	 
		buf = new BufferedReader(isr);
		String s = "";
		int i=0;
		//读取解析文件
		while ((s=buf.readLine())!=null) {
			
			i++;
			total = i;
			System.out.println("【"+i+"】"+s);
			int state = state(s);//解析货源所处状态码
			source = this.getSource(state);
			DAOFactory.getIFileDAOInstance(Tools.getPath("日志")+"运行日志【"+source+"】.txt").FileOutputStream("【"+i+"】"+s+"\r\n", true);
			try{
				choose(state,s);//选择解析货源
			} catch(Exception e) {
				DAOFactory.getIFileDAOInstance("系统致命异常.txt").FileOutputStream("【"+i+"】"+s+"\r\n"+e+"\r\n", true);
				System.out.println(e);
				Tools.sleep(3333);
			}
			
		}
		System.out.println("-------------解--析--完--成--!--------------");
	}
	
	
}
