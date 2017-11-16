package com.flickerpuzzle.core.database;

/**
 * Created by ashakant on 11/16/17.
 */
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Later<RESULT> implements Future<RESULT>{
    /** An implementation of Future<?> that can wrap another future */
    private final Lock mLock;
    private final Condition mCondition;
    private RESULT mValue;
    private Future<?> mInner;
    private boolean mCancelled;
    private boolean mValueSet;

    public Later(){
        mLock = new ReentrantLock();
        mCondition = mLock.newCondition();
    }

    /**
     * A Generic class for cancel the future task
     * @param-boolean
     * @return-boolean
     */
    @Override
    public boolean cancel(boolean a_mayInterruptIfRunning){
        if (mInner == null){
            mCancelled = true;
            return false;
        }
        else{
            return mInner.cancel(a_mayInterruptIfRunning);
        }
    }

    /**
     * check is future task has been cancelled
     * @param-void
     * @return-boolean
     */
    @Override
    public boolean isCancelled(){
        if (mInner == null){
            return mCancelled;
        }
        else{
            return mInner.isCancelled();
        }
    }

    /**
     * check is future task has been done
     * @param-void
     * @return-boolean
     */
    @Override
    public boolean isDone(){
        if (mInner == null){
            return mValue != null;
        }
        else{
            return mInner.isDone();
        }
    }

    /**
     * get  future task
     * @param-void
     * @return-RESULT
     */
    @Override
    public RESULT get() throws InterruptedException, ExecutionException
    {
        mLock.lock();
        try{
            if (!mValueSet){
                if (mInner == null){
                    mCondition.await();
                }
                else{
                    mInner.get();
                    get();
                }
            }
            return mValue;
        }
        finally{
            mLock.unlock();
        }
    }

    /**
     * get  future task
     * @param-long,TimeUnit
     * @return-RESULT
     */
    @Override
    public RESULT get(long a_timeout, TimeUnit a_unit) throws InterruptedException, ExecutionException, TimeoutException{
        mLock.lock();
        try{
            if (mValue == null){
                if (mInner == null){
                    mCondition.await(a_timeout, a_unit);
                }
                else{
                    mInner.get();
                }
            }
            if (mValue == null){
                throw new TimeoutException();
            }
            else{
                return mValue;
            }
        }
        finally{
            mLock.unlock();
        }
    }

    /**
     * setter for future task
     * @param-RESULT
     * @return-boolean
     */
    public boolean set(RESULT a_value){
        /* Should never be called on same thread as get() */
        if (mValueSet){
            return false;
        }
        else{
            mLock.lock();
            try{
                mValueSet = true;
                mValue = a_value;
                mCondition.signalAll();
                return true;
            }
            finally{
                mLock.unlock();
            }
        }
    }

    /**
     * wraper for future task
     * @param-Future<?>
     * @return-void
     */
    public void wrap(Future<?> a_f){
        mInner = a_f;
    }
}