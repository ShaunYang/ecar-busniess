package com.pyec.ecarbusiness.ui;



import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pyec.ecarbusiness.R;

public class FragmentDiscover extends Fragment {
	private View mView;
	private Activity mActivity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mActivity = getActivity();
		mView = inflater.inflate(R.layout.fragment_discover, container, false);

		return mView;
	}
}
