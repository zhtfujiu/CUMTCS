package com.data;

import com.cumtcs.R;

import android.content.Context;
import android.widget.ImageView;

public class SetImageView {
	private String[] AllStoreName;
	private int[] arrayRescource=new int[28];
	public SetImageView(Context context,ImageView iv,String str) {
		setArrayRes();
		AllStoreName=context.getResources().getStringArray(R.array.location);
		for(int i=0;i<AllStoreName.length;i++){
			if(str.equals(AllStoreName[i])){
				iv.setImageResource(arrayRescource[i]);
			}
		}
	}
	

	private void setArrayRes(){
		arrayRescource[0]=R.drawable.p0;
		arrayRescource[1]=R.drawable.p1;
		arrayRescource[2]=R.drawable.p2;
		arrayRescource[3]=R.drawable.p3;
		arrayRescource[4]=R.drawable.p4;
		arrayRescource[5]=R.drawable.p5;
		arrayRescource[6]=R.drawable.p6;
		arrayRescource[7]=R.drawable.p7;
		arrayRescource[8]=R.drawable.p8;
		arrayRescource[9]=R.drawable.p9;
		arrayRescource[10]=R.drawable.p10;
		arrayRescource[11]=R.drawable.p11;
		arrayRescource[12]=R.drawable.p12;
		arrayRescource[13]=R.drawable.p13;
		arrayRescource[14]=R.drawable.p14;
		arrayRescource[15]=R.drawable.p15;
		arrayRescource[16]=R.drawable.p16;
		arrayRescource[17]=R.drawable.p17;
		arrayRescource[18]=R.drawable.p18;
		arrayRescource[19]=R.drawable.p19;
		arrayRescource[20]=R.drawable.p20;
		arrayRescource[21]=R.drawable.p21;
		arrayRescource[22]=R.drawable.p22;
		arrayRescource[23]=R.drawable.p23;
		arrayRescource[24]=R.drawable.p24;
		arrayRescource[25]=R.drawable.p25;
		arrayRescource[26]=R.drawable.p26;
		arrayRescource[27]=R.drawable.p27;
	}
}
