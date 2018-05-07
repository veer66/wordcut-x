package rockers.veer66;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class PatEdgeBuilder implements EdgeBuilder, DagEdgeBuilder {
	public int start;
	public int i;
	public State state;
	public Predicate<Character> isPatChar;
	public EdgeType etype;

	PatEdgeBuilder(Predicate<Character> isPatChar, EdgeType etype) {
		this.start = 0;
		this.i = 0;
		this.state = State.INIT;
		this.isPatChar = isPatChar;
		this.etype = etype;
	}

	public State toTextState(Character nch) {
		if (nch != null) {
			return isPatChar.test(nch) ? State.NON_PAT_FINAL : State.NON_PAT;
		} else {
			return State.NON_PAT_FINAL;
		}
	}

	public State toSpaceState(Character nch) {
		if (nch != null) {
			return isPatChar.test(nch) ? State.PAT : State.PAT_FINAL;
		} else {
			return State.PAT_FINAL;
		}
	}

	public State toAnotherState(Character ch, Character nch) {
		return isPatChar.test(ch) ? toSpaceState(nch) : toTextState(nch);
	}

	public boolean isPatFinal() {
		return state == State.PAT_FINAL;
	}

	public void transit(Character ch, Character nch) {
		switch (state) {
		case INIT:
			start = i;
			state = toAnotherState(ch, nch);
			break;
		case NON_PAT:
			state = toAnotherState(ch, nch);
			break;
		case NON_PAT_FINAL:
			start = i;
			state = toSpaceState(nch);
			break;
		case PAT_FINAL:
			start = i;
			state = toTextState(nch);
			break;
		case PAT:
			state = toAnotherState(ch, nch);
		}
		i++;
	}

	@Override
	public Edge build(EdgeBuildingContext context, List<Edge> path) {
		var nextChar = context.i == context.text.length ? null : context.text[context.i + 1];
		transit(context.ch, nextChar);
		if (!isPatFinal())
			return null;
		var source = path.get(start);
		return new Edge(start, etype, source.w + 1, source.unk);
	}

	public static PatEdgeBuilder createLatinEdgeBuilder() {
		return new PatEdgeBuilder(ch -> (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'), EdgeType.LATIN);
	}

	public static Set<Character> PUNC_SET;

	static {
		PUNC_SET = new HashSet<>();
		Arrays.asList(' ', '\n', '\t', '\r', '"', '(', ')', '"', '”').stream().forEach(ch -> PUNC_SET.add(ch));
	}
	
	public static PatEdgeBuilder createPuncEdgeBuilder() {
		return new PatEdgeBuilder(ch -> PUNC_SET.contains(ch), EdgeType.PUNC);
	}

	@Override
	public List<DagEdge> buildDagEdges(EdgeBuildingContext context) {
		var nch = context.i + 1 == context.text.length ? null : context.text[context.i + 1];
		transit(context.ch, nch);
		if (isPatFinal()) 
			return Arrays.asList(new DagEdge(start, context.i + 1, etype));
		return Arrays.asList();
	}
}