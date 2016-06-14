package com.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Handler;
import android.os.Message;

public class HttpThread_infor extends Thread {

	private String url2,url1,name,password,storeName,userID;
	private boolean flag=false;
	private int i=0;
	//
	private Handler handler;
	//
	public HttpThread_infor(String a,String b,String c,String d) {
		url2=a;
		userID=b;
		name=c;
		password=d;
	}
	public HttpThread_infor(String url,String storeName,String url1,Handler handler){
		this.url2=url;
		this.storeName=storeName;
		flag=true;
		this.url1=url1;
		this.handler=handler;
	}
	public HttpThread_infor(String url2,String userID,int i,String url1,Handler handler){
		this.url2=url2;
		this.userID=userID;
		flag=true;
		this.i=i;
		this.url1=url1;
		this.handler=handler;
	}
	private void doPost(){
		try {
			URL httpUrl=new URL(url2);
			HttpURLConnection conn=(HttpURLConnection) httpUrl.openConnection();
			conn.setRequestMethod("POST");
			conn.setReadTimeout(5000);
			//
			OutputStream out=conn.getOutputStream();
			String content=null;
			if(flag&&i!=1){
				content="storeName="+storeName;
			}
			if(flag&&i==1){
				content="userID="+userID;
			}
			if(!flag){
				content="userID="+userID+"&userNicheng="+name+"&password="+password;
			}					
			out.write(content.getBytes());
			BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer sb=new StringBuffer();
			String str;
			while((str=br.readLine())!=null){
				sb.append(str);
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void doGetData(){
		URL httpUrl;
		try {
			httpUrl=new URL(url1);
			HttpURLConnection conn=(HttpURLConnection)httpUrl.openConnection();
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			
			//¹«ÓÃµÄ
			BufferedReader reader =new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer sb=new StringBuffer();
			String str;
			while((str=reader.readLine())!=null){
				sb.append(str);
			}
			//
			Message msg=new Message();
			msg.obj=sb.toString();
			handler.sendMessage(msg);
						
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		doPost();
		if(flag){
			doGetData();
		}		
	}
	
	
}
