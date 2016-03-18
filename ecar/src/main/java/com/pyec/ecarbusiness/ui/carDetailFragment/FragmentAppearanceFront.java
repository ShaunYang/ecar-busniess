package com.pyec.ecarbusiness.ui.carDetailFragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pyec.ecarbusiness.R;
import com.pyec.ecarbusiness.adapter.CarCheckItemAdapter;
import com.pyec.ecarbusiness.baseClass.BaseFragment;
import com.pyec.ecarbusiness.baseClass.DataBaseInterface;
import com.pyec.ecarbusiness.baseClass.MyApplication;
import com.pyec.ecarbusiness.baseClass.PinnedSectionListView;
import com.pyec.ecarbusiness.dataStruct.CarCheckItem;
import com.pyec.ecarbusiness.dataStruct.CarDetailData;
import com.pyec.ecarbusiness.showBigImg.ImagePreviewActivity;

public class FragmentAppearanceFront extends BaseFragment {

	private View mView;
	private View mHeadView;
	private Activity mActivity;
	private MyApplication myApplication;
	
	private ImageView imgCarPart;
	private PinnedSectionListView pListView;
	private CarCheckItemAdapter mAdapter;
	private Gson gson = new Gson();
	public CarDetailData carDetailData = new CarDetailData();//下载数据
	private List<CarCheckItem> mListData = new ArrayList<CarCheckItem>();//显示数据
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mActivity = getActivity();
		mView = inflater.inflate(R.layout.car_fragment_comment, container, false);
		myApplication = (MyApplication) mActivity.getApplication();
		initData();
		initView();
		return mView;
	}

	
	private void initView()
	{

		pListView = (PinnedSectionListView)mView.findViewById(R.id.list);
		pListView.setFastScrollEnabled(false);
		mHeadView = LayoutInflater.from(getActivity()).inflate(R.layout.car_check_head, null);
		imgCarPart = (ImageView)mHeadView.findViewById(R.id.imgCarPart);
		imgCarPart.setBackgroundResource(R.drawable.img_appearance_front);
		ImageLoader.getInstance().displayImage(carDetailData.check_item_info.get(0).priview, imgCarPart);
		pListView.addHeaderView(mHeadView);
		mAdapter = new CarCheckItemAdapter(getActivity(), mListData);
		pListView.setAdapter(mAdapter);
		pListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				if(position <= 1)
					return;
				String[] images = new String [mListData.size()-1];
				String[] imageNmes = new String [mListData.size()-1];
				for(int i = 0 ; i < images.length ; i ++)
				{
					images[i] = mListData.get(i+1).pic;
					imageNmes[i] = mListData.get(i+1).name + ":"+
							mListData.get(i+1).value;
				}
				ImagePreviewActivity.showImagePrivew(getActivity(),position-2, images, imageNmes);
			}
		});

	}
	
	private void initData()
	{
		DataBaseInterface dbInterface = new DataBaseInterface(getActivity(),"app_data");
		carDetailData =  gson.fromJson(dbInterface.getCarData("carData"), new TypeToken<CarDetailData>() {}.getType());
		mListData = carDetailData.check_item_info.get(0).item;
		CarCheckItem item = new CarCheckItem(); 
		item.name = "title";
		item.value = "区域";
		item.pic = "损伤描述";
		mListData.add(0, item);
	}

}
