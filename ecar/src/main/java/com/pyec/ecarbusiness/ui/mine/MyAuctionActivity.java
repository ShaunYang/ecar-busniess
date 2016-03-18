package com.pyec.ecarbusiness.ui.mine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

import com.loopj.android.http.TextHttpResponseHandler;
import com.pyec.ecarbusiness.R;
import com.pyec.ecarbusiness.baseClass.MyApplication;

public class MyAuctionActivity extends FragmentActivity{    
    //定义FragmentTabHost对象  
    private FragmentTabHost mTabHost;  
            
    //定义一个布局  
    private LayoutInflater layoutInflater;  
	private MyApplication myApplication;
                
    //定义数组来存放Fragment界面  
    private Class fragmentArray[] = {FragmentMyAuctionUnderway.class,FragmentMyAuctionSuccess.class,FragmentMyAuctionFail.class};  
            
     
    //Tab选项卡的文字  
    private ArrayList<String> mTextviewArray = new ArrayList<String>();
            
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_my_auction);  

		myApplication = (MyApplication)getApplication();
		mTextviewArray.add(this.getString(R.string.myAuctionUnderway));
		mTextviewArray.add(this.getString(R.string.myAuctionSuccess));
		mTextviewArray.add(this.getString(R.string.myAuctionFail));
        initView();
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
            TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray.get(i)).setIndicator(getTabItemView(i));  
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
               	textView.setTextColor(getResources().getColorStateList(R.color.red_FF3300));
               } else {//不选中  
               	textView.setTextColor(getResources().getColorStateList(R.color.grey_999999));
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
    		view = layoutInflater.inflate(R.layout.auction_page_tab, null);
			textView = (TextView) view.findViewById(R.id.textview);
			textView.setText(getString(R.string.myAuctionUnderway));
			textView.setTextColor(getResources().getColorStateList(R.color.red_FF3300));
    		break;
    	case 1:
    		view = layoutInflater.inflate(R.layout.auction_page_tab, null);
			textView = (TextView) view.findViewById(R.id.textview);
			textView.setText(getString(R.string.myAuctionSuccess));
    		break;
    	case 2:
    		view = layoutInflater.inflate(R.layout.auction_page_tab, null);
			textView = (TextView) view.findViewById(R.id.textview);
			textView.setText(getString(R.string.myAuctionFail));
    		break;
    	}
        return view;
    }
}
