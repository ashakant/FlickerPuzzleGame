package com.flickerpuzzle.component.request;

import com.flickerpuzzle.component.api.IAApiUrlBuilder;
import com.flickerpuzzle.component.dto.PhotoObjectDTO;

/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public class AApiUrlBuilder implements IAApiUrlBuilder{
	public static final String FLICKR_API_KEY = "ee20619c4016f181c4db15d7cb01398c";
    public static final String FLICKR_FORMAT = "json";
    //public static final String PHOTOSET_ID = "72157648862199180";
    public static final String PHOTOSET_ID = "72157649979789165";
    
    public static final String BASE_URL="https://www.flickr.com/services/rest/?" ;
 	protected String mUrl;

	public String getUrl() {
		return mUrl;
	}

	/**  
	 * setter for api url
	 * @param-String
	 * @return-void
	 */
	public void setUrl(String a_url) {
		this.mUrl = a_url;
	}

	/**  
	 * Url builder for main flicker json content url 
	 * @param-void
	 * @return-String
	 */
	@Override
	public String buildFlickrUrl() {
		this.mUrl=RequestPacketConstant.BASE_URL+
				"method="+RequestPacketConstant.MethodKeys.METHOD_FLICKR_GETPHOTO+
				"format="+RequestPacketConstant.RequestKeys.FORMAT_KEY+
				"api_key="+FLICKR_API_KEY+"&"+
				"photoset_id="+PHOTOSET_ID
				;
		return mUrl;
	}
	
	/**  
	 * Url builder for fetching different size of images like -small images . big images etc
	 * @param-void
	 * @return-String
	 */
	public String buildFlickrPhotoSmallUrl(PhotoObjectDTO a_photoObj,String a_fileSize){
		this.mUrl=RequestPacketConstant.PhotoSetsKeys.INITIAL+
				a_photoObj.getPhotoFarm()+
				RequestPacketConstant.PhotoSetsKeys.MIDDLE_KEY+
				a_photoObj.getPhotoServer()+
				"/"+
				a_photoObj.getPhotoId()+
				"_"+
				a_photoObj.getPhotoSecret()+
				a_fileSize ;
		return mUrl;
	}
}
