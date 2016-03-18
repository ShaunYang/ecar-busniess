package com.pyec.ecarbusiness.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Text;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pyec.ecarbusiness.R;
import com.pyec.ecarbusiness.baseClass.PinnedSectionListView.PinnedSectionListAdapter;
import com.pyec.ecarbusiness.dataStruct.CarDetailData;

public class CarDetailInfoAdapter extends BaseAdapter implements PinnedSectionListAdapter{

	private Gson gson = new Gson();
	private Context mContext;
	private List<Map<String, String>> mList = null;
	
	public CarDetailInfoAdapter(Context context, List<Map<String, String>> list) {
		this.mContext = context;
		this.mList = list;
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
        return 5;
    }

    @Override 
    public int getItemViewType(int position) {
    	int type = 0;
    	if(mList.get(position).get("type").equals("title"))
    		type =  0;
    	else
    	{
			switch(Integer.valueOf(mList.get(position).get("itemName")))
			{
			case 1:
				type = 1;
				break;
			case 2:
				type = 2;
				break;
			case 3:
				type = 3;
				break;
			case 4:
				type = 4;
				break;
			}
    	}
    	return type;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == 0;
    }
    
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		Map<String, String> mapData = mList.get(position);
		ViewHolder viewHolder = null;

		Map<String, String> data;
		Resources res  = null ; 
		String[] items = null ;
		TextView text = null;
		res = mContext.getResources();
		if (getItemViewType(position) == 0) {
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
			viewHolder.txtContent.setText(mapData.get("content"));
			viewHolder.txtTime.setVisibility(View.GONE);
			return convertView;
		}
		else if(getItemViewType(position) == 1)//检测师简述
		{
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.car_detail_part1, null);

				viewHolder.txtContent = (TextView) convertView
						.findViewById(R.id.textContent);
				viewHolder.txtContent.setTextColor(mContext.getResources().getColor(R.color.grey_999999));
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.txtContent.setText(mapData.get("content"));
			return convertView;
		}
		else if(getItemViewType(position) == 2)//基本信息
		{
			items = res.getStringArray(R.array.carBaseInfo);
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.car_detail_part2, null);
				int id = 0 ;
				for(int i = 0 ; i < items.length ; i ++)
				{
					id = convertView.getResources().getIdentifier("itemName_" + i, "id","com.pyec.ecarbusiness");
					text  = (TextView) convertView.findViewById(id);
					text.setTextColor(mContext.getResources().getColor(R.color.grey_999999));
					viewHolder.listItemTextViews.add(text);
					
					id = convertView.getResources().getIdentifier("itemContent_" + i, "id","com.pyec.ecarbusiness");
					text = (TextView) convertView.findViewById(id);
					text.setTextColor(mContext.getResources().getColor(R.color.grey_333333));
					viewHolder.listContentTextViews.add(text);
				}
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			data = new HashMap<String, String>();
			data =  gson.fromJson(mapData.get("content"), new TypeToken<HashMap<String, String>>() {}.getType());
			for(int j = 0 ; j < items.length ; j ++)
			{
				viewHolder.listItemTextViews.get(j).setText(items[j]);
				viewHolder.listContentTextViews.get(j).setText(data.get("baseInfor_"+j));
			}
			return convertView;
		}
		else if(getItemViewType(position) == 3)//基本配置
		{
			items = res.getStringArray(R.array.carBaseConfig);
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.car_detail_part3, null);
				int id = 0 ;
				for(int i = 0 ; i < items.length ; i ++)
				{
					id = convertView.getResources().getIdentifier("itemName_" + i, "id","com.pyec.ecarbusiness");
					text  = (TextView) convertView.findViewById(id);
					text.setTextColor(mContext.getResources().getColor(R.color.grey_999999));
					viewHolder.listItemTextViews.add(text);
					
					id = convertView.getResources().getIdentifier("itemContent_" + i, "id","com.pyec.ecarbusiness");
					text = (TextView) convertView.findViewById(id);
					text.setTextColor(mContext.getResources().getColor(R.color.grey_333333));
					viewHolder.listContentTextViews.add(text);
				}
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			data = new HashMap<String, String>();
			data =  gson.fromJson(mapData.get("content"), new TypeToken<HashMap<String, String>>() {}.getType());
			for(int j = 0 ; j < items.length ; j ++)
			{
				viewHolder.listItemTextViews.get(j).setText(items[j]);
				viewHolder.listContentTextViews.get(j).setText(data.get("carBaseConfig_"+j));
			}
			return convertView;
		}
		else //主要配置if(getItemViewType(position) == 4)
		{
			items = res.getStringArray(R.array.carMainConfig);
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.car_detail_part4, null);
				int id = 0 ;
				for(int i = 0 ; i < items.length ; i ++)
				{
					id = convertView.getResources().getIdentifier("itemName_" + i, "id","com.pyec.ecarbusiness");
					text  = (TextView) convertView.findViewById(id);
					text.setTextColor(mContext.getResources().getColor(R.color.grey_999999));
					viewHolder.listItemTextViews.add(text);
					
					id = convertView.getResources().getIdentifier("itemContent_" + i, "id","com.pyec.ecarbusiness");
					text = (TextView) convertView.findViewById(id);
					text.setTextColor(mContext.getResources().getColor(R.color.grey_333333));
					viewHolder.listContentTextViews.add(text);
				}
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			data = new HashMap<String, String>();
			data =  gson.fromJson(mapData.get("content"), new TypeToken<HashMap<String, String>>() {}.getType());
			
			for(int j = 0 ; j < items.length ; j ++)
			{
				viewHolder.listItemTextViews.get(j).setText(items[j]);
				viewHolder.listContentTextViews.get(j).setText(data.get("carMainConfig_"+j));
			}
			return convertView;
		}
		
			
	}

	
	
	final static class ViewHolder {

		List<TextView> listItemTextViews = new ArrayList<TextView>(); 

		List<TextView> listContentTextViews = new ArrayList<TextView>(); 
		
		TextView txtContent;
		TextView txtTime;
		
		TextView txtBrand;
		TextView txtInfor;
		TextView txtScore;
		
		ImageView imgCar;
	}

}
