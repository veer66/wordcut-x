package rockers.veer66;

import java.util.List;
import java.util.stream.Collectors;

import rockers.veer66.StrItem;
import rockers.veer66.CharPrefixTree;;

public class Dix {
	public static CharPrefixTree createDix(List<String> sortedWordList) {
		var items = sortedWordList.stream()
				.map(w -> new StrItem(w,0))
				.collect(Collectors.toList());
		return new CharPrefixTree(items);
	}
}