package com.flickerpuzzle.component.cache.cacheverifier;

/**
 * Created by ashakant on 11/16/17.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageFileCacheManager {

    private File mCacheDir;

    public ImageFileCacheManager(Context a_context){
        /**
         * Find the dir at SDCARD to save cached images
         * */
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
            /**
             * if SDCARD is mounted (SDCARD is present on device and mounted)
             * */
            mCacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"LazyList");
        }
        else{
            /**
             * if checking on simulator the create cache dir in your application context
             * */
            mCacheDir=a_context.getCacheDir();
        }
        if(!mCacheDir.exists()){
            /**create cache dir in your application context */
            mCacheDir.mkdirs();
        }
    }

    public File getFile(String a_url){
        /**Identify images by hashcode or encode by URLEncoder.encode */
        String filename=String.valueOf(a_url.hashCode());

        File f = new File(mCacheDir, filename);
        return f;

    }
    /**
     * clear memory cache
     * @param-void
     * @return-void
     */
    public void clear(){
        /**list all files inside cache directory */
        File[] files=mCacheDir.listFiles();
        if(files==null)
            return;
        /**delete all cache directory files */
        for(File f:files)
            f.delete();
    }

    /**
     * copy inut stream to output stream
     * @param-InputStream,OutputStream
     * @return-void
     */
    public void CopyStream(InputStream a_is, OutputStream a_os){
        final int buffer_size=1024;
        try{
            byte[] bytes=new byte[buffer_size];
            for(;;){
                /**Read byte from input stream */
                int count=a_is.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;
                /**Write byte from output stream */
                a_os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }

    /**
     * decode the images , down sample the image size for minimize memory size
     * @param-File
     * @return-Bitmap
     */
    public Bitmap decodeFile(File a_f){
        try {
            /**Decode image size */
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream stream1=new FileInputStream(a_f);
            BitmapFactory.decodeStream(stream1,null,o);
            stream1.close();
            /**Find the correct scale value. It should be the power of 2.*/
            /**Set width/height of recreated image */
            final int REQUIRED_SIZE=85;

            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }
            /**decode with current scale values */
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            FileInputStream stream2=new FileInputStream(a_f);
            Bitmap bitmap=BitmapFactory.decodeStream(stream2, null, o2);
            stream2.close();
            return bitmap;

        } catch (FileNotFoundException e) {
        }
        catch (IOException e) {
        }
        return null;
    }

    /**
     * helper function to recursively delete directory of cache in case of clear cache call
     * @param-File
     * @return-Bitmap
     */

    public void deleteRecursive(File a_fileOrDirectory) {
        if (a_fileOrDirectory.isDirectory())
            for (File child : a_fileOrDirectory.listFiles())
                deleteRecursive(child);
        a_fileOrDirectory.delete();
    }
}
