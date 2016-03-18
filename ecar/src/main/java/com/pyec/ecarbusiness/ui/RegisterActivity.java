package com.pyec.ecarbusiness.ui;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.pyec.ecarbusiness.R;
import com.pyec.ecarbusiness.baseClass.BaseActivity;
import com.pyec.ecarbusiness.baseClass.MyApplication;
import com.pyec.ecarbusiness.baseClass.MyApplication.timeListener;
import com.pyec.ecarbusiness.httpClient.AppHttpInterface;

public class RegisterActivity extends BaseActivity{

	private CheckBox cBox;
	private MyApplication myApplication;
	private EditText editAccount;
	private EditText editPassword;
	private EditText editVerificationCode;
	private TextView textGetVerificationCode;
	private Gson gson = new Gson();
	private int time = 30;
	private boolean getState = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		myApplication = (MyApplication)getApplication();
		initView();
	}
	
	
	private void initView(){
		
		LinearLayout layout = (LinearLayout)findViewById(R.id.layBack);
		layout.setOnClickListener(onClickListener);
		
		Button btn = (Button)findViewById(R.id.btnPhone);
		btn.setOnClickListener(onClickListener);
		
		btn = (Button)findViewById(R.id.btnSureSubmit);
		btn.setOnClickListener(onClickListener);
		
		
		
		textGetVerificationCode = (TextView)findViewById(R.id.textGetVerificationCode);
		textGetVerificationCode.setOnClickListener(onClickListener);
		
		TextView text = (TextView)findViewById(R.id.textAgreement);
		text.setOnClickListener(onClickListener);
		
		cBox = (CheckBox)findViewById(R.id.cBox);
		
		editAccount = (EditText)findViewById(R.id.editAccount);
		editPassword = (EditText)findViewById(R.id.editPassword);
		editVerificationCode = (EditText)findViewById(R.id.editVerificationCode);
	}
	
	/***
	 * 点击响应
	 */
	private OnClickListener onClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.layBack:
				Intent userData = new Intent();
				setResult(0, userData); // 设置返回数据
				finish();
				break;
			case R.id.btnPhone:
				Intent intent = new Intent();
				intent.setAction("android.intent.action.CALL");
				intent.setData(Uri.parse("tel:"+myApplication.phoneNum));
				startActivity(intent);
				break;
			case R.id.btnSureSubmit:
				if(!cBox.isClickable())
				{
					Toast.makeText(RegisterActivity.this, "请勾选《朋友e车商户端协议》", Toast.LENGTH_SHORT).show();
					return;
				}
				if(editAccount.getText().toString() == null ||editAccount.getText().toString().equals(""))
				{
					Toast.makeText(RegisterActivity.this, "手机号不能为空！", Toast.LENGTH_SHORT).show();
					return;
				}
				if(editVerificationCode.getText().toString() == null ||editVerificationCode.getText().toString().equals(""))
				{
					Toast.makeText(RegisterActivity.this, "验证码不能为空！", Toast.LENGTH_SHORT).show();
					return;
				}
				if(editPassword.getText().toString() == null ||editPassword.getText().toString().equals(""))
				{
					Toast.makeText(RegisterActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
					return;
				}
		        AppHttpInterface.postRegisterData(editAccount.getText().toString(),editPassword.getText().toString(),
		        		editVerificationCode.getText().toString(), mHandlerRegisterResult);
				break;
			case R.id.textGetVerificationCode:
				if(getState)
				{
					if(editAccount.getText().toString() == null ||editAccount.getText().toString().equals(""))
					{
						Toast.makeText(RegisterActivity.this, "手机号不能为空！", Toast.LENGTH_SHORT).show();
						return;
					}
			        AppHttpInterface.getRegisterVerificationCode(editAccount.getText().toString(), mHandlerGetVerificationCode);
				}
				break;
			case R.id.textAgreement:
				intent = new Intent(RegisterActivity.this,WebViewActivity.class);
				startActivity(intent);
				break;
			default:
				break;
			}
		}
	};
	
	
	protected TextHttpResponseHandler mHandlerGetVerificationCode = new TextHttpResponseHandler() {

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
				{
					Toast.makeText(RegisterActivity.this, data.get("resMsg"), Toast.LENGTH_SHORT).show();
				}
				else
				{
					getState = false;
					timeChangeRun();
				}
			}
		}

		@Override
		public void onFailure(int arg0, Header[] arg1, String arg2,
				Throwable arg3) {
			Toast.makeText(RegisterActivity.this, "服务器连接失败！", Toast.LENGTH_SHORT).show();
		}
	};
	
	
	protected TextHttpResponseHandler mHandlerRegisterResult = new TextHttpResponseHandler() {

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
				{
					Toast.makeText(RegisterActivity.this, data.get("resMsg"), Toast.LENGTH_SHORT).show();
				}
				else
				{
					Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
					Intent userData = new Intent();
					userData.putExtra("userName", editAccount.getText().toString());
					userData.putExtra("userPwd", editPassword.getText().toString());
					setResult(1, userData); // 设置返回数据
					finish();
				}
			}
		}

		@Override
		public void onFailure(int arg0, Header[] arg1, String arg2,
				Throwable arg3) {
			Toast.makeText(RegisterActivity.this, "服务器连接失败！", Toast.LENGTH_SHORT).show();
		}
	};
	
	
	
	private void timeChangeRun() {
		myApplication = (MyApplication)getApplication();
		myApplication.startTime();
		myApplication.setOnTimeListener(new timeListener() {
		@Override
		public void timeRunListener(boolean state) {
			// TODO Auto-generated method stub
			if(getState)
			{
				return;
			}
			if(time >= 1)
			{
				time--;
				textGetVerificationCode.setText(time+"s");
				textGetVerificationCode.setBackgroundColor(getResources().getColor(R.color.grey_666666));
			}
			else
			{
				getState = true;
				time = 30;
				textGetVerificationCode.setText(getResources().getString(R.string.getVerificationCode));
				textGetVerificationCode.setBackgroundColor(getResources().getColor(R.color.red_FF3300));
			}
			}
		});
		
	}
}
