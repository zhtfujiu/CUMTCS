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

import com.data.DetailAdapter;
import com.data.ReFalshListView;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class HttpThread_Json extends Thread {

	private String url;
	private Context context;
	private ReFalshListView reflashlistview;
	private DetailAdapter adapter;
	private Handler handler;
	public List<DetailData> old_data_list,data_list;
	
	
	public HttpThread_Json(Context context,String url,ReFalshListView reflashlistview,
			DetailAdapter adapter,Handler handler,List<DetailData> old_data_list) {
		this.context=context;
		this.url=url;
		this.reflashlistview=reflashlistview;
		this.adapter=adapter;
		this.handler=handler;
		this.old_data_list=old_data_list;
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
			data_list=parseJson(sb.toString());
			handler.post(new Runnable(){

				@Override
				public void run() {
//					data_list.addAll(old_data_list);//把原列表加在新的后面
//					data_list.remove(data_list.size()-1);
//					data_list.remove(data_list.size()-1);
					adapter.setData(data_list);
					reflashlistview.setAdapter(adapter);
				}
				
			});
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private List<DetailData> parseJson(String json){
		JSONObject jsonobject;
		ArrayList<DetailData> details=new ArrayList<DetailData>();
		try {
			jsonobject = new JSONObject(json);
			int Key=jsonobject.getInt("Key");
			if(Key==1){
				JSONArray detials_jsonarray=jsonobject.getJSONArray("details");
				for(int i=0;i<detials_jsonarray.length();i++){
					DetailData detailDataObject=new DetailData();
					
					JSONObject user=detials_jsonarray.getJSONObject(i);
					String userID=user.getString("userID");
					String time=user.getString("time");
					String storeID=user.getString("storeID");
					String locationID=user.getString("locationID");
					String ScoreOfShangpin=user.getString("ScoreOfShangpin");
					String ScoreOfFuwu=user.getString("ScoreOfFuwu");
					String comment=user.getString("comment");				
					//自己改的，把获取的这些添到details里
					detailDataObject.setUserID(userID);
					detailDataObject.setTime(time);
					detailDataObject.setStoreID(storeID);
					detailDataObject.setLocationID(locationID);
					detailDataObject.setScoreOfFuwu(ScoreOfFuwu);
					detailDataObject.setScoreOfShangpin(ScoreOfShangpin);					
					detailDataObject.setComment(comment);
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
