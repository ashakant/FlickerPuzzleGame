package com.flickerpuzzle.component.api;

import android.content.Context;
import android.os.Handler;

import com.flickerpuzzle.core.network.IHttpResponseListener;

/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public class ABaseApiCaller extends Object implements IHttpResponseListener{
	protected	IHttpResponseHandler mResponseHandler ;
	protected	Context mContext ;
	private Handler mHandler ;

	public ABaseApiCaller(Context a_context){
		this.mContext=a_context ;
		this.mHandler=new Handler() ;
		this.mHandler.getLooper() ;
	}

	/**  
	 * callback to UI with resposne from server 
	 * @param-ResponseObject<?>
	 * @return-void
	 */
	public void postSucess(final ResponseObject<?>   a_resObject){
		mHandler.post(new Runnable(){
			@Override
			public void run() {
				mResponseHandler.onSucess(a_resObject) ;
			}
		}) ;
	}
	/**  
	 * callback to UI with Error
	 * @param-ResponseObject<?>
	 * @return-void
	 */
	public void postError(final ASDKError  a_resObject){
		mHandler.post(new Runnable(){
			@Override
			public void run() {
				mResponseHandler.onError(a_resObject);
			}
		}) ;
	}

	/**  
	 * callback handler for HttpRequest error
	 * @param-ASDKError<?>
	 * @return-void
	 */
	@Override
	public void onHttpRequestError(ASDKError a_err) {
		System.out.println("Hi");
	}

	/**  
	 * callback handler for HttpResponse 
	 * @param-ASDKError<?>
	 * @return-void
	 */
	@Override
	public void onReceiveResponse(ResponseObject<?> a_resObject) {
		System.out.println("Hi");
	}

	/**  
	 * getter for application context
	 * @param-void
	 * @return-Context
	 */
	public Context getContext() {
		return mContext;
	}

	/**  
	 * setter for application context
	 * @param-Context
	 * @return-void
	 */
	public void setContext(Context a_context) {
		this.mContext = a_context;
	}
}
