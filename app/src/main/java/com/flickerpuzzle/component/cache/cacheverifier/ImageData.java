package com.flickerpuzzle.component.cache.cacheverifier;

/**
 * Created by ashakant on 11/16/17.
 */

import android.graphics.Bitmap;

/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public class ImageData extends Object{

    private Bitmap mBitmap;

    public ImageData(Bitmap a_bitmap){
        this.mBitmap=a_bitmap;
    }

    /**
     * getter for Bitmap data/class
     * @param-void
     * @return-Bitmap
     */
    public Bitmap getBitmap() {
        return mBitmap;
    }

    /**
     * setter for Bitmap data/class
     * @param-Bitmap
     * @return-void
     */
    public void setBitmap(Bitmap a_bitmap) {
        this.mBitmap = a_bitmap;
    }
}
