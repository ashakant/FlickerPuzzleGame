package com.flickerpuzzle.component.response;

import com.flickerpuzzle.component.api.GetFlickrPhotoSetsApi;
import com.flickerpuzzle.component.api.ImageDownLoaderHandler;
import com.flickerpuzzle.component.api.ResponseObject;

/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public interface IResponseParser {
	Object parse(ImageDownLoaderHandler handler, ResponseObject<?> a_Response) ;
	Object parse(GetFlickrPhotoSetsApi handler, ResponseObject<?> a_Response) ;
}
