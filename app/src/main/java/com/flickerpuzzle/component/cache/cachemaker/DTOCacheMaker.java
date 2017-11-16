package com.flickerpuzzle.component.cache.cachemaker;

/**
 * Created by ashakant on 11/16/17.
 */

import android.content.ContentValues;
import android.content.res.Resources;

import com.flickerpuzzle.R;
import com.flickerpuzzle.component.api.GetFlickrPhotoSetsApi;
import com.flickerpuzzle.component.api.ResponseObject;
import com.flickerpuzzle.component.dto.FlickrDTO;
import com.flickerpuzzle.core.database.AClientSQLiteHelper;
import com.flickerpuzzle.core.database.DataBaseCallBack;
import com.flickerpuzzle.core.database.SDKClientSqlDb;

import java.util.List;



public class DTOCacheMaker implements IDTOCacheMaker{

    /**
     * Create caching of response object to sqlite db
     * @param-GetFlickrPhotoSetsApi,ResponseObject<?>
     * @return-boolean
     */
    @Override
    public boolean cacheMaker(GetFlickrPhotoSetsApi a_handler,ResponseObject<?> a_response) {

        final Resources r=a_handler.getContext().getResources() ;
        AClientSQLiteHelper dbsmallurl = new AClientSQLiteHelper(a_handler.getContext(),
                r.getString(R.string.flick_url_schemaCmd_small),
                r.getString(R.string.flick_url_table_small)) ;

        final SDKClientSqlDb  sqlDbSmall=new SDKClientSqlDb(dbsmallurl) ;
        final FlickrDTO flickrDTOSmall=(FlickrDTO)a_response.get() ;

        List<String>    thumbImgList=flickrDTOSmall.getThumbPhotUrl() ;
        List<String>    mediumImgList=flickrDTOSmall.getMainPhotUrl() ;
        for(int i=0;i<flickrDTOSmall.getThumbPhotUrl().size();i++){
            final ContentValues value_s=new ContentValues() ;
            value_s.put(r.getString(R.string.col_url_small),
                    thumbImgList.get(i));
            value_s.put(r.getString(R.string.col_url_medium),
                    mediumImgList.get(i));
            sqlDbSmall.insert(r.getString(R.string.flick_url_table_small),null,value_s,new DataBaseCallBack(){
                @Override
                public void exec(long l) {
                    System.out.println("Sucessfully added");
                }
            }) ;
        }
        return true;
    }
}
