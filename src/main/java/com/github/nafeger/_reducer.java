package com.github.nafeger;


public interface _reducer<MEMO, E> {
	MEMO call(MEMO m, E e);
}
