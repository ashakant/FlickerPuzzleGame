package com.flickerpuzzle.component.cache.cacheverifier;

/**
 * Created by ashakant on 11/16/17.
 */
import android.content.res.Resources;
import android.database.Cursor;

import com.flickerpuzzle.R;
import com.flickerpuzzle.component.api.GetFlickrPhotoSetsApi;
import com.flickerpuzzle.component.api.ResponseObject;
import com.flickerpuzzle.component.dto.FlickrDTO;
import com.flickerpuzzle.component.request.IRequestGenerator;
import com.flickerpuzzle.component.request.RequestGenerator;
import com.flickerpuzzle.component.request.RequestPacket;
import com.flickerpuzzle.core.database.AClientSQLiteHelper;
import com.flickerpuzzle.core.database.ICursorHandler;
import com.flickerpuzzle.core.database.SDKClientSqlDb;

import java.util.List;
import java.util.concurrent.Future;

public class DTOCacheVerifier implements IDTOCacheVerifier{
    private Future<String> mDbFutureTask  ;

    /**
     * Verifies is requested content corresponding to api are avaialble in local cache before
     * fetching from server
     * @param-GetFlickrPhotoSetsApi
     * @return-Future<?>
     */
    @Override
    public Future<?> cacheVerifier(final GetFlickrPhotoSetsApi a_handler) {
        final Resources r=a_handler.getContext().getResources() ;
        final String[] columnSmallUrl=r.getStringArray(R.array.photoset_column_small) ;
        AClientSQLiteHelper dbS = new AClientSQLiteHelper(a_handler.getContext(),
                r.getString(R.string.flick_url_schemaCmd_small),
                r.getString(R.string.flick_url_table_small)) ;
        final SDKClientSqlDb  sqlDbSmall=new SDKClientSqlDb(dbS) ;

        final FlickrDTO  tempFlickrDTO=new FlickrDTO() ;
        try
        {
            mDbFutureTask=sqlDbSmall.query(r.getString(R.string.flick_url_table_small),
                    columnSmallUrl, null, null, new ICursorHandler<String>(){
                        @Override
                        public String handle(Cursor c) {
                            List<String> surlList=tempFlickrDTO.getThumbPhotUrl() ;
                            List<String> murlList=tempFlickrDTO.getMainPhotUrl() ;
                            if(c.moveToFirst()){
                                do{
                                    surlList.add(c.getString(c.getColumnIndex(columnSmallUrl[0]))) ;
                                    murlList.add(c.getString(c.getColumnIndex(columnSmallUrl[1]))) ;
                                }while (c.moveToNext());
                                c.close();
                                a_handler.postSucess(new ResponseObject<FlickrDTO>(tempFlickrDTO));
                            }
                            if(c.getCount()!=0){return "Sucess";}
                            else{return null ;}

                        }
                        @Override
                        public void callback(String result) {
                            if(result==null){
                                /**
                                 * 1.  Get build all request parameter -url,header,method, data here
                                 */
                                IRequestGenerator  reqGenerator=new RequestGenerator() ;
                                RequestPacket flickrRequest=reqGenerator.makeRequest(a_handler) ;
                                /**
                                 * 2.  Finally make Http call to Http module and get callback here
                                 */
                                //ashakant
                                //IApiInvoker invoker=new ApiInvoker(a_handler.getContext()) ;
                                //invoker.invoke(a_handler, flickrRequest);
                            }
                        }
                    }) ;
        }
        catch(Exception e)
        {
            System.out.println("DB Exception");
        }
        return mDbFutureTask ;
    }
}