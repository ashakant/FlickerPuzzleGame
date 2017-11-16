package com.flickerpuzzle.component.cache.cacheverifier;

/**
 * Created by ashakant on 11/16/17.
 */


import com.flickerpuzzle.component.api.GetFlickrPhotoSetsApi;

import java.util.concurrent.Future;



public interface IDTOCacheVerifier {
    Future<?> cacheVerifier(GetFlickrPhotoSetsApi a_handler) ;
}
