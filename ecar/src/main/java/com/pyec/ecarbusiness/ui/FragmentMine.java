package com.pyec.ecarbusiness.ui;

import com.pyec.ecarbusiness.R;
import com.pyec.ecarbusiness.baseClass.MyApplication;
import com.pyec.ecarbusiness.ui.mine.MyAccountActivity;
import com.pyec.ecarbusiness.ui.mine.MyAuctionActivity;
import com.pyec.ecarbusiness.ui.mine.PerfectDataActivity;
import com.pyec.ecarbusiness.ui.mine.SettingActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FragmentMine extends Fragment {

	private View mView;
	private MyApplication myApplication;
	
	
	private String payBalance;
	private String auctionBalance;
	
	private TextView textAuctionBalance;
	private TextView textBuyBalance;
	private TextView textUserName;
	private LinearLayout layUserAccount;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_mine, container, false);
		myApplication = (MyApplication)getActivity().getApplication();
		initView();
		setUserData();
		return mView;
	}
	
	private void initView()
	{
		layUserAccount = (LinearLayout)mView.findViewById(R.id.layUserAccount);
		textUserName = (TextView)mView.findViewById(R.id.textUserName);
		textAuctionBalance = (TextView)mView.findViewById(R.id.textAuctionBalance);
		textBuyBalance = (TextView)mView.findViewById(R.id.textBuyBalance);
		RelativeLayout rLay = (RelativeLayout)mView.findViewById(R.id.layUserData);
		LinearLayout.LayoutParams linearParams =  (LinearLayout.LayoutParams)rLay.getLayoutParams();
		int wdith =  getActivity().getWindowManager().getDefaultDisplay().getWidth();
		int hight = (int)(wdith*(260.0/720.0));
		linearParams.width =  wdith;
        linearParams.height = hight;
        rLay.setLayoutParams(linearParams);
		rLay.setOnClickListener(onClickListener);
		
		LinearLayout lLay = (LinearLayout)mView.findViewById(R.id.layBack);

		lLay.setOnClickListener(onClickListener);
		
		lLay = (LinearLayout)mView.findViewById(R.id.layAuctionRecord);
		lLay.setOnClickListener(onClickListener);
		lLay = (LinearLayout)mView.findViewById(R.id.layMyAccount);
		lLay.setOnClickListener(onClickListener);
		lLay = (LinearLayout)mView.findViewById(R.id.layHurryBuyAndSell);
		lLay.setOnClickListener(onClickListener);
		lLay = (LinearLayout)mView.findViewById(R.id.layPerfectData);
		lLay.setOnClickListener(onClickListener);
		lLay  = (LinearLayout)mView.findViewById(R.id.laySetting);
		lLay.setOnClickListener(onClickListener);
		lLay = (LinearLayout)mView.findViewById(R.id.layShareGift);
		lLay.setOnClickListener(onClickListener);
	}
	
	
	/***
	 * 点击响应
	 */
	private OnClickListener onClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			

			Intent intent = null; 
			switch (v.getId()) {
			case R.id.btnPhone:
				intent = new Intent();
				intent.setAction("android.intent.action.CALL");
				intent.setData(Uri.parse("tel:"+myApplication.phoneNum));
				startActivity(intent);
				break;
			case R.id.layUserData:
				if(judgeLoginState())
				{
				}else
				{
					return;
				}
				break;
			case R.id.layAuctionRecord:
				if(judgeLoginState())
				{
					intent = new Intent(getActivity(),MyAuctionActivity.class);
					startActivity(intent);
				}else
				{
					return;
				}
				break;
			case R.id.layMyAccount:
//				if(judgeLoginState())
//				{
//					
//				}else
//				{
//					return;
//				}

				intent = new Intent(getActivity(),MyAccountActivity.class);
				startActivity(intent);
				break;
			case R.id.layHurryBuyAndSell:
				if(judgeLoginState())
				{
					
				}else
				{
					return;
				}
				break;
			case R.id.layPerfectData:
//				if(judgeLoginState())
//				{
//					
//				}else
//				{
//					return;
//				}

				intent = new Intent(getActivity(),PerfectDataActivity.class);
				startActivity(intent);
				break;
			case R.id.laySetting:

				intent = new Intent(getActivity(),SettingActivity.class);
				startActivity(intent);
				break;
			case R.id.layShareGift:
				if(judgeLoginState())
				{
					
				}else
				{
					return;
				}
				break;
			default:
				break;
			}
		}
	};
	
	
	private boolean judgeLoginState()
	{
		if(myApplication.getToken().equals(""))
		{
			Intent intent = new Intent(getActivity(),LoginActivity.class);
			intent.putExtra("type", "mine");
			startActivityForResult(intent, 1002);
			return false;
		}
		else
			return true;
	}
	
	
	
	private void setUserData(){
		if(!myApplication.getToken().equals(""))
		{
			textAuctionBalance.setText(myApplication.getAuctionBalance());
			textBuyBalance.setText(myApplication.getPayBalance());
			textUserName.setText(myApplication.getUserAccount());
			layUserAccount.setVisibility(View.VISIBLE);
		}
		else
		{
			textUserName.setText(getResources().getString(R.string.clickToLogin));
			layUserAccount.setVisibility(View.GONE);
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(resultCode == 1)
		{
			 setUserData();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	
}
