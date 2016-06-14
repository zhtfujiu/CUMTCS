package com.data;

import com.cumtcs.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ReFalshListView extends ListView implements OnScrollListener {

	View header;// ���������ļ�
	int headerHeight;// ���������ļ��ĸ߶�
	int scrollState;// ��ǰ����״̬
	int firstVisibleItem;// ��һ����ʾ��item
	boolean isRemark;// ��ǣ���ǰ����listview��˰��µ�
	int startY;// ����ʱ��Yֵ
	
	int state;// ��ǰ״̬
	final int NONE = 0;// ����״̬
	final int PULL = 1;// ��ʾ����״̬
	final int RELEASE = 2;// ��ʾ�ͷ�״̬
	final int REFLASHING = 3;// ����ˢ��״̬
	IReflashListener iReflashListener;//ˢ�����ݵĽӿ�

	public ReFalshListView(Context context) {
		super(context);
		initView(context);
	}

	public ReFalshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public ReFalshListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(context);
	}

	/**
	 * ��ʼ�����棬��Ӷ������ֵ�listview 
	 * @param contenxt
	 */
	private void initView(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		header = inflater.inflate(R.layout.faxian_list_header, null);
		measureView(header);
		headerHeight = header.getMeasuredHeight();
		topPadding(-headerHeight);
		//
		this.addHeaderView(header);
		this.setOnScrollListener(this);		
	}

	/**
	 * ����header���ֵ��ϱ߾�
	 * 
	 * @param toppadding
	 */
	private void topPadding(int toppadding) {
		header.setPadding(header.getPaddingLeft(), toppadding,
				header.getPaddingRight(), header.getPaddingBottom());
		header.invalidate();
	}

	/**
	 * ֪ͨ�����֣���ռ�õĿ���
	 */
	private void measureView(View view) {
		ViewGroup.LayoutParams p = view.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
		int height;
		int tempHeight = p.height;
		if (tempHeight > 0) {
			height = MeasureSpec.makeMeasureSpec(tempHeight,
					MeasureSpec.EXACTLY);
		} else {
			height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		view.measure(width, height);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.firstVisibleItem = firstVisibleItem;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		this.scrollState = scrollState;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN: 
			
			if(firstVisibleItem==0){
				isRemark = true;
				startY = (int) ev.getY();				
			}			
			break;
		case MotionEvent.ACTION_MOVE:
			onMove(ev);
			break;
		case MotionEvent.ACTION_UP: {
			if(state==RELEASE){
				state=REFLASHING;
				//������������
				reflashViewByState();
				iReflashListener.onReflash();//
			}else if(state==PULL){
				state=NONE;
				isRemark=false;
				reflashViewByState();
			}
		}
			break;
		}
		// TODO Auto-generated method stub
		return super.onTouchEvent(ev);
	}

	/**
	 * �жϻ����Ĳ���
	 * 
	 * @param ev
	 */
	private void onMove(MotionEvent ev) {
		if (!isRemark) {
			return;
		}
		int tempY = (int) ev.getY();
		int space = tempY - startY;
		int topPadding = space - headerHeight;
		switch (state) {
		case NONE: {
			if (space > 0) {
				state = PULL;
				reflashViewByState();
			}
		}break;
		case PULL: {
			topPadding(topPadding);
			if (space > headerHeight + 30 &&  
					scrollState == SCROLL_STATE_TOUCH_SCROLL) {
				state = RELEASE;
				reflashViewByState();
			}
		}break;
		case RELEASE: {
			topPadding(topPadding);
			if (space < headerHeight + 30) {
				state = PULL;
				reflashViewByState();
			} else if (space <= 0) {
				state = NONE;
				isRemark = false;
				reflashViewByState();
			}
		}break;
		}
	}

	/**
	 * ���ݵ�ǰ״̬���ı������ʾ
	 */
	private void reflashViewByState(){
		TextView tip=(TextView) header.findViewById(R.id.faxian_list_header_tip);
		ImageView arrow=(ImageView) header.findViewById(R.id.faxian_list_header_arrow);
		ProgressBar progress=(ProgressBar) header.findViewById(R.id.faxian_list_header_progress);
		//��ͷ��ת����
		RotateAnimation anim1=new RotateAnimation(0, 180,RotateAnimation.RELATIVE_TO_SELF,0.5f
				,RotateAnimation.RELATIVE_TO_SELF,0.5f);
		anim1.setDuration(500);
		anim1.setFillAfter(true);
		RotateAnimation anim2=new RotateAnimation(180, 0,RotateAnimation.RELATIVE_TO_SELF,0.5f
				,RotateAnimation.RELATIVE_TO_SELF,0.5f);
		anim2.setDuration(500);
		anim2.setFillAfter(true);
		switch(state){
		case NONE:{
			arrow.clearAnimation();
			topPadding(-headerHeight);
			
		}break;
		case PULL:{
			arrow.setVisibility(View.VISIBLE);
			progress.setVisibility(View.GONE);
			tip.setText("����ˢ��");
			arrow.clearAnimation();
			arrow.setAnimation(anim2);
		}break;
		case RELEASE:{
			arrow.setVisibility(View.VISIBLE);
			progress.setVisibility(View.GONE);
			tip.setText("�ɿ�ˢ��");			
			arrow.clearAnimation();
			arrow.setAnimation(anim1);
		}break;
		case REFLASHING:{
			topPadding(50);
			arrow.setVisibility(View.GONE);
			progress.setVisibility(View.VISIBLE);
			tip.setText("����ˢ��...");
			arrow.clearAnimation();
		}break;
				
		}
	}

	public void setInterface(IReflashListener iReflashListener){
		this.iReflashListener=iReflashListener;
	}
	/**
	 * ˢ�����ݽӿ�
	 * @author ���
	 */
	public interface IReflashListener{
		public void onReflash();
	}
	/**
	 * ��ȡ�����ݣ�
	 */
	public void reflashComplete() {
		state = NONE;
		isRemark = false;
		//��Ϊ�������ͼ�ͷͼƬ��С��һ�£���������ý�������ʧ�ͻ�¶����
		ProgressBar progress=(ProgressBar) header.findViewById(R.id.faxian_list_header_progress);
		progress.setVisibility(View.GONE);
		reflashViewByState();
		//
		
	}
	
}
