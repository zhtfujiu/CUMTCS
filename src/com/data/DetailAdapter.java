package com.data;

import java.util.List;

import com.Json.DetailData;
import com.cumtcs.R;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailAdapter extends BaseAdapter {

	List<DetailData> detail_list;
	LayoutInflater inflater;
	//�����JSON�Ĺ���
	private Context context=null;
	private Handler handler=new Handler();
	private String url="http://192.168.109.1:8080//ServletForGETMethod/Json";
	
	
	public DetailAdapter(Context context) {
		this.context=context;
		url=context.getResources().getString(R.string.url)+"Json";
		inflater=LayoutInflater.from(context);
	}
	public void setData(List<DetailData> detail_list){
		this.detail_list=detail_list;
	}
	
	//ԭ��
	public DetailAdapter(Context context,List<DetailData> detail_list) {		
		this.detail_list=detail_list;
		this.context=context;
		this.inflater = LayoutInflater.from(context);
		url=context.getResources().getString(R.string.url)+"Json";
	}
	//ԭ��
	public void onDataChanged(List<DetailData> detail_list){
		this.detail_list=detail_list;
		this.notifyDataSetChanged();//�Զ�ˢ������
	}
	//ԭ��
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return detail_list.size();
	}
	//ԭ��
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return detail_list.get(arg0);
	}
	//ԭ��
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	//ԭ��
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		ViewHolder viewHolder;
		if(arg1==null){
			viewHolder=new ViewHolder();
			arg1=inflater.inflate(R.layout.faxian_list_item, null);
			viewHolder.userNicheng=(TextView) arg1.findViewById(R.id.listitem_usernicheng);
			viewHolder.storeID=(TextView) arg1.findViewById(R.id.listitem_storeName);
			viewHolder.locationID=(TextView) arg1.findViewById(R.id.listitem_locationName);
			viewHolder.time=(TextView) arg1.findViewById(R.id.listitem_time);
			viewHolder.comment=(TextView) arg1.findViewById(R.id.listitem_comment);
			viewHolder.ScoreOfShangpin=(TextView) arg1.findViewById(R.id.listitem_shangpin);
			viewHolder.ScoreOfFuwu=(TextView) arg1.findViewById(R.id.listitem_fuwu);
			viewHolder.picture=(ImageView) arg1.findViewById(R.id.listitem_picture);
			arg1.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) arg1.getTag();			
		}
		/**
		 * ��������Ҫ֪��setTag�����Ǹ�ʲô�ģ�
		 * ���Ǹ�View�����һ����ǩ����ǩ�������κ����ݣ�
		 * ��������������ó���һ������
		 * ��Ϊ�����ǰ�vlist2.xml��Ԫ�س��������Ϊһ����ViewHolder��
		 * ����setTag�������ǩ����ViewHolderʵ����������һ�����ԡ�
		 * ����֮�����ViewHolderʵ�����Ķ���holder�Ĳ�����
		 * ������Ϊjava�����û��ƶ�һֱ���ı�convertView�����ݣ�
		 * ������ÿ�ζ���ȥnewһ����
		 */
		DetailData item_DetailData=detail_list.get(arg0);
		
		viewHolder.userNicheng.setText(item_DetailData.getUserNicheng());
		viewHolder.storeID.setText(item_DetailData.getStoreID());
		viewHolder.locationID.setText(item_DetailData.getLocationID());
		viewHolder.time.setText(item_DetailData.getTime());
		viewHolder.comment.setText(item_DetailData.getComment());
		viewHolder.ScoreOfShangpin.setText(setSPText(item_DetailData.getScoreOfShangpin()));
		viewHolder.ScoreOfFuwu.setText(setFWText(item_DetailData.getScoreOfFuwu()));
		
		new HttpImage(url, handler, viewHolder.picture).start();
		return arg1;
	}
	public String setSPText(String sp){
		String str=context.getResources().getString(R.string.ScoreOfShangpin);
		sp=String.format(str, sp);
		return sp;
	}
	public String setFWText(String fw){
		String str=context.getResources().getString(R.string.ScoreOfFuwu);
		fw=String.format(str, fw);
		return fw;
	}
	
	
	//ԭ��
	class ViewHolder{
		TextView userNicheng,storeID,locationID,time,comment,ScoreOfShangpin,ScoreOfFuwu;		
		ImageView picture;
	}
}
