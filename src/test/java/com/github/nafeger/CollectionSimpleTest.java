package com.github.nafeger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


public class CollectionSimpleTest extends AbstractMockitoTest {

	@Test
	public void testSimpleForeach() {
		Runnable one = mock(Runnable.class);
		Runnable two = mock(Runnable.class);
		Runnable three = mock(Runnable.class);
		ArrayList<Runnable> list = Lists.newArrayList(one, two, three);
		_.each(list,new _f<Runnable>() {
			public void call(Runnable f) {
				f.run();
			}
		});
		verify(one).run();
		verify(two).run();
		verify(three).run();
	}
	
	@Test
	public void testSimpleMap() {
		List<Integer> rv = _.map(Lists.newArrayList(1,2,3), new _t<Integer, Integer>() {
			public Integer call(Integer f) {
				return f*2;
			}
		});
		assertEquals(2, (int)rv.get(0));
		assertEquals(4, (int)rv.get(1));
		assertEquals(6, (int)rv.get(2));
	}

	@Test
	public void testSimpleReduce() {
		int rv = _.reduce(Lists.newArrayList(1,2,3), 0 , new _reducer<Integer, Integer>() {
			public Integer call(Integer memo, Integer f) {
				return memo + f;
			}
		});
		assertEquals(6, rv);
		
		rv = _.reduce(Lists.newArrayList(1,2,3), 10 , new _reducer<Integer, Integer>() {
			public Integer call(Integer memo, Integer f) {
				return memo + f;
			}
		});
		assertEquals(16, rv);
	}
	
	@Test
	public void testReduceRight() {
		
		//var list = [[0, 1], [2, 3], [4, 5]];
		//var flat = _.reduceRight(list, function(a, b) { return a.concat(b); }, []);
		//=> [4, 5, 2, 3, 0, 1]
		
		
		List<int[]> list = Lists.newArrayList(new int[]{0, 1}, new int[]{2, 3}, new int[]{4, 5});
		String flat = _.reduceRight(list, "", new _reducer<String, int[]>() {

			public String call(String m, int[] f) {
				for (int e: f) {
					m += e;
				}
				return m;
			}
			
		});
		assertEquals("452301", flat);
		//function(a, b) { return a.concat(b); }, []);
	}
	
	@Test
	public void testFind() {
		
		int rv = _.find(Lists.newArrayList(1,2,3), new _m<Integer>() {
			public boolean call(Integer e) {
				return e % 2 == 0;
			}
		});
		assertEquals(2, rv);
	}
	
	@Test
	public void testFilter() {
		
		List<Integer> rv = _.filter(Lists.newArrayList(1,2,3,4,5,6), new _m<Integer>() {
			public boolean call(Integer e) {
				return e % 2 == 0;
			}
		});
		assertEquals(2, (int)rv.get(0));
		assertEquals(4, (int)rv.get(1));
		assertEquals(6, (int)rv.get(2));
		assertEquals(3, rv.size());
	}
	
	@Test
	public void testReject() {
		
		List<Integer> rv = _.reject(Lists.newArrayList(1,2,3,4,5,6), new _m<Integer>() {
			public boolean call(Integer e) {
				return e % 2 == 0;
			}
		});
		assertEquals(1, (int)rv.get(0));
		assertEquals(3, (int)rv.get(1));
		assertEquals(5, (int)rv.get(2));
		assertEquals(3, rv.size());
	}
	
	@Test
	public void testAll() {
		assertTrue(_.all(Lists.newArrayList(1,2,3,4,5,6), _.identityMatcher(Integer.class)));
	}
	
	@Test
	public void testAny() {
		assertTrue(_.any(Lists.newArrayList(1,2,3,4,5,6), new _m<Integer>() {
			public boolean call(Integer e) {
				return e % 2 == 0;
			}
		}));
		
		assertFalse(_.any(Lists.newArrayList(1,3,5), new _m<Integer>() {
			public boolean call(Integer e) {
				return e % 2 == 0;
			}
		}));
	}
	
	@Test
	public void testPluck() {
		Map<String, Object> one = Maps.newHashMap();
		one.put("name","moe");
		one.put("age",40);
		Map<String, Object> two = Maps.newHashMap();
		two.put("name","larry");
		two.put("age",60);
		Map<String, Object> three = Maps.newHashMap();
		three.put("name","curly");
		three.put("age",50);
		
		List<Object> plucked = _.pluck(Lists.newArrayList(one, two, three), "name");
		assertEquals(3,plucked.size());
		assertEquals("moe", plucked.get(0));
		assertEquals("larry", plucked.get(1));
		assertEquals("curly", plucked.get(2));
	}
}