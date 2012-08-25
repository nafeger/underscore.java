package com.github.nafeger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * This is an attempt to model the underscore library in Java in as javascripty a way 
 * as I can come up with.
 * 
 * Deviations:
 * 	* Obviously java doesn't have function pointers, so I had to make my own, they are named
 *    in a non-java way, so they will take up as little space as possible.  
 *  * Specific method deviations
 *    * include forwards to any
 *    * invoke forwards to each but returns the iterable.
 *    
 * open questions:
 *  * Should I use reflection to make this tool more generic?
 */
public class _ {
	public static <T> void each(Iterable<T> iterable, _f<T> f) {
		for (T t: iterable) {
			f.call(t);
		}
	}
	
	/**
	 * Alias to {@link _#each(Iterable, _f)}
	 * @param iterable
	 * @param f
	 */
	public static <T> void forEach(Iterable<T> iterable, _f<T> f) {
		each(iterable, f);
	}
	
	
	public static <F, T> List<T> map(Iterable<F> iterable, _t<F, T> transform) {
		List<T> rv = new ArrayList<T>();
		for (F f: iterable) {
			rv.add(transform.call(f));
		}
		return rv;
	}
	
	/**
	 * Alias to {@link _#map(Iterable, _t)}
	 * @param iterable
	 * @param transform
	 * @return
	 */
	public static <F, T> List<T> collect(Iterable<F> iterable, _t<F, T> transform) {
		return map(iterable, transform);
	}
	
	public static <MEMO, E> MEMO reduce(Iterable<E> iterable, MEMO memo, _reducer<MEMO, E> reducer) {
		for (E e: iterable) {
			memo = reducer.call(memo, e);
		}
		return memo;
	}
	
	/**
	 * 
	 * Alias to {@link _#reduce(Iterable, Object, _reducer)}
	 * @param iterable
	 * @param memo
	 * @param reducer
	 * @return
	 */
	public static <MEMO, E> MEMO inject(Iterable<E> iterable, MEMO memo, _reducer<MEMO, E> reducer) {
		return reduce(iterable, memo, reducer);
	}
	/**
	 * Alias to {@link _#reduce(Iterable, Object, _reducer)}
	 * @param iterable
	 * @param memo
	 * @param reducer
	 * @return
	 */
	public static <MEMO, E> MEMO foldl(Iterable<E> iterable, MEMO memo, _reducer<MEMO, E> reducer) {
		return reduce(iterable, memo, reducer);
	}
	
	public static <MEMO, E> MEMO reduceRight(Iterable<E> iterable, MEMO memo, _reducer<MEMO, E> reducer) {
		Stack<E> stack = new Stack<E>(); // lame
		for (E e: iterable) { 
			stack.push(e);
		}
		while (!stack.isEmpty()) {
			memo = reducer.call(memo, stack.pop());
		}
		return memo;
	}

	public static <E> E find(Iterable<E> iterable, final _m<E> _matcher) {
		try {
			_.each(iterable, new _f<E>() {
				public void call(E e) {
					if (_matcher.call(e)) {
						throw new FoundException(e);
					}
				}
			});
			return null;
		} catch (FoundException e) {
			return (E)e.getE(); // probably not the fastest route
		}
	}
	
	public static class FoundException extends RuntimeException {
		private Object e;
		public FoundException(Object e) {
			super();
			this.e = e;
		}
		public Object getE() {
			return e;
		}
	}

	public static <E> List<E> filter(Iterable<E> iterable, final _m<E> _matcher) {
		final List<E> rv = new ArrayList<E>();
		_.each(iterable, new _f<E>() {

			public void call(E e) {
				if (_matcher.call(e)) {
					rv.add(e);
				}
			}
		});
		return rv;
	}
	
	/**
	 * Alias to {@link _#filter(Iterable, _m)}
	 * @param iterable
	 * @param _matcher
	 * @return
	 */
	public static <E> List<E> select(Iterable<E> iterable, final _m<E> _matcher) {
		return filter(iterable, _matcher);
	}

	public static <E> List<E> reject(Iterable<E> iterable, final _m<E> _matcher) {
		final List<E> rv = new ArrayList<E>();
		_.each(iterable, new _f<E>() {

			public void call(E e) {
				if (!_matcher.call(e)) {
					rv.add(e);
				}
			}
		});
		return rv;
	}
	

	public static <E> boolean all(Iterable<E> iterable, _m<E> matcher) {
		for(E e: iterable) {
			if (!matcher.call(e)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * alias to {@link _#all(Iterable, _m)}
	 * @param iterable
	 * @param matcher
	 * @return
	 */
	public static <E> boolean every(Iterable<E> iterable, _m<E> matcher) {
		return all(iterable, matcher);
	}
	
	public static <E> boolean any(Iterable<E> iterable, _m<E> matcher) {
		for(E e: iterable) {
			if (matcher.call(e)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Alias to {@link _#some(Iterable, _m)}
	 * @param iterable
	 * @param matcher
	 * @return
	 */
	public static <E> boolean some(Iterable<E> iterable, _m<E> matcher) {
		return any(iterable, matcher);
	}
	
	/**
	 * Alias to {@link _#any(Iterable, _m)}
	 * This doesn't seem to be the original intent.
	 * @param iterable
	 * @param matcher
	 * @return
	 */
	public static <E> boolean include(Iterable<E> iterable, _m<E> matcher) {
		return any(iterable, matcher);
	}
	
	/**
	 * Alias to {@link _#include(Iterable, _m)}
	 * @param iterable
	 * @param matcher
	 * @return
	 */
	public static <E> boolean contains(Iterable<E> iterable, _m<E> matcher) {
		return include(iterable, matcher);
	}
	
	/**
	 * This is not the intention, but currently this is an alias to {@link _#each(Iterable, _f)}
	 * @param iterable
	 * @param func
	 * @return
	 */
	public static <E> Iterable<E> invoke(Iterable<E> iterable, _f<E> func) {
		each(iterable, func);
		return iterable;
	}
	
	public static <KEY, VALUE, M extends Map<KEY,VALUE>> List<VALUE> pluck(Iterable<M> map, KEY key) {
		List<VALUE> rv = new ArrayList<VALUE>();
		for (Map<KEY, VALUE> m: map) {
			rv.add(m.get(key));
		}
		return rv;
	}
	
	public static <KEY, VALUE> List<VALUE> pluck(Map<KEY, VALUE> map, KEY key) {
		return pluck(Collections.singletonList(map), key);
	}
	
	
	//
	// Utilities
	//
	
	/**
	 * returns an {@link _m} that always returns true
	 * @param clazz the class of the type you want to transform.
	 * This is a deviation from the js.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E> _m<E> identityMatcher(Class<E> clazz) {
		return (_m<E>) identity;
	}
	
	private static _m<Object> identity = new _m<Object>() {
		public boolean call(Object e) {
			return true;
		}
	};
	
	/**
	 * returns an {@link _t} that always returns the passed in element.
	 * @param clazz the class of the type you want to transform.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E> _t<E,E> identityTransformer(Class<E> clazz) {
		return (_t<E,E>) identityTransformer;
	}
	
	private static _t<Object,Object> identityTransformer = new _t<Object,Object>() {
		public Object call(Object f) {
			return f;
		}
	};

}
