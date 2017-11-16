package com.flickerpuzzle.component.api;

import android.content.Context;

import com.flickerpuzzle.component.request.RequestPacket;
import com.flickerpuzzle.core.network.HttpClientService;
import com.flickerpuzzle.core.network.executor.Scheduler;

import org.apache.http.MethodNotSupportedException;

import java.net.URISyntaxException;

/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public class ApiInvoker implements IApiInvoker{
	protected	HttpClientService mHttpClientService ; 
	protected	Context mContext ;

	public ApiInvoker(Context a_context){
		this.mContext=a_context;
	}

	/**  
	 * Invoker to api to http layer encapsulates one layer information to another layer , central point of
	 * contact with http layer / module
	 * @param-ABaseApiCaller,RequestPacket
	 * @return-boolean
	 */
	@Override
	public boolean invoke(final ABaseApiCaller a_handler,RequestPacket a_reqPck) {
		/**
		 * Note : 
		 * start of new thread call ,that is http thread here 
		 */
		mHttpClientService = new HttpClientService(mContext,"") ;
		try {
			mHttpClientService.submitRequest(a_reqPck.getUrl(), a_reqPck.getHeader(), 
					a_reqPck.getData(), a_reqPck.getMethod(),a_handler);
		} catch (MethodNotSupportedException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		Scheduler scheduler =Scheduler.getInstance() ;
		scheduler.schedule(mHttpClientService);
		return true;
	}
}
