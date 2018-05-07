package rockers.veer66;

import java.util.List;

public class Pointer {
	public int nodeId;
	public int s;
	public int offset;
	public boolean isFinal;
	
	Pointer(int nodeId, int s, int offset, boolean isFinal) {
		this.nodeId = nodeId;
		this.s = s;
		this.offset = offset;
		this.isFinal = isFinal;
	}
	
	public boolean update(CharPrefixTree dix, char ch) {
		var target = dix.seek(new CharNode(nodeId, offset, ch));
		if (target == null) return false;
		nodeId = target.index;
		isFinal = target.isFinal;
		offset++;
		return true;
	}
	
	public Edge genEdge(List<Edge> path) {
		var source = path.get(s);
		return new Edge(s, EdgeType.DICT, source.w + 1, source.unk);
	}
}
