package com.github.nafeger;

import org.junit.Before;
import org.mockito.MockitoAnnotations;


public abstract class AbstractMockitoTest {
	
	@Before
	public void superSetup() {
		MockitoAnnotations.initMocks(this);
	}

}
