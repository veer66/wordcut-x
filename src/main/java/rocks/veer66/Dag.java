package rocks.veer66;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Dag {
	public List<List<DagEdge>> dag;

	public static EdgeBuildingContext createDefaultContext(char[] text) {
		var context = new EdgeBuildingContext();
		context.text = text;
		context.leftBoundary = 0;
		context.bestEdge = null;
		return context;
	}

	public static List<EdgeBuilder> createDefaultBuilders(CharPrefixTree dix) {
		return Arrays.asList(new DictEdgeBuilder(dix), PatEdgeBuilder.createLatinEdgeBuilder(),
				PatEdgeBuilder.createPuncEdgeBuilder(), new UnkEdgeBuilder());
	}

	public static List<DagEdgeBuilder> createDefaultDagBuilders(CharPrefixTree dix) {
		return Arrays.asList(new DictEdgeBuilder(dix), PatEdgeBuilder.createLatinEdgeBuilder(),
				PatEdgeBuilder.createPuncEdgeBuilder());
	}

	public static List<Edge> createDefaultPath() {
		var path = new ArrayList<Edge>();
		path.add(new Edge(0, EdgeType.INIT, 0, 0));
		return path;
	}

	public static List<Edge> buildPath(CharPrefixTree dix, char[] text) {
		var builders = createDefaultBuilders(dix);
		var path = createDefaultPath();
		var context = createDefaultContext(text);

		for (var i = 0; i < text.length; i++) {
			context.ch = text[i];
			context.i = i;
			context.bestEdge = null;
			for (var builder : builders) {
				var edge = builder.build(context, path);
				if (Edge.isBetterThan(edge, context.bestEdge)) {
					context.bestEdge = edge;
				}
			}

			if (context.bestEdge == null)
				throw new RuntimeException("Best edge can't be null");

			path.add(context.bestEdge);

			if (!context.bestEdge.isUnk())
				context.leftBoundary = i + 1;
		}
		return path;
	}

	Dag(int size) {
		dag = new ArrayList<>(size + 1);
		IntStream.range(0, size + 1).forEach(i -> {
			dag.add(new ArrayList<>());
		});
		dag.get(0).add(new DagEdge(0, 0, EdgeType.INIT));
	}

	public void build(CharPrefixTree dix, char[] text) {
		var builders = createDefaultDagBuilders(dix);
		var context = createDefaultContext(text);

		for (var i = 0; i < text.length; i++) {
			context.ch = text[i];
			context.i = i;
			context.bestEdge = null;

			for (var builder : builders) {
				for (var edge : builder.buildDagEdges(context)) {
					dag.get(edge.e).add(edge);
				}
			}
		}
		var leftBoundary = 0;
		for (var i = 1; i <= text.length; i++) {
			if (dag.get(i).size() == 0) {
				dag.get(i).add(new DagEdge(leftBoundary, i, EdgeType.UNK));
			} else {
				leftBoundary = i;
			}
		}
	}

	public static Dag buildDag(CharPrefixTree dix, char[] text) {
		var dag = new Dag(text.length);
		dag.build(dix, text);
		return dag;
	}

}
