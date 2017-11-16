package com.flickerpuzzle.component.api;

import com.flickerpuzzle.component.request.RequestPacket;

/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public interface IApiInvoker {
	boolean invoke(ABaseApiCaller a_handler, RequestPacket a_reqPck) ;
}
