package com.pyec.ecarbusiness.ui;

import java.util.ArrayList;
import java.util.List;

import com.pyec.ecarbusiness.R;
import com.pyec.ecarbusiness.baseClass.BaseFragment;
import com.pyec.ecarbusiness.ui.auction.FragmentAuctionFinish;
import com.pyec.ecarbusiness.ui.auction.FragmentAuctionNow;
import com.pyec.ecarbusiness.ui.mine.MyAuctionActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class FragmentAuctionNew extends BaseFragment {

	private RadioGroup mRadioGroup;
	private RadioButton mRadio01;
	private RadioButton mRadio02;

	private ViewPager mViewPager;
	private TabAdapter mAdapter;
	private List<Fragment> mFragments = new ArrayList<Fragment>();
	
	FragmentTabHost mTabHost = null;


	FragmentAuctionNow fragmentAuctionNow ;
	FragmentAuctionFinish fragmentAuctionFinish;
	
	private View mView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mView = inflater.inflate(R.layout.fragment_auction_new, null);

		initView();
		initEvent();
		return mView;

	}
	
	private void initView() {
		TextView text = (TextView)mView.findViewById(R.id.txtMyAuction);
		text.setOnClickListener(onClickListener);
		
    	mRadioGroup = (RadioGroup)mView.findViewById(R.id.id_radioGroup);
		mRadio01 = (RadioButton)mView.findViewById(R.id.id_tab1);
		mRadio02 = (RadioButton)mView.findViewById(R.id.id_tab2);


		fragmentAuctionNow = new FragmentAuctionNow();
		fragmentAuctionFinish = new FragmentAuctionFinish();
		
		mFragments.add(fragmentAuctionNow);
		mFragments.add(fragmentAuctionFinish);
		mViewPager=(ViewPager)mView.findViewById(R.id.id_viewpager);
		mViewPager.setOffscreenPageLimit(1);
		mAdapter=new TabAdapter(getChildFragmentManager(), mFragments);
		mViewPager.setAdapter(mAdapter);

	}
    
    private void initEvent() {
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.id_tab1:
					mRadio01.setBackgroundResource(R.drawable.radio_bg_select);
					mRadio02.setBackgroundResource(R.drawable.radio_bg_unselect);
					
					mRadio01.setTextColor(getResources().getColor(R.color.red_FF3300));
					mRadio02.setTextColor(getResources().getColor(R.color.grey_333333));
					mViewPager.setCurrentItem(0);// 选择某一页
					break;
				case R.id.id_tab2:
					mRadio01.setBackgroundResource(R.drawable.radio_bg_unselect);
					mRadio02.setBackgroundResource(R.drawable.radio_bg_select);

					mRadio02.setTextColor(getResources().getColor(R.color.red_FF3300));
					mRadio01.setTextColor(getResources().getColor(R.color.grey_333333));
					mViewPager.setCurrentItem(1);
					break;
				}

			}
		});
		mViewPager.setOnPageChangeListener(new TabOnPageChangeListener());
	}
	
    /**
     * 功能：主页引导栏的三个Fragment页面设置适配器
     */
    public class TabAdapter extends FragmentPagerAdapter {
    	private List<Fragment> mFragments;

    	public TabAdapter(FragmentManager fm, List<Fragment> mFragments) {
    		super(fm);
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
			case R.id.btnSearch:
				break;
			case R.id.txtMyAuction:
				Intent  intent = new Intent(getActivity(),MyAuctionActivity.class);
				startActivity(intent);
				break;
			default:
				break;
			}
		}
	};
}