package com.flickerpuzzle.component.dto;

import java.util.ArrayList;
import java.util.List;


/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public class FlickerDataObject extends Object{
	private List<PhotoObjectDTO> mPhotoObjectArray ;
	private String mOwnerName ;
	private	int mPhotoNumber ;
	
	public FlickerDataObject(){
		mPhotoObjectArray=new ArrayList<PhotoObjectDTO>() ;
	}

	/**  
	 * get user name as DTO
	 * @param-void
	 * @return-String
	 */
	public String getOwnerName() {
		return mOwnerName;
	}

	/**  
	 * set user name as DTO
	 * @param-String
	 * @return-void
	 */
	public void setOwnerName(String a_OwnerName) {
		this.mOwnerName = a_OwnerName;
	}

	/**  
	 * get user PhotoObjectDTO 
	 * @param-void
	 * @return-List<PhotoObjectDTO>
	 */
	public List<PhotoObjectDTO> getPhotoObjectArray() {
		return mPhotoObjectArray;
	}

	/**  
	 * set user name as DTO
	 * @param-List<PhotoObjectDTO>
	 * @return-void
	 */
	public void setPhotoObjectArray(List<PhotoObjectDTO> a_PhotoObjectArray) {
		this.mPhotoObjectArray = a_PhotoObjectArray;
	}
	
	/**  
	 * get user photNumber
	 * @param-void
	 * @return-List<PhotoObjectDTO>
	 */
	public int getPhotoNumber() {
		return mPhotoNumber;
	}
	
	/**  
	 * set user photoNumber DTO
	 * @param-int
	 * @return-void
	 */
	public void setPhotoNumber(int a_PhotoNumber) {
		this.mPhotoNumber = a_PhotoNumber;
	}
}
