package com.flickerpuzzle.client;

/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public class Settings implements ISettings{
	
	private String mAppKey ;
	private String mSecretCode ;
	
	
	public Settings(){
		mAppKey="ee20619c4016f181c4db15d7cb01398c";
		mSecretCode="ee20619c4016f181c4db15d7cb01398c" ;
	}
	/**  
	 * Return application key shared by flickr 
	 * @param-void
	 * @return-String 
	 * etc
	 */

	@Override
	public String getApplicationApiKey() {
		return mAppKey;
	}
	/**  
	 * Return secret key shared by flickr 
	 * @param-void
	 * @return-String 
	 * etc
	 */

	@Override
	public String getSecretCode() {
		return mSecretCode;
	}

}
