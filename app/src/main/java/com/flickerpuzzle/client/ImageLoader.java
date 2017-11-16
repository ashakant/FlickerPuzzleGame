package com.flickerpuzzle.client;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.flickerpuzzle.component.cache.cacheverifier.ImageFileCacheManager;
import com.flickerpuzzle.component.cache.cacheverifier.MemoryCacheManager;

import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public class ImageLoader  {
	/**
	 * Initialize MemoryCache
	 * */
	private MemoryCacheManager mMemoryCache ;
	private ImageFileCacheManager mFileCache ;
	/**
	 * Create Map (collection) to store image and image url in key value pair 
	 * */
	private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>()) ;
	/** 
	 * get called only once for each list/grid view 
	 * */
	public ImageLoader(Context a_context){
		mFileCache = new ImageFileCacheManager(a_context) ;
		mMemoryCache = new MemoryCacheManager() ;
	}
	
	/**  
	 * check is bitmap/images corresponding to requested url are available in cache
	 * @param-String,ImageView
	 * @return-Bitmap
	 */
	public Bitmap isCached(String a_url,ImageView a_imageView){
		Bitmap bitmap ;
		bitmap=isImgInLocalCache(a_url,a_imageView) ;
		if(bitmap!=null ){
			return bitmap;
		}
		bitmap=isImgInExternalCache(a_url,a_imageView) ;
		return bitmap ;
	}
	
	/**  
	 * First level of verification , is requested images are available in local cache
	 * @param-String,ImageView
	 * @return-Bitmap
	 */
	private Bitmap isImgInLocalCache(String url,ImageView imageView)
	{
		/**
		 * Store image and Url in Map
		 * */
		imageViews.put(imageView, url) ;
		/**
		 * Check image is stored in MemoryCache Map or not (see MemoryCache.java)
		 * */
		Bitmap bitmap = mMemoryCache.get(url) ;
		if(bitmap!=null)
			return bitmap ;
		
		return bitmap ;
	}

	/**  
	 * Second level of verification , is requested images are available external memory
	 * @param-String,ImageView
	 * @return-Bitmap
	 */
	private Bitmap isImgInExternalCache(String url,ImageView imgView)
	{
		PhotoToLoad photoToLoad=new PhotoToLoad(url, imgView) ;
		
		/**
		 * Store image and url in PhotoToLoad object
		 * */
		/*if(imageViewReused(mPhotoToLoad)){
			return ;
		}*/
		File file=mFileCache.getFile(photoToLoad.url);
		/**
		 * from SD cache CHECK : if trying to decode file which not exist in cache return null 
		 * */
		Bitmap bmp = mFileCache.decodeFile(file);
		if(bmp!=null){
			/**
			 * set image data in local Memory Cache 
			 * */
			mMemoryCache.put(photoToLoad.url, bmp);
			if(imageViewReused(photoToLoad))
				return bmp;
		}
		return bmp ;
	}

	private class PhotoToLoad{
		public String url;
		public ImageView imageView;
		
		public PhotoToLoad(String u, ImageView i){
			url=u; 
			imageView=i;
		}
	}

	/**  
	 * check is is image requested is available in currently running memory
	 * @param-PhotoToLoad
	 * @return-boolean
	 */
	boolean imageViewReused(PhotoToLoad photoToLoad){
		String tag=imageViews.get(photoToLoad.imageView);
		/**Check url is already exist in imageViews MAP */
		if(tag==null || !tag.equals(photoToLoad.url))
			return true;
		return false;
	}
	
	/**  
	 * clear the cache of image 
	 * @param-void
	 * @return-void
	 */
	public void clearCache() {
		/**Clear cache directory downloaded images and stored data in maps */
		mMemoryCache.clear();
		mFileCache.clear();
	}

	/**  
	 * getter for memory cache
	 * @param-void
	 * @return-MemoryCacheManager
	 */
	public MemoryCacheManager getMemoryCache() {
		return mMemoryCache;
	}

	/**  
	 * setter for memory cache
	 * @param-MemoryCacheManager
	 * @return-void
	 */
	public void setMemoryCache(MemoryCacheManager mMemoryCache) {
		this.mMemoryCache = mMemoryCache;
	}
}
