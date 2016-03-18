package com.pyec.ecarbusiness.showBigImg;

import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.bitmap.BitmapCallBack;

import com.pyec.ecarbusiness.R;
import com.pyec.ecarbusiness.baseClass.BaseActivity;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 图片预览界面
 * 
 * @author kymjs
 */
public class ImagePreviewActivity extends Activity implements
		OnPageChangeListener {

	public static final String BUNDLE_KEY_IMAGES = "bundle_key_images";
	private static final String BUNDLE_KEY_INDEX = "bundle_key_index";
	private static final String BUNDLE_KEY_STRINGS = "bundle_key_strings";
	private static final String STRINGSD = "bundle_key_strings";
	private ViewPager mViewPager;
	private SamplePagerAdapter mAdapter;
	private TextView mTvImgIndex;
	private TextView mIvMore;
	private int mCurrentPostion = 0;
	private String[] mImageUrls;

	private KJBitmap kjb;
	private String[] mStrings;

	public static void showImagePrivew(Context context, int index,
			String[] images, String[] strings) {
		Intent intent = new Intent(context, ImagePreviewActivity.class);

		intent.putExtra(BUNDLE_KEY_IMAGES, images);
		intent.putExtra(BUNDLE_KEY_STRINGS, strings);
		intent.putExtra(BUNDLE_KEY_INDEX, index);
		context.startActivity(intent);
	}

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		getActionBar().hide();
		setContentView(R.layout.activity_image_preview);
		init();
	}

	protected void init() {
		kjb = new KJBitmap();
		mViewPager = (ViewPager) findViewById(R.id.view_pager);

		mImageUrls = getIntent().getStringArrayExtra(BUNDLE_KEY_IMAGES);
		mStrings = getIntent().getStringArrayExtra(BUNDLE_KEY_STRINGS);
		int index = getIntent().getIntExtra(BUNDLE_KEY_INDEX, 0);

		mAdapter = new SamplePagerAdapter(mImageUrls);
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(this);
		mViewPager.setCurrentItem(index);

		mTvImgIndex = (TextView) findViewById(R.id.tv_img_index);
		mIvMore = (TextView) findViewById(R.id.iv_more);

		onPageSelected(index);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int idx) {
		mCurrentPostion = idx;
		if (mImageUrls != null && mImageUrls.length >= 1) {
			if (mTvImgIndex != null) {
				mTvImgIndex.setText((mCurrentPostion + 1) + "/"
						+ mImageUrls.length);
				mIvMore.setText(mStrings[mCurrentPostion]);
			}
		}
	}

	class SamplePagerAdapter extends RecyclingPagerAdapter {

		private String[] images = new String[] {};

		SamplePagerAdapter(String[] images) {
			this.images = images;
		}

		public String getItem(int position) {
			return images[position];
		}

		@Override
		public int getCount() {
			return images.length;
		}

		@Override
		@SuppressLint("InflateParams")
		public View getView(int position, View convertView, ViewGroup container) {
			ViewHolder vh = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(container.getContext())
						.inflate(R.layout.image_preview_item, null);
				vh = new ViewHolder(convertView);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}
			vh.image.setOnFinishListener(new OnPhotoTapListener() {
				@Override
				public void onPhotoTap(View view, float x, float y) {
					ImagePreviewActivity.this.finish();
				}
			});
			final ProgressBar bar = vh.progress;
			KJBitmap kjbitmap = new KJBitmap();
			kjbitmap.displayWithDefWH(vh.image, images[position],
					new ColorDrawable(0x000000), new ColorDrawable(0x000000),
					new BitmapCallBack() {
						@Override
						public void onPreLoad() {
							super.onPreLoad();
							bar.setVisibility(View.VISIBLE);
						}

						@Override
						public void onFinish() {
							super.onFinish();
							bar.setVisibility(View.GONE);
						}

						@Override
						public void onFailure(Exception arg0) {
							Toast.makeText(ImagePreviewActivity.this, "加载图片失败",
									Toast.LENGTH_SHORT).show();
						}
					});
			return convertView;
		}
	}

	static class ViewHolder {
		PhotoView image;
		ProgressBar progress;

		ViewHolder(View view) {
			image = (PhotoView) view.findViewById(R.id.photoview);
			progress = (ProgressBar) view.findViewById(R.id.progress);
		}
	}
}
