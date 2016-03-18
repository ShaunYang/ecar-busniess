package com.pyec.ecarbusiness.ui.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pyec.ecarbusiness.R;
import com.pyec.ecarbusiness.baseClass.BaseActivity;
import com.pyec.ecarbusiness.baseClass.MyApplication;

public class MyAccountActivity extends BaseActivity{


	private MyApplication myApplication;
	private TextView textPayBalance;
	private TextView textAuctionBalance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_account);
		myApplication = (MyApplication)getApplication();
		initView();
		
	}
	
	private void initView(){
		LinearLayout layBack = (LinearLayout)findViewById(R.id.layBack);
		layBack.setOnClickListener(onClickListener);

		Button btnPhone = (Button)findViewById(R.id.btnPhone);
		btnPhone.setOnClickListener(onClickListener);
		
		
		RelativeLayout lay = (RelativeLayout)findViewById(R.id.layAuctionAccount);
		lay.setOnClickListener(onClickListener);
		lay = (RelativeLayout)findViewById(R.id.layPayAccount);
		lay.setOnClickListener(onClickListener);
		textPayBalance = (TextView)findViewById(R.id.textPayBalance);
		textPayBalance.setText(myApplication.getPayBalance());
		textAuctionBalance = (TextView)findViewById(R.id.textAuctonBalance);
		textAuctionBalance.setText(myApplication.getAuctionBalance());
	}
	
	
	/***
	 * 点击响应
	 */
	private OnClickListener onClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = null; 
			switch (v.getId()) {
			case R.id.layBack:
				finish();
				break;
			case R.id.btnPhone:
				intent = new Intent();
				intent.setAction("android.intent.action.CALL");
				intent.setData(Uri.parse("tel:"+myApplication.phoneNum));
				startActivity(intent);
				break;
			case R.id.layPayAccount:
				intent = new Intent(MyAccountActivity.this, MyAccountDetailActivity.class);
				intent.putExtra("type", "pay");
				intent.putExtra("balance", textPayBalance.getText());
				startActivity(intent);
				break;
			case R.id.layAuctionAccount:
				intent = new Intent(MyAccountActivity.this, MyAccountDetailActivity.class);
				intent.putExtra("type", "auction");
				intent.putExtra("balance", textAuctionBalance.getText());
				startActivity(intent);
				break;
			default:
				break;
			}
		}
	};
	
}
