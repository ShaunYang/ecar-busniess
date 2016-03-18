package com.pyec.ecarbusiness.baseClass;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;

public class MyApplication extends Application{

	public String userAccount = "";
	



	public String token = "";
	
	public String m_sLocation;
	
	public String payBalance = "";

	public String auctionBalance = "";
	
	public String isVip = "";
	
	public static Context mContext;
	
	
	/**
	 * @return the userAccount
	 */
	public String getUserAccount() {
		return userAccount;
	}


	/**
	 * @param userAccount the userAccount to set
	 */
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	
	/**
	 * @return the payBalance
	 */
	public String getPayBalance() {
		return payBalance;
	}


	/**
	 * @return the isVip
	 */
	public String getIsVip() {
		return isVip;
	}


	/**
	 * @param isVip the isVip to set
	 */
	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}


	/**
	 * @param payBalance the payBalance to set
	 */
	public void setPayBalance(String payBalance) {
		this.payBalance = payBalance;
	}


	/**
	 * @return the auctionBalance
	 */
	public String getAuctionBalance() {
		return auctionBalance;
	}


	/**
	 * @param auctionBalance the auctionBalance to set
	 */
	public void setAuctionBalance(String auctionBalance) {
		this.auctionBalance = auctionBalance;
	}


	public String phoneNum = "10086";
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mContext=this;
	}
	
	
	public String getToken() {
		return token;
	}
  
	public void setToken(String token) {
		this.token = token;
	}
	
	
	/**
	 * 获取位置信息
	 * @return 位置信息
	 */
	public String getSLocation() {
		return m_sLocation;
	}

	/**
	 * 设置所在城市
	 * @param m_sLocation 城市
	 */
	public void setSLocation(String m_sLocation) {
		this.m_sLocation = m_sLocation;
	}

	
	
	/**
	 * 配置ImageLoder
	 */
	public void configImageLoader() {
		// 初始化ImageLoader
		@SuppressWarnings("deprecation")
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(false) // 设置下载的图片是否缓存在SD卡中
				.build(); // 创建配置过得DisplayImageOption对象

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).defaultDisplayImageOptions(options)
				.threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);		
	}
	
	public Bitmap getImage(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 540f;// 这里设置高度为800f
		float ww = 750f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	public Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		Log.i("", "img size = "+baos.toByteArray().length);
		int size = baos.toByteArray().length;
		while (baos.toByteArray().length / 1024 > 50) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
			Log.i("", "img size = "+baos.toByteArray().length);
			size = baos.toByteArray().length;
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	/**
	 * Save Bitmap to a file.保存图片到SD卡。
	 * 
	 * @param bitmap
	 * @param file
	 * @return error message if the saving is failed. null if the saving is
	 *         successful.
	 * @throws IOException
	 */
	public void saveBitmapToFile(Bitmap bitmap, String fileFullName)
			throws IOException {
		BufferedOutputStream os = null;
		try {
			File file = new File(fileFullName);
			int end = fileFullName.lastIndexOf(File.separator);
			String _filePath = fileFullName.substring(0, end);
			File filePath = new File(_filePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			file.createNewFile();
			os = new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					Log.e("", e.getMessage(), e);
				}
			}
		}
	}
	

	private  timeListener timeRunListener;
	protected static final long TIME = 1000;

	private boolean timeRunState = false;
 	private  Handler handler = new Handler();
	private  Runnable runnable = new Runnable() {
		@Override
		public void run() {
 			handler.postDelayed(this, TIME);
			timeRunListener.timeRunListener(true);
 		}
	};
	
	public  void setOnTimeListener(timeListener timeListener) {
		timeRunListener = timeListener;
	}
	
	public interface timeListener{
		void timeRunListener(boolean state);
	}
	
	public void startTime(){
		if(!timeRunState)
		{
			handler.postDelayed(runnable, TIME);
			timeRunState = true;
		}
	}
	
}
