package rockers.veer66;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class SimpleExample {
	
	public static void main(String[] args) throws IOException {
		URL dixUrl = URI.create("http://file.veer66.rocks/dix/tdict-std.txt").toURL();
		var wc = Wordcut.fromDixUrl(dixUrl);
		System.out.println(wc.putDelimiter("กากาก", "|"));
		// Result: กา|กาก
	}
}
