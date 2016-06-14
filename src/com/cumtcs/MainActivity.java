package com.cumtcs;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.data.FileService;

import android.app.ActivityGroup;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends ActivityGroup {

	// ���������
	private ImageButton  main_shangjia_img,main_woyaoping_img, main_geren_img;
	// ����ViewPager & ������PagerAdapter
	private ViewPager main_ViewPager;
	private PagerAdapter main_ViewPager_Adapter;
	private List<View> main_view_list = new ArrayList<View>();
	///��һ���������洢�û���ID���ǳƣ�Ȼ��ת�浽GerenActivity
	private String [] ArrMsg=new String [2];
	private Bundle bundle,bundle2=new Bundle();
	//�洢������
	private FileService fileService;
	
	private LinearLayout main_tab_shangjia,main_tab_woyaoping, main_tab_geren;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init_Main();
		init_Event();
	}

	private void init_Event() {
			
		//
		MainActOnClickListener mainLis = new MainActOnClickListener();
		main_tab_shangjia.setOnClickListener(mainLis);
		main_tab_woyaoping.setOnClickListener(mainLis);
		main_tab_geren.setOnClickListener(mainLis);

		main_ViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageSelected(int arg0) {
				int current_item = main_ViewPager.getCurrentItem();
				resetImage();
				switch (current_item) {
				case 0:
					main_shangjia_img
							.setImageResource(R.drawable.faxian_pressd);
					break;
				case 1:
					main_woyaoping_img
							.setImageResource(R.drawable.faxian_pressd);
					main_shangjia_img.setImageResource(R.drawable.faxian);
					break;
				case 2:
					main_geren_img.setImageResource(R.drawable.faxian_pressd);
					main_shangjia_img.setImageResource(R.drawable.faxian);
					break;
				}
			}

		});

	}

	private void init_Main() {
		// ��ȡ�����ļ�����û�ID���ǳ�
		fileService = new FileService(this);
		try {
			ArrMsg[0] = fileService.getUserName("name.txt").get("userID");// ��ȡID
			ArrMsg[1] = fileService.getUserName("name.txt").get("name");// ��ȡ�ǳ�
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Toast.makeText(this, "û�ҵ��ļ�", 3).show();
			e.printStackTrace();
		}
		
		
		// ImageButton��ʼ��
		main_shangjia_img=(ImageButton) findViewById(R.id.main_tab_sj_img);
		main_woyaoping_img = (ImageButton) findViewById(R.id.main_tab_wyp_img);
		main_geren_img = (ImageButton) findViewById(R.id.main_tab_gr_img);
		// ViewPager��ʼ��
		main_ViewPager = (ViewPager) findViewById(R.id.main_viewpager);
		// Tabs��ʼ��
		main_tab_shangjia=(LinearLayout) findViewById(R.id.main_tab_shangjia);
		main_tab_woyaoping = (LinearLayout) findViewById(R.id.main_tab_woyaoping);
		main_tab_geren = (LinearLayout) findViewById(R.id.main_tab_geren);

		Main_init faxian_init = new Main_init(main_view_list, this,
				main_ViewPager_Adapter, main_ViewPager,ArrMsg);

		// �����ְ�ť��ɫ�����వɫ
		resetImage();
	}

	private void resetImage() {
		// �����е�ͼƬ�л�Ϊ��ɫ��
		main_shangjia_img.setImageResource(R.drawable.faxian_pressd);
		main_woyaoping_img.setImageResource(R.drawable.faxian);
		main_geren_img.setImageResource(R.drawable.faxian);
	}

	private class MainActOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {

			switch (arg0.getId()) {
			case R.id.main_tab_shangjia:
				main_ViewPager.setCurrentItem(0);
				main_shangjia_img.setImageResource(R.drawable.faxian_pressd);
				main_woyaoping_img.setImageResource(R.drawable.faxian);
				main_geren_img.setImageResource(R.drawable.faxian);
				break;
			case R.id.main_tab_woyaoping:
				main_ViewPager.setCurrentItem(1);
				main_woyaoping_img.setImageResource(R.drawable.faxian_pressd);
				main_shangjia_img.setImageResource(R.drawable.faxian);
				main_geren_img.setImageResource(R.drawable.faxian);
				break;
			case R.id.main_tab_geren:
				main_ViewPager.setCurrentItem(2);
				main_geren_img.setImageResource(R.drawable.faxian_pressd);
				main_shangjia_img.setImageResource(R.drawable.faxian);
				main_woyaoping_img.setImageResource(R.drawable.faxian);
				break;
			}
		}
	}
}
