package com.cumtcs;

import java.io.FileNotFoundException;
import java.util.Map;

import com.data.FileService;
import com.data.HttpThread_CheckPsw;
import com.data.HttpThread_ReceivePsw;
import com.data.HttpThread_infor;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class Denglu extends Activity {

	private static EditText denglu_et_name, denglu_et_password;
	private static Button btn_denglu,btn_to_zhuce;
	private static String userID, password_mingwen, password_miwen;
	private CheckBox checkbox;
	private boolean isChecked;
	private FileService fileService;
	private static boolean result_Name,result_Psw;
	public static String tempPsw;
	private String PswAndName[] =new String [2];
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			PswAndName=msg.obj.toString().split(":");
			if(password_miwen.equals(PswAndName[0])){
				//若密码一致，则把密码、昵称都暂存入本机
				result_Psw = fileService.savePswToRom("psw.txt", userID,password_miwen);	
				result_Name = fileService.saveNameToRom("name.txt", userID,PswAndName[1]);
				if(result_Psw&&result_Name){
					Toast.makeText(Denglu.this, "保存成功！\n欢迎登录！", Toast.LENGTH_SHORT).show();
				}
				startActivity(new Intent(Denglu.this,MainActivity.class));
				Denglu.this.finish();
			}else{
				Toast.makeText(Denglu.this, "用户名或密码错误！请核验！", Toast.LENGTH_SHORT).show();
			}
		};
	};
	/**
	 * 用这个flag确定是否直接登录
	 */
	private boolean FLAG_FromStart=false;//这个是从启动开始确定
	public boolean FLAG_FromGeren=false;//这个是个人页面的切换账号
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.denglu);
		init_Content();
		CheckFile();//确定是否直接登录
		if(FLAG_FromStart){
			startActivity(new Intent(Denglu.this,MainActivity.class));
			Denglu.this.finish();
		}else{
			Listener();			
		}
	}

	private void CheckFile(){
		Map<String,String> map=null;
		try {
			map=fileService.getUserName("name.txt");
			if(map.get("userID")!=null){
				FLAG_FromStart=true;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void init_Content() {
		denglu_et_name = (EditText) findViewById(R.id.denglu_et_name);
		denglu_et_password = (EditText) findViewById(R.id.denglu_et_password);
		btn_denglu = (Button) findViewById(R.id.btn_denglu);
		btn_to_zhuce=(Button) findViewById(R.id.btn_to_zhuce);
		checkbox = (CheckBox) findViewById(R.id.denglu_checkbox);
		checkbox.setSelected(true);
		fileService = new FileService(this);
	}

	private void Listener() {
		denglu_et_name.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				userID = denglu_et_name.getText().toString().trim();
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}

		});

		denglu_et_password.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				password_mingwen = denglu_et_password.getText().toString();
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}

		});

		btn_denglu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(password_mingwen==null||userID==null){
					Toast.makeText(Denglu.this, "用户名、密码均不能为空！", Toast.LENGTH_SHORT).show();
				}else{
					upLoadInfo();
				}				
			}
		});

		checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				isChecked = arg1;
			}
		});

		btn_to_zhuce.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(Denglu.this,Zhuce.class));
				Denglu.this.finish();
			}
		});
	}

	public void upLoadInfo(){
		// 采用密文方式核验密码
		MD5 md5 = new MD5(password_mingwen);
		password_miwen = md5.GetMD5Code().trim();// 密码用MD5加密，避免明文密码在网络中传输
		//
		HttpThread_CheckPsw checkPsw=new HttpThread_CheckPsw(this,userID,handler);
		checkPsw.start();
	}
}
