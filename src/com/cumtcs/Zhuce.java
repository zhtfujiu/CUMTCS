package com.cumtcs;

import com.data.CheckPassword;
import com.data.CheckUserId;
import com.data.FileService;
import com.data.HttpThread_Zhuce_CheckID;
import com.data.HttpThread_infor;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Zhuce extends Activity {

	private static EditText zhuce_et_ID,zhuce_et_name,zhuce_et_password_1,
			zhuce_et_password_2;
	private static Button btn_zhuce;
	private static String ID,name, password_1, password_2, password_miwen;
	//�������浽����ROM
	private String[] ArrInfo=new String[3];
	private FileService fileService;
	private boolean result_Name,result_Psw;
	//����bundle��һ�����ID�Ƿ���ע�ᣬ��һ����������
	private Handler handler1=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.obj.equals("0")){
				//֤����ID��ע��
				Toast.makeText(Zhuce.this, "��ID��ע�ᣬ����飡", Toast.LENGTH_SHORT).show();
				zhuce_et_ID.setFocusable(true);
				zhuce_et_ID.setFocusableInTouchMode(true);		
			}
		};
	};
	private Handler handler2=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.obj.equals("0")){
				//֤����IDע��ɹ�
				result_Psw = fileService.savePswToRom("psw.txt", ID,password_miwen);	
				result_Name = fileService.saveNameToRom("name.txt", ID,name);
				if(result_Psw&&result_Name){
					Toast.makeText(Zhuce.this, "ע��ɹ���\n��лʹ�ã�", Toast.LENGTH_SHORT).show();
				}
				startActivity(new Intent(Zhuce.this,MainActivity.class));
				Zhuce.this.finish();				
			}			
		};
	};
	//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhuce);
		init_Content();
		Listener();
	}

	void init_Content() {// ����
		zhuce_et_ID = (EditText) findViewById(R.id.zhuce_et_ID);
		zhuce_et_name = (EditText) findViewById(R.id.zhuce_et_name);
		zhuce_et_password_1 = (EditText) findViewById(R.id.zhuce_et_password_1);
		zhuce_et_password_2 = (EditText) findViewById(R.id.zhuce_et_password_again);		
		
		btn_zhuce = (Button) findViewById(R.id.btn_zhuce_action);
//		btn_zhuce.setClickable(false);
		btn_zhuce.setBackgroundColor(Color.GRAY);
		btn_zhuce.setTextColor(Color.WHITE);
		fileService = new FileService(this);
	}

	private void Listener() {
		
		zhuce_et_ID.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {}

			@Override
			public void afterTextChanged(Editable arg0) {
				// ���ѧ���Ƿ���ϱ�׼
				ID=zhuce_et_ID.getText().toString();				
			}
		});

		zhuce_et_name.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				name=zhuce_et_name.getText().toString();
			}
		});
		
		zhuce_et_password_1.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				password_1=zhuce_et_password_1.getText().toString();				
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {}
		});
		
		zhuce_et_password_2.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				password_2=zhuce_et_password_2.getText().toString();
			}
		});
		btn_zhuce.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				zhuce_action();
			}
		});
	}

	public void zhuce_action() {
		CheckUserId check1 = new CheckUserId(ID);
		CheckPassword check=new CheckPassword(password_1);
		boolean flag1=check1.getFlag(),flag2=check.IsOK(),flag3=password_2.equals(password_1);
		if (!flag1) {
			Toast.makeText(Zhuce.this, "��ȷ�� �û���Ϊѧ�ţ�", Toast.LENGTH_SHORT).show();
		}
		if(!flag2){
			Toast.makeText(Zhuce.this, "����6--16λ", Toast.LENGTH_SHORT).show();
		}
		if(!flag3){
			Toast.makeText(Zhuce.this, "�������벻һ�£���ȷ��", Toast.LENGTH_SHORT).show();
		}
		if(flag1&&flag2&&flag3){
			btn_zhuce.setClickable(true);
			btn_zhuce.setBackgroundColor(Color.WHITE);
			btn_zhuce.setTextColor(Color.rgb(126, 192, 238));
			// ������MD5���ܣ��������������������д���
			MD5 md5 = new MD5(password_1);
			password_miwen = md5.GetMD5Code();
			// ע��			
			new HttpThread_Zhuce_CheckID(this, ID, name, password_miwen, handler1,handler2).start();
		}		
	}
}
