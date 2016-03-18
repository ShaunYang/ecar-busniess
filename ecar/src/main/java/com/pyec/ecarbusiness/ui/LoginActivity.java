package com.pyec.ecarbusiness.ui;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
import com.pyec.ecarbusiness.dataStruct.CarDetailData;
import com.pyec.ecarbusiness.httpClient.AppHttpInterface;

public class LoginActivity extends BaseActivity{

	private MyApplication myApplication;
	private EditText editAccount;
	private EditText editPassword;
	private Gson gson = new Gson();

//	private String userName = "";
//	private String userPwd = "";
	private String type;
	private String carId;
	public static final int REGISTER_ACTIVITY = 10001;// 注册
	public static final int FORGET_ACTIVITY = 10002;// 忘记密码
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
//        if(type.endsWith("detail"))
//        	carId = intent.getStringExtra("carId");
		myApplication = (MyApplication)getApplication();
		initView();
	}

	private void initView(){
		
		LinearLayout layout = (LinearLayout)findViewById(R.id.layBack);
		layout.setOnClickListener(onClickListener);

		layout = (LinearLayout)findViewById(R.id.layRegister);
		layout.setOnClickListener(onClickListener);
		
		Button btn = (Button)findViewById(R.id.btnPhone);
		btn.setOnClickListener(onClickListener);
		
		btn = (Button)findViewById(R.id.btnLogin);
		btn.setOnClickListener(onClickListener);
		
		TextView text = (TextView)findViewById(R.id.textForgetPassword);
		text.setOnClickListener(onClickListener);
		
		editAccount = (EditText)findViewById(R.id.editAccount);
		editPassword = (EditText)findViewById(R.id.editPassword);
	}
	
	
	/***
	 * 点击响应
	 */
	private OnClickListener onClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = null ; 
			switch (v.getId()) {
			case R.id.layBack:
				setResult(0, intent); 
				finish();
				break;
			case R.id.btnPhone:
				intent = new Intent();
				intent.setAction("android.intent.action.CALL");
				intent.setData(Uri.parse("tel:"+myApplication.phoneNum));
				startActivity(intent);
				break;
			case R.id.btnLogin:
				if(editAccount.getText().toString() == null ||editAccount.getText().toString().equals("")) 
				{
					Toast.makeText(LoginActivity.this, "手机号不能为空！", Toast.LENGTH_SHORT).show();
					return;
				}
				if(editPassword.getText().toString() == null ||editPassword.getText().toString().equals("")) 
				{
					Toast.makeText(LoginActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
					return;
				}
//				if(type.equals("mine"))
//				{
			        AppHttpInterface.login(editAccount.getText().toString(), editPassword.getText().toString(), mHandlerAll);
//				}
//				else
//				{
//			        AppHttpInterface.loginCarDetail(editAccount.getText().toString(), editPassword.getText().toString(),carId, mHandlerAll);
//				}
				break;
			case R.id.layRegister:
				intent = new Intent(LoginActivity.this,RegisterActivity.class);
				startActivityForResult(intent,REGISTER_ACTIVITY);
				break;
			case R.id.textForgetPassword:
				intent = new Intent(LoginActivity.this,ForgetPasswordActivity.class);
				startActivityForResult(intent, FORGET_ACTIVITY);
				break;
			default:
				break;
			}
		}
	};
	
	
	protected TextHttpResponseHandler mHandlerAll = new TextHttpResponseHandler() {

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
					Toast.makeText(LoginActivity.this, data.get("resMsg"), Toast.LENGTH_SHORT).show();
				else
				{
					myApplication.setToken(data.get("token"));
					myApplication.setIsVip(data.get("isvip"));
					myApplication.setPayBalance(data.get("consume_balance"));
					myApplication.setAuctionBalance(data.get("balance"));
					myApplication.setUserAccount(editAccount.getText().toString());
					Toast.makeText(LoginActivity.this, "登入成功！", Toast.LENGTH_SHORT).show();
					Intent userData = new Intent();

					if(type.equals("mine"))//返回我的
					{
						setResult(1, userData); 
					}
					else  //返回详情页
					{
						setResult(1, userData); 
					}
					
					saveUserAccount();
					finish();
				}
			}
		}

		@Override
		public void onFailure(int arg0, Header[] arg1, String arg2,
				Throwable arg3) {
			Toast.makeText(LoginActivity.this, "服务器连接失败！", Toast.LENGTH_SHORT).show();
		}
	};
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == 1)
		{
			String userName = data.getStringExtra("userName").toString();
			String userPwd = data.getStringExtra("userPwd").toString();
			editAccount.setText(userName);
			editPassword.setText(userPwd);
			if(type.equals("mine"))
			{
		        AppHttpInterface.login(userName, userPwd, mHandlerAll);
			}
			else
			{
		        AppHttpInterface.loginCarDetail(userName, userPwd,carId, mHandlerAll);
			}
			
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	/**
	 * 保存用户名密码和是否第一次登入
	 */
	private void saveUserAccount(){
		
		SharedPreferences settings = getSharedPreferences("userInfo", 0); 
		settings.edit().putString("account", editAccount.getText().toString())
		.putString("password", editPassword.getText().toString())
		.putBoolean("isFirstLogin", false)
		.commit();
	}
}
