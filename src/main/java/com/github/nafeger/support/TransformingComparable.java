package com.github.nafeger.support;

import java.util.Comparator;

import com.github.nafeger._t;


/**
 * Accepts as input a transformer which outputs a comparable item to sort.
 * TODO: consider adding some caching to this, as the transformer will be called
 * for each comparison, which could get expensive.
 */
public class TransformingComparable<E, C extends Comparable<? super C>> implements Comparator<E> {
	
	private _t<E, C> mapper;

	public TransformingComparable(_t<E, C> mapper) {
		this.mapper = mapper;
	}

	public int compare(E o1, E o2) {
		return this.mapper.call(o1).compareTo(this.mapper.call(o2));
	}

}
