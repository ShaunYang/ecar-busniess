package com.pyec.ecarbusiness.ui.auction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.Contacts.Data;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.pyec.ecarbusiness.R;
import com.pyec.ecarbusiness.adapter.AuctionNowAdapter;
import com.pyec.ecarbusiness.baseClass.BaseFragment;
import com.pyec.ecarbusiness.baseClass.MyApplication;
import com.pyec.ecarbusiness.baseClass.MyApplication.timeListener;
import com.pyec.ecarbusiness.baseClass.PinnedSectionListView;
import com.pyec.ecarbusiness.baseClass.PullToRefreshLayout;
import com.pyec.ecarbusiness.baseClass.PullToRefreshLayout.OnRefreshListener;
import com.pyec.ecarbusiness.dataStruct.AuctionListData;
import com.pyec.ecarbusiness.dataStruct.CarData;
import com.pyec.ecarbusiness.httpClient.AppHttpInterface;
import com.pyec.ecarbusiness.ui.carDetailFragment.CarDetailTabActivity;

public class FragmentAuctionNow extends BaseFragment implements OnRefreshListener{

	private MyApplication myApplication;
	private PinnedSectionListView pListView;
	private View mView;
	private PullToRefreshLayout ptrl;
	private AuctionNowAdapter mAdapter;
	private Gson gson = new Gson();
	private String auctionId = "";
	private int page = 0;
	private List<AuctionListData> lisReturnData = new ArrayList<AuctionListData>();//下载数据
	private List<AuctionListData> mListData = new ArrayList<AuctionListData>();//显示数据
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_auction_now, null);
		initView();

//		LoadDataExecute();
		ptrl.autoRefresh();
		return mView;
	}
	

	private void initView()
	{

		ptrl = ((PullToRefreshLayout)mView.findViewById(R.id.refresh_view));
		ptrl.setOnRefreshListener(this);
		pListView = (PinnedSectionListView)mView.findViewById(R.id.list);
		pListView.setFastScrollEnabled(false);
		mAdapter = new AuctionNowAdapter(getActivity(), mListData);
		pListView.setAdapter(mAdapter);
		pListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				if(mListData.get(position).type.equals("title"))
				{
					if(mListData.get(position).auction_id.equals(auctionId))
						auctionId = "";
					else
						auctionId = mListData.get(position).auction_id;
					mListData.clear();
					formatListData();
				}
				else
				{
					Intent  intent = new Intent(getActivity(),CarDetailTabActivity.class);
	                intent.putExtra("carId",mListData.get(position).sell_id);
					startActivity(intent);
				}
			}
		});

	}
	
    /**
     * 获取场次和第一页车辆数据
     */
	private void LoadDataExecute() {
		Map<String,Object> mapScreenData = new HashMap<String,Object>();
		String data[] = {};
		mapScreenData.put("y", data);
		mapScreenData.put("p", data);
		mapScreenData.put("s2",data);
		mapScreenData.put("s1",data);
		mapScreenData.put("c", data);
		mapScreenData.put("m", data);
		String screenData = gson.toJson(mapScreenData);
		AppHttpInterface.getAuctionNowData(screenData, mHandlerAll);
	}

	protected TextHttpResponseHandler mHandlerAll = new TextHttpResponseHandler() {

		@Override
		public void onSuccess(int arg0, Header[] arg1, String resultData) {
			if(resultData.equals("") || resultData == null)
				return;
			else
			{
				ptrl.refreshFinish(PullToRefreshLayout.SUCCEED);
		        lisReturnData =  gson.fromJson(resultData, new TypeToken<ArrayList<AuctionListData>>() {}.getType());
		        mListData.clear();
				formatListData();
			}
		}

		@Override
		public void onFailure(int arg0, Header[] arg1, String arg2,
				Throwable arg3) {
			ptrl.refreshFinish(PullToRefreshLayout.FAIL);
			Toast.makeText(getActivity(), "服务器连接中断", Toast.LENGTH_LONG).show();
		}
	};

	
    /**
     * 获取某一场场次的某页车辆数据
     */
	private void LoadCarDataExecute() {
		Map<String,Object> mapScreenData = new HashMap<String,Object>();
		String data[] = {};
		mapScreenData.put("y", data);
		mapScreenData.put("p", data);
		mapScreenData.put("s2",data);
		mapScreenData.put("s1",data);
		mapScreenData.put("c", data);
		mapScreenData.put("m", data);
		String screenData = gson.toJson(mapScreenData);
		AppHttpInterface.getAuctionCarDataByPage(auctionId,page+"",screenData, mHandlerCar);
	}

	protected TextHttpResponseHandler mHandlerCar = new TextHttpResponseHandler() {

		@Override
		public void onSuccess(int arg0, Header[] arg1, String resultData) {
			if(resultData.equals("") || resultData == null)
				return;
			else
			{
				ptrl.loadmoreFinish(PullToRefreshLayout.SUCCEED);
				List<AuctionListData> lisReturnData = new ArrayList<AuctionListData>();//下载数据
		        lisReturnData =  gson.fromJson(resultData, new TypeToken<ArrayList<AuctionListData>>() {}.getType());
		        mListData.addAll(lisReturnData);
		        mAdapter.notifyDataSetChanged();
			}
		}

		@Override
		public void onFailure(int arg0, Header[] arg1, String arg2,
				Throwable arg3) {
			ptrl.loadmoreFinish(PullToRefreshLayout.FAIL);
			Toast.makeText(getActivity(), "服务器连接中断", Toast.LENGTH_LONG).show();
		}
	};
	
	
	private void formatListData()
	{
        AuctionListData data = null;
        for(int i = 0 ; i < lisReturnData.size() ; i ++)
        {
        	if(!auctionId.equals(""))//判断是否选中场次
        	{
        		if(!lisReturnData.get(i).auction_id.equals(auctionId))//只显示选中的场次
        			continue;
        	}
    		data = new AuctionListData();
    		data.type = "title";
    		data.title = lisReturnData.get(i).title;
    		data.auction_id = lisReturnData.get(i).auction_id;
    		data.status = lisReturnData.get(i).status;
    		data.title = lisReturnData.get(i).title;
    		data.now =  lisReturnData.get(i).now;
    		data.endtime = lisReturnData.get(i).endtime;
    		data.starttime = lisReturnData.get(i).starttime;
    		mListData.add(data);
    		if(!auctionId.equals(""))//为空时只显示场次信息，不显示车辆信息
    		{
    			List<CarData> listCarData = new ArrayList<CarData>();
    	        listCarData = lisReturnData.get(i).sell;
    	        for(int j = 0 ; j < listCarData.size() ; j ++)
    	        {
    	    		data = new AuctionListData();
    	    		data.type = "item";
    	    		data.auction_id = lisReturnData.get(i).auction_id;
    	        	data.sell_id = listCarData.get(j).sell_id;
    	    		data.info = listCarData.get(j).info;
    	    		data.img01 = listCarData.get(j).img01;
    	    		data.area = listCarData.get(j).area;
    	    		data.score = listCarData.get(j).score;
    	    		data.regist_date = listCarData.get(j).regist_date;
    	    		data.kilometer = listCarData.get(j).kilometer;
    	    		mListData.add(data);
    	        }
    		}
        }
        mAdapter.notifyDataSetChanged();
		timeChangeRun();
	}
	@Override
	public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		// 下拉刷新操作
			LoadDataExecute();
	}


	@Override
	public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		// 加载操作
		if(!auctionId.endsWith(""))
		{
			LoadCarDataExecute();
		}
		else
		{
			LoadDataExecute();
		}
	}
	


	
	
	private void timeChangeRun() {
		myApplication = (MyApplication) getActivity().getApplication();
		myApplication.startTime();
		myApplication.setOnTimeListener(new timeListener() {

			@Override
			public void timeRunListener(boolean state) {
				// TODO Auto-generated method stub

				if (auctionId.equals("")) {

					for (int index = 0; index < mListData.size(); index++) {
						if (mListData.size() <= 0
								|| mListData.get(index).now == null
								|| !auctionId.endsWith("")) {
							return;
						} else {
							int nNowTime = Integer.valueOf(mListData.get(index).now);
							nNowTime++;
							mListData.get(index).setNow(nNowTime + "");
						}
					}
				} else {
					if (mListData.size() > 0) {
						int nNowTime = Integer.valueOf(mListData.get(0).now);
						nNowTime++;
						mListData.get(0).setNow(nNowTime + "");
					}
				}
				for (int j = 0; j < lisReturnData.size(); j++) {
					int nNowTime = Integer.valueOf(lisReturnData.get(j).now);
					nNowTime++;
					lisReturnData.get(j).setNow(nNowTime + "");
				}
				mAdapter.notifyDataSetChanged();
			}
		});
	}
}
