package com.pyec.ecarbusiness.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.pyec.ecarbusiness.R;
import com.pyec.ecarbusiness.baseClass.MyApplication;
import com.pyec.ecarbusiness.dataStruct.AuctionListData;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AuctionFinishAdapter extends BaseAdapter{


	private Context mContext;
	private List<AuctionListData> mList = null;

	private MyApplication myApplication;
	public AuctionFinishAdapter(Context context, List<AuctionListData> list) {

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
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
        AuctionListData mapData = mList.get(position);
		ViewHolder viewHolder = null;

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
		return convertView;
	}

	
	
	final static class ViewHolder {
		TextView txtBrand;
		TextView txtInfor;
		TextView txtScore;
		ImageView imgCar;
	}
}
