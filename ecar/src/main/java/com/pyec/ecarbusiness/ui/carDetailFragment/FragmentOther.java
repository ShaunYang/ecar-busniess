package com.pyec.ecarbusiness.ui.carDetailFragment;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

import com.google.gson.Gson;
import com.pyec.ecarbusiness.R;
import com.pyec.ecarbusiness.baseClass.BaseFragment;
import com.pyec.ecarbusiness.baseClass.MyApplication;
import com.pyec.ecarbusiness.ui.carDetailFragment.FragmentAppearance.OnTabChangedListener;

public class FragmentOther extends BaseFragment {

	private MyApplication myApplication;
	private Gson gson = new Gson();

	Map<String, String> data = new HashMap<String, String>();

    //定义FragmentTabHost对象  
    private FragmentTabHost mTabHost;  
            
    //定义一个布局  
    private LayoutInflater layoutInflater;  
                
    //定义数组来存放Fragment界面  
    private Class fragmentArray[] = {FragmentOtherFront.class,FragmentOtherBehind.class,FragmentOtherBottom.class};  
     
    //Tab选项卡的文字  
    private String mTextviewArray[] = {"车头机舱","后备箱","底盘"};
    
	private View mView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mView = inflater.inflate(R.layout.car_fragment_apparence, null);
		 initView();
		return mView;

	}
	
	/**
	 * 初始化组件
	 */
	private void initView() {
		
				
		// 实例化布局对象
		layoutInflater = LayoutInflater.from(getActivity());

		// 实例化TabHost对象，得到TabHost
		mTabHost = (FragmentTabHost)mView.findViewById(android.R.id.tabhost);
		mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

		// 得到fragment的个数
		int count = fragmentArray.length;

		for (int i = 0; i < count; i++) {
			// 为每一个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i])
					.setIndicator(getTabItemView(i));
			// 将Tab按钮添加进Tab选项卡中
			mTabHost.addTab(tabSpec, fragmentArray[i], null);
			// 设置Tab按钮的背景
			// mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
			mTabHost.setOnTabChangedListener(new OnTabChangedListener());
		}
	}

	class OnTabChangedListener implements OnTabChangeListener {
		public void onTabChanged(String tabId) {
			mTabHost.setCurrentTabByTag(tabId);

			for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
				TextView textView = (TextView) mTabHost.getTabWidget()
						.getChildAt(i).findViewById(R.id.textview); // 设置字体和风格
				if (mTabHost.getCurrentTab() == i) {// 选中
					textView.setTextColor(getResources().getColorStateList(
							R.color.red_FF3300));
				} else {// 不选中
					textView.setTextColor(getResources().getColorStateList(
							R.color.grey_333333));
				}
			}
		}
	}

	/**
	 * 给Tab按钮设置图标和文字
	 */
	private View getTabItemView(int index) {
		View view = null;
		TextView textView = null;
		switch (index) {
		case 0:
			view = layoutInflater.inflate(R.layout.auction_page_tab, null);
			textView = (TextView) view.findViewById(R.id.textview);
			break;
		case 1:
			view = layoutInflater.inflate(R.layout.auction_page_tab, null);
			textView = (TextView) view.findViewById(R.id.textview);
			break;
		case 2:
			view = layoutInflater.inflate(R.layout.auction_page_tab, null);
			textView = (TextView) view.findViewById(R.id.textview);
			break;
		}
		textView.setText(mTextviewArray[index]);
		return view;
	}
}
