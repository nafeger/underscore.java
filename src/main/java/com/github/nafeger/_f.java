package com.github.nafeger;


/**
 * The basic function interface, will be called once 
 * per element of the iterable.
 */
public interface _f<E> {
	void call(E e);
}
