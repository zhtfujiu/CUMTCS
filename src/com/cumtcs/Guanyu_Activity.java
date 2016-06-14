package com.cumtcs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Guanyu_Activity extends Activity {

	private Button btn_guanyu_fanhui;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guanyu);
		init_View();
	}
	
	public void init_View(){
		btn_guanyu_fanhui=(Button) findViewById(R.id.btn_guanyu_fanhui);
		btn_guanyu_fanhui.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View arg0) {ToGeren();}
		});
	}
	private void ToGeren(){
		Guanyu_Activity.this.finish();
	}
}
