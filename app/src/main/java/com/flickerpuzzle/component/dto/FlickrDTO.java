package com.flickerpuzzle.component.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public class FlickrDTO extends Object{
	private List<String>  mThumbPhotUrl;
	private List<String>  mMainPhotUrl;
	
	public FlickrDTO(){
		mThumbPhotUrl=new ArrayList<String>() ;
		mMainPhotUrl=new ArrayList<String>() ;
	}

	/**  
	 * get user ThumbPhotUrl
	 * @param-void
	 * @return-List<String>
	 */
	public List<String> getThumbPhotUrl() {
		return mThumbPhotUrl;
	}

	/**  
	 * set user ThumbPhotUrl
	 * @param-List<String>
	 * @return-void
	 */
	public void setThumbPhotUrl(List<String> mThumbPhotUrl) {
		this.mThumbPhotUrl = mThumbPhotUrl;
	}
	
	/**  
	 * get user main photo url
	 * @param-void
	 * @return-List<String> 
	 */
	public List<String> getMainPhotUrl() {
		return mMainPhotUrl;
	}

	/**  
	 * set user main photo url
	 * @param-List<String> 
	 * @return-void
	 */
	public void setMainPhotUrl(List<String> a_mainPhotUrl) {
		this.mMainPhotUrl = a_mainPhotUrl;
	}
	
	/**  
	 * get user photo small size url
	 * @param-void
	 * @return-List<String>
	 */
	public int getThumbPhotUrlSize(){
		return mThumbPhotUrl.size() ;
	}
	
	/**  
	 * get user photo small size url
	 * @param-void
	 * @return-int
	 */
	public int getMainPhotUrlSize(){
		return mMainPhotUrl.size() ;
	}
}
