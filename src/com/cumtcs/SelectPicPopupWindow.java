package com.cumtcs;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import com.cumtcs.WoyaopingActivity;

public class SelectPicPopupWindow extends PopupWindow {

	private Button addpic_paizhao, addpic_benditupian, addpic_btn_cancel;
	private View mMenuView;

	public SelectPicPopupWindow(Activity context, OnClickListener itemsOnClick) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.addpic, null);
		addpic_paizhao = (Button) mMenuView.findViewById(R.id.addpic_paizhao);
		addpic_benditupian = (Button) mMenuView
				.findViewById(R.id.addpic_benditupian);
		addpic_btn_cancel = (Button) mMenuView
				.findViewById(R.id.addpic_btn_cancel);
		// ȡ����ť
		addpic_btn_cancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// ���ٵ�����
				dismiss();
			}
		});
		// ���ð�ť����
		addpic_benditupian.setOnClickListener(itemsOnClick);
		addpic_paizhao.setOnClickListener(itemsOnClick);
		// ����SelectPicPopupWindow��View
		this.setContentView(mMenuView);
		// ����SelectPicPopupWindow��������Ŀ�
		this.setWidth(LayoutParams.FILL_PARENT);
		// ����SelectPicPopupWindow��������ĸ�
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// ����SelectPicPopupWindow��������ɵ��
		this.setFocusable(true);
		// ʵ����һ��ColorDrawable��ɫΪ��͸��
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		// ����SelectPicPopupWindow��������ı���
		this.setBackgroundDrawable(dw);
		// mMenuView���OnTouchListener�����жϻ�ȡ����λ�������ѡ������������ٵ�����
		mMenuView.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				int height = mMenuView.findViewById(R.id.addpic_pop_layout)
						.getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						dismiss();
					}
				}
				return true;
			}
		});

	}

}