package com.flickerpuzzle.core.network.executor;

/**
 * Created by ashakant on 11/16/17.
 */


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class PausableThreadPoolExecutor extends ThreadPoolExecutor {
    private boolean mIsPaused;
    private ReentrantLock mLock;
    private Condition mCondition;


    /**
     * @param corePoolSize    The size of the pool
     * @param maximumPoolSize The maximum size of the pool
     * @param keepAliveTime   The amount of time you wish to keep a single task alive
     * @param unit            The unit of time that the keep alive time represents
     * @param workQueue       The queue that holds your tasks
     * @see {@link ThreadPoolExecutor#ThreadPoolExecutor(int, int, long, TimeUnit, BlockingQueue)}
     */
    public PausableThreadPoolExecutor(int a_corePoolSize, int a_maximumPoolSize,
                                      long a_keepAliveTime, TimeUnit a_unit, BlockingQueue<Runnable> a_workQueue) {
        super(a_corePoolSize, a_maximumPoolSize, a_keepAliveTime, a_unit, a_workQueue);
        mLock = new ReentrantLock();
        mCondition = mLock.newCondition();
    }
    /**
     * called before the executation of thread
     * @param-thread   The thread being executed
     * @return-void
     */
    @Override
    protected void beforeExecute(Thread a_thread, Runnable a_runnable) {
        super.beforeExecute(a_thread, a_runnable);
        mLock.lock();
        try {
            while (mIsPaused)
                mCondition.await();
        } catch (InterruptedException ie) {
            a_thread.interrupt();
        } finally {
            mLock.unlock();
        }
    }

    /**
     * check is thread is running
     * @param-void
     * @return-boolean
     */
    public boolean isRunning() {
        return !mIsPaused;
    }

    /**
     * check is thread is pause
     * @param-void
     * @return-boolean
     */
    public boolean isPaused() {
        return mIsPaused;
    }

    /**
     * Pause the thread execution
     * @param-void
     * @return-void
     */
    public void pause() {
        mLock.lock();
        try {
            mIsPaused = true;
        } finally {
            mLock.unlock();
        }
    }

    /**
     * Resume thread execution
     * @param-void
     * @return-void
     */
    public void resume() {
        mLock.lock();
        try {
            mIsPaused = false;
            mCondition.signalAll();
        } finally {
            mLock.unlock();
        }
    }
}