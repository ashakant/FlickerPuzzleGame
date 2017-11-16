package com.flickerpuzzle.client;


import android.content.Context;

import com.flickerpuzzle.core.network.executor.Scheduler;
/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public class UserSession implements IUserSession{
	
	/**  
	 * get user profile instance 
	 * @param-void
	 * @return-UserProfile
	 * etc
	 */
	@Override
	public IUserProfile getUserProfile() {
		return new UserProfile() ;
	}

	/**  
	 * set user settings instance 
	 * @param-void
	 * @return-ISettings
	 * etc
	 */
	@Override
	public ISettings getSetings() {
		return new Settings() ;
	}

	/**  
	 * called during activity life cycle to resume session and map the all network and database thread 
	 * according to activity life cycle for consistent behavior of application 
	 * @param-void
	 * @return-void
	 * 
	 */
	@Override
	public void onResumeSession() {
		Scheduler.getInstance().resume() ;
	}
	/**  
	 * called during activity life cycle to resume session and map the all network and database thread 
	 * according to activity life cycle for consistent behavior of application 
	 * @param-void
	 * @return-void
	 * 
	 */
	@Override
	public void onPauseSession() {
		Scheduler.getInstance().pause() ;
	}

	@Override
	public void onCloseSession() {
		Scheduler.getInstance().clear() ;
	}
	/**  
	 * called during activity life cycle to resume session and map the all network and database thread 
	 * according to activity life cycle for consistent behavior of application 
	 * @param-void
	 * @return-void
	 * 
	 */
	@Override
	public IApiCaller getApiCaller(Context a_Context) {
		return new ApiCaller(a_Context) ;
	}

}
