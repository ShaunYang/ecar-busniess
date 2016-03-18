package com.pyec.ecarbusiness.ui.carDetailFragment;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pyec.ecarbusiness.R;
import com.pyec.ecarbusiness.adapter.AuctionNowAdapter;
import com.pyec.ecarbusiness.adapter.CarDetailInfoAdapter;
import com.pyec.ecarbusiness.baseClass.BaseFragment;
import com.pyec.ecarbusiness.baseClass.DataBaseInterface;
import com.pyec.ecarbusiness.baseClass.MyApplication;
import com.pyec.ecarbusiness.baseClass.PinnedSectionListView;
import com.pyec.ecarbusiness.baseClass.PullToRefreshLayout;
import com.pyec.ecarbusiness.baseClass.MyApplication.timeListener;
import com.pyec.ecarbusiness.dataStruct.AuctionListData;
import com.pyec.ecarbusiness.dataStruct.CarBaseImg;
import com.pyec.ecarbusiness.dataStruct.CarDetailData;
import com.pyec.ecarbusiness.showBigImg.ImagePreviewActivity;
import com.pyec.ecarbusiness.ui.LoginActivity;
import com.pyec.ecarbusiness.ui.MainTabActivity;
import com.pyec.ecarbusiness.viewPage.ADInfo;
import com.pyec.ecarbusiness.viewPage.CycleViewPager;
import com.pyec.ecarbusiness.viewPage.ViewFactory;
import com.pyec.ecarbusiness.viewPage.CycleViewPager.ImageCycleViewListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;


public class FragmentCarInfo extends BaseFragment {

	private MyApplication myApplication;
	private PinnedSectionListView pListView;
	private Gson gson = new Gson();
	private CarDetailInfoAdapter mAdapter;
	public CarDetailData carDetailData = new CarDetailData();//下载数据

	private List<Map<String, String>> mListData = new ArrayList<Map<String, String>>();//显示数据
	private View mView;
	private View mHeadView;
	private TextView textCarBrand;
	private TextView textScore;
	private TextView textTime;
	private TextView textCarId;
	
	private TextView textAuctionState;
	private Button btnRevisedPrice;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mView = inflater.inflate(R.layout.car_fragment_info, null);
		initView();
		initData();
		return mView;

	}
	
	private void initView()
	{
		LinearLayout layAuction = (LinearLayout)mView.findViewById(R.id.layAuction);
		layAuction.setOnClickListener(onClickListener);
		pListView = (PinnedSectionListView)mView.findViewById(R.id.list);
		pListView.setFastScrollEnabled(false);
		mHeadView = LayoutInflater.from(getActivity()).inflate(R.layout.car_detail_part0, null);
		textCarBrand = (TextView)mHeadView.findViewById(R.id.textCarBrand);
		textScore = (TextView)mHeadView.findViewById(R.id.textScore);
		textTime = (TextView)mHeadView.findViewById(R.id.textTime);
		textCarId = (TextView)mHeadView.findViewById(R.id.textCarId);
		btnRevisedPrice = (Button)mView.findViewById(R.id.btnRevisedPrice);
		textAuctionState = (TextView)mView.findViewById(R.id.textAuctionState);
		
		pListView.addHeaderView(mHeadView);
		mAdapter = new CarDetailInfoAdapter(getActivity(), mListData);
		pListView.setAdapter(mAdapter);
		pListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	private void initData()
	{
		DataBaseInterface dbInterface = new DataBaseInterface(getActivity(),"app_data");
		carDetailData =  gson.fromJson(dbInterface.getCarData("carData"), new TypeToken<CarDetailData>() {}.getType());
		
		textCarBrand.setText(carDetailData.check_info.brand_model);
		textScore.setText(carDetailData.check_info.score);
		textTime.setText(carDetailData.start_time);
		textCarId.setText(carDetailData.check_info.no);
		if(carDetailData.is_give_price.equals("1"))
		{
			textAuctionState.setText("你的竞价金额："+carDetailData.my_price+"元");
			btnRevisedPrice.setVisibility(View.VISIBLE);
		}
		else
		{
			btnRevisedPrice.setVisibility(View.GONE);
		}
		
		Map<String, String> data;
		data = new HashMap<String, String>();
		data.put("type", "title");
		data.put("content", "检测师简述");
		mListData.add(data);

		data = new HashMap<String, String>();
		data.put("type", "item");
		data.put("itemName", "1");
		data.put("content", carDetailData.check_info.report);
		mListData.add(data);
		
		data = new HashMap<String, String>();
		data.put("type", "title");
		data.put("content", "基本信息");
		mListData.add(data);

		data = new HashMap<String, String>();
		data.put("type", "item");
		data.put("itemName", "2");
		//构造基本信息
		Map<String, String> dataContent = new HashMap<String, String>();
		dataContent.put("baseInfor_0", carDetailData.check_info.kilometer);
		dataContent.put("baseInfor_1", carDetailData.check_info.speed_changing_box);
		dataContent.put("baseInfor_2", carDetailData.check_info.brand_model);
		dataContent.put("baseInfor_3", carDetailData.check_info.production);
		dataContent.put("baseInfor_4", carDetailData.check_info.use_type);
		dataContent.put("baseInfor_5", carDetailData.check_info.insurance_date);
		dataContent.put("baseInfor_6", carDetailData.check_info.discharge_standard);
		if(carDetailData.check_info.other.contains("0"))
			dataContent.put("baseInfor_7", "有");
		else
			dataContent.put("baseInfor_7", "无");
		if(carDetailData.check_info.other.contains("2"))
			dataContent.put("baseInfor_8", "有");
		else
			dataContent.put("baseInfor_8", "无");
		if(carDetailData.check_info.other.contains("4"))
			dataContent.put("baseInfor_9", "有");
		else
			dataContent.put("baseInfor_9", "无");
		dataContent.put("baseInfor_10", carDetailData.check_info.belong);
		dataContent.put("baseInfor_11", carDetailData.check_info.own_type);
		dataContent.put("baseInfor_12", carDetailData.check_info.color);
		dataContent.put("baseInfor_13", carDetailData.check_info.regist_date);
		dataContent.put("baseInfor_14", carDetailData.check_info.annual_check_date);
		dataContent.put("baseInfor_15", carDetailData.check_info.area);
		dataContent.put("baseInfor_16", carDetailData.check_info.change_ownership_times);

		if(carDetailData.check_info.other.contains("2"))
			dataContent.put("baseInfor_17", "有");
		else
			dataContent.put("baseInfor_17", "无");
		if(carDetailData.check_info.other.contains("4"))
			dataContent.put("baseInfor_18", "有");
		else
			dataContent.put("baseInfor_18", "无");
		if(carDetailData.check_info.other.contains("6"))
			dataContent.put("baseInfor_19", "有");
		else
			dataContent.put("baseInfor_19", "无");
		data.put("content", gson.toJson(dataContent));
		mListData.add(data);
		
		
		data = new HashMap<String, String>();
		data.put("type", "title");
		data.put("content", "基本配置");
		mListData.add(data);
		

		data = new HashMap<String, String>();
		data.put("type", "item");
		data.put("itemName", "3");
		
		dataContent = new HashMap<String, String>();
		dataContent.put("carBaseConfig_0", carDetailData.check_conf_info.guide_price);
		dataContent.put("carBaseConfig_1", carDetailData.check_conf_info.fuel_supply_method);
		dataContent.put("carBaseConfig_2", carDetailData.check_conf_info.body_style);
		dataContent.put("carBaseConfig_3", carDetailData.check_conf_info.before_tire_style);
		dataContent.put("carBaseConfig_4", carDetailData.check_conf_info.after_tire_style);
		dataContent.put("carBaseConfig_5", carDetailData.check_conf_info.wheel_hub_material);
		dataContent.put("carBaseConfig_6", carDetailData.check_conf_info.max_power);
		dataContent.put("carBaseConfig_7", carDetailData.check_conf_info.gas_bag_num);
		dataContent.put("carBaseConfig_8", carDetailData.check_conf_info.model_type);
		dataContent.put("carBaseConfig_9", carDetailData.check_conf_info.fuel_type);
		dataContent.put("carBaseConfig_10", carDetailData.check_conf_info.drive_style);
		dataContent.put("carBaseConfig_11", carDetailData.check_conf_info.door_num);
		dataContent.put("carBaseConfig_12", carDetailData.check_conf_info.seat_num);
		dataContent.put("carBaseConfig_13", carDetailData.check_conf_info.spare_tire_style);
		dataContent.put("carBaseConfig_14", carDetailData.check_conf_info.max_housepower);
		dataContent.put("carBaseConfig_15", carDetailData.check_conf_info.speaker_num);
		data.put("content", gson.toJson(dataContent));
		mListData.add(data);
		
		data = new HashMap<String, String>();
		data.put("type", "title");
		data.put("content", "主要配置");
		mListData.add(data);
		

		data = new HashMap<String, String>();
		data.put("type", "item");
		data.put("itemName", "4");
		

		dataContent = new HashMap<String, String>();
		if(carDetailData.check_conf_info.check_conf_info.contains("0"))
			dataContent.put("carMainConfig_0", "有");
		else
			dataContent.put("carMainConfig_0", "无");
		if(carDetailData.check_conf_info.check_conf_info.contains("2"))
			dataContent.put("carMainConfig_1", "有");
		else
			dataContent.put("carMainConfig_1", "无");

		if(carDetailData.check_conf_info.check_conf_info.contains("4"))
			dataContent.put("carMainConfig_2", "有");
		else
			dataContent.put("carMainConfig_2", "无");

		if(carDetailData.check_conf_info.check_conf_info.contains("6"))
			dataContent.put("carMainConfig_3", "有");
		else
			dataContent.put("carMainConfig_3", "无");
		if(carDetailData.check_conf_info.check_conf_info.contains("8"))
			dataContent.put("carMainConfig_4", "有");
		else
			dataContent.put("carMainConfig_4", "无");

		if(carDetailData.check_conf_info.check_conf_info.contains("10"))
			dataContent.put("carMainConfig_5", "有");
		else
			dataContent.put("carMainConfig_5", "无");

		if(carDetailData.check_conf_info.check_conf_info.contains("12"))
			dataContent.put("carMainConfig_6", "有");
		else
			dataContent.put("carMainConfig_6", "无");

		if(carDetailData.check_conf_info.check_conf_info.contains("14"))
			dataContent.put("carMainConfig_7", "有");
		else
			dataContent.put("carMainConfig_7", "无");

		if(carDetailData.check_conf_info.check_conf_info.contains("16"))
			dataContent.put("carMainConfig_8", "有");
		else
			dataContent.put("carMainConfig_8", "无");

		if(carDetailData.check_conf_info.check_conf_info.contains("1"))
			dataContent.put("carMainConfig_9", "有");
		else
			dataContent.put("carMainConfig_9", "无");

		if(carDetailData.check_conf_info.check_conf_info.contains("3"))
			dataContent.put("carMainConfig_10", "有");
		else
			dataContent.put("carMainConfig_10", "无");

		if(carDetailData.check_conf_info.check_conf_info.contains("5"))
			dataContent.put("carMainConfig_11", "有");
		else
			dataContent.put("carMainConfig_11", "无");

		if(carDetailData.check_conf_info.check_conf_info.contains("7"))
			dataContent.put("carMainConfig_12", "有");
		else
			dataContent.put("carMainConfig_12", "无");

		if(carDetailData.check_conf_info.check_conf_info.contains("9"))
			dataContent.put("carMainConfig_13", "有");
		else
			dataContent.put("carMainConfig_13", "无");

		if(carDetailData.check_conf_info.check_conf_info.contains("11"))
			dataContent.put("carMainConfig_14", "有");
		else
			dataContent.put("carMainConfig_14", "无");

		if(carDetailData.check_conf_info.check_conf_info.contains("13"))
			dataContent.put("carMainConfig_15", "有");
		else
			dataContent.put("carMainConfig_15", "无");

		if(carDetailData.check_conf_info.check_conf_info.contains("15"))
			dataContent.put("carMainConfig_16", "有");
		else
			dataContent.put("carMainConfig_16", "无");

		if(carDetailData.check_conf_info.check_conf_info.contains("17"))
			dataContent.put("carMainConfig_17", "有");
		else
			dataContent.put("carMainConfig_17", "无");

		data.put("content", gson.toJson(dataContent));
		mListData.add(data);
			
		mAdapter.notifyDataSetChanged();
		timeChangeRun();
		initialize();
	}
	
	
	private void timeChangeRun() {
		myApplication = (MyApplication) getActivity().getApplication();
		myApplication.startTime();
		myApplication.setOnTimeListener(new timeListener() {
		@Override
		public void timeRunListener(boolean state) {
			// TODO Auto-generated method stub
//
//			if (carDetailData.status.equals("1")) {
				int nNowTime = Integer.valueOf(carDetailData.now);
				nNowTime++;
				carDetailData.now = nNowTime + "";
				ContrastTime(nNowTime , Integer.valueOf(carDetailData.start_time) , Integer.valueOf(carDetailData.end_time),carDetailData.status);
//				}
			}
		});
		
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
	public String ContrastTime(int nNowTime , int nStartTime , int nEndTime ,String state) {
		int nTime = 0;
		SimpleDateFormat sFormat = null;

		int nSecond;
		int nMinute;
		int nHour;
		int nDay;
		nTime = nStartTime - nNowTime;
		//判断是否开始竞拍
		if(nStartTime > nNowTime)//等待开始
		{
			nTime = nStartTime - nNowTime;
			nSecond = nTime%60;
			nMinute = (nTime/60)%60;
			nHour = (nTime/(60*60))%24;
			nDay = (nTime/(60*60))/24;
			DecimalFormat df = new DecimalFormat("00");
			if(nDay <= 0)
				textTime.setText("距开始："+df.format(nHour)+":"+df.format(nMinute)+":"+df.format(nSecond));
			else
				textTime.setText("距开始："+nDay+"天"+df.format(nHour)+":"+df.format(nMinute)+":"+df.format(nSecond));
			return "wait";
		}else if(nEndTime > nNowTime && nNowTime > nStartTime)//正在竞拍
		{
			nTime = nEndTime - nNowTime;
			nSecond = nTime%60;
			nMinute = (nTime/60)%60;
			nHour = (nTime/(60*60))%24;
			nDay = (nTime/(60*60))/24;
			DecimalFormat df = new DecimalFormat("00");
			if(nDay <= 0)
				textTime.setText("离结束："+df.format(nHour)+":"+df.format(nMinute)+":"+df.format(nSecond));
			else
				textTime.setText("离结束："+nDay+"天"+df.format(nHour)+":"+df.format(nMinute)+":"+df.format(nSecond));
			return "underway";
		}else if(0 >= nEndTime - nNowTime)
		{
			textTime.setText("已结束");
			return "end";
		}else
		{
			return null;
		}
	}
	
	

	private List<ImageView> views = new ArrayList<ImageView>();
	private List<ADInfo> infos = new ArrayList<ADInfo>();
	private CycleViewPager cycleViewPager;
	
	@SuppressLint("NewApi")
	private void initialize() {
		
		cycleViewPager = (CycleViewPager)getActivity().getFragmentManager().findFragmentById(R.id.fragment_cycle_viewpager_content);

		
		for(int i = 0; i < carDetailData.img.size(); i ++){
			ADInfo info = new ADInfo();
			info.setUrl(
					carDetailData.img.get(i).url);
			info.setContent("图片-->" + 
					carDetailData.img.get(i).name );
			infos.add(info);
		}
		
		// 将最后一个ImageView添加进来.setBackgroundResource(R.drawable.img_detail_banner_bg)
		views.add(ViewFactory.getImageView(getActivity(), infos.get(infos.size() - 1).getUrl()));
		for (int i = 0; i < infos.size(); i++) {
			views.add(ViewFactory.getImageView(getActivity(), infos.get(i).getUrl()));
		}
		// 将第一个ImageView添加进来
		views.add(ViewFactory.getImageView(getActivity(), infos.get(0).getUrl()));
		
		// 设置循环，在调用setData方法前调用
		cycleViewPager.setCycle(true);

		// 在加载数据前设置是否循环
		cycleViewPager.setData(views, infos, mAdCycleViewListener);
		//设置轮播
		cycleViewPager.setWheel(true);

	    // 设置轮播时间，默认5000ms
		cycleViewPager.setTime(5000);
		//设置圆点指示图标组居中显示，默认靠右
		cycleViewPager.setIndicatorCenter();
	}
	
	private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {

		@Override
		public void onImageClick(ADInfo info, int position, View imageView) {
			if (cycleViewPager.isCycle()) {
				position = position - 1;

				String[] images = new String [carDetailData.img.size()];
				String[] imageNmes = new String [carDetailData.img.size()];
				for(int i = 0 ; i < images.length ; i ++)
				{
					images[i] = carDetailData.img.get(i).url;
					imageNmes[i] = carDetailData.img.get(i).name;
				}
				ImagePreviewActivity.showImagePrivew(getActivity(), position, images, imageNmes);
			}
			
		}

	};
	
	
	/***
	 * 点击响应
	 */
	private OnClickListener onClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.layAuction:
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				intent.putExtra("type", "detail");
				startActivityForResult(intent , 10000);
				break;
			default:
				break;
			}
		}
	};
}
