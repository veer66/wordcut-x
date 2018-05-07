package rockers.veer66;

import java.util.List;

public interface EdgeBuilder {
	 Edge build(EdgeBuildingContext context, List<Edge> path);
}
