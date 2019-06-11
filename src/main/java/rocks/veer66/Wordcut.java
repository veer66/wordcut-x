package rocks.veer66;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
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
	
	public List<List<DagEdge>> buildDag(String text) {
		var ch_text = text.toCharArray();
		return Dag.buildDag(dix, ch_text).dag;
	}


	public String putDelimiter(String text, String delim) {
		var strList = segmentToStrList(text);		
		return String.join(delim, strList);
	}


	public static Wordcut fromDixUrl(URL url) throws IOException {
		var isr = new InputStreamReader(url.openStream(), "UTF-8");
		var br = new BufferedReader(isr);
		var lines = br.lines().collect(Collectors.toList());
		var dix = Dix.createDix(lines);
		return new Wordcut(dix);
	}
}
