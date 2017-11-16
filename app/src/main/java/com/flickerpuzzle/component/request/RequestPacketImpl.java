package com.flickerpuzzle.component.request;

import java.util.Map;

/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public class RequestPacketImpl extends RequestPacket{

	private String mUrl;
    private String mData;
    private Map<String, String> mHeader;
    private String mMethod;
    
    /**  
     * getter for Request url
     * @param-void
     * @return-String
     */
 	@Override
	public String getUrl() {
		return this.mUrl;
	}

 	  /**  
     * getter for Request data
     * @param-void
     * @return-String
     */
 	@Override
	public String getData() {
		return this.mData;
	}

	  /**  
     * getter for Request header
     * @param-void
     * @return-Map<String, String>
     */
 	@Override
	public Map<String, String> getHeader() {
		return this.mHeader;
	}

	  /**  
     * getter for Request method
     * @param-void
     * @return-String
     */
	@Override
	public String getMethod() {
		return mMethod;
	}

	  /**  
     * setter for Request url
     * @param-String
     * @return-void
     */
	@Override
	public void setUrl(String a_url) {
		this.mUrl=a_url ;
	}

	  /**  
     * setter for Request data
     * @param-voStringid
     * @return-void
     */
	@Override
	public void setData(String a_data) {
		this.mData=a_data ;
		
	}

	  /**  
     * setter for Request header
     * @param-Map<String, String>
     * @return-void
     */
	@Override
	public void setHeader(Map<String, String> a_header) {
		this.mHeader=a_header ;
		
	}

	  /**  
     * setter for Request method
     * @param-String
     * @return-void
     */
	@Override
	public void setMethod(String a_method) {
		this.mMethod=a_method ;
	}

	  /**  
     * release memomry of header
     * @param-void
     * @return-void
     */
	@Override
	public void release() {
		mHeader.clear();
		
	}
}
