package com.flickerpuzzle.client;

import android.content.Context;

/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public interface IUserSession {
	public IUserProfile getUserProfile();
	public ISettings getSetings();
	public IApiCaller getApiCaller(Context a_Context);
	public void onResumeSession() ;
	public void onPauseSession() ;
	public void onCloseSession() ;
}
