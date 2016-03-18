package com.pyec.ecarbusiness.baseClass;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment{

	private MyApplication myApplication;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        myApplication = (MyApplication)getActivity().getApplication();
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
	}

	//恢复数据
	@Override
	public void onViewStateRestored(Bundle savedInstanceState){
		super.onViewStateRestored(savedInstanceState);
		if(savedInstanceState != null){
		}
	}

}
