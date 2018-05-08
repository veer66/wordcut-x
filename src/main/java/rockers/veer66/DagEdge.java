package rockers.veer66;

public class DagEdge {
	public int s;
	public int e;
	public EdgeType etype;
	
	public DagEdge(int s, int e, EdgeType etype) {		
		this.s = s;
		this.e = e;
		this.etype = etype;
	}

	@Override
	public boolean equals(Object o) {
		var _o = (DagEdge)o;
		return _o.s == s && _o.e == e && _o.etype == etype;
	}
}
