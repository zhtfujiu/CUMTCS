package com.cumtcs;

import java.io.FileNotFoundException;
import java.util.Map;

import com.data.ChangeIDName;
import com.data.FileService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GerenActivity extends Activity implements View.OnClickListener{

	private LinearLayout Geren_yuwoxiangguan,Geren_xiugaimima,Geren_guanyu,Geren_qiehuanID;
	private static TextView  User_Nicheng;
	private static TextView User_ID;
	private static boolean IsRemember=false;
	private static FileService fileService; 
	//用Intent给与我相关传UserID
//	private Bundle bundle=new Bundle();
	private Intent intent=new Intent();
	//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tab_geren);
		init_Geren();
	}
	public static String getUserID(){
		return User_ID.getText().toString();
	}
	public static String getUserNicheng(){
		return User_Nicheng.getText().toString();
	}
	public static void setUserNicheng(String str){
		User_Nicheng.setText(str);
	}
	public static void SetIsRemember(boolean flag){
		IsRemember=flag;
	}
	public void init_Geren(){
		//首先获取从MainActivity传递来的用户数据
		Bundle bundle=this.getIntent().getExtras();
		String [] arrStr=new String[2];
		arrStr=bundle.getStringArray("msgToGeren");
		//传给“与我相关”
		intent.setClass(GerenActivity.this, Yuwoxiangguan_Activity.class);
		bundle.putStringArray("msgToYuwoxiangguan", arrStr);
		intent.putExtras(bundle);		
		//
		User_Nicheng=(TextView) findViewById(R.id.User_Nicheng);
		User_ID=(TextView) findViewById(R.id.UserID);
		//用户信息考进TextView里
		User_ID.setText(arrStr[0]);
		User_Nicheng.setText(arrStr[1]);
		
		//
		Geren_yuwoxiangguan=(LinearLayout) findViewById(R.id.Yuwoxiangguan);
		Geren_xiugaimima=(LinearLayout) findViewById(R.id.Xiugaimima);
		Geren_guanyu=(LinearLayout) findViewById(R.id.Guanyu);
		Geren_qiehuanID=(LinearLayout) findViewById(R.id.QiehuanID);
		Geren_yuwoxiangguan.setOnClickListener(this);
		Geren_xiugaimima.setOnClickListener(this);
		Geren_guanyu.setOnClickListener(this);
		Geren_qiehuanID.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.Yuwoxiangguan:startActivity(intent);break;
		case R.id.Xiugaimima:startActivity(new Intent(this,Xiugaimima_Activity.class));break;
		case R.id.Guanyu:startActivity(new Intent(this,Guanyu_Activity.class));break;
		case R.id.QiehuanID:startActivity(new Intent(this,QiehuanIDActivity.class));break;
		}
	}
}
