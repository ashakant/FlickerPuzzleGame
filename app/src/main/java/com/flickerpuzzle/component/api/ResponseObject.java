package com.flickerpuzzle.component.api;


/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public class ResponseObject<T> {
	private T t ;
	public ResponseObject(T a_object){
		this.t=a_object;
	}
	public T get(){
        return this.t;
    }
	public void set(T t1){
        this.t=t1;
    }
}
