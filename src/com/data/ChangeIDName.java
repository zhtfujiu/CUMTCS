package com.data;

import com.cumtcs.R;

import android.content.Context;

public class ChangeIDName {

	public ChangeIDName() {
	}
	public String setID(Context context,String s){
		String str=context.getResources().getString(R.string.User_ID);
		str=String.format(str, s);
		System.out.println(str);
		return str;
	}
	public String setName(Context context,String s){
		String str=context.getResources().getString(R.string.User_Nicheng);
		str=String.format(str, s);
		System.out.println(str);
		return str;
	}
}
