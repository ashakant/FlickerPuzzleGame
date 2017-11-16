package com.flickerpuzzle.component.dto;


/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public class PhotoObjectDTO extends Object{
	private String mPhotoFarm ;
	private String mPhotoServer ;
	private String mPhotoId ;
	private String mPhotoSecret ;

	public PhotoObjectDTO(String a_photofarm,String a_photoserverid,
			String a_photoid,String photosecret){
		this.mPhotoFarm=a_photofarm ;
		this.mPhotoServer=a_photoserverid ;
		this.mPhotoId=a_photoid ;
		this.mPhotoSecret=photosecret ;
	}
	
	public PhotoObjectDTO(){
	}

	/**  
	 * get PhotoFarm as parameter
	 * @param-void
	 * @return-String
	 */
	public String getPhotoFarm() {
		return mPhotoFarm;
	}

	/**  
	 * set PhotoFarm as parameter
	 * @param-String
	 * @return-void
	 */
	public void setPhotoFarm(String a_photoFarm) {
		this.mPhotoFarm = a_photoFarm;
	}

	/**  
	 * get photo server as parameter
	 * @param-void
	 * @return-String
	 */
	public String getPhotoServer() {
		return mPhotoServer;
	}
	
	/**  
	 * set server as parameter
	 * @param-String
	 * @return-void
	 */
	public void setPhotoServer(String a_photoServerId) {
		this.mPhotoServer = a_photoServerId;
	}

	/**  
	 * get Photo id as parameter
	 * @param-void
	 * @return-String
	 */
	public String getPhotoId() {
		return mPhotoId;
	}

	/**  
	 * set id as parameter
	 * @param-String
	 * @return-void
	 */
	public void setPhotoId(String a_photoId) {
		this.mPhotoId = a_photoId;
	}
	
	/**  
	 * get Photo secret as parameter
	 * @param-void
	 * @return-String
	 */
	public String getPhotoSecret() {
		return mPhotoSecret;
	}

	/**  
	 * set secret as parameter
	 * @param-String
	 * @return-void
	 */
	public void setPhotoSecret(String a_photoSecret) {
		this.mPhotoSecret = a_photoSecret;
	}
}
