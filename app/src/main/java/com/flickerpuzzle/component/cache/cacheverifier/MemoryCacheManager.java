package com.flickerpuzzle.component.cache.cacheverifier;

/**
 * Created by ashakant on 11/16/17.
 */

import android.graphics.Bitmap;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;


public class MemoryCacheManager {
    /**Last argument true for LRU ordering */
    private Map<String, Bitmap> cache = Collections.synchronizedMap(
            new LinkedHashMap<String, Bitmap>(10,1.5f,true));
    /**current allocated size*/
    private long size=0;
    /**max memory cache folder used to download images in bytes*/
    private long limit=1000000;
    public MemoryCacheManager(){
        /**use 25% of available heap size*/
        setLimit(Runtime.getRuntime().maxMemory()/4);
    }

    /**
     * Limit the size of main meomry cache
     * @param-long
     * @return-void
     */
    public void setLimit(long a_new_limit){
        limit=a_new_limit;
    }

    /**
     * Return bitmap corresponding to mapping of url and  image bitmap
     * @param-String
     * @return-Bitmap
     */
    public Bitmap get(String a_id){
        try{
            if(!cache.containsKey(a_id))
                return null;
            /**NullPointerException sometimes happen here http://code.google.com/p/osmdroid/issues/detail?id=78 */
            return cache.get(a_id);
        }catch(NullPointerException ex){
            return null;
        }
    }

    /**
     *  put url and bitmap mapping in main memory cache
     * @param-String,Bitmap
     * @return-void
     */
    public void put(String a_id, Bitmap a_bitmap){
        try{
            if(cache.containsKey(a_id))
                size-=getSizeInBytes(cache.get(a_id));
            cache.put(a_id, a_bitmap);
            size+=getSizeInBytes(a_bitmap);
            checkSize();
        }catch(Throwable th){
        }
    }

    /**
     * validates the size of cache
     * @param-void
     * @return-void
     */
    private void checkSize() {
        if(size>limit){
            Iterator<Entry<String, Bitmap>> iter=cache.entrySet().iterator();
            /**least recently accessed item will be the first one iterated */
            while(iter.hasNext()){
                Entry<String, Bitmap> entry=iter.next();
                size-=getSizeInBytes(entry.getValue());
                iter.remove();
                if(size<=limit)
                    break;
            }
        }
    }

    /**
     * clear main memory cache
     * @param-void
     * @return-void
     */
    public void clear() {
        try{
            /**NullPointerException sometimes happen here http://code.google.com/p/osmdroid/issues/detail?id=78 */
            cache.clear();
            size=0;
        }catch(NullPointerException ex){
        }
    }

    /**
     * return the size of bitmap
     * @param-Bitmap
     * @return-long
     */
    long getSizeInBytes(Bitmap bitmap) {
        if(bitmap==null)
            return 0;
        return bitmap.getRowBytes() * bitmap.getHeight();
    }
}