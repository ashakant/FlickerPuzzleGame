package com.flickerpuzzle.component.api;

/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public class ASDKError {

	private int mError ;
	private String mErrorMsg;
	
	public ASDKError(int err,String a_errMsg){
		this.mError=err ;
		this.mErrorMsg=a_errMsg ;		
	}

	/**  
	 * get error id
	 * @param-void
	 * @return-int
	 */

	public int getError() {
		return mError;
	}
	
	/**  
	 * set error id
	 * @param-int
	 * @return-void
	 */
	public void setError(int a_error) {
		this.mError = a_error;
	}
	
	/**  
	 * set error message
	 * @param-void
	 * @return-String
	 */
	public String getErrorMsg() {
		return mErrorMsg;
	}
	
	/**  
	 * set error message
	 * @param-String
	 * @return-void
	 */
	public void setErrorMsg(String a_errorMsg) {
		this.mErrorMsg = a_errorMsg;
	}
	
}
