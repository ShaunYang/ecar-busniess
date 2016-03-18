package com.pyec.ecarbusiness.ui.carDetailFragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.pyec.ecarbusiness.R;
import com.pyec.ecarbusiness.baseClass.DataBaseInterface;
import com.pyec.ecarbusiness.baseClass.MyApplication;
import com.pyec.ecarbusiness.baseClass.PullToRefreshLayout;
import com.pyec.ecarbusiness.dataStruct.AuctionListData;
import com.pyec.ecarbusiness.dataStruct.CarDetailData;
import com.pyec.ecarbusiness.httpClient.AppHttpInterface;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;


public class CarDetailTabActivity extends FragmentActivity {

	private String carId;
	
	private RadioGroup mRadioGroup;
	private RadioButton mRadio01;
	private RadioButton mRadio02;
	private RadioButton mRadio03;
	private RadioButton mRadio04;
	private RadioButton mRadio05;

	private ViewPager mViewPager;
	private TabAdapter mAdapter;
	private List<Fragment> mFragments = new ArrayList<Fragment>();
	
	FragmentTabHost mTabHost = null;

	
	private FragmentCarInfo fragmentCarInfo;

	private MyApplication myApplication;
	private Gson gson = new Gson();


	public static final int LOGIN_ACTIVITY = 10000;// 登入
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail_tab); 
		myApplication = (MyApplication)getApplication();

        Intent intent = getIntent();
        carId = intent.getStringExtra("carId");
        
		initCarDetailData();
	}


	
	private void initView() {
		
		LinearLayout layBack = (LinearLayout)findViewById(R.id.layBack);
		layBack.setOnClickListener(onClickListener);

		Button btnPhone = (Button)findViewById(R.id.btnPhone);
		btnPhone.setOnClickListener(onClickListener);
		
    	mRadioGroup = (RadioGroup)findViewById(R.id.id_radioGroup);
		mRadio01 = (RadioButton)findViewById(R.id.id_tab1);
		mRadio02 = (RadioButton)findViewById(R.id.id_tab2);
		mRadio03 = (RadioButton)findViewById(R.id.id_tab3);
		mRadio04 = (RadioButton)findViewById(R.id.id_tab4);
		mRadio05 = (RadioButton)findViewById(R.id.id_tab5);

		fragmentCarInfo = new FragmentCarInfo();
		
		mFragments.add(new FragmentCarInfo());
		mFragments.add(new FragmentAppearance());
		mFragments.add(new FragmentSkeleton());
		mFragments.add(new FragmentInterior());
		mFragments.add(new FragmentOther());
		mViewPager=(ViewPager)findViewById(R.id.id_viewpager);
		mViewPager.setOffscreenPageLimit(4);
		mAdapter = new TabAdapter(getSupportFragmentManager(), mFragments);
		mViewPager.setAdapter(mAdapter);

	}
    
    private void initEvent() {
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.id_tab1:
					mRadio01.setTextColor(getResources().getColor(R.color.white_FFFFFF));
					mRadio02.setTextColor(getResources().getColor(R.color.grey_333333));
					mRadio03.setTextColor(getResources().getColor(R.color.grey_333333));
					mRadio04.setTextColor(getResources().getColor(R.color.grey_333333));
					mRadio05.setTextColor(getResources().getColor(R.color.grey_333333));
					mViewPager.setCurrentItem(0);// 选择某一页
					break;
				case R.id.id_tab2:
					mRadio01.setTextColor(getResources().getColor(R.color.grey_333333));
					mRadio02.setTextColor(getResources().getColor(R.color.white_FFFFFF));
					mRadio03.setTextColor(getResources().getColor(R.color.grey_333333));
					mRadio04.setTextColor(getResources().getColor(R.color.grey_333333));
					mRadio05.setTextColor(getResources().getColor(R.color.grey_333333));
					mViewPager.setCurrentItem(1);
					break;
				case R.id.id_tab3:
					mRadio01.setTextColor(getResources().getColor(R.color.grey_333333));
					mRadio02.setTextColor(getResources().getColor(R.color.grey_333333));
					mRadio03.setTextColor(getResources().getColor(R.color.white_FFFFFF));
					mRadio04.setTextColor(getResources().getColor(R.color.grey_333333));
					mRadio05.setTextColor(getResources().getColor(R.color.grey_333333));
					mViewPager.setCurrentItem(2);
					break;
				case R.id.id_tab4:
					mRadio01.setTextColor(getResources().getColor(R.color.grey_333333));
					mRadio02.setTextColor(getResources().getColor(R.color.grey_333333));
					mRadio03.setTextColor(getResources().getColor(R.color.grey_333333));
					mRadio04.setTextColor(getResources().getColor(R.color.white_FFFFFF));
					mRadio05.setTextColor(getResources().getColor(R.color.grey_333333));
					mViewPager.setCurrentItem(3);
					break;
				case R.id.id_tab5:
					mRadio01.setTextColor(getResources().getColor(R.color.grey_333333));
					mRadio02.setTextColor(getResources().getColor(R.color.grey_333333));
					mRadio03.setTextColor(getResources().getColor(R.color.grey_333333));
					mRadio04.setTextColor(getResources().getColor(R.color.grey_333333));
					mRadio05.setTextColor(getResources().getColor(R.color.white_FFFFFF));
					mViewPager.setCurrentItem(4);
					break;
				}

			}
		});
		mViewPager.setOnPageChangeListener(new TabOnPageChangeListener());
	}
	
    
    private void initCarDetailData()
    {
		AppHttpInterface.getCarDetailData(carId,myApplication.getToken(), mHandlerAll);
    }
    
	protected TextHttpResponseHandler mHandlerAll = new TextHttpResponseHandler() {

		@Override
		public void onSuccess(int arg0, Header[] arg1, String resultData) {
			if(resultData.equals("") || resultData == null)
				return;
			else
			{
				DataBaseInterface dbInterface = new DataBaseInterface(CarDetailTabActivity.this,"app_data");
				dbInterface.delCarData("carData");
				dbInterface.saveCarData(resultData);
				
				mFragments.clear();
				initView();
				initEvent();
			}
		}

		@Override
		public void onFailure(int arg0, Header[] arg1, String arg2,
				Throwable arg3) {
			Toast.makeText(CarDetailTabActivity.this, "服务器连接中断", Toast.LENGTH_LONG).show();
		}
	};
    
    
    
    /**
     * 功能：主页引导栏的三个Fragment页面设置适配器
     */
    public class TabAdapter extends FragmentPagerAdapter {
		private List<Fragment> mFragments;
    	public TabAdapter(FragmentManager fm ,List<Fragment> mFragments) {
			super(fm);
			// TODO Auto-generated constructor stub
    		this.mFragments = mFragments;
		}

    	public Fragment getItem(int position) {
    		return mFragments.get(position);
    	}

    	public int getCount() {
    		return mFragments.size();
    	}
    }
    
    
    /**
	 * 页卡滑动改变事件  
	 */
	public class TabOnPageChangeListener implements OnPageChangeListener {

		/**
		 * 当滑动状态改变时调用  
		 * state=0的时候表示什么都没做，就是停在那
		 * state=1的时候表示正在滑动 
		 * state==2的时候表示滑动完毕了
		 */
		public void onPageScrollStateChanged(int state) {

		}

		/**
		 * 当前页面被滑动时调用   
		 * position:当前页面
		 * positionOffset:当前页面偏移的百分比
		 * positionOffsetPixels:当前页面偏移的像素位置  
		 */
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		}

		//当新的页面被选中时调用  
		public void onPageSelected(int position) {
			switch (position) {
			case 0:
				mRadio01.setChecked(true);
				break;
			case 1:
				mRadio02.setChecked(true);    
				break;
			case 2:
				mRadio03.setChecked(true);
				break;
			case 3:
				mRadio04.setChecked(true);    
				break;
			case 4:
				mRadio05.setChecked(true);
				break;
			}
		}
	}	
	
	
	/***
	 * 点击响应
	 */
	private OnClickListener onClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.layBack:
				finish();
				break;
			case R.id.btnPhone:
				Intent intent = new Intent();
				intent.setAction("android.intent.action.CALL");
				intent.setData(Uri.parse("tel:"+myApplication.phoneNum));
				startActivity(intent);
				break;
			default:
				break;
			}
		}
	};
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if(requestCode == 10000 && resultCode == 1)
		{
			initCarDetailData();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}