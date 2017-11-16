package com.flickerpuzzle.client;

/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */


/**
 * 
 * @author test
 *
 */

public class AClient {
	protected IUserSession mSession;
	
	/**  
	 * Class Constructor
	 * @param
	 * @return
	 * etc
	 */
	public AClient(){
		mSession=new UserSession();
	}

	/**  
	 * getSession - not networking style session , session here signifies that client has been 
	 * authenticated properly and can start communication with server
	 * @param-void
	 * @return-IUserSession
	 * etc
	 */
	public IUserSession getSession() {
		return mSession;
	}
	/**  
	 * Setter for session
	 * @param-IUserSession
	 * @return-void
	 * etc
	 */
	public void setSession(IUserSession mSession) {
		this.mSession = mSession;
	}
}
