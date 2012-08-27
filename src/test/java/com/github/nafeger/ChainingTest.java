package com.github.nafeger;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

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
	/*
	@Test
	public void testLyrics() {
		List<Lyric> lyrics = Lists.newArrayList(
		              new Lyric(1, "I'm a lumberjack and I'm okay"),
		              new Lyric(2, "I sleep all night and I work all day"),
		              new Lyric(3, "He's a lumberjack and he's okay"),
		              new Lyric(4, "He sleeps all night and he works all day"));

        _.chain(lyrics)
          .map(new _f<Lyric, String[]>() {
        	  public String[] call(Lyric l) {
        		  return l.getWords().split(" ");
        	  }
        	  //, (line) { return line.words.split(' '); })
          })
          .flatten()
          .reduce(new _r<Integer, String>() {

			public Integer call(Integer m, String e) {
				return null;
			}
        	  
          })
          //.reduce(function(counts, word) {
            //counts[word] = (counts[word] || 0) + 1;
            //return counts;
        //}, {})
        .firstValue();

        => {lumberjack : 2, all : 4, night : 2 ... }
		
	}
	*/

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
