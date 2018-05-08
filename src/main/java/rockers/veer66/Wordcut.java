package rockers.veer66;

import java.util.List;
import java.util.stream.Collectors;

public class Wordcut {
	CharPrefixTree dix;
	
	
	public static List<String >pathToStrList(List<Edge> path, char[] text) {
		return TextRange.fromPath(path)
				.stream()
				.map(r -> String.valueOf(text, r.s, r.e - r.s))
				.collect(Collectors.toList());		
	}


	public Wordcut(CharPrefixTree dix) {
		this.dix = dix;
	}
	
	public List<TextRange> segment(String text) {
		var ch_text = text.toCharArray();
		var path = Dag.buildPath(dix, ch_text);
		return TextRange.fromPath(path);
	}
	
	public List<String> segmentToStrList(String text) {
		var ch_text = text.toCharArray();
		var path = Dag.buildPath(dix, ch_text);
		return pathToStrList(path, ch_text);
	}
	
	public Dag buildDag(String text) {
		var ch_text = text.toCharArray();
		return Dag.buildDag(dix, ch_text);
	}
}
