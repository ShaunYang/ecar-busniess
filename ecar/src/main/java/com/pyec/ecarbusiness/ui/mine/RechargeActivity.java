package com.pyec.ecarbusiness.ui.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.pyec.ecarbusiness.R;
import com.pyec.ecarbusiness.baseClass.BaseActivity;
import com.pyec.ecarbusiness.baseClass.MyApplication;

public class RechargeActivity extends BaseActivity{

	private ImageView imgAliPaySelect;
	private ImageView imgWeiXinSelect;
	
	private EditText editRecharge;

	private String type;

	private MyApplication myApplication;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recharge);
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

		editRecharge = (EditText)findViewById(R.id.editRechargeCnt);
		
		imgAliPaySelect = (ImageView)findViewById(R.id.imgAliPaySatate);
		imgWeiXinSelect = (ImageView)findViewById(R.id.imgWeiXinSatate);
		
		RelativeLayout lay = (RelativeLayout)findViewById(R.id.layAliPay);
		lay.setOnClickListener(onClickListener);
		lay = (RelativeLayout)findViewById(R.id.layWeiXinPay);
		lay.setOnClickListener(onClickListener);
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
			case R.id.layAliPay:
				imgAliPaySelect.setBackgroundResource(R.drawable.pay_select_img);
				imgWeiXinSelect.setBackgroundResource(R.drawable.pay_unselect_img);
				break;
			case R.id.layWeiXinPay:
				imgWeiXinSelect.setBackgroundResource(R.drawable.pay_select_img);
				imgAliPaySelect.setBackgroundResource(R.drawable.pay_unselect_img);
				break;
			default:
				break;
			}
		}
	};
}
