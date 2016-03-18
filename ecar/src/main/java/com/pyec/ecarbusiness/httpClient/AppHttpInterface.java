package com.pyec.ecarbusiness.httpClient;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pyec.ecarbusiness.baseClass.MyApplication;

public class AppHttpInterface {

	static public String m_sUrl = "http://120.27.161.243/fecar_mk/auction/Merchant/";

	/**
	 * @return the m_sUrl
	 */
	public static String getUrl() {
		return m_sUrl;
	}


	public MyApplication myApplication;
	public static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

	/**
	 * 登陆
	 * 
	 * @param username 用户名
	 * @param password 用户密码
	 * @param handler 获取数据回调
	 */
	public static void login(String username, String pwd,
			AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("username", username);
		params.put("pwd", pwd);
		asyncHttpClient.post(m_sUrl + "User/login", params, handler);
	}
	
	/**
	 * 详情页登入
	 * @param username  用户名
	 * @param pwd		密码
	 * @param carId     车辆Id
	 * @param handler   获取数据回调
	 */
	public static void loginCarDetail(String username, String pwd,
			String carId, AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("username", username);
		params.put("pwd", pwd);
		params.put("sellid", carId);
		asyncHttpClient.post(m_sUrl + "User/login2", params, handler);

	}

	/**
	 * 获取正在拍卖列表场次和第一页车源数据
	 * @param screenData 筛选条件
	 * @param handler    获取数据回调
	 */
	public static void getAuctionNowData(String screenData ,AsyncHttpResponseHandler handler)
	{
		RequestParams params = new RequestParams();
		params.put("selector", screenData);
		asyncHttpClient.get(m_sUrl + "Sell/getSellAreaList2", params, handler);
	}
	
	
	/**
	 * 获取竞拍列表中某场的某页的车源信息
	 * @param auction_id 场次ID
	 * @param page       页数
	 * @param screenData 筛选条件
	 * @param handler    获取数据回调
	 */
	public static void getAuctionCarDataByPage(String auction_id , String page , String screenData ,AsyncHttpResponseHandler handler)
	{
		RequestParams params = new RequestParams();
		params.put("auction_id", auction_id);
		params.put("page", page);
		params.put("selector", screenData);
		asyncHttpClient.get(m_sUrl + "Sell/more", params, handler);
	}
	
	/**
	 * 获取竞拍完成车辆数据
	 * @param page     页数
	 * @param status   状态
	 * @param is_has_base_price 有无底价
	 * @param handler  获取数据回调
	 */
	public static void getAuctionFinishData(String page , String status , String is_has_base_price , AsyncHttpResponseHandler handler)
	{
		RequestParams params = new RequestParams();
		params.put("page", page);
		params.put("status",status);
		params.put("is_has_base_price", is_has_base_price);
		asyncHttpClient.get(m_sUrl + "Sell/getSellAreaList", params, handler);
	}
	
	/**
	 * 获取车辆详情
	 * @param sell_id 车辆id
	 * @param token   连接标识
	 * @param handler 获取数据回调
	 */
	public static void getCarDetailData(String sell_id , String token , AsyncHttpResponseHandler handler)
	{
		RequestParams params = new RequestParams();
		params.put("sell_id",sell_id);
		params.put("token", token);
		asyncHttpClient.get(m_sUrl + "sell/getDetail", params, handler);
	}
	
	

	/**
	 * 注册获取验证码
	 * @param username 手机号
	 * @param handler  获取数据回调
	 */
	public static void getRegisterVerificationCode(String username , AsyncHttpResponseHandler handler)
	{
		RequestParams params = new RequestParams();
		params.put("username",username);
		asyncHttpClient.post(m_sUrl + "User/sendVerifyCode", params, handler);
	}
	
	
	/**
	 * 注册账号
	 * @param username    用户名
	 * @param pwd         用户密码
	 * @param verify_code 验证码
	 * @param handler     注册数据回调
	 */
	public static void postRegisterData(String username ,String pwd, String verify_code,  AsyncHttpResponseHandler handler)
	{
		RequestParams params = new RequestParams();
		params.put("username",username);
		params.put("pwd",pwd);
		params.put("verify_code",verify_code);
		asyncHttpClient.post(m_sUrl + "User/regist", params, handler);
	}
	
	/**
	 * 重置密码
	 * @param username     用户名
	 * @param pwd          新密码
	 * @param verify_code  验证码
	 * @param handler      重置密码回调
	 */
	public static void postForgetPasswordData(String username ,String pwd, String verify_code,  AsyncHttpResponseHandler handler)
	{
		RequestParams params = new RequestParams();
		params.put("username",username);
		params.put("pwd",pwd);
		params.put("verify_code",verify_code);
		asyncHttpClient.post(m_sUrl + "User/resetPwd", params, handler);
	}
	
	/**
	 * 忘记密码获取验证码
	 * @param username    用户名
	 * @param handler     获取验证码回调
	 */
	public static void getForgetVerificationCode(String username , AsyncHttpResponseHandler handler)
	{
		RequestParams params = new RequestParams();
		params.put("username",username);
		asyncHttpClient.post(m_sUrl + "User/sendVerifyCodeFo", params, handler);
	}
	
	
	/**
	 * 获取我的竞拍数据
	 * @param status      竞拍状态
	 * @param token		       令牌
	 * @param page        页数
	 * @param handler     获取数据回调
	 */
	public static void getMyAuctionData(String status, String token, String page , AsyncHttpResponseHandler handler)
	{
		RequestParams params = new RequestParams();
		params.put("status",status);
		params.put("token",token);
		params.put("page",page);
		asyncHttpClient.get(m_sUrl + "SellAdmin/getSellReco", params, handler);
	}
}
