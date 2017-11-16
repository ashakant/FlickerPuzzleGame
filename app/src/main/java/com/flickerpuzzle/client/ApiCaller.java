package com.flickerpuzzle.client;

import android.content.Context;

import com.flickerpuzzle.component.api.GetFlickrPhotoSetsApi;
import com.flickerpuzzle.component.api.IHttpResponseHandler;
import com.flickerpuzzle.component.api.ImageDownLoaderHandler;

/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */

public class ApiCaller implements IApiCaller{
	private Context mContext ;
	
	public ApiCaller(Context a_context){
		this.mContext=a_context ;
	}
	/**  
	 * Acts as subsystem for api call , single point of call from ui for server call 
	 * all api should be implement here for next level of call . Fetch the jSON api call 
	 * required to further fetch images .
	 * @param-A callback IHttpResponseHandler 
	 * @return-void
	 * etc
	 */
	@Override
	public void getPhotoSetRequest(IHttpResponseHandler a_argCallBack) {
		GetFlickrPhotoSetsApi getFlickrPhotoSet=new GetFlickrPhotoSetsApi(mContext) ;
		getFlickrPhotoSet.getPhotoSetRequest(a_argCallBack);
	}
	/**  
	 * Acts as subsytem for api call , single point of call from ui for server call 
	 * all api should be implement here for next level of call . Simpley fetch images  
	 * from server
	 * @param-A callback IHttpResponseHandler 
	 * @return-void
	 * etc
	 */
	@Override
	public void fetchImage(String url, IHttpResponseHandler a_argCallBack) {
		ImageDownLoaderHandler imgDownLoaderHandler=new ImageDownLoaderHandler(mContext) ;
		imgDownLoaderHandler.fetchImage(url, a_argCallBack);
	}
}

