package com.github.nafeger;


/**
 * Transformer: Take and element FROM and turn it into a TO.
 */
public interface _t<FROM, TO> {
	TO call(FROM f);
}
