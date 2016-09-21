package com.cwl.browser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.EditorKit;
import javax.swing.text.html.FormSubmitEvent;
import javax.swing.text.html.HTMLEditorKit;


public class WebBrowser extends JFrame implements HyperlinkListener,
		ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField jtf = new JTextField(40);
	private JEditorPane jep = new JEditorPane();
	private String index = "http://www.baidu.com";
	
	public WebBrowser() throws HeadlessException {
		// TODO Auto-generated constructor stub
	}

	public WebBrowser(GraphicsConfiguration gc) {
		super(gc);
		// TODO Auto-generated constructor stub
	}

	public WebBrowser(String title) throws HeadlessException {
		super(title);
		jtf.setText(index);
		jtf.setBackground(Color.WHITE);
		jtf.addActionListener(this);
		
		jep.setEditable(false);
		jep.setBackground(Color.WHITE);
		jep.addHyperlinkListener(this);
		
		
		//监听index属性被重新设置的事件
		jep.addPropertyChangeListener("index", new PropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				EditorKit kit = jep.getEditorKit();
				if (kit.getClass() == HTMLEditorKit.class) {
					((HTMLEditorKit)kit).setAutoFormSubmission(false);//按手工方式提交表单
				}
			}
		});
		try {
			jep.setPage(index);
		} catch(IOException e) {
			showError(index);
		}
		JScrollPane scrollPane = new JScrollPane(jep);
		Container container = getContentPane();
		container.add(jtf,BorderLayout.NORTH);
		container.add(scrollPane,BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	public void showError(String url) {
		jep.setContentType("text/html");
		jep.setText("<html>无法打开网页:"+url+"。输入的URL不合法，或者网页不存在。</html>");
	}

	public WebBrowser(String title, GraphicsConfiguration gc) {
		super(title, gc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			jep.setPage(jtf.getText());
		} catch (IOException e1) {
			showError(jtf.getText());
		}
	}

	/**
	 * 处理用户选择超级链接或者提交表单事件
	 */
	@Override
	public void hyperlinkUpdate(HyperlinkEvent e) {
		 if(e.getClass() == FormSubmitEvent.class) {//处理提交表单事件
			 FormSubmitEvent fevt = (FormSubmitEvent) e;
			 URL url = fevt.getURL();//获取URL
			 String method = fevt.getMethod().toString();//获取请求方式
			 String data = fevt.getData();//获取表单数据
			 
			 if(method.equals("GET")) {
				 try {
					jep.setPage(url.toString()+"?"+data);
					jtf.setText(url.toString()+"?"+data);
				} catch (IOException e1) {
					showError(e.getURL().toString());
				}
			 } else if(method.equals("POST")) {
				 try {
					URLConnection uc = url.openConnection();
					//发送HTTP响应正文
					uc.setDoOutput(true);
					OutputStreamWriter out = new OutputStreamWriter(uc.getOutputStream());
					out.write(data);
					out.close();
					//接收HTTP响应正文
					InputStream in  = uc.getInputStream();
					ByteArrayOutputStream buffer = new ByteArrayOutputStream();
					byte[] buff = new byte[1024];
					int len = -1;
					while ((len = in.read(buff))!= -1) {
						buffer.write(buff,0,len);
					}
					in.close();
					jep.setText(new String(buffer.toByteArray()));
					jtf.setText(url.toString());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			 }
		 }else if(e.getEventType()==HyperlinkEvent.EventType.ACTIVATED){
			 try {
				jep.setPage(e.getURL());
				jtf.setText(e.getURL().toString());
			} catch (IOException e1) {
				showError(e.getURL().toString());
			}
		 }

	}

	public static void main(String[] args) {
		WebBrowser browser = new WebBrowser("CWL");

	}

}
