package com.flickerpuzzle.component.api;

/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public interface IHttpResponseHandler {
	public void onSucess(ResponseObject<?> a_resObject) ;
	public void onError(ASDKError a_error) ;
}
