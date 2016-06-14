package com.cumtcs;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.Json.DetailStoreItem;
import com.Json.HttpThread_StoreJson;
import com.data.DetailStoreItemAdapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;


public class ShangjiaActivity extends Activity {

	private EditText edittext;
	private ImageButton imagebtn;
	private ListView listview;
	private static List<DetailStoreItem> detail_list=new ArrayList<DetailStoreItem>();
	private DetailStoreItemAdapter adapter;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			detail_list=parseJson(msg.obj.toString());
			adapter=new DetailStoreItemAdapter(ShangjiaActivity.this, R.layout.shangjia_list_item,detail_list);	
			listview.setAdapter(adapter);
			storeNameList=new String[detail_list.size()];
		};
	};
	private String url;
	//这些为检索功能服务
	private String[] storeNameList;
	private String strForSearch;
	private int[] NumOfNotIn;
	//以下为分类检索服务
	private LinearLayout lay1,lay2,lay3;
	//Activity之间传数据
	private Intent intent=new Intent();
	private Bundle bundle=new Bundle();
	private String[] msgToStore=new String[3];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tab_shangjia);
		//
		init_view();
		init_Search();
	}

	private void init_view(){
		edittext=(EditText) findViewById(R.id.shangjia_tab_detail_edittext);
		listview=(ListView) findViewById(R.id.shangjia_detail_listview);
		imagebtn=(ImageButton) findViewById(R.id.shangjia_tab_detail_imgbtn);
		lay1=(LinearLayout) findViewById(R.id.main_tab_shangjia_top1);
		lay2=(LinearLayout) findViewById(R.id.main_tab_shangjia_top2);
		lay3=(LinearLayout) findViewById(R.id.main_tab_shangjia_top3);
		layout_event();//顶部三个按钮的监听，来选择排序方式
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(ShangjiaActivity.this,"这是"+detail_list.get(arg2).getTv1(), 3).show();
//				startActivity(new Intent(ShangjiaActivity.this,StoreActivity.class));
				msgToStore[0]=detail_list.get(arg2).getTv1();
				msgToStore[1]=detail_list.get(arg2).getTv2();
				msgToStore[2]=detail_list.get(arg2).getTv3();				
				intent.setClass(ShangjiaActivity.this, StoreActivity.class);
				bundle.putStringArray("message",msgToStore );
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});				
		url=getResources().getString(R.string.url)+"ShangjiaServlet";
		new HttpThread_StoreJson(url,	handler).start();
	}
	private void init_Search(){
		edittext.addTextChangedListener(new TextWatcher() {			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {}			
			@Override
			public void afterTextChanged(Editable arg0) {
				strForSearch=edittext.getText().toString();
//				reSetListView();
				if(strForSearch!=null){
					imagebtn.setVisibility(View.VISIBLE);
					reNewListView();
				}else{
					imagebtn.setVisibility(View.GONE);					
				}	
							
			}
		});
	
		imagebtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				edittext.setText(null);
				reSetListView();
				imagebtn.setVisibility(View.GONE);
			}
		});
	}

	private void reSetListView(){
		HttpThread_StoreJson h=new HttpThread_StoreJson(url,	handler);
		h.start();
	}
	private void reNewListView(){
		int sum=0;
		CharSequence cs=strForSearch;
		for(int i=0;i<detail_list.size();i++){
			storeNameList[i]=detail_list.get(i).getTv1();
		}
		for(int i=0;i<storeNameList.length;i++){
			if(!storeNameList[i].contains(cs)){
				sum++;
			}			
		}
		NumOfNotIn=new int[sum];
		for(int k=0,k2=0;k<storeNameList.length;k++){
			if(!storeNameList[k].contains(cs)){
				NumOfNotIn[k2]=k;
				k2++;
			}
		}
		for(int j=0;j<sum;j++){
			detail_list.remove(NumOfNotIn[j]);
			for(int m=j+1;m<sum;m++){
				NumOfNotIn[m]--;
			}
		}
		adapter.notifyDataSetChanged();
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
				Toast.makeText(ShangjiaActivity.this, "Error", 1).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	 private void layout_event(){
		lay2.setBackgroundColor(Color.argb(0, 202, 225, 255));
		lay3.setBackgroundColor(Color.argb(0, 202, 225, 255));
		lay1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				changeStyle(1);
			}
		});
		lay2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				changeStyle(2);
			}
		});
		lay3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				changeStyle(3);
			}
		});
	 }
	 private void changeStyle(int i){
		 switch(i){
		case 1:
			lay1.setBackgroundColor(Color.argb(255, 202, 225, 255));
			lay2.setBackgroundColor(Color.argb(0, 202, 225, 255));
			lay3.setBackgroundColor(Color.argb(0, 202, 225, 255));
			url = getResources().getString(R.string.url) + "ShangjiaServlet";
			HttpThread_StoreJson h = new HttpThread_StoreJson(url, handler);
			h.start();
			 break;
		case 2:
			lay1.setBackgroundColor(Color.argb(0, 202, 225, 255));
			lay2.setBackgroundColor(Color.argb(255, 202, 225, 255));
			lay3.setBackgroundColor(Color.argb(0, 202, 225, 255));
			url = getResources().getString(R.string.url) + "ShangjiaServlet2";
			HttpThread_StoreJson h2 = new HttpThread_StoreJson(url, handler);
			h2.start();
			break;
		case 3:
			lay1.setBackgroundColor(Color.argb(0, 202, 225, 255));
			lay2.setBackgroundColor(Color.argb(0, 202, 225, 255));
			lay3.setBackgroundColor(Color.argb(255, 202, 225, 255));
			url = getResources().getString(R.string.url) + "ShangjiaServlet3";
			HttpThread_StoreJson h3 = new HttpThread_StoreJson(url, handler);
			h3.start();
			break;
		 }		 
	 }
}
