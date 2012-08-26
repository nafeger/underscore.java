package com.github.nafeger;


/**
 * Reducer: is called with a memo or seed, and each element
 * in a collection, and returns a new memo, the memo will
 * be passed in on subsequent calls.
 */
public interface _r<MEMO, E> {
	MEMO call(MEMO m, E e);
}
