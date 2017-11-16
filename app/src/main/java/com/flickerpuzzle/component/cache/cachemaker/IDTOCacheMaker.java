package com.flickerpuzzle.component.cache.cachemaker;

import com.flickerpuzzle.component.api.GetFlickrPhotoSetsApi ;
import com.flickerpuzzle.component.api.ResponseObject ;
/**
 * Created by ashakant on 11/16/17.
 */

public interface IDTOCacheMaker {
    boolean cacheMaker(GetFlickrPhotoSetsApi a_handler,ResponseObject<?> a_response) ;
}
