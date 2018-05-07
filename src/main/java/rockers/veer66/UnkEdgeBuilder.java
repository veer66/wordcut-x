package rockers.veer66;

import java.util.List;

public class UnkEdgeBuilder implements EdgeBuilder {
	@Override
	public Edge build(EdgeBuildingContext context, List<Edge> path) {
		if (context.bestEdge != null)
			return null;
		var source = path.get(context.leftBoundary);
		return new Edge(context.leftBoundary, EdgeType.UNK, source.w + 1, source.unk + 1);
	}
}
