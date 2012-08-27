package com.github.nafeger.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.nafeger._;
import com.github.nafeger._f;
import com.github.nafeger._m;
import com.github.nafeger._r;
import com.github.nafeger._t;

/**
 * Class constructed with an Iterable<E> keeping member state and running methods
 * in {@link _}.  This tool allows you to run multiple methods in order.  Keep in 
 * mind though that due to java's Generics the actual {@link Chain} reference
 * returned at the end of any given method may not be the same.  
 */
public class Chain<E> {
	private List<E> list;
	private boolean singleValue = false;
	public Chain(Iterable<E> iterable) {
		this(iterable, false);
	}
	
	private Chain(Iterable<E> iterable, boolean singleValue) {
		if (iterable instanceof Collection) {
			this.list = new ArrayList<E>((Collection)iterable);
		} else {
			this.list = new ArrayList<E>();
			for (E e: iterable) {
				this.list.add(e);
			}
		}
		this.singleValue = singleValue;
	}

	/**
	 * End the chain and return the member list
	 */
	public List<E> value() {
		return this.list;
	}
	public E firstValue() {
		return this.list.isEmpty() ? null : this.list.get(0);
	}

	public <T> Chain<T> map(_t<E, T> transform) {
		return new Chain<T>(_.map(this.list, transform));
	}
	public Chain<E> each(_f<E> f) {
		_.each(this.list, f);
		return this;
	}
	public Chain<E> forEach(_f<E> f) {
		return this.each(f);
	}

	public <T> Chain<T> collect(_t<E, T> transform) {
		return this.map(transform);
	}
	/*
	public <MEMO, E> MEMO reduce(MEMO memo, _r<MEMO, E> reducer) {
		_.reduce(this.list, memo, reducer);
	}
	public <MEMO, E> MEMO inject(MEMO memo, _r<MEMO, E> reducer) {
		reduce();
	}
	public <MEMO, E> MEMO foldl(MEMO memo, _r<MEMO, E> reducer) {
		reduce();
	}

	public <MEMO, E> MEMO reduceRight(MEMO memo, _r<MEMO, E> reducer) {
	}
	*/
	// TODO: find?

	public Chain<E> filter(final _m<E> matcher) {
		this.list = _.filter(this.list, matcher);
		return this;
	}

	public Chain<E> select(final _m<E> matcher) {
		return this.filter(matcher);
	}
	
	public Chain<E> reject(final _m<E> matcher) {
		this.list = _.reject(this.list, matcher);
		return this;
	}

	public <F> Chain<F> flatten(Class<F> clazz) {
		return new Chain<F>(_.flatten(this.list, clazz));
	}
}
