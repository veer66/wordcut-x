package rocks.veer66;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class WordcutTest {

	CharPrefixTree minDix;
	Wordcut minWordcut;

	@Before
	public void setUp() throws Exception {
		minDix = Dix.createDix(Arrays.asList("กา", "กาก"));
		minWordcut = new Wordcut(minDix);
	}

	@Test
	public void segment() {
		assertEquals(Arrays.asList("กาก", "กา"), minWordcut.segmentToStrList("กากกา"));
	}

	@Test
	public void textRangeEquality() {
		assertEquals(new TextRange(1, 2), new TextRange(1, 2));
	}

	@Test
	public void segmentRanges() {
		assertEquals(Arrays.asList(new TextRange(0, 3), new TextRange(3, 5)), minWordcut.segment("กากกา"));
	}
	
	@Test
	public void putDelimiter() {
		assertEquals("กาก|กา", minWordcut.putDelimiter("กากกา", "|"));
	}
	
	@Test
	public void wordlistFromFile() throws IOException {
		var url = WordcutTest.class.getResource("/two_thai_words.txt");
		var wordcut = Wordcut.fromDixUrl(url);
		assertEquals("กาก|กา", wordcut.putDelimiter("กากกา", "|"));
	}
	
	@Test
	public void dagEdgeEquality() {
		assertEquals(new DagEdge(0,1,EdgeType.DICT), new DagEdge(0,1,EdgeType.DICT));
	}
	
	@Test
	public void basicDag() {
		assertEquals(
			Arrays.asList(
				Arrays.asList(new DagEdge(0, 0, EdgeType.INIT)),     // 0
				Arrays.asList(new DagEdge(0, 1, EdgeType.UNK)),      // 1
				Arrays.asList(new DagEdge(0, 2, EdgeType.DICT)),     // 2
				Arrays.asList(new DagEdge(0, 3, EdgeType.DICT)),     // 3
				Arrays.asList(new DagEdge(3, 4, EdgeType.UNK)),      // 4
				Arrays.asList(new DagEdge(3, 5, EdgeType.DICT))),    // 5
			minWordcut.buildDag("กากกา"));		
	}
	
	@Test
	public void dagWithPunc() {
		assertEquals(Arrays.asList(
				Arrays.asList(new DagEdge(0, 0, EdgeType.INIT)),     // 0
				Arrays.asList(new DagEdge(0, 1, EdgeType.UNK)),      // 1
				Arrays.asList(new DagEdge(0, 2, EdgeType.DICT)),     // 2
				Arrays.asList(new DagEdge(2, 3, EdgeType.PUNC)),     // 3
				Arrays.asList(new DagEdge(3, 4, EdgeType.UNK)),      // 4
				Arrays.asList(new DagEdge(3, 5, EdgeType.DICT))),    // 5
			minWordcut.buildDag("กา กา"));	
	}
		
	@Test
	public void dagWithLatin() {
		assertEquals(Arrays.asList(
				Arrays.asList(new DagEdge(0, 0, EdgeType.INIT)),     // 0
				Arrays.asList(new DagEdge(0, 1, EdgeType.UNK)),      // 1
				Arrays.asList(new DagEdge(0, 2, EdgeType.DICT)),     // 2
				Arrays.asList(new DagEdge(2, 3, EdgeType.UNK)),     // 3
				Arrays.asList(new DagEdge(2, 4, EdgeType.LATIN))),     // 4
			minWordcut.buildDag("กาAB"));
	}
	
	@Test
	public void dagEmpty() {
		assertEquals(Arrays.asList(
				Arrays.asList(new DagEdge(0, 0, EdgeType.INIT))),     // 0
			minWordcut.buildDag(""));	
	}
}
