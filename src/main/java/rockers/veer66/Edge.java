package rockers.veer66;

public class Edge {
	
	public int p;
	public EdgeType etype;
	public int w;
	public int unk;
	
	Edge(int p, EdgeType etype, int w, int unk) {
		this.p = p;
		this.etype = etype;
		this.w = w;
		this.unk = unk;
	}
	
	public boolean isUnk() {
		return etype == EdgeType.UNK;
	}
	
	public boolean isBetterThan(Edge o) {
		 if (unk < o.unk) return true;
		 if (unk > o.unk) return false;
		 if (w < o.w) return true;
		 if (w > o.w) return false;
		 if (o.isUnk() && !isUnk()) return true;
		 return false;
	}
	
	public static boolean isBetterThan(Edge a, Edge b) {
		if (a == null) return false;
		if (b == null) return true;
		return a.isBetterThan(b);
	}
	
	@Override
	public String toString() {
		return String.format("(EDGE :p %d :etype %s :w %d :unk %d)", p, etype, w, unk);
	}
	
}
