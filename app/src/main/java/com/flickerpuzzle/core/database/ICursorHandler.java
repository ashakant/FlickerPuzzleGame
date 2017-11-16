package com.flickerpuzzle.core.database;

/**
 * Created by ashakant on 11/16/17.
 */

import android.database.Cursor;

public interface ICursorHandler <OBJECT_TYPE> {
    /** This method is executed on a DB reader thread
     * Its implementation should convert the Cursor to some type useful for the application
     */
    OBJECT_TYPE handle(Cursor a_cursor);

    /** This method is called on a thread provided by the ExecutorService that is given to SqlDb in its constructor
     * It provides the application with the result of the work done in the handle() method.
     */
    void callback(OBJECT_TYPE a_result);
}
