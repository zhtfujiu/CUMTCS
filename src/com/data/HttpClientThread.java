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
		//����HttpClient����
		HttpClient client=new DefaultHttpClient();
		//����HttpPost����
		HttpPost post=new HttpPost(url);
		//ͨ��NameValuePair�洢����
		ArrayList<NameValuePair> list=new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("userID", userID));
		list.add(new BasicNameValuePair("time", time));
		try {
			//����Ҫ���͵�����
			post.setEntity(new UrlEncodedFormEntity(list));
			HttpResponse response=client.execute(post);
			//�ж�����
			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
				//ȥ�����������ص�����
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
