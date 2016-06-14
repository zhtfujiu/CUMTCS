package com.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;

import com.cumtcs.Denglu;
import com.cumtcs.R;
import com.cumtcs.Xiugaimima_Activity;

public class HttpThread_ReceivePsw extends Thread {

	private boolean flag=true;
	private String url;
	public HttpThread_ReceivePsw() {
	}
	public HttpThread_ReceivePsw(Context context,boolean flag2) {
		flag=flag2;
		url=context.getResources().getString(R.string.url)+"ReturnPswToAPP";
	}
	
	@Override
	public void run() {
		try {
			URL httpUrl=new URL(url);
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
			//
			if(flag){
				Xiugaimima_Activity.tempMima=sb.toString();
			}else{
				Denglu.tempPsw=sb.toString();
			}			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
