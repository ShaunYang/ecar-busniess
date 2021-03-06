package com.pyec.ecarbusiness.ui;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.pyec.ecarbusiness.R;
import com.pyec.ecarbusiness.baseClass.BaseFragment;
import com.pyec.ecarbusiness.baseClass.MyApplication;
import com.pyec.ecarbusiness.ui.auction.FragmentAuctionFinish;
import com.pyec.ecarbusiness.ui.auction.FragmentAuctionNow;
import com.pyec.ecarbusiness.ui.carDetailFragment.CarDetailTabActivity;
import com.pyec.ecarbusiness.ui.mine.MyAccountActivity;
import com.pyec.ecarbusiness.ui.mine.MyAuctionActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class FragmentAuction extends BaseFragment {

	private View mView;
	private Activity mActivity;
	private MyApplication myApplication;
	
	private Gson gson = new Gson();
	Map<String, String> data = new HashMap<String, String>();

    //定义FragmentTabHost对象  
    private FragmentTabHost mTabHost;  
            
    //定义一个布局  
    private LayoutInflater layoutInflater;  
                
    //定义数组来存放Fragment界面  
    private Class fragmentArray[] = {FragmentAuctionNow.class,FragmentAuctionFinish.class};  
     
    //Tab选项卡的文字  
    private String mTextviewArray[] = {"正在竞价", "竞价完成"};
    
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mActivity = getActivity();
		mView = inflater.inflate(R.layout.fragment_auction, container, false);
		myApplication = (MyApplication) mActivity.getApplication();
		initView();
		return mView;
	}
	

	/**
	 * 初始化组件
	 */
	private void initView() {
		
		Button btn = (Button)mView.findViewById(R.id.btnSearch);
		btn.setOnClickListener(onClickListener);
		
		TextView text = (TextView)mView.findViewById(R.id.txtMyAuction);
		text.setOnClickListener(onClickListener);
				
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
			textView.setText("正在竞价");
			textView.setTextColor(this.getResources().getColorStateList(R.color.red_FF3300));
			break;
		case 1:
			view = layoutInflater.inflate(R.layout.auction_page_tab, null);
			textView = (TextView) view.findViewById(R.id.textview);
			textView.setText("竞价完成");
			break;
		}
		return view;
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

	protected TextHttpResponseHandler mHandler = new TextHttpResponseHandler() {

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2) {
			// TODO Auto-generated method stub

			data = gson.fromJson(arg2, new TypeToken<Map<String, String>>() {
			}.getType());
			if(data == null || data.get("resCode") == null)
				return;
			Log.d("", data.toString());

		}

		@Override
		public void onFailure(int arg0, Header[] arg1, String arg2,
				Throwable arg3) {
			// TODO Auto-generated method stub

			Toast.makeText(mActivity,"服务器连接中断" , Toast.LENGTH_LONG).show();
		}
	};

}