package com.pyec.ecarbusiness.ui;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.loopj.android.http.TextHttpResponseHandler;
import com.pyec.ecarbusiness.R;
import com.pyec.ecarbusiness.baseClass.MyApplication;
        

public class MainTabActivity extends FragmentActivity{    
    

	public static final int AUCTION_PAGE = 1000;// 竞拍大厅
	public static final int DISCROVER_PAGE = 1001;// 竞拍大厅
	public static final int MINE_PAGE = 1002;// 竞拍大厅

	public FragmentManager fragmentManager;
	//定义FragmentTabHost对象  
    private FragmentTabHost mTabHost;  
            
	private long m_lFirstTime;
	
    //定义一个布局  
    private LayoutInflater layoutInflater;  
	private MyApplication myApplication;
                
    //定义数组来存放Fragment界面  
    private Class fragmentArray[] = {FragmentAuctionNew.class,FragmentDiscover.class,FragmentMine.class};  
            
    //定义数组来存放按钮图片  
    private int mImageViewArray[] = {R.drawable.menu_auction,R.drawable.menu_discover,R.drawable.menu_mine};  
     
    //Tab选项卡的文字  
    private String mTextviewArray[] = {"auction", "discrover", "mine"};  
            
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.main_tab_layout);  

		fragmentManager = getSupportFragmentManager();
		myApplication = (MyApplication)getApplication();
        initView();  

        mLocationClient = new LocationClient(this); // 声明LocationClient类
        mLocationClient.registerLocationListener(myListener); // 注册监听函数
        setLocationOption();
        mLocationClient.start();
        
    }  
             
    
	protected TextHttpResponseHandler mHandlerAll = new TextHttpResponseHandler() {

		@Override
		public void onSuccess(int arg0, Header[] arg1, String resultData) {
			if(resultData.equals("") || resultData == null)
				return;
			else
			{
				Map<String , String> data = new HashMap<String, String>();
				myApplication.setToken(data.get("token"));

			}
		}

		@Override
		public void onFailure(int arg0, Header[] arg1, String arg2,
				Throwable arg3) {
		}
	};
    
    /** 
     * 初始化组件 
     */
    private void initView(){  
        //实例化布局对象  
        layoutInflater = LayoutInflater.from(this);  
                        
        //实例化TabHost对象，得到TabHost  
        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);  
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);   
                
        //得到fragment的个数  
        int count = fragmentArray.length;     
                        
        for(int i = 0; i < count; i++){    
            //为每一个Tab按钮设置图标、文字和内容  
            TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));  
            //将Tab按钮添加进Tab选项卡中  
            mTabHost.addTab(tabSpec, fragmentArray[i], null);  
            //设置Tab按钮的背景  
    	    mTabHost.setOnTabChangedListener(new OnTabChangedListener());
        }  
    }
    
    class OnTabChangedListener implements OnTabChangeListener {
    	public void onTabChanged(String tabId) {
    	mTabHost.setCurrentTabByTag(tabId);
    	   System.out.println("tabid " + tabId);
    	   System.out.println("curreny after: " + mTabHost.getCurrentTabTag());
    	   
    	   for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) { 
               TextView textView = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(R.id.textview); // 设置字体和风格  
               if (mTabHost.getCurrentTab() == i) {//选中  
               	textView.setTextColor(getResources().getColorStateList(android.R.color.holo_red_light));
               } else {//不选中  
               	textView.setTextColor(getResources().getColorStateList(android.R.color.darker_gray));
               } 
           } 
    	}
	}
    
    
    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) 
    {
        View view = null;			
        TextView textView  = null;
    	switch(index)
    	{
    	case 0:
    		view = layoutInflater.inflate(R.layout.tab_menu_auction, null);
			textView = (TextView) view.findViewById(R.id.textview);
			textView.setText("竞拍大厅");
			textView.setTextColor(this.getResources().getColorStateList(android.R.color.holo_red_light));
    		break;
    	case 1:
    		view = layoutInflater.inflate(R.layout.tab_menu_discover, null);
			textView = (TextView) view.findViewById(R.id.textview);
			textView.setText("发现");
    		break;
    	case 2:
    		view = layoutInflater.inflate(R.layout.tab_menu_mine, null);
			textView = (TextView) view.findViewById(R.id.textview);
			textView.setText("我的");
    		break;
    	}
        return view;
    }
    
    
    
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		switch (keyCode)
		{
		case KeyEvent.KEYCODE_MENU:
			break;
		case KeyEvent.KEYCODE_BACK:
			long lSecondTime = System.currentTimeMillis();
			if (lSecondTime - m_lFirstTime > 2000)
			{
				Toast toast = Toast.makeText(MainTabActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT); 
				toast.show();
				m_lFirstTime = lSecondTime;
				return true;
			}
			else
			{
				finish();
			}
			break;
		}
		return true;
	}
	
	
	/**
	 * GPS定位
	 */
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();

	/**
	 * 设置相关参数
	 */
	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setLocationMode(LocationMode.Hight_Accuracy);
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		option.disableCache(true);// 禁止启用缓存定位
		mLocationClient.setLocOption(option);
	}


	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			Log.d("Location ", "---" + location.getCity() + "---");
			String sCity = location.getCity();
			if (sCity != null) {
				mLocationClient.stop();
				String[] temp = sCity.split("市");
				MyApplication myApplication = (MyApplication)getApplication();
				myApplication.setSLocation(temp[0]);
			}
		}
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Fragment fragment;
		if(resultCode == 1)
		{
			switch (requestCode) {
			case MINE_PAGE://我的界面登入返回
				fragment = fragmentManager.findFragmentByTag("mine");
				fragment.onActivityResult(requestCode, resultCode, data);
				break;
			default:
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}