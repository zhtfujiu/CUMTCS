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
	private boolean Flag=true;//flagΪtrue�ǣ����Խ���ע�Ṧ�ܡ����ҷ��������ֱ����ֹ��
	public HttpThread_Zhuce_CheckID(Context context,String userID,
			String userNicheng,String password,Handler handler1,Handler handler2) {
		this.context=context;
		this.userID=userID;
		this.userNicheng=userNicheng;
		this.password=password;
		this.handler1=handler1;
		this.handler2=handler2;
		//setStoreName���servlet��userID  storeID�����ԡ������userID����ȥ
		url1=context.getResources().getString(R.string.url)+"setStoreName";
		url2=context.getResources().getString(R.string.url)+"returnCheckIDResult";
		url3=context.getResources().getString(R.string.url)+"imooc_servlet";
		url4=context.getResources().getString(R.string.url)+"imooc_servlet";
	}
	private void postID(){
		//��ID�ϴ�
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
			//Ϊʲôû��������6�д���Ͳ����ϴ� ����
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
		//��ȡ������
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
		//��ID��name��psw�ϴ�
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
		//�ټ��飬���Ƿ�ע��ɹ�
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
