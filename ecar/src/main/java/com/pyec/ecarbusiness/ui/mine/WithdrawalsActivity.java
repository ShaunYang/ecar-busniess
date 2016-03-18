package com.pyec.ecarbusiness.ui.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pyec.ecarbusiness.R;
import com.pyec.ecarbusiness.baseClass.BaseActivity;
import com.pyec.ecarbusiness.baseClass.MyApplication;

public class WithdrawalsActivity extends BaseActivity{

	private String type;
	private TextView textBalance;
	private MyApplication myApplication;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdrawals);
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
		
		
		textBalance = (TextView)findViewById(R.id.textBalance);
		TextView textTitle = (TextView)findViewById(R.id.textTitle);
		if(type.endsWith("pay"))
		{
			textTitle.setText(getResources().getString(R.string.payAccount));
			textBalance.setText(myApplication.getPayBalance());
		}
		else
		{

			textTitle.setText(getResources().getString(R.string.auctionAccount));
			textBalance.setText(myApplication.getAuctionBalance());
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
			default:
				break;
			}
		}
	};
}
