package com.pyec.ecarbusiness.ui.mine;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.os.Bundle;
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
import com.pyec.ecarbusiness.adapter.AuctionFinishAdapter;
import com.pyec.ecarbusiness.baseClass.BaseFragment;
import com.pyec.ecarbusiness.baseClass.MyApplication;
import com.pyec.ecarbusiness.baseClass.MyListView;
import com.pyec.ecarbusiness.baseClass.PullToRefreshLayout;
import com.pyec.ecarbusiness.baseClass.PullToRefreshLayout.OnRefreshListener;
import com.pyec.ecarbusiness.dataStruct.AuctionListData;
import com.pyec.ecarbusiness.httpClient.AppHttpInterface;

public class FragmentMyAuctionSuccess extends BaseFragment implements OnRefreshListener{


	private MyApplication myApplication;
	private MyListView myListView;
	private AuctionFinishAdapter mAdapter;
	private PullToRefreshLayout ptrl;
	private View mView;
	private int mPage = 0;
	private Gson gson = new Gson();
	private List<AuctionListData> mListData = new ArrayList<AuctionListData>();//显示数据
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		myApplication = (MyApplication)getActivity().getApplication();
		mView = inflater.inflate(R.layout.fragment_auction_finish, null);
		initView();
		ptrl.autoRefresh();
		return mView;

	}
	
	private void initView()
	{
		ptrl = ((PullToRefreshLayout)mView.findViewById(R.id.refresh_view));
		ptrl.setOnRefreshListener(this);
		myListView = (MyListView)mView.findViewById(R.id.list);
		myListView.setFastScrollEnabled(false);
//		mAdapter = new SimpleAdapter(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1);
		mAdapter = new AuctionFinishAdapter(getActivity(), mListData);
		myListView.setAdapter(mAdapter);
		myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub

			}
		});
	}
	
	
	  /**
     * 获取第一页车辆数据
     */
	private void LoadDataExecute() {
		AppHttpInterface.getMyAuctionData("1",myApplication.getToken(),mPage+"", mHandlerAll);
	}

	protected TextHttpResponseHandler mHandlerAll = new TextHttpResponseHandler() {

		@Override
		public void onSuccess(int arg0, Header[] arg1, String resultData) {
			if(resultData.equals("") || resultData == null)
				return;
			else
			{
//				List<AuctionListData> lisReturnData = new ArrayList<AuctionListData>();//下载数据
//				lisReturnData =  gson.fromJson(resultData, new TypeToken<ArrayList<AuctionListData>>() {}.getType());
//				if(mPage == 0)
//					mListData.clear();
//		        mListData.addAll(lisReturnData);
//		        mAdapter.notifyDataSetChanged();
		        mPage++;
				ptrl.refreshFinish(PullToRefreshLayout.SUCCEED);
			}
		}

		@Override
		public void onFailure(int arg0, Header[] arg1, String arg2,
				Throwable arg3) {
			ptrl.refreshFinish(PullToRefreshLayout.FAIL);
			Toast.makeText(getActivity(), "服务器连接中断", Toast.LENGTH_LONG).show();
		}
	};

	
	@Override
	public void onRefresh(final PullToRefreshLayout pullToRefreshLayout)
	{
		// 下拉刷新操作
		mPage = 0 ; 
		LoadDataExecute();
	}

	@Override
	public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout)
	{
		// 加载操作
		LoadDataExecute();
	}
}
