package com.flickerpuzzle.component.api;

import android.content.Context;

import com.flickerpuzzle.component.request.IRequestGenerator;
import com.flickerpuzzle.component.request.RequestGenerator;
import com.flickerpuzzle.component.request.RequestPacket;


/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public class ImageDownLoaderHandler extends ABaseApiCaller{

	public ImageDownLoaderHandler(Context a_context) {
		super(a_context);
	}

	/**  
	 * api for get the flickr  image from photset
	 * @param-IHttpResponseHandler
	 * @return-void
	 */
	public void fetchImage(String url,IHttpResponseHandler a_argCallBack) {
		super.mResponseHandler=a_argCallBack ;
		/**	
		 * 1.  Get build all request parameter -url,header,method, data here
		 */
		IRequestGenerator  reqGenerator=new RequestGenerator() ;
		RequestPacket flickrRequest=reqGenerator.makeRequest(this) ;
		flickrRequest.setUrl(url);
		/**
		 * 2.  Finally make Http call to Http module and get callback here  
		 */
		IApiInvoker invoker=new ApiInvoker(this.mContext) ;
		invoker.invoke(this, flickrRequest);
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
	 * handlr for callback from http on sucess respsone
	 * @param-ResponseObject<?>
	 * @return-void
	 */
	@Override
	public void onReceiveResponse(ResponseObject<?> a_Response) {
		postSucess(a_Response);
	}
}
