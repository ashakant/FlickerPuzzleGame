package com.flickerpuzzle.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.flickerpuzzle.R;
import com.flickerpuzzle.client.IUserSession;
import com.flickerpuzzle.client.ImageLoader;
import com.flickerpuzzle.component.api.ASDKError;
import com.flickerpuzzle.component.api.IHttpResponseHandler;
import com.flickerpuzzle.component.api.ResponseObject;
import com.flickerpuzzle.component.cache.cacheverifier.ImageData;

import java.util.List;

/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public class LazyImageLoadAdapter extends BaseAdapter {
	private HelloGridView mActivity;  
	private List<String> mData;  
	private static LayoutInflater mInflater=null;  
	private IUserSession mIUserSession ;
	private boolean mbDefaultImg =false;

	public LazyImageLoadAdapter(HelloGridView a_activity, List<String> a_list,IUserSession session) {
		mActivity = a_activity;
		mData=a_list;
		mInflater = (LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mIUserSession=session;
	}
	/**  
	 * get size of adapter
	 * @param-void
	 * @return-int
	 */
	public int getCount() {
		return mData.size() ;
	}
	/**  
	 * get adapter item corresponding to input position
	 * @param-int
	 * @return-Object
	 */
	public Object getItem(int position) {
		return mData.get(position) ;
	}
	/**  
	 * get adapter item corresponding to input position
	 * @param-int
	 * @return-long
	 */
	public long getItemId(int position) {
		return position;
	}
	/**  
	 * Create a holder Class to contain inflated xml file elements
	 * @param
	 * @return
	 */
	public static class ViewHolder{
		public ImageView image;
	}
	/**  
	 * get adapter view , main function to handle the image display as well fetch images either from local cache of 
	 * remotely
	 * @param-int,View,ViewGroup
	 * @return-long
	 */
	public View getView(final int position, View a_convertView, ViewGroup a_parent) {
		View vi=a_convertView;
		final ViewHolder holder;
		if(a_convertView==null){
			vi = mInflater.inflate(R.layout.listview_row, null);
			holder = new ViewHolder();
			holder.image=(ImageView)vi.findViewById(R.id.image);
			vi.setTag( holder );
		}
		else 
			holder=(ViewHolder)vi.getTag();
		final ImageView imgView = holder.image;
		imgView.setPadding(5, 5, 5, 5);
		
		if(mbDefaultImg==false){
			/**
			 * Check is image has been cached locally before making remote call
			 */
			final ImageLoader imgLoader=new ImageLoader(mActivity) ;
			Bitmap bitmap=imgLoader.isCached(mData.get(position),imgView) ;
			if(bitmap!=null ){
				imgView.setImageBitmap(bitmap);
				return vi;
			}	
			this.mIUserSession.getApiCaller(mActivity).fetchImage(mData.get(position),new IHttpResponseHandler(){
				@Override
				public void onSucess(ResponseObject<?> resObject) {
					ImageData ImgData=(ImageData)resObject.get();
					imgLoader.getMemoryCache().put(mData.get(position), ImgData.getBitmap());
					imgView.setLayoutParams(new LinearLayout.LayoutParams(
			                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)) ;
					imgView.setScaleType(ImageView.ScaleType.FIT_XY);
					imgView.setAdjustViewBounds(true) ;
					imgView.setImageBitmap(ImgData.getBitmap());
				}
				@Override
				public void onError(ASDKError error) {
					System.out.println("Failed in image download");
				}
			}) ;
		}
		else{
			imgView.setImageResource(R.drawable.images);
		}
		return vi;
	}
	/**  
	 * set/associates bitmap/images to the image view id
	 * @param-position,ImageView
	 * @return-void
	 */
	public void setBitmapbyIndex(final int position,final ImageView imgView){
		final ImageLoader imgLoader=new ImageLoader(mActivity) ;
		Bitmap bitmap=imgLoader.isCached(mData.get(position),imgView) ;
		if(bitmap!=null ){
			imgView.setImageBitmap(bitmap);
			imgView.invalidate();
			return ;
		}	
		this.mIUserSession.getApiCaller(mActivity).fetchImage(mData.get(position),new IHttpResponseHandler(){
			@Override
			public void onSucess(ResponseObject<?> resObject) {
				ImageData ImgData=(ImageData)resObject.get();
				imgLoader.getMemoryCache().put(mData.get(position), ImgData.getBitmap());
				imgView.setLayoutParams(new LinearLayout.LayoutParams(
		                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)) ;
				imgView.setAdjustViewBounds(true) ;
				imgView.setImageBitmap(ImgData.getBitmap());
			}
			@Override
			public void onError(ASDKError error) {
				System.out.println("Failed in image download");
			}
		}) ;
	}
	public boolean isDefaultImg() {
		return mbDefaultImg;
	}
	public void setDefaultImg(boolean mbDefaultImg) {
		this.mbDefaultImg = mbDefaultImg;
	}
}