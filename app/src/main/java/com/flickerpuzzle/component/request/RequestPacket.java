package com.flickerpuzzle.component.request;

import java.util.Map;

/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public abstract class RequestPacket {
	public abstract String getUrl(); 
	public abstract String getData();
	public abstract Map<String, String> getHeader();
	public abstract String getMethod(); 
	
	public abstract void setUrl(String  a_url); 
	public abstract void setData(String a_data);
	public abstract void setHeader(Map<String, String> a_header);
	public abstract void setMethod(String a_method); 
	public abstract void release() ;
}
