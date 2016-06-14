package com.data;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientThread extends Thread {
	private String url,userID,time;
	public HttpClientThread(String url,String userID,String time) {
		this.url=url;
		this.userID=userID;
		this.time=time;
	}
	
	private void doHttpPost(){
		//创建HttpClient对象
		HttpClient client=new DefaultHttpClient();
		//创建HttpPost对象
		HttpPost post=new HttpPost(url);
		//通过NameValuePair存储数据
		ArrayList<NameValuePair> list=new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("userID", userID));
		list.add(new BasicNameValuePair("time", time));
		try {
			//设置要发送的数据
			post.setEntity(new UrlEncodedFormEntity(list));
			HttpResponse response=client.execute(post);
			//判断类型
			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
				//去除服务器返回的数据
				String content=EntityUtils.toString(response.getEntity());
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void doHttpGet(){
		
	}
	
	@Override
	public void run() {
		doHttpPost();
	}
}
