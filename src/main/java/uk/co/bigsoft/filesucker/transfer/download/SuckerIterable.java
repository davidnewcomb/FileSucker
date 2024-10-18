package uk.co.bigsoft.filesucker.transfer.download;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.co.bigsoft.filesucker.transfer.suckertype.SuckerType;
import uk.co.bigsoft.filesucker.transfer.suckertype.SuckerTypeFactory;

public class SuckerIterable implements Iterable<SuckerItem> {
	private SuckerTypeFactory factory = new SuckerTypeFactory();
	private ArrayList<SuckerType> suckers = new ArrayList<>();
	private int[] currentIndex;
	private int[] maxIndex;
	private ArrayList<int[]> things = new ArrayList<>();

	private String baseDir;
	private String prefix;
	private String suffix;
	private boolean b4extn;
	
	public SuckerIterable(String url, String baseDir, String prefix, String suffix, boolean b4extn) {

		this.baseDir = baseDir;
		this.prefix = prefix;
		this.suffix = suffix;
		this.b4extn = b4extn;
		
		Pattern p = Pattern.compile("\\{[^}]*\\}");
		Matcher matcher = p.matcher(url);

		int pos = 0;

		while (matcher.find()) {

			int start = matcher.start();
			int end = matcher.end();

			if (pos != start) {
				String chunk = url.substring(pos, start);
				suckers.add(factory.create(chunk));
			}
			suckers.add(factory.create(matcher.group()));
			pos = end;
		}

		if (pos < url.length() - 1) {
			String chunk = url.substring(pos, url.length());
			suckers.add(factory.create(chunk));
		}

		currentIndex = suckers.stream().mapToInt(s -> 0).toArray();
		maxIndex = suckers.stream().mapToInt(s -> s.getList().size()).toArray();

		things.add(currentIndex);

		while (true) {
			currentIndex = nextCurrent(0, currentIndex, maxIndex);
			if (currentIndex.length == 0) {
				break;
			}
			things.add(currentIndex);
		}

	}

	private int[] nextCurrent(int idx, int[] cur, int[] max) {

		int[] copy = cur.clone();
		if (copy[idx] < max[idx] - 1) {
			++copy[idx];
			return copy;
		}

		boolean finished = true;
		for (int i = 0; i < copy.length; ++i) {
			if (copy[i] != max[i] - 1) {
				finished = false;
			}
		}
		if (finished) {
			return new int[0];
		}

		copy[idx] = 0;
		return nextCurrent(++idx, copy, max);
	}

	public int size() {
		return things.size();
	}

	@Override
	public Iterator<SuckerItem> iterator() {
		return new SuckerIterator(suckers, things, baseDir, prefix, suffix, b4extn);
	}
}
