package com.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;

public class HttpThread_detail extends Thread {
	
	private String userID,userNicheng,storeID,locationID,comment,url,IsHaoping;
	private String time;
	private int ScoreOfShangpin,ScoreOfFuwu;
	private boolean flag=false;
	public HttpThread_detail(String url1,String userID1,String userNicheng1,String locationID1,
			String storeID1,String comment1,String time1,int ScoreOfShangpin1,int ScoreOfFuwu1){
		url=url1;
		userID=userID1;
		userNicheng=userNicheng1;
		locationID=locationID1;
		storeID=storeID1;
		comment=comment1;
		time=time1;
		ScoreOfShangpin=ScoreOfShangpin1;
		ScoreOfFuwu=ScoreOfFuwu1;
		
	}
	public HttpThread_detail(String url1,String userID1){
		url=url1;
		userID=userID1;
		flag=true;
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
			if(flag){
				content = "userID=" + userID;
			}else{
				content = "userID=" + userID + "&userNicheng="+userNicheng+"&locationID=" + locationID
						+ "&storeID=" + storeID + "&comment=" + comment + "&time="
						+ time+"&ScoreOfShangpin="+ScoreOfShangpin+"&ScoreOfFuwu="+ScoreOfFuwu;
			}
			
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
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		doPost();
	}
}
