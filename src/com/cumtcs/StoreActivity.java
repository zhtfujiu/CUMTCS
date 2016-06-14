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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StoreActivity extends Activity implements IReflashListener{

	/**
	 * 用Intent,你要用Intent启动另外一个Activity，
	 * 可以put***放数据
	 * 再在被启动的Activity上通过getIntent().get**();获取你要的数据
	 */
	private String storeName;
	private TextView tv_name,tv_sp,tv_fw;
	private String[] arrForReciveMsg=new String[3];
	//
	private ImageView iv;
	private Button btn_store_fanhui;
	private List<DetailData> detail_list = new ArrayList<DetailData>();
	public DetailData detail_list_item;
	//
	private DetailAdapter detailadapter;
	private ReFalshListView listview;
	private String url1 = "http://192.168.109.1:8080//ServletForGETMethod/JsonStoreDetail";
	private String url2="http://192.168.109.1:8080//ServletForGETMethod/setStoreName";
	//下面两个数组加载IMG用
	private String[] AllStoreName;
	private int[] arrayRescource=new int[28];
	//
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			detail_list=parseJson(msg.obj.toString());
			detailadapter = new DetailAdapter(StoreActivity.this, detail_list);
			listview.setAdapter(detailadapter);
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.store);
		Bundle bundle=this.getIntent().getExtras();
		arrForReciveMsg=bundle.getStringArray("message");		
		init_View();
	}
	private void init_View() {
		btn_store_fanhui=(Button) findViewById(R.id.btn_storeActivity_fanhui);
		tv_name=(TextView) findViewById(R.id.store_tv1);
		tv_sp=(TextView) findViewById(R.id.store_tv2_star);
		tv_fw=(TextView) findViewById(R.id.store_tv3_star);
		iv=(ImageView) findViewById(R.id.store_img);
		
		btn_store_fanhui.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				StoreActivity.this.finish();
			}
		});
		listview = (ReFalshListView) findViewById(R.id.store_listview);
		listview.setInterface(this);
		storeName=arrForReciveMsg[0];//暂定
		setImg(storeName,iv);
		tv_name.setText(storeName);
		//
		int i,j;
		i=Integer.parseInt(arrForReciveMsg[1]);
		j=Integer.parseInt(arrForReciveMsg[2]);
		String str2="",str3="";
		for(int num1=0;num1<i;num1++){
			str2+="★";
		}
		for(int num2=0;num2<j;num2++){
			str3+="★";
		}
		//
		tv_sp.setText(str2);
		tv_fw.setText(str3);
		new HttpThread_infor(url2,storeName,url1,handler).start();
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
		HttpThread_infor httpInfor=new HttpThread_infor(url2,storeName,url1,handler);
		httpInfor.start();
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
				Toast.makeText(StoreActivity.this, "Error", 1).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void setImg(String str,ImageView iv){
		setArrayRes();
		AllStoreName=getResources().getStringArray(R.array.AllStoreName);
		for(int i=0;i<AllStoreName.length;i++){
			if(str.equals(AllStoreName[i])){
				iv.setImageResource(arrayRescource[i]);
			}
		}
	}
	private void setArrayRes(){
		arrayRescource[0]=R.drawable.p0;
		arrayRescource[1]=R.drawable.p1;
		arrayRescource[2]=R.drawable.p2;
		arrayRescource[3]=R.drawable.p3;
		arrayRescource[4]=R.drawable.p4;
		arrayRescource[5]=R.drawable.p5;
		arrayRescource[6]=R.drawable.p6;
		arrayRescource[7]=R.drawable.p7;
		arrayRescource[8]=R.drawable.p8;
		arrayRescource[9]=R.drawable.p9;
		arrayRescource[10]=R.drawable.p10;
		arrayRescource[11]=R.drawable.p11;
		arrayRescource[12]=R.drawable.p12;
		arrayRescource[13]=R.drawable.p13;
		arrayRescource[14]=R.drawable.p14;
		arrayRescource[15]=R.drawable.p15;
		arrayRescource[16]=R.drawable.p16;
		arrayRescource[17]=R.drawable.p17;
		arrayRescource[18]=R.drawable.p18;
		arrayRescource[19]=R.drawable.p19;
		arrayRescource[20]=R.drawable.p20;
		arrayRescource[21]=R.drawable.p21;
		arrayRescource[22]=R.drawable.p22;
		arrayRescource[23]=R.drawable.p23;
		arrayRescource[24]=R.drawable.p24;
		arrayRescource[25]=R.drawable.p25;
		arrayRescource[26]=R.drawable.p26;
		arrayRescource[27]=R.drawable.p27;
	}
}
