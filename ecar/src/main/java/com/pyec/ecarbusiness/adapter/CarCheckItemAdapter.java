package com.pyec.ecarbusiness.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pyec.ecarbusiness.R;
import com.pyec.ecarbusiness.baseClass.MyApplication;
import com.pyec.ecarbusiness.baseClass.PinnedSectionListView.PinnedSectionListAdapter;
import com.pyec.ecarbusiness.dataStruct.CarCheckItem;

public class CarCheckItemAdapter extends BaseAdapter implements PinnedSectionListAdapter{

	private Context mContext;
	private MyApplication myApplication;
	private List<CarCheckItem> mList = null;
	
	public CarCheckItemAdapter(Context context, List<CarCheckItem> list) {
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
    	if(mList.get(position).name.equals("title"))
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

		CarCheckItem data = mList.get(position);
		ViewHolder viewHolder = null;
		if (getItemViewType(position) == 1) {
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.car_check_item_title, null);
				viewHolder.textName = (TextView) convertView
						.findViewById(R.id.textName);
				viewHolder.textContent = (TextView) convertView
						.findViewById(R.id.textContent);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.textName.setText(data.value);
			viewHolder.textContent.setText(data.pic);
			return convertView;
		}
		else
		{
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.car_check_item, null);
				viewHolder.textName = (TextView) convertView
						.findViewById(R.id.textName);
				viewHolder.textContent = (TextView) convertView
						.findViewById(R.id.textContent);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.textName.setText(data.name);
			viewHolder.textContent.setText(data.value);
			return convertView;
		}
	}

	
	
	final static class ViewHolder {
		TextView textName;
		TextView textContent;
	}

}
