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
	//添加上JSON的功能
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
	
	//原有
	public DetailAdapter(Context context,List<DetailData> detail_list) {		
		this.detail_list=detail_list;
		this.context=context;
		this.inflater = LayoutInflater.from(context);
		url=context.getResources().getString(R.string.url)+"Json";
	}
	//原有
	public void onDataChanged(List<DetailData> detail_list){
		this.detail_list=detail_list;
		this.notifyDataSetChanged();//自动刷新数据
	}
	//原有
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return detail_list.size();
	}
	//原有
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return detail_list.get(arg0);
	}
	//原有
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	//原有
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
		 * 首先我们要知道setTag方法是干什么的，
		 * 他是给View对象的一个标签，标签可以是任何内容，
		 * 我们这里把他设置成了一个对象，
		 * 因为我们是把vlist2.xml的元素抽象出来成为一个类ViewHolder，
		 * 用了setTag，这个标签就是ViewHolder实例化后对象的一个属性。
		 * 我们之后对于ViewHolder实例化的对象holder的操作，
		 * 都会因为java的引用机制而一直存活并改变convertView的内容，
		 * 而不是每次都是去new一个。
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
	
	
	//原有
	class ViewHolder{
		TextView userNicheng,storeID,locationID,time,comment,ScoreOfShangpin,ScoreOfFuwu;		
		ImageView picture;
	}
}
