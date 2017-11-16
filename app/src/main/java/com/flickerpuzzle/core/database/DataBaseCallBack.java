package com.flickerpuzzle.core.database;

/**
 * Created by ashakant on 11/16/17.
 */

public abstract class DataBaseCallBack {
    abstract public void exec(long a_l); //long 'l' is the number of rows affected.
    public void onError(Exception a_e) {}
}
