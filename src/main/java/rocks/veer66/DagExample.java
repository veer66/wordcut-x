package rocks.veer66;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class DagExample {

	public static void main(String[] args) throws IOException {
		URL dixUrl = URI.create("http://file.veer66.rocks/dix/tdict-std.txt").toURL();
		var wc = Wordcut.fromDixUrl(dixUrl);
		System.out.println(wc.buildDag("กากาก"));
		// RESULT:
		// [[(DAG-EDGE :s 0 :e 0 :etype INIT)], 
		// [(DAG-EDGE :s 0 :e 1 :etype UNK)], 
		// [(DAG-EDGE :s 0 :e 2 :etype DICT)], 
		// [(DAG-EDGE :s 0 :e 3 :etype DICT)], 
		// [(DAG-EDGE :s 2 :e 4 :etype DICT)], 
		// [(DAG-EDGE :s 2 :e 5 :etype DICT)]]

	}

}
