package rockers.veer66;

import java.util.ArrayList;
import java.util.List;

public class DictEdgeBuilder implements EdgeBuilder, DagEdgeBuilder {

	CharPrefixTree dix;
	List<Pointer> pointers;
	int pointers_size = 0;
	final static int MAX_SIZE = 0xFF;
	
	DictEdgeBuilder(CharPrefixTree dix) {
		this.dix = dix;
		pointers = new ArrayList<>(MAX_SIZE);
	}
	
	public void addPointer(EdgeBuildingContext context) {
		pointers.set(pointers_size, new Pointer(0,context.i, 0,false));
		pointers_size++;
	}
	
	public void updatePointers(EdgeBuildingContext context) {
		var j = 0;
		for (var i = 0; i < pointers_size; i++) {
			if (pointers.get(i).update(dix, context.ch)) {
				if (j < i)					
					pointers.set(j, pointers.get(i));
				j++;
			}			
		}
		pointers_size = j;
	}
	
	public Edge genEdge(List<Edge> path) {
		Edge bestEdge = null;
		for (var i = 0; i < pointers_size; i++) {
			var pointer = pointers.get(i);
			if (pointer.isFinal) {
				var edge = pointer.genEdge(path);
				if (bestEdge == null) {
					bestEdge = edge;
				} else if (edge.isBetterThan(bestEdge)) {
					bestEdge = edge;
				}
					
				
			}
		}
		return bestEdge;
	}
	
	@Override
	public Edge build(EdgeBuildingContext context, List<Edge> path) {
		addPointer(context);
		updatePointers(context);
		return genEdge(path);	
	}

	@Override
	public List<DagEdge> buildDagEdges(EdgeBuildingContext context) {
		addPointer(context);
		updatePointers(context);
		var edges = new ArrayList<DagEdge>();
		for (var i = 0; i < pointers_size; i++) {
			var p = pointers.get(i);
			if (p.isFinal) {
				edges.add(new DagEdge(p.s, context.i + 1, EdgeType.DICT));
			}
		}
		return edges;
	}

}
