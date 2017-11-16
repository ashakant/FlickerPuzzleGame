package com.flickerpuzzle.component.request;

import com.flickerpuzzle.component.api.GetFlickrPhotoSetsApi;
import com.flickerpuzzle.component.api.ImageDownLoaderHandler;

/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public class RequestGenerator implements IRequestGenerator{
	
	/**  
	 * Responsible to create all request packet corresponding  to particular API
	 * @param-ImageDownLoaderHandler
	 * @return-RequestPacket
	 */
	@Override
	public RequestPacket makeRequest(final ImageDownLoaderHandler a_handler) {
		RequestPacketImpl flickrAppsRequest=new RequestPacketImpl() ;
		AApiUrlBuilder  mBuilder=new AApiUrlBuilder() ;   ;
		/**1. 	set URL */
		flickrAppsRequest.setUrl(mBuilder.buildFlickrUrl()) ;
		/**2. 	set Header */
		flickrAppsRequest.setHeader(null);
		/**3. 	set Method */
		flickrAppsRequest.setMethod("get") ;
		/**4	set Data */
		/**set Data */
		flickrAppsRequest.setData(null) ;
		return flickrAppsRequest;
	}

	/**  
	 * Responsible to create all request packet corresponding  to particular API
	 * @param-GetFlickrPhotoSetsApi
	 * @return-RequestPacket
	 */
	@Override
	public RequestPacket makeRequest(GetFlickrPhotoSetsApi a_handler) {
		RequestPacketImpl flickrAppsRequest=new RequestPacketImpl() ;
		AApiUrlBuilder  builder=new AApiUrlBuilder() ;
		/**1. 	set URL */
		flickrAppsRequest.setUrl(builder.buildFlickrUrl()) ;
		/**2. 	set Header */
		flickrAppsRequest.setHeader(null);
		/**3. 	set Method */
		flickrAppsRequest.setMethod("get") ;
		/**4	set Data */
		/**set Data */
		flickrAppsRequest.setData(null) ;
		return flickrAppsRequest;
	}
}
