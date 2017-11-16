package com.flickerpuzzle.component.request;

import com.flickerpuzzle.component.api.GetFlickrPhotoSetsApi;
import com.flickerpuzzle.component.api.ImageDownLoaderHandler;

/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public interface IRequestGenerator {
	RequestPacket makeRequest(ImageDownLoaderHandler a_handler) ;
	RequestPacket makeRequest(GetFlickrPhotoSetsApi a_handler) ;

	
}
