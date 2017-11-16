package com.flickerpuzzle.core.network;

/**
 * Created by ashakant on 11/16/17.
 */

import com.flickerpuzzle.component.api.ASDKError;
import com.flickerpuzzle.component.api.ResponseObject;


public interface IHttpResponseListener {
    /**
     * Hamdler for HTTP request error
     *
     * @param Exception
     * @return void
     */
    public void onHttpRequestError(ASDKError a_err);
    /**
     * Handler for on receing the HTTP resposne
     * @param <T>
     *
     * @param int , HashMap , String and String
     * @return void
     */
    public void onReceiveResponse(ResponseObject<?> a_response);
}
