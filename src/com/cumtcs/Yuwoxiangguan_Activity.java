package com.cumtcs;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.Json.DetailData;
import com.data.DetailAdapter;
import com.data.HttpThread_infor;
import com.data.ReFalshListView;
import com.data.ReFalshListView.IReflashListener;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Yuwoxiangguan_Activity extends Activity implements IReflashListener{

	private Button btn_yuwoxiangguan_fanhui;
	private static String userID;
	//
    private List<DetailData> detail_list = new ArrayList<DetailData>();
	public DetailData detail_list_item;
	//
	private DetailAdapter detailadapter;
	private ReFalshListView listview;
	private String url1 = "http://192.168.109.1:8080//ServletForGETMethod/JsonStoreDetail";
	private String url2="http://192.168.109.1:8080//ServletForGETMethod/setStoreName";
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			detail_list=parseJson(msg.obj.toString());
			detailadapter = new DetailAdapter(Yuwoxiangguan_Activity.this, detail_list);
			listview.setAdapter(detailadapter);
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yuwoxiangguan);
		init_View();
	}
	
	public void init_View(){
		Bundle bundle=this.getIntent().getExtras();
		userID=bundle.getStringArray("msgToYuwoxiangguan")[0];
		btn_yuwoxiangguan_fanhui=(Button) findViewById(R.id.btn_yuwoxiangguan_fanhui);
		btn_yuwoxiangguan_fanhui.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View arg0) {ToGeren();}
		});
		listview = (ReFalshListView) findViewById(R.id.yuwoxiangguan_reListview);
		listview.setInterface(this);
		HttpThread_infor httpInfor=new HttpThread_infor(url2,userID,1,url1,handler);
		httpInfor.start();
	}
	public void showList(List<DetailData> detail_list) {
		if (detailadapter == null) {
			listview = (ReFalshListView) findViewById(R.id.yuwoxiangguan_reListview);
			listview.setInterface(this);
			detailadapter = new DetailAdapter(this, detail_list);
			listview.setAdapter(detailadapter);
		} else {
			detailadapter.onDataChanged(detail_list);
		}
	}
	public void getDataFromServer() {
		HttpThread_infor httpInfor=new HttpThread_infor(url2,userID,1,url1,handler);
		httpInfor.start();
	}
	
	private void ToGeren(){
		Yuwoxiangguan_Activity.this.finish();
	}

	@Override
	public void onReflash() {
		// 获取最新数据
		getDataFromServer();
		// 通知界面显示数据
		showList(detail_list);
		// 通知listview刷新完毕
		listview.reflashComplete();
		Toast.makeText(this, "已获取最新数据", Toast.LENGTH_SHORT).show();
	}

	public  List<DetailData> parseJson(String json){
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
					String userNicheng=user.getString("userNicheng");
					String time=user.getString("time");
					String storeID=user.getString("storeID");
					String locationID=user.getString("locationID");
					String ScoreOfShangpin=user.getString("ScoreOfShangpin");
					String ScoreOfFuwu=user.getString("ScoreOfFuwu");
					String comment=user.getString("comment");				
					//自己改的，把获取的这些添到details里
					detailDataObject.setUserID(userID);
					detailDataObject.setUserNicheng(userNicheng);
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
				Toast.makeText(Yuwoxiangguan_Activity.this, "Error", 1).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
