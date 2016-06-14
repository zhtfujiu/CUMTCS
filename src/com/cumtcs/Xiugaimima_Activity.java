package com.cumtcs;

import java.io.FileNotFoundException;

import com.data.FileService;
import com.data.HttpThread_CheckPsw;
import com.data.HttpThread_ReceivePsw;
import com.data.HttpThread_Xiugaimima;
import com.data.HttpThread_infor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Xiugaimima_Activity extends Activity {

	private Button btn_xiugaimima_fanhui,xiugaimima_baocun;
	private EditText xiugaimima_nicheng_et,xiugaimima_yuanmima_et,xiugaimima_xinmima_et;
	private String xinnicheng,yuanmima,xinmima,userID;
	public static String tempMima;
	private FileService fileService;
	private String url;
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			String str_name=msg.obj.toString().split(":")[1];
			String str_psw=msg.obj.toString().split(":")[0];
			try {
				if(str_name.equals(fileService.getUserName("name.txt").get("name"))&&
						str_psw.equals(fileService.getUserPsw("psw.txt").get("password"))){
					GerenActivity.setUserNicheng(xinnicheng);
					Toast.makeText(Xiugaimima_Activity.this, "修改成功！", 3).show();
					Xiugaimima_Activity.this.finish();
				}else{
					Toast.makeText(Xiugaimima_Activity.this, "修改失败，请再试！", 3).show();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xiugaimima);
		init_View();
	}
	
	public void init_View() {
		url=getResources().getString(R.string.url)+"XiugaimimaServlet";
		btn_xiugaimima_fanhui = (Button) findViewById(R.id.btn_xiugaimima_fanhui);
		xiugaimima_baocun = (Button) findViewById(R.id.xiugaimima_baocun);
		xiugaimima_nicheng_et = (EditText) findViewById(R.id.xiugaimima_nicheng_et);
		xiugaimima_yuanmima_et = (EditText) findViewById(R.id.xiugaimima_yuanmima_et);
		xiugaimima_xinmima_et = (EditText) findViewById(R.id.xiugaimima_xinmima_et);
		btn_xiugaimima_fanhui.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ToGeren();
			}
		});
		xiugaimima_nicheng_et.addTextChangedListener(new TextWatcher() {			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {}			
			@Override
			public void afterTextChanged(Editable arg0) {
				xinnicheng=xiugaimima_nicheng_et.getText().toString();
			}
		});
		xiugaimima_yuanmima_et.addTextChangedListener(new TextWatcher() {			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {}			
			@Override
			public void afterTextChanged(Editable arg0) {
				yuanmima=xiugaimima_yuanmima_et.getText().toString();
			}
		});
		xiugaimima_xinmima_et.addTextChangedListener(new TextWatcher() {			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {}			
			@Override
			public void afterTextChanged(Editable arg0) {
				xinmima=xiugaimima_xinmima_et.getText().toString();
			}
		});
		xiugaimima_baocun.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				getNewInfo();
				JudgeAndChange();
			}
		});
	
		fileService = new FileService(this);
	}
	private void getNewInfo(){	
		if(xinnicheng==null&&xinmima==null&&yuanmima==null){
			Toast.makeText(this, "你特么有意思没，啥都不改就保存", Toast.LENGTH_SHORT).show();
		}
		if(xinnicheng==null&&xinmima!=null&&yuanmima!=null){
			try {
				userID=fileService.getUserName("name.txt").get("userID");
				xinnicheng=fileService.getUserName("name.txt").get("name");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			MD5 md51 = new MD5(xinmima);
			xinmima = md51.GetMD5Code().trim();// 密码用MD5加密，避免明文密码在网络中传输
			MD5 md52 = new MD5(yuanmima);
			yuanmima = md52.GetMD5Code().trim();
		}
		if(xinnicheng!=null&&xinmima==null&&yuanmima==null){
			try {
				userID=fileService.getUserName("name.txt").get("userID");
				xinmima=fileService.getUserPsw("psw.txt").get("password");
				yuanmima=xinmima;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	private void JudgeAndChange(){
		try {
			tempMima=fileService.getUserPsw("psw.txt").get("password");
//			Toast.makeText(Xiugaimima_Activity.this, tempMima+"\n"+yuanmima+"\n"+xinnicheng, 4).show();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//首先检测原密码是否与tempMima一致，这样才有改的权限
		if(tempMima.equals(yuanmima)){
			//做修改的操作
			//更改本地文件
			fileService.saveNameToRom("name.txt", userID, xinnicheng);
			fileService.savePswToRom("psw.txt", userID, xinmima);
			//更改服务器端数据库
			new HttpThread_Xiugaimima(this,userID, xinnicheng, xinmima,url,handler).start();
		}else{
			Toast.makeText(this, "原密码错误！无权限修改！", Toast.LENGTH_SHORT).show();
		}
	}
	private void ToGeren(){
		Xiugaimima_Activity.this.finish();
	}
	
}
