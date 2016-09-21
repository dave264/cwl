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
 * ��ȡ�����ļ�
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
	 * ���췽����ʼ��
	 * @param filePath
	 * @param source
	 */
	public Reader(String filePath,int index) {
		Reader.filePath = filePath;
		Reader.index = index;
		Tools.setPath(filePath);
	}
	
	/**
	 * ��Դ״̬��
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
		if("�콾".equals(source)) {
			return 1;
		}
		if("����ͨ".equals(source)) {
			return 2;
		}
		if("������Ϣ��".equals(source)) {
			return 3;
		}
		if("�й�������".equals(source)) {
			return 4;
		}
		if("�����������".equals(source)) {
			return 5;
		}
		if("������".equals(source)) {
			return 6;
		}
		if("��ͨ��".equals(source)) {
			return 7;
		}
		if("�����".equals(source)) {
			return 8;
		}
		if("51�ֻ���".equals(source)) {
			return 9;
		}
		if("����ͨ".equals(source)) {
			return 10;
		}
		if("�ְ�����".equals(source)) {
			return 11;
		}
		if("�����й�".equals(source)) {
			return 12;
		}
		if("�ǻۻ���".equals(source)) {
			return 13;
		}
		if("QQ".equals(source)) {
			return 14;
		}
		if("����QQ".equals(source)) {
			return 15;
		}
		if("56315".equals(source)) {
			return 16;
		}
		if("�л������".equals(source)) {
			return 17;
		}
		if("��������".equals(source)) {
			return 18;
		}
		if("��һ����".equals(source)) {
			return 19;
		}
		if("ƽ������".equals(source)) {
			return 20;
		}
		if("˳������".equals(source)) {
			return 21;
		}
		if("����ͨ".equals(source)) {
			return 22;
		}
		if("����".equals(source)) {
			return 23;
		}
		if("�˰�".equals(source)) {
			return 24;
		}
		if("���߻��˸�".equals(source)) {
			return 25;
		}
		if("�����".equals(source)) {
			return 26;
		}
		if("������".equals(source)) {
			return 27;
		}
		if("·�ݱ�".equals(source)) {
			return 28;
		}
		if("������".equals(source)) {
			return 29;
		}
		return 0;
	}
	
	/**
	 * ͨ��״̬���ȡ��Դ
	 * @param state
	 * @return
	 */
	public String getSource(int state) {
		
		String source = "";
		if(state == 0) {
			source = "����";
		}
		if(state ==1 ) {
			source = "�콾";
		}
		if(state ==2) {
			source = "����ͨ";
		}
		if(state == 3) {
			source = "������Ϣ��";
		}
		if(state == 4) {
			source = "�й�������";
		}
		if(state == 5) {
			source = "�����������";
		}
		if(state == 6) {
			source = "������";
		}
		if(state == 7) {
			source = "��ͨ��";
		}
		if(state == 8) {
			source = "�����";
		}
		if(state == 9) {
			source = "51�ֻ���";
		}
		if(state == 10) {
			source = "����ͨ";
		}
		if(state == 11) {
			source = "�ְ�����";
		}
		if(state == 12) {
			source = "�����й�";
		}
		if(state == 13) {
			source = "�ǻۻ���";
		}
		if(state == 14) {
			source = "QQ";
		}
		if(state == 15) {
			source = "����QQ";
		}
		if(state == 16) {
			source = "56315";
		}
		if(state == 17) {
			source = "�л������";
		}
		if(state == 18) {
			source = "��������";
		}
		if(state == 19) {
			source = "��һ����";
		}
		if(state == 20) {
			source = "ƽ������";
		}
		if(state == 21) {
			source = "˳������";
		}
		if(state == 22) {
			source = "����ͨ";
		}
		if(state == 23) {
			source = "����";
		}
		if(state == 24) {
			source = "�˰�";
		}
		if(state == 25) {
			source = "���߻��˸�";
		}
		if(state == 26) {
			source = "�����";
		}
		if(state == 27) {
			source = "������";
		}
		if(state == 28) {
			source = "·�ݱ�";
		}
		if(state == 29) {
			source = "������";
		}
		return source;
	}
	
	/**
	 * ������Դ
	 * @param state   ��Դ״̬��
	 * @param s       ��������
	 */
	public void choose(int state,String s) {
		
		List<Map<String,String>> data = null;
		switch(state) {
			case 1:
				data = TianJiao.parse(s, "�콾");
				break;
			case 2:
				data = TianXiaTong.parse(s, "����ͨ");
				break;
			case 3:
				data = WuLiuXinXi.parse(s, "������Ϣ��");
				break;
			case 4:
				data = HuoYunWang.parse(s,"�й�������");
				break;
			case 5:
				data = ChuangBang.parse(s, "�����������");
				break;
			case 6:
				data = YiWuLiu.parse(s, "������");
				break;
			case 7:
				data = WuTongWang.parse(s, "��ͨ��");
				break;
			case 8:
				data = YiPeiHuo.parse(s, "�����");
				break;
			case 9:
				data = Phone51.parse(s, "51�ֻ���");
				break;
			case 10:
				data = TeYunTong.parse(s, "����ͨ");
				break;
			case 11:
				data = LinAnWuLiu.parse(s, "�ְ�����");
				break;
			case 12:
				data = WuLiuZhongGuo.parse(s, "�����й�");
				break;
			case 13:
				data = IntelligentTruck.parse(s, "�ǻۻ���");
				break;
			case 14://QQ
				data = null;
				break;
			case 15://����QQ
				data = null;
				break;
			case 16:
				data = _56315.parse(s, "56315");
				break;
			case 17://�л������
				data = null;
				break;
			case 18://��������
				data = null;
				break;
			case 19:
				data = FirstLogistics.parse(s, "��һ����");
				break;
			case 20://ƽ������
				data = null;
				break;
			case 21://˳������
				data = null;
				break;
			case 22://����ͨ
				data = null;
				break;
			case 23://����
				data = null;
				break;
			case 24:
				data = Transport.parse(s, "�˰�");
				break;
			case 25:
				data = WirelessPort.parse(s, "���߻��˸�");
				break;
			case 26:
				data = EasyPicking.parse(s, "�����");
				break;
			case 27:
				data = WanJiWuLiu.parse(s, "������");
				break;
			case 28:
				data = LuJieBao.parse(s, "·�ݱ�");
				break;
			case 29:
				data = HuaYunWang.parse(s, "������");
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
	 * ��ȡ�����ļ�
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
		//��ȡ�����ļ�
		while ((s=buf.readLine())!=null) {
			
			i++;
			total = i;
			System.out.println("��"+i+"��"+s);
			int state = state(s);//������Դ����״̬��
			source = this.getSource(state);
			DAOFactory.getIFileDAOInstance(Tools.getPath("��־")+"������־��"+source+"��.txt").FileOutputStream("��"+i+"��"+s+"\r\n", true);
			try{
				choose(state,s);//ѡ�������Դ
			} catch(Exception e) {
				DAOFactory.getIFileDAOInstance("ϵͳ�����쳣.txt").FileOutputStream("��"+i+"��"+s+"\r\n"+e+"\r\n", true);
				System.out.println(e);
				Tools.sleep(3333);
			}
			
		}
		System.out.println("-------------��--��--��--��--!--------------");
	}
	
	
}
