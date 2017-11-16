package com.flickerpuzzle.component.response;

import com.flickerpuzzle.component.api.GetFlickrPhotoSetsApi;
import com.flickerpuzzle.component.api.ImageDownLoaderHandler;
import com.flickerpuzzle.component.api.ResponseObject;
import com.flickerpuzzle.component.dto.FlickerDataObject;
import com.flickerpuzzle.component.dto.FlickrDTO;
import com.flickerpuzzle.component.dto.PhotoObjectDTO;
import com.flickerpuzzle.component.request.AApiUrlBuilder;
import com.flickerpuzzle.component.request.RequestPacketConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public class ResponseParser implements IResponseParser{
	
	@Override
	public Object parse(final ImageDownLoaderHandler a_handler,ResponseObject<?> a_response) {
		return false;
	}
	
	/**  
	 * Parse the raw content send by flicker server api , shared by http module
	 * @param-GetFlickrPhotoSetsApi,ResponseObject<?> 
	 * @return-Object
	 */
	@Override
	public Object parse(GetFlickrPhotoSetsApi a_handler,final ResponseObject<?> a_response) {
		final FlickrDTO mFlickrDTO=new FlickrDTO() ;
		String resString=(String)a_response.get() ;
		resString=resString.replace("jsonFlickrApi(", "");
		resString=resString.replace(")", "") ;
		JSONObject responseObject ;
		try {
			responseObject = new JSONObject((String)resString);
			String photset=responseObject.getString("photoset") ;
			JSONObject photosetsObject=new JSONObject(photset) ;
			FlickerDataObject  flickObject=new FlickerDataObject() ;
			flickObject.setOwnerName(photosetsObject.getString("ownername"));
			JSONArray arr=photosetsObject.getJSONArray("photo") ;
			List<PhotoObjectDTO> tempPhotoObjectArray=new ArrayList<PhotoObjectDTO>() ;
			AApiUrlBuilder  builder=new AApiUrlBuilder() ;
			for(int i=0;i<arr.length();i++){
				PhotoObjectDTO photoObjectDTO=new PhotoObjectDTO() ;
				JSONObject PhotoObjectJsonObj=new JSONObject(arr.get(i).toString()) ;
				photoObjectDTO.setPhotoFarm(PhotoObjectJsonObj.getString("farm"));
				photoObjectDTO.setPhotoServer(PhotoObjectJsonObj.getString("server"));
				photoObjectDTO.setPhotoId(PhotoObjectJsonObj.getString("id"));
				photoObjectDTO.setPhotoSecret(PhotoObjectJsonObj.getString("secret"));
				String s_urlThumb=builder.buildFlickrPhotoSmallUrl(
						photoObjectDTO,
						RequestPacketConstant.PhotoSetsKeys.IMAGE_SMALL) ;
				System.out.println("ThumbUrl:"+s_urlThumb);
				String s_urlMedium=builder.buildFlickrPhotoSmallUrl(
						photoObjectDTO,
						RequestPacketConstant.PhotoSetsKeys.IMAGE_MEDIUM) ;
				System.out.println("ThumbUrl:"+s_urlMedium);
				mFlickrDTO.getThumbPhotUrl().add(s_urlThumb) ;
				mFlickrDTO.getMainPhotUrl().add(s_urlMedium) ;
				tempPhotoObjectArray.add(photoObjectDTO) ;
			}
			flickObject.setPhotoObjectArray(tempPhotoObjectArray);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return mFlickrDTO;
	}

}
