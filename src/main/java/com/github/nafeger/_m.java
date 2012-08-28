package com.github.nafeger;


/**
 * Matcher: this takes an element and returns true
 * if it matches.
 * Effectively transforming E int a boolean.
 */
public interface _m<E> extends _t<E, Boolean> {
	Boolean call(E e, int index, Iterable<E> list);
}
