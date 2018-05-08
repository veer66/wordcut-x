package rockers.veer66;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TextRange {
	public int s;
	public int e;
	
	public TextRange(int s, int e) {
		this.s = s;
		this.e = e;
	}

	public static List<TextRange> fromPath(List<Edge> path) {
		if (path.isEmpty())
			return new ArrayList<>();
		List<TextRange> ranges = new ArrayList<>();
		var e = path.size() -1;		
		while (e > 0) {
			var edge = path.get(e);
			var s = edge.p;
			ranges.add(new TextRange(s, e));
			e = s;
		}
		Collections.reverse(ranges);
		return ranges;
	}
	
	@Override
	public boolean equals(Object o) {
		var _o = (TextRange)o;
		return _o.s == s && _o.e == e;
	}
}
