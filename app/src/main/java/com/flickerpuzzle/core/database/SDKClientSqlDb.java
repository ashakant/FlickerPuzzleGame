package com.flickerpuzzle.core.database;

/**
 * Created by ashakant on 11/16/17.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.flickerpuzzle.core.network.executor.Scheduler;

import java.util.concurrent.Future;


public class SDKClientSqlDb {
    private final SQLiteDatabase mDdb;
    /**
     * An executor which provides thread on which results from queries will be returned
     */
    private Scheduler mScheduler;

    public SDKClientSqlDb(SQLiteOpenHelper helper) {
        mDdb = helper.getWritableDatabase();
        /**
         *  Writable database handles both  reads and writes
         */
        /**
         Sync pragma in sqlite forces each write to be commited to disk before returning, this can
         be very slow and is not typically required by most applications. Setting disable sync to 'true'
         disables this and speeds up dbwrites significantly. More information -http://www.sqlite.org/pragma
         .html#pragma_synchronous
         */
        mDdb.execSQL("PRAGMA synchronous=0");
        /**if (Build.VERSION.SDK_INT >= 11)
         {
         Write ahead logging is available from android api 11 and higher and significantly speeds up database
         operations. More info - http://www.sqlite.org/draft/wal.html _db.enableWriteAheadLogging();
         }*/
        mScheduler = Scheduler.getInstance();
    }


    public <RESULT> Future<RESULT> query(final String a_table, final String[] a_columns, final String a_selection,
                                         final String[] a_selectionArgs,
                                         final String a_groupBy,
                                         final String a_having,
                                         final String a_orderBy,
                                         final String a_limit,
                                         final ICursorHandler<RESULT> a_handler) {
        final Later<RESULT> lresult = new Later<RESULT>();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Cursor c = syncQuery(a_table, a_columns, a_selection, a_selectionArgs, a_groupBy, a_having, a_orderBy, a_limit);
                final RESULT result = a_handler.handle(c);
                closeCursor(c);
                lresult.set(result);
                mScheduler.schedule(new Runnable() {
                    @Override
                    public void run() {
                        a_handler.callback(result);
                    }
                });
            }
        };
        mScheduler.schedule(r);
        return lresult;
    }

    public <RESULT> Future<RESULT> query(final String a_table,
                                         final String[] a_columns,
                                         final String a_selection,
                                         final String[] a_selectionArgs,
                                         final String a_groupBy,
                                         final String a_having,
                                         final String a_orderBy,
                                         final ICursorHandler<RESULT> a_handler) {
        return query(a_table, a_columns, a_selection, a_selectionArgs, a_groupBy,
                a_having, a_orderBy, null /*limit*/, a_handler);
    }

    public <RESULT> Future<RESULT> query(final String a_table,
                                         final String[] a_columns,
                                         final String a_selection,
                                         final String[] a_selectionArgs,
                                         final ICursorHandler<RESULT> a_handler) {
        return query(a_table, a_columns, a_selection, a_selectionArgs, null, null,
                null, a_handler);
    }

    /**
     * Modification methods: These methods execute on a single thread dedicated
     * for DB writes. They return the number of rows effected via a DBCallback.
     * All these methods return a Future, instead of a void, and this future can be used by the calling thread to wait till
     * a result is available. This provides, some sort of synchronous support in this otherwise async library.
     */
    public Future<Integer> delete(final String a_table, final String a_whereClause,
                                  final String[] a_whereArgs, final DataBaseCallBack a_cb) {
        final Later<Integer> lresult = new Later<Integer>();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    int numRows = mDdb.delete(a_table, a_whereClause, a_whereArgs);
                    lresult.set(numRows);
                    callbackInAppExecutor(a_cb, numRows);
                } catch (Exception e) {
                    errorbackInAppExecutor(a_cb, e);
                }
            }
        };
        mScheduler.schedule(r);
        return lresult;
    }

    public Future<Long> insertWithOnConflict(final String a_table,
                                             final String a_nullColumnHack,
                                             final ContentValues a_initialValues,
                                             final int a_conflictAlgorithm,
                                             final DataBaseCallBack a_cb) {
        final Later<Long> lresult = new Later<Long>();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    long id = mDdb.insertWithOnConflict(a_table, a_nullColumnHack,
                            a_initialValues, a_conflictAlgorithm);
                    lresult.set(id);
                    callbackInAppExecutor(a_cb, id);
                } catch (Exception e) {
                    errorbackInAppExecutor(a_cb, e);
                }
            }
        };
        this.mScheduler.schedule(r);
        return lresult;
    }

    public Future<Long> insert(String a_table, String a_nullColumnHack,
                               ContentValues a_values, DataBaseCallBack a_cb) {
        return insertWithOnConflict(a_table, a_nullColumnHack, a_values,
                SQLiteDatabase.CONFLICT_NONE, a_cb);
    }

    public Future<Integer> updateWithOnConflict(final String a_table,
                                                final ContentValues a_values,
                                                final String a_whereClause,
                                                final String[] a_whereArgs,
                                                final int a_conflictAlgorithm,
                                                final DataBaseCallBack a_cb) {
        final Later<Integer> lresult = new Later<Integer>();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    int numRows = mDdb.updateWithOnConflict(a_table, a_values,
                            a_whereClause, a_whereArgs, a_conflictAlgorithm);
                    lresult.set(numRows);
                    callbackInAppExecutor(a_cb, numRows);
                } catch (Exception e) {
                    errorbackInAppExecutor(a_cb, e);
                }
            }
        };
        this.mScheduler.schedule(r);
        return lresult;
    }

    /**
     * --------------------------------------- PRIVATES -----------------------------------------------------------
     */
    private Cursor syncQuery(String a_table, String[] a_columns, String a_selection, String[] a_selectionArgs,
                             String a_groupBy, String a_having, String a_orderBy, String a_limit) {
        return mDdb.query(a_table, a_columns, a_selection, a_selectionArgs,
                a_groupBy, a_groupBy, a_orderBy, a_limit);
    }

    private void callbackInAppExecutor(final DataBaseCallBack a_cb, final long a_arg) {
        if (a_cb != null) {

            mScheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    a_cb.exec(a_arg);
                }
            });
        }
    }

    private void errorbackInAppExecutor(final DataBaseCallBack a_cb, final Exception e) {
        if (a_cb != null) {
            mScheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    a_cb.onError(e);
                }
            });
        }
    }

    private void closeCursor(Cursor a_cursor) {
        if (!a_cursor.isClosed()) {
            a_cursor.close();
        }
    }
}