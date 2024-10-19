package uk.co.bigsoft.filesucker.transfer.download;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class HttpRange {
	String unit;
	long from;
	long to;
	long total;
}

public class HttpSupport {
	// content-range=bytes 22-330122/330123
	private static final Pattern contentRangePattern1 = Pattern.compile("([a-z]+) ([0-9]+)-([0-9]+)/([0-9]+)");
//	private static final Pattern contentRangePattern2 = Pattern.compile("([a-z]+) ([0-9]+)-([0-9]+)/\\*");
//	private static final Pattern contentRangePattern3 = Pattern.compile("([a-z]+) \\*/[0-9]+");

	public static int retrieveContentLength(HttpHeaders headers) {
		Map<String, List<String>> h = headers.map();
		Set<String> keys = h.keySet();
		int cl = 0;
		for (String k : keys) {
			if (k.equalsIgnoreCase("content-length")) {
				List<String> x = h.get(k);
				if (x.size() == 0) {
					continue;
				}
				String s = x.get(0);
				cl = Integer.parseInt(s);
			}
		}
		return cl;
	}

	public static String rangeLastModifiedDateString(String filepath) {
		try {
			Path path = Paths.get(filepath);
			FileTime ft = Files.getLastModifiedTime(path);
			Instant instant = ft.toInstant();
			ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, ZoneId.of("GMT"));
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MM yyyy hh:mm:ss");
			String s = formatter.format(zdt);
			return s;
		} catch (IOException e) {
			return null;
		}
	}

	public static HttpRange retrieveContentRange(HttpHeaders headers) {
		Map<String, List<String>> h = headers.map();
		Set<String> keys = h.keySet();
		for (String k : keys) {
			if (k.equalsIgnoreCase("content-range")) {
				List<String> x = h.get(k);
				if (x.size() == 0) {
					continue;
				}
				Matcher m = contentRangePattern1.matcher(x.get(0));
				if (m.matches()) {
					HttpRange r = new HttpRange();
					r.unit = m.group(0);
					r.from = Integer.parseInt(m.group(1));
					r.to = Integer.parseInt(m.group(2));
					r.total = Integer.parseInt(m.group(3));
					return r;
				}
			}
		}
		return null;
	}

}
