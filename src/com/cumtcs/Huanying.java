package com.cumtcs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Huanying extends Activity{

	private static Button btn_huanying_denglu,btn_huanying_zhuce;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.huanying);
		init_Button();
		btn_huanying_denglu.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Huanying.this.finish();
				startActivity(new Intent(Huanying.this,Denglu.class));
			}
			
		});
		btn_huanying_zhuce.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Huanying.this.finish();
				startActivity(new Intent(Huanying.this,Zhuce.class));
			}
			
		});
	}

	private void init_Button(){
		btn_huanying_denglu=(Button) findViewById(R.id.btn_huanying_denglu);
		btn_huanying_zhuce=(Button) findViewById(R.id.btn_huanying_zhuce);
	}

	
}
