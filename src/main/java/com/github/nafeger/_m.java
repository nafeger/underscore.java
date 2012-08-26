package com.github.nafeger;


/**
 * Matcher: this takes an element and returns true
 * if it matches.
 */
public interface _m<E> extends _t<E, Boolean> {
	Boolean call(E e);
}
