package com.cumtcs;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

class Main_init{

	private View view_1,view_2,view_3;
	private Intent intent_2=new Intent(),intent_3=new Intent();
	private Bundle bundle_2=new Bundle(),bundle_3=new Bundle();
	public Main_init(final List<View> main_view_list,MainActivity m,
			PagerAdapter main_ViewPager_Adapter,ViewPager main_ViewPager,
			String[] ArrStr){
		//初始化3个布局
		LayoutInflater main_Inflater=LayoutInflater.from(m);
		//这一段用来传用户信息，从MainActivity到Woyaoping Activity
//		intent_2.setClass(m, WoyaopingActivity.class);
//		bundle_2.putStringArray("msgToWoyaoping", ArrStr);
//		intent_2.putExtras(bundle_3);
		//这一段用来传用户信息，从MainActivity到geren Activity
		intent_3.setClass(m, GerenActivity.class);
		bundle_3.putStringArray("msgToGeren", ArrStr);
		intent_3.putExtras(bundle_3);
		//
		view_1=m.getLocalActivityManager().
				startActivity(null, new Intent(m, ShangjiaActivity.class)).getDecorView();
		view_2=m.getLocalActivityManager().
				startActivity(null, new Intent(m, WoyaopingActivity.class)).getDecorView();
		view_3=m.getLocalActivityManager().
				startActivity(null, intent_3).getDecorView();
		
	    main_view_list.add(view_1);
	    main_view_list.add(view_2);
	    main_view_list.add(view_3);
	    //初始化适配器
	    main_ViewPager_Adapter=new PagerAdapter(){
	    	
			@Override
			public void destroyItem(ViewGroup container, int position,Object object) {
				//销毁
				// TODO Auto-generated method stub
				container.removeView(main_view_list.get(position));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				// TODO Auto-generated method stub
				//初始化
				View view=main_view_list.get(position);
				container.addView(view);
				return view;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return main_view_list.size();
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0==arg1;
			}   	
			
	    };
	    main_ViewPager.setAdapter(main_ViewPager_Adapter);
	}
}
