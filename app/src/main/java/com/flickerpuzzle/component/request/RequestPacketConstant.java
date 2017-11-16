package com.flickerpuzzle.component.request;

/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public class RequestPacketConstant {
	public static String BASE_URL="https://www.flickr.com/services/rest/?" ;
	public   String FLICKR_API_KEY = "819cb77d6338526760f00ea3a3ed9082";
	
	//ee20619c4016f181c4db15d7cb01398c
	public   final String PHOTOSET_ID = "72157649979789165";
	public interface MethodKeys{
		public static final String METHOD_FLICKR_GETPHOTO = "flickr.photosets.getPhotos&" ;
	}

	public interface RequestKeys{
		public static final String PHOTOSET_ID = "photoset_id";
		public static final String FORMAT_KEY = "json&" ;
	}

	public interface PhotoSetsKeys{
		public static final String INITIAL = "http://farm";
		public static final String MIDDLE_KEY = ".static.flickr.com/";
		public static final String IMAGE_MEDIUM = "_m.jpg";
		public static final String IMAGE_SMALL = "_m.jpg";
	}
}
