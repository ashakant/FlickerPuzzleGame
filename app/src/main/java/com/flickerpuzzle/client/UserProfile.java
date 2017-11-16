package com.flickerpuzzle.client;

/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public class UserProfile implements IUserProfile{

	private String mUserName ;

	/**  
	 * getter method to get username while parsing the server respsonse 
	 * @param-void
	 * @return-String
	 * etc
	 */
	@Override
	public String getUserName() {
		return mUserName;
	}
	/**  
	 * setter method to get username while parsing the server respsonse 
	 * @param-void
	 * @return-String
	 * etc
	 */
	@Override
	public void setUserName(String a_userName) {
		mUserName=a_userName ;
		
	}
}
