package com.flickerpuzzle.client;

import com.flickerpuzzle.component.api.IHttpResponseHandler;

/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public interface IApiCaller {
	public void getPhotoSetRequest(IHttpResponseHandler a_argCallBack) ;
	public void fetchImage(String url, IHttpResponseHandler a_argCallBack) ;
}
