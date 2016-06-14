package com.cumtcs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import com.data.FileService;
import com.data.HttpThread_UpLoadPic;
import com.data.HttpThread_detail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

public class WoyaopingActivity extends Activity implements OnClickListener {

	private String userID,userNicheng;
	private Button woyaoping_fabu_btn;
	private EditText woyaoping_edittext;
	private static int REQ_1 = 1, REQ_2 = 2;
	private String mFilePath, currentDate,storeID,locationID,comment,
	url="http://192.168.109.1:8080//ServletForGETMethod/DetailServlet";
	private int ScoreOfShangpin,ScoreOfFuwu;
	//IsHaoping三个值，1 2 3分别对应好、一般、差。
	private ImageView woyaoping_imgView1;
	private SelectPicPopupWindow menuWindow;
	private Spinner woyaoping_spinner1,woyaoping_spinner2;
    private String []ArrayLocationName,ArrayStoreName;
    private ArrayAdapter<String> adapter;
    private OnClickListener itemsOnClick;
    private RatingBar ratingBar_1,ratingBar_2;
    private FileService fileService;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tab_woyaoping);
		initView();
	}

	private void initView() {
		fileService = new FileService(this);
		try {
			userID= fileService.getUserName("name.txt").get("userID");// 获取ID
			userNicheng=fileService.getUserName("name.txt").get("name");
		} catch (FileNotFoundException e) {
			Toast.makeText(this, "没找到文件", 3).show();
			e.printStackTrace();
		}
		woyaoping_edittext = (EditText) findViewById(R.id.woyaoping_et1);
		woyaoping_edittext.setText(null);	
		woyaoping_fabu_btn = (Button) findViewById(R.id.woyaoping_fabu_btn);
		woyaoping_fabu_btn.setOnClickListener(this);
		woyaoping_fabu_btn.setTextColor(Color.BLACK);
		woyaoping_imgView1 = (ImageView) findViewById(R.id.woyaoping_imgView1);
		woyaoping_imgView1.setOnClickListener(this);
		woyaoping_spinner1=(Spinner) findViewById(R.id.woyaoping_spinner1);
		woyaoping_spinner1.setAlpha(100);
		woyaoping_spinner2=(Spinner) findViewById(R.id.woyaoping_spinner2);
		woyaoping_spinner2.setAlpha(100);
		//
		ratingBar_1=(RatingBar) findViewById(R.id.woyaoping_ratingbar_huo);
		ratingBar_2=(RatingBar) findViewById(R.id.woyaoping_ratingbar_fuwu);
		setRatingBar();
		//
		ArrayLocationName=getResources().getStringArray(R.array.location);
		//给两个Spinner添加监听
		SetSpinner();
		
		// 用下面的函数获取SD卡的路径
		mFilePath = Environment.getExternalStorageDirectory().getPath();

		//
		woyaoping_edittext.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				comment=woyaoping_edittext.getText().toString();
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				comment=woyaoping_edittext.getText().toString();
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				comment=woyaoping_edittext.getText().toString();
			}
		});
	}

	public void SetSpinner(){
		//对1设置监听
		woyaoping_spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				switch(arg2){
				case 0:
					locationID=ArrayLocationName[0];
					ArrayStoreName=getResources().getStringArray(R.array.storeName1);break;
				case 1:
					locationID=ArrayLocationName[1];
					ArrayStoreName=getResources().getStringArray(R.array.storeName2);break;
				case 2:
					locationID=ArrayLocationName[2];
					ArrayStoreName=getResources().getStringArray(R.array.storeName3);break;
				case 3:
					locationID=ArrayLocationName[3];
					ArrayStoreName=getResources().getStringArray(R.array.storeName4);break;
				case 4:
					locationID=ArrayLocationName[4];
					ArrayStoreName=getResources().getStringArray(R.array.storeName5);break;
				case 5:
					locationID=ArrayLocationName[5];
					ArrayStoreName=getResources().getStringArray(R.array.storeName6);break;
				case 6:
					locationID=ArrayLocationName[6];
					ArrayStoreName=getResources().getStringArray(R.array.storeName7);break;	
				}
				// 对Spinner2设置Adapter
				// 建立Adapter并且绑定数据源
				adapter = new ArrayAdapter<String>(WoyaopingActivity.this, android.R.layout.simple_spinner_item,
						ArrayStoreName);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// 绑定 Adapter到控件
				woyaoping_spinner2.setAdapter(adapter);
				// 刷新
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		//对2进行监听
		woyaoping_spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				storeID=ArrayStoreName[arg2];				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	public void setRatingBar(){
		ratingBar_1.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) {
				ScoreOfShangpin=(int) arg1;
			}
		});
		ratingBar_2.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) {
				ScoreOfFuwu=(int) arg1;
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(mFilePath);
				Bitmap bitmap = BitmapFactory.decodeStream(fis);
				woyaoping_imgView1.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.woyaoping_imgView1:showAddpicMenu();break;
		case R.id.woyaoping_fabu_btn:{			
			if(comment!=null){
				currentDate=getCurrentData();
				//GerenActivity.getUserID()
				HttpThread_detail h=new HttpThread_detail(url,userID,userNicheng,
						locationID,storeID,comment,currentDate,ScoreOfShangpin,ScoreOfFuwu);
				h.start();
				Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this, "评论区不能为空", Toast.LENGTH_SHORT).show();
			}
			UpLoadComplete();
		}break;
		}
	}

	public String getCurrentData() {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy.MM.dd HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String currentDate = formatter.format(curDate);
		return currentDate;
	}

	public void showAddpicMenu() {
		itemsOnClick = new OnClickListener() {

			public void onClick(View v) {
				menuWindow.dismiss();
				switch (v.getId()) {
				case R.id.addpic_paizhao:{
					currentDate = getCurrentData();
					mFilePath += "/CUMTCS/" + currentDate + ".jpg";
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					// 用android.net的uri。将上面的SD卡路径指过来。
					Uri pic_uri = Uri.fromFile(new File(mFilePath));
					// 指定上面自定义 的保存位置
					intent.putExtra(MediaStore.EXTRA_OUTPUT, pic_uri);
					startActivityForResult(intent, REQ_2);
				}break;
				case R.id.addpic_benditupian:{
				}break;
				}
			}
		};
		
		// 实例化SelectPicPopupWindow
		menuWindow = new SelectPicPopupWindow(WoyaopingActivity.this,
				itemsOnClick);
		// 显示窗口
		menuWindow.showAtLocation(
				WoyaopingActivity.this.findViewById(R.id.main_view_woyaoping), Gravity.BOTTOM
						| Gravity.CENTER_HORIZONTAL, 0, 0);
		// 设置layout在PopupWindow中显示的位置
	}

	private void UpLoadComplete(){
		woyaoping_edittext.setText(null);	
		comment=null;
		woyaoping_spinner1.setSelection(0);
		woyaoping_spinner2.setSelection(0);
		ratingBar_1.setRating(0);
		ratingBar_2.setRating(0);
	}

}
