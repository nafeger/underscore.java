package com.github.nafeger;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ChainingTest {

	@Test
	public void testMapChain() {
		List<Integer> rv = _.chain(Lists.newArrayList(1, 2, 3)).map(
				new _t<Integer, Integer>(){

					public Integer call(Integer f) {
						return f*2;
					}}).value();
		assertEquals(3, rv.size());
		assertEquals(2, (int)rv.get(0));
		assertEquals(4, (int)rv.get(1));
		assertEquals(6, (int)rv.get(2));
	}
	
	final static List<Lyric> lyrics = Collections.unmodifiableList(Lists.newArrayList(
		              new Lyric(1, "I'm a lumberjack and I'm okay"),
		              new Lyric(2, "I sleep all night and I work all day"),
		              new Lyric(3, "He's a lumberjack and he's okay"),
		              new Lyric(4, "He sleeps all night and he works all day")));
	@Test
	public void testLyricsToOneLine() {
		
		List<String[]> lines = _.chain(lyrics)
		.map(new _t<Lyric, String[]>() {
			public String[] call(Lyric f) {
				return f.getWords().split(" ");
			}
		}).value();
		assertEquals(4, lines.size());
		assertEquals(6, lines.get(0).length);
		assertEquals(9, lines.get(1).length);
		assertEquals(6, lines.get(2).length);
		assertEquals(9, lines.get(3).length);
		
		List<String> allLines = _.chain(lyrics)
		.map(new _t<Lyric, String[]>() {
			public String[] call(Lyric f) {
				return f.getWords().split(" ");
			}
		}).flatten(String.class).value();
		assertEquals(30, allLines.size());
		assertEquals("I'm", allLines.get(0));
		assertEquals("day", allLines.get(29));
		
		List<String> allLinesThroughList = _.chain(lyrics)
		.map(new _t<Lyric, List<String>>() {
			public List<String> call(Lyric f) {
				return Arrays.asList(f.getWords().split(" "));
			}
		}).flatten(String.class).value();
		assertEquals(30, allLinesThroughList .size());
		assertEquals("I'm", allLinesThroughList .get(0));
		assertEquals("day", allLinesThroughList .get(29));
		//.flatten().value();
	}

	@Test
	public void testLyrics() {
		Map<String, Integer> counter = _.chain(lyrics)
		.map(new _t<Lyric, String[]>() {
			public String[] call(Lyric f) {
				return f.getWords().split(" ");
			}
		})
		.flatten(String.class)
		.reduce(Maps.<String, Integer> newHashMap(), new _r<Map<String, Integer>, String>() {

			public Map<String, Integer> call(Map<String, Integer> m, String e) {
				Integer count = m.get(e);
				if (count == null) {
					count = 0;
				}
				m.put(e, count + 1);
				return m;
			}
		});
		assertEquals(17, counter.size());
		assertEquals(2, (int) counter.get("lumberjack"));
		assertEquals(4, (int) counter.get("all"));

	}

	private static class Lyric {
		private int line;
		private String words;

		public Lyric(int line, String words) {
			this.setLine(line);
			this.setWords(words);
		}

		public int getLine() {
			return line;
		}

		public void setLine(int line) {
			this.line = line;
		}

		public String getWords() {
			return words;
		}

		public void setWords(String words) {
			this.words = words;
		}
	}
}
