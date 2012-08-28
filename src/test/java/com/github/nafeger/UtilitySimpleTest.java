package com.github.nafeger;

import static org.junit.Assert.*;

import org.junit.Test;


public class UtilitySimpleTest {

	@Test
	public void testIdentity() {
		assertEquals(12, (int)_.identityTransformer(Integer.class).call(12, 1, null));
	}

}
