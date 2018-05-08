package rockers.veer66;

import static org.junit.Assert.*;

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
		var toks = minWordcut.segmentToStrList("กากกา");
		assertEquals(Arrays.asList("กาก", "กา"), toks);
	}

}
