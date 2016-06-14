package com.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.cumtcs.R;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class HttpThread_Xiugaimima extends Thread {

	private String userID,userNicheng,password,url;
	private Context context;
	private Handler handler;
	private String url1,url2;
	public HttpThread_Xiugaimima(Context context,String userID,String userNicheng,
			String password,String url,Handler handler) {
		this.context=context;
		this.userID=userID;
		this.userNicheng=userNicheng;
		this.password=password;
		this.url=url;
		this.handler=handler;
		url1=context.getResources().getString(R.string.url)+"getUserId";
		url2=context.getResources().getString(R.string.url)+"ReturnPswToAPP";
	}
	@Override
	public void run() {
		doPost();
		getInfo();
	}
	
	private void doPost(){
		try {
			URL httpUrl=new URL(url);
			HttpURLConnection conn=(HttpURLConnection) httpUrl.openConnection();
			conn.setRequestMethod("POST");
			conn.setReadTimeout(5000);
			//
			OutputStream out=conn.getOutputStream();
			String content;
			content="userID="+userID+"&userNicheng="+userNicheng+"&password="+password;
			out.write(content.getBytes());
			//
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

	private void getInfo(){
		doPost2();
		getPswFromServer();
	}

	private void doPost2(){
		try {
			URL httpUrl=new URL(url1);
			HttpURLConnection conn=(HttpURLConnection) httpUrl.openConnection();
			conn.setRequestMethod("POST");
			conn.setReadTimeout(5000);
			//
			OutputStream out=conn.getOutputStream();
			String content;
			content="userID="+userID;
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
	
	private void getPswFromServer(){
		try {
			URL httpUrl=new URL(url2);
			HttpURLConnection conn=(HttpURLConnection)httpUrl.openConnection();
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			//
			BufferedReader reader =new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer sb=new StringBuffer();
			String str;
			while((str=reader.readLine())!=null){
				sb.append(str);
			}
			Message msg=new Message();
			msg.obj=sb.toString();
			handler.sendMessage(msg);			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
