package com.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.cumtcs.R;
import com.cumtcs.Zhuce;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class HttpThread_Zhuce_CheckID extends Thread {

	private Handler handler1,handler2;
	private Context context;
	private String userID,userNicheng,password,url1,url2,url3,url4;
	private boolean Flag=true;//flag为true是，可以进行注册功能。查找发现问题后，直接终止。
	public HttpThread_Zhuce_CheckID(Context context,String userID,
			String userNicheng,String password,Handler handler1,Handler handler2) {
		this.context=context;
		this.userID=userID;
		this.userNicheng=userNicheng;
		this.password=password;
		this.handler1=handler1;
		this.handler2=handler2;
		//setStoreName这个servlet里userID  storeID都可以。负责吧userID传上去
		url1=context.getResources().getString(R.string.url)+"setStoreName";
		url2=context.getResources().getString(R.string.url)+"returnCheckIDResult";
		url3=context.getResources().getString(R.string.url)+"imooc_servlet";
		url4=context.getResources().getString(R.string.url)+"imooc_servlet";
	}
	private void postID(){
		//将ID上传
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
			//为什么没有下面这6行代码就不能上传 ！？
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
	
	private void getID(){
		//获取检验结果
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
			if(sb.toString().equals("0")){
				Flag=false;
			}
			Message msg=new Message();
			msg.obj=sb.toString();
			handler1.sendMessage(msg);			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void postInfo(){
		//将ID、name、psw上传
		try {
			URL httpUrl=new URL(url3);
			HttpURLConnection conn=(HttpURLConnection) httpUrl.openConnection();
			conn.setRequestMethod("POST");
			conn.setReadTimeout(5000);
			//
			OutputStream out=conn.getOutputStream();
			String content=null;			
			content="userID="+userID+"&userNicheng="+userNicheng+"&password="+password;
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
	
	private void getInfo(){
		//再检验，看是否注册成功
		postID();
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
			handler2.sendMessage(msg);			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		postID();
		getID();
		if(Flag){
			postInfo();
			getInfo();
		}
		
	}
}
