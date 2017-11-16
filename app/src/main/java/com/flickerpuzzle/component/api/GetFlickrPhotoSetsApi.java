package com.flickerpuzzle.component.api;

import android.content.Context;

import com.flickerpuzzle.component.cache.cachemaker.DTOCacheMaker;
import com.flickerpuzzle.component.cache.cachemaker.IDTOCacheMaker;
import com.flickerpuzzle.component.cache.cacheverifier.DTOCacheVerifier;
import com.flickerpuzzle.component.cache.cacheverifier.IDTOCacheVerifier;
import com.flickerpuzzle.component.dto.FlickrDTO;
import com.flickerpuzzle.component.response.ResponseParser;
import com.flickerpuzzle.core.network.executor.Scheduler;


/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public class GetFlickrPhotoSetsApi extends ABaseApiCaller{
	public GetFlickrPhotoSetsApi(Context a_context) {
		super(a_context);
	}

	/**  
	 * api for get the flickr  user photoset request
	 * @param-IHttpResponseHandler
	 * @return-void
	 */
	public void getPhotoSetRequest(IHttpResponseHandler a_argCallBack){
		super.mResponseHandler=a_argCallBack ;
		IDTOCacheVerifier cacheHandler=new DTOCacheVerifier() ;
		cacheHandler.cacheVerifier(this) ;
	}
	
	/**  
	 * handlr for callback from http on error
	 * @param-ASDKError
	 * @return-void
	 */
	@Override
	public void onHttpRequestError(ASDKError a_err) {
		postError(a_err);
	}

	/**  
	 * handler for callback from http on sucess respsone
	 * @param-ResponseObject<?>
	 * @return-void
	 */
	@Override
	public void onReceiveResponse(final ResponseObject<?> a_Response){
		Scheduler s=Scheduler.getInstance() ;
		s.schedule(new Runnable(){
			@Override
			public void run() {
				ResponseParser parser=new ResponseParser() ;
				final Object object=parser.parse(GetFlickrPhotoSetsApi.this, a_Response) ;
				
				FlickrDTO flickrDTO=(FlickrDTO)object ;
				IDTOCacheMaker cacheMaker=new DTOCacheMaker() ;
				cacheMaker.cacheMaker(GetFlickrPhotoSetsApi.this, new ResponseObject<FlickrDTO>(flickrDTO)) ;
				postSucess(new ResponseObject<Object>(flickrDTO));
			}
		}) ;
	}
}
