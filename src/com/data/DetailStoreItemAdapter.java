package com.data;

import java.util.List;

import com.Json.DetailStoreItem;
import com.cumtcs.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailStoreItemAdapter  extends ArrayAdapter<DetailStoreItem> {

	private List<DetailStoreItem> detail_list=null;
	private LayoutInflater inflater=null;
	private Context context;
	private int myResourceId;
	private View view;
	//下面两个数组加载IMG用
	private String[] AllStoreName;
	private int[] arrayRescource=new int[28];
	public DetailStoreItemAdapter(Context context, int resource,
			List<DetailStoreItem> objects) {
		super(context, resource, objects);
		this.myResourceId = resource;
		this.context = context;
		detail_list=objects;
		inflater = LayoutInflater.from(context);		
	}
	@Override
	public int getCount() {
		return detail_list.size();
	}
	@Override
	public DetailStoreItem getItem(int position) {
		return detail_list.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {

		DetailStoreItem item=getItem(arg0);
		View view=inflater.inflate(myResourceId, null);
		ImageView iv=(ImageView) view.findViewById(R.id.shangjia_haoping_list_item_img);
		TextView tv1=(TextView) view.findViewById(R.id.shangjia_haoping_list_item_tv1);
		TextView tv2=(TextView) view.findViewById(R.id.shangjia_haoping_list_item_tv2_star);
		TextView tv3=(TextView) view.findViewById(R.id.shangjia_haoping_list_item_tv3_star);	
		//
		setImg(item.getTv1(),iv);
		//
		int i,j;
		i=Integer.parseInt(item.getTv2());
		j=Integer.parseInt(item.getTv3());
		String str2="",str3="";
		for(int num1=0;num1<i;num1++){
			str2+="★";
		}
		for(int num2=0;num2<j;num2++){
			str3+="★";
		}
		tv1.setText(item.getTv1());
		tv2.setText(str2);
		tv3.setText(str3);
		return view;
	}

	
	private void setImg(String str,ImageView iv){
		setArrayRes();
		AllStoreName=context.getResources().getStringArray(R.array.AllStoreName);
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
