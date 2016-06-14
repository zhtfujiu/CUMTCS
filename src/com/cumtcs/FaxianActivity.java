package com.cumtcs;

import java.util.ArrayList;
import java.util.List;

import com.Json.DetailData;
import com.Json.HttpThread_Json;
import com.data.DetailAdapter;
import com.data.ReFalshListView;
import com.data.ReFalshListView.IReflashListener;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
public class FaxianActivity extends Activity implements IReflashListener{

	private List<DetailData> detail_list = new ArrayList<DetailData>();
	public DetailData detail_list_item;
	//
	private DetailAdapter detailadapter;
	private ReFalshListView listview;
	private String url = "http://192.168.109.1:8080//ServletForGETMethod/Json";
	private Handler handler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tab_faxian);		
		setData();
		showList(detail_list);		
	}

	public void showList(List<DetailData> detail_list) {
		if (detailadapter == null) {
			listview = (ReFalshListView) findViewById(R.id.faxian_listview);
			listview.setInterface(this);
			detailadapter = new DetailAdapter(this, detail_list);
			listview.setAdapter(detailadapter);
		} else {
			detailadapter.onDataChanged(detail_list);
		}		
	}

	public void getDataFromServer() {
		HttpThread_Json json = new HttpThread_Json(this, url, listview,
				detailadapter, handler, detail_list);
		json.start();
	}

	public String setSPText(String sp){
		String str=getResources().getString(R.string.ScoreOfShangpin);
		sp=String.format(str, sp);
		return sp;
	}
	public String setFWText(String fw){
		String str=getResources().getString(R.string.ScoreOfFuwu);
		fw=String.format(str, fw);
		return fw;
	}
	public void setData() {
		for (int i = 0; i < 2; i++) {
			DetailData item = new DetailData();
			item.setUserID("08133576");
			item.setLocationID("图书馆");
			item.setStoreID("C吧");
			item.setScoreOfShangpin("3");
			item.setScoreOfFuwu("3");			
			item.setTime("2016-04-04 16:58:14.0");
			item.setComment("哈哈哈哈哈");
			detail_list.add(item);
		}
	}

	@Override
	public void onReflash() {
		// 获取最新数据
		getDataFromServer();
		// 通知界面显示数据
		showList(detail_list);
		// 通知listview刷新完毕
		listview.reflashComplete();
	}

}
