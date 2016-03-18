package com.pyec.ecarbusiness.adapter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.pyec.ecarbusiness.R;
import com.pyec.ecarbusiness.baseClass.MyApplication;
import com.pyec.ecarbusiness.baseClass.PinnedSectionListView.PinnedSectionListAdapter;
import com.pyec.ecarbusiness.dataStruct.AuctionListData;
import com.pyec.ecarbusiness.httpClient.AppHttpInterface;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AuctionNowAdapter extends BaseAdapter implements PinnedSectionListAdapter{

	private Context mContext;
	private MyApplication myApplication;
	private List<AuctionListData> mList = null;
	
	public AuctionNowAdapter(Context context, List<AuctionListData> list) {
		this.mContext = context;
		this.mList = list;
        myApplication = (MyApplication)((Activity)context).getApplication();
        myApplication.configImageLoader();
	}
	

	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		if(this.mList == null)
			return 0;
		return this.mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
    @Override 
    public int getViewTypeCount() {
        return 2;
    }

    @Override 
    public int getItemViewType(int position) {
    	if(mList.get(position).type.equals("title"))
    		return 1;
    	else
    		return 0;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == 1;
    }
    
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

        AuctionListData mapData = mList.get(position);
		ViewHolder viewHolder = null;
		if (getItemViewType(position) == 1) {
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.car_list_item_title, null);
				viewHolder.txtContent = (TextView) convertView
						.findViewById(R.id.textContent);
				viewHolder.txtTime = (TextView) convertView
						.findViewById(R.id.textTime);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.txtContent.setTextColor(Color.DKGRAY);
			viewHolder.txtContent.setTag("" + position);
			viewHolder.txtTime.setText("离结束:"+mapData.now+"/" + mapData.endtime+ "/"+mapData.starttime);
			viewHolder.txtContent.setText(mapData.title);
			ContrastTime(Integer.valueOf(mapData.now) , Integer.valueOf(mapData.starttime) , Integer.valueOf(mapData.endtime) , viewHolder.txtTime , mapData.status);
			return convertView;
		}
		else
		{
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.car_list_item, null);
				viewHolder.txtBrand = (TextView) convertView
						.findViewById(R.id.textBrand);
				viewHolder.txtInfor = (TextView) convertView
						.findViewById(R.id.textInfor);
				viewHolder.txtScore = (TextView) convertView
						.findViewById(R.id.textScore);
				viewHolder.imgCar = (ImageView) convertView
						.findViewById(R.id.carImg);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.txtBrand.setText(mapData.info);
			viewHolder.txtScore.setText(mapData.score);
			viewHolder.txtInfor.setText(mapData.regist_date+"/"+mapData.kilometer+"公里"+"/"+mapData.area);

			ImageLoader.getInstance().displayImage(mapData.img01, viewHolder.imgCar);
//			Item item = mListTest.get(position);
//			viewHolder.txtContent.setText(item.toString());
			return convertView;
		}
	}

	
	
	 /***
	  *时间状态和显示设置 
	  * @param nNowTime    当前时间
	  * @param nStartTime  开始时间
	  * @param nEndTime    结束时间
	  * @param txtTime     显示时间的TextView
	  * @param sStartTime  开始的时间
	  * @return            当前时间减比较时间的差值
	  */
	public String ContrastTime(int nNowTime , int nStartTime , int nEndTime ,TextView txtTime ,String state) {
		int nTime = 0;
		SimpleDateFormat sFormat = null;

		int nSecond;
		int nMinute;
		int nHour;
		int nDay;
		nTime = nStartTime - nNowTime;
		//判断是否开始竞拍
		if(state.equals("2"))//等待开始
		{
			nTime = nStartTime - nNowTime;
			nSecond = nTime%60;
			nMinute = (nTime/60)%60;
			nHour = (nTime/(60*60))%24;
			nDay = (nTime/(60*60))/24;
			DecimalFormat df = new DecimalFormat("00");
			if(nDay <= 0)
				txtTime.setText("距开始："+df.format(nHour)+":"+df.format(nMinute)+":"+df.format(nSecond));
			else
				txtTime.setText("距开始："+nDay+"天"+df.format(nHour)+":"+df.format(nMinute)+":"+df.format(nSecond));
			return "wait";
		}else if(state.equals("1"))//正在竞拍
		{
			nTime = nEndTime - nNowTime;
			nSecond = nTime%60;
			nMinute = (nTime/60)%60;
			nHour = (nTime/(60*60))%24;
			nDay = (nTime/(60*60))/24;
			DecimalFormat df = new DecimalFormat("00");
			if(nDay <= 0)
				txtTime.setText("离结束："+df.format(nHour)+":"+df.format(nMinute)+":"+df.format(nSecond));
			else
				txtTime.setText("离结束："+nDay+"天"+df.format(nHour)+":"+df.format(nMinute)+":"+df.format(nSecond));
			return "underway";
		}else if(0 >= nEndTime - nNowTime)
		{
			txtTime.setText("已结束");
			return "end";
		}else
		{
			return null;
		}
	}
	
	
	final static class ViewHolder {
		TextView txtContent;
		TextView txtTime;
		
		TextView txtBrand;
		TextView txtInfor;
		TextView txtScore;
		
		ImageView imgCar;
		
	}

}
