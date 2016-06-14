package com.Json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.Toast;

import com.data.DetailStoreItemAdapter;

public class HttpThread_StoreJson extends Thread {

	private String url;
	private Context context;
	private Handler handler;
	public HttpThread_StoreJson(Context context,String url,ListView listview,
			DetailStoreItemAdapter adapter,Handler handler) {
		this.context=context;
		this.url=url;
		this.handler=handler;		
	}
	public HttpThread_StoreJson(String url,Handler handler) {
		this.url=url;
		this.handler=handler;	
	}
	@Override
	public void run() {
		URL httpUrl;
		try {
			httpUrl=new URL(url);
			HttpURLConnection conn=(HttpURLConnection)httpUrl.openConnection();
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			BufferedReader reader =new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer sb=new StringBuffer();
			String str;
			while((str=reader.readLine())!=null){
				sb.append(str);
			}
//			data_list=parseJson(sb.toString());
			Message msg=new Message();
			msg.obj=sb.toString();
			handler.sendMessage(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private List<DetailStoreItem> parseJson(String json){
		JSONObject jsonobject;
		ArrayList<DetailStoreItem> details=new ArrayList<DetailStoreItem>();
		try {
			jsonobject = new JSONObject(json);
			int Key=jsonobject.getInt("Key");
			if(Key==1){
				JSONArray detials_jsonarray=jsonobject.getJSONArray("details");
				for(int i=0;i<detials_jsonarray.length();i++){
					DetailStoreItem detailDataObject=new DetailStoreItem();
					
					JSONObject user=detials_jsonarray.getJSONObject(i);
					String tv1=user.getString("tv_name");
					String tv2=user.getString("tv_sp");
					String tv3=user.getString("tv_fw");
					//自己改的，把获取的这些添到details里
					detailDataObject.setTv1(tv1);
					detailDataObject.setTv2(tv2);
					detailDataObject.setTv3(tv3);
					//
					details.add(detailDataObject);
				}
				return details;
			}else{
				//如果有错，toast显示
				Toast.makeText(context, "Error", 1).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
