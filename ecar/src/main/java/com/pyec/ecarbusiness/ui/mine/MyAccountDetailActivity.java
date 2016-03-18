package com.pyec.ecarbusiness.ui.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pyec.ecarbusiness.R;
import com.pyec.ecarbusiness.baseClass.BaseActivity;
import com.pyec.ecarbusiness.baseClass.MyApplication;

public class MyAccountDetailActivity extends BaseActivity{

	private String type;
	private MyApplication myApplication;
	private TextView textAccountBalance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_account_detail);
		myApplication = (MyApplication)getApplication();
		
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        initView();
	}
	
	private void initView(){
		LinearLayout layBack = (LinearLayout)findViewById(R.id.layBack);
		layBack.setOnClickListener(onClickListener);

		Button btnPhone = (Button)findViewById(R.id.btnPhone);
		btnPhone.setOnClickListener(onClickListener);
		

		Button btn = (Button)findViewById(R.id.btnRecharge);
		btn.setOnClickListener(onClickListener);
		btn = (Button)findViewById(R.id.btnWithdrawals);
		btn.setOnClickListener(onClickListener);
		
		textAccountBalance = (TextView)findViewById(R.id.textAccountBalance);
		
		ImageView img = (ImageView)findViewById(R.id.imgIcon);
		TextView text = (TextView)findViewById(R.id.textAccountType);
		if(type.endsWith("pay"))
		{
			text.setText(R.string.payAccount);
			img.setBackgroundResource(R.drawable.account_pay);
			textAccountBalance.setText(myApplication.getPayBalance());
		}
		else
		{
			text.setText(R.string.auctionAccount);
			img.setBackgroundResource(R.drawable.auction_account);
			textAccountBalance.setText(myApplication.getAuctionBalance());
		}
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
			case R.id.btnRecharge:
				intent = new Intent(MyAccountDetailActivity.this,RechargeActivity.class);
				intent.putExtra("type", type);
				startActivity(intent);
				break;
			case R.id.btnWithdrawals:
				intent = new Intent(MyAccountDetailActivity.this,WithdrawalsActivity.class);
				intent.putExtra("type", type);
				startActivity(intent);
				break;
			default:
				break;
			}
		}
	};
}
