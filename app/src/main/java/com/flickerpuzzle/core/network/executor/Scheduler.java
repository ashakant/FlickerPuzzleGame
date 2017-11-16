package com.flickerpuzzle.core.network.executor;

/**
 * Created by ashakant on 11/16/17.
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;



public class Scheduler {
    private PausableThreadPoolExecutor mExecutor;
    private LinkedBlockingQueue<Runnable> mQueue;
    private static Scheduler instance = new Scheduler( );

    private  Scheduler() {
        int processors = Runtime.getRuntime().availableProcessors();
        mQueue = new LinkedBlockingQueue<Runnable>();
        mExecutor = new PausableThreadPoolExecutor(processors, 10, 10, TimeUnit.SECONDS, mQueue);
    }

    /**
     * return single instance
     * @param-void
     * @return-Scheduler
     */
    public static Scheduler getInstance( ) {
        return instance;
    }

    /**
     * put the thread in executor queue
     * @param-Runnable
     * @return-void
     */
    public void schedule(Runnable a_runnable) {
        mExecutor.execute(a_runnable);
    }

    /**
     * Pause the thread
     * @param-void
     * @return-void
     */
    public void pause() {
        mExecutor.pause();
    }

    /**
     * Resume the thread
     * @param-void
     * @return-void
     */
    public void resume() {
        mExecutor.resume();
    }

    /**
     * free all thread from queue
     * @param-void
     * @return-void
     */
    public void clear() {
        mQueue.clear();
    }

    /**
     * check is thread queue is empty
     * @param-void
     * @return-boolean
     */
    public boolean isEmpty() {
        return mQueue.isEmpty();
    }
}
