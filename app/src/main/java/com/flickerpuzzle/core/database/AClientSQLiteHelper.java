package com.flickerpuzzle.core.database;

/**
 * Created by ashakant on 11/16/17.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class AClientSQLiteHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "aclient.db" ;
    private static final int DATABASE_VERSION = 1 ;
    private String mTableCmd ;
    private String mTableName ;

    public AClientSQLiteHelper(Context a_context, String a_tableCmd,String a_tableName) {
        super(a_context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mTableCmd=a_tableCmd ;
        this.mTableName=a_tableName ;
    }

    /**
     * Android callback handler for on creation of database
     * @param-SQLiteDatabase
     * @return-void
     */
    @Override
    public void onCreate(SQLiteDatabase a_database) {
        a_database.execSQL(mTableCmd);
    }

    /**
     * Called as callback from OS in case of database upgrade
     * @param-SQLiteDatabase, int ,int
     * @return-void
     */
    @Override
    public void onUpgrade(SQLiteDatabase a_database, int a_arg1, int a_arg2) {
        a_database.execSQL("DROP TABLE IF EXISTS " + mTableName) ;
        onCreate(a_database);
    }
}