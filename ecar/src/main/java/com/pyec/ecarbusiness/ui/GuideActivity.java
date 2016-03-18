package com.pyec.ecarbusiness.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.pyec.ecarbusiness.R;
import com.pyec.ecarbusiness.adapter.GuideAdapter;
import com.pyec.ecarbusiness.baseClass.BaseActivity;
import com.pyec.ecarbusiness.baseClass.MyApplication;
import com.pyec.ecarbusiness.httpClient.AppHttpInterface;

public class GuideActivity extends BaseActivity{

	private Gson gson = new Gson();

	private MyApplication myApplication;
	private ViewPager mPager;// 页卡内容
	private ArrayList<View> listViews; // Tab页面列表
	GuideAdapter adapter;
	LayoutInflater mInflater;
	
	private boolean isFirstLogin;
	private String userAccount;
	private String userPwd;
	
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);

		myApplication = (MyApplication)getApplication();
		initData();
		initPageView();
		
		
	}

	private void initPageView() {
		mInflater = getLayoutInflater();
		listViews = new ArrayList<View>();
		if (isFirstLogin) {//判断是否第一次登入 
			listViews.add(mInflater.inflate(R.layout.guide_1, null));
			listViews.add(mInflater.inflate(R.layout.guide_2, null));
			listViews.add(mInflater.inflate(R.layout.guide_3, null));
			Button btn = (Button) listViews.get(2).findViewById(R.id.btnStart);
			btn.setOnClickListener(onClickListener);
		} else {
			listViews.add(mInflater.inflate(R.layout.guide_0, null));
		}
		adapter = new GuideAdapter(listViews);
		mPager = (ViewPager) findViewById(R.id.viewPage);
		mPager.setAdapter(adapter);
		mPager.setCurrentItem(0);
		
	}

	/**
	 * 获取用户信息，判断是否有登入
	 */
	private void initData()
	{
		SharedPreferences settings = getSharedPreferences("userInfo", 0); 
		isFirstLogin = settings.getBoolean("isFirstLogin", true);
		userAccount = settings.getString("account", "");
		userPwd = settings.getString("password","");
		
		if((!userAccount.equals(""))&&(!userPwd.equals("")))
		{
	        AppHttpInterface.login(userAccount, userPwd, mHandler);
		}
	}
	
	protected TextHttpResponseHandler mHandler = new TextHttpResponseHandler() {

		@Override
		public void onSuccess(int arg0, Header[] arg1, String resultData) {
			if(resultData.equals("") || resultData == null)
			{
				return;
			}
			else
			{
				Map<String , String> data = new HashMap<String, String>();
				data =  gson.fromJson(resultData, new TypeToken<Map<String , String>>() {}.getType());
				if(data.get("resCode").equals("0"))
					return;
				else
				{
					myApplication.setUserAccount(userAccount);
					myApplication.setToken(data.get("token"));
					myApplication.setPayBalance(data.get("consume_balance"));
					myApplication.setAuctionBalance(data.get("balance"));
					myApplication.setIsVip(data.get("isvip"));
					if(!isFirstLogin)
					{
						Intent intent = new Intent(GuideActivity.this,MainTabActivity.class);
						startActivity(intent);
					}
					finish();
				}
			}
		}

		@Override
		public void onFailure(int arg0, Header[] arg1, String arg2,
				Throwable arg3) {
		}
	};
	/***
	 * 点击响应
	 */
	private OnClickListener onClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = null ; 
			switch (v.getId()) {
			case R.id.btnStart:
				intent = new Intent(GuideActivity.this,MainTabActivity.class);
				startActivity(intent);
			default:
				break;
			}
		}
	};
}
