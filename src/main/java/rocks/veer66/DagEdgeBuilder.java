package rocks.veer66;

import java.util.List;

public interface DagEdgeBuilder {
	List<DagEdge> buildDagEdges(EdgeBuildingContext context);
}
