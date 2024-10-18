package uk.co.bigsoft.filesucker.transfer.download;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.co.bigsoft.filesucker.task.TaskConfig;
import uk.co.bigsoft.filesucker.transfer.suckertype.SuckerType;

public class SuckerIterator implements Iterator<SuckerItem> {

	private static final Pattern looperPattern = Pattern.compile("\\{[0-9]+}");
	private static final int IDX_BASE = 0;
	private static final int IDX_DOT = 1;
	private static final int IDX_EXTN = 2;

	private List<SuckerType> suckers;
	private Iterator<int[]> iter;
	private TaskConfig taskConfig;

	public SuckerIterator(List<SuckerType> suckersList, ArrayList<int[]> things, TaskConfig taskConfig) {
		this.taskConfig = taskConfig;
		suckers = suckersList;
		iter = things.iterator();
	}

	@Override
	public boolean hasNext() {
		return iter.hasNext();
	}

	@Override
	public SuckerItem next() {
		HashMap<Integer, String> map = new HashMap<>();

		int[] current = iter.next();

		for (int i = 0; i < suckers.size(); ++i) {
			SuckerType st = suckers.get(i);
			String s = st.getList().get(current[i]);
			if (s != null) {
				map.put(Integer.valueOf(st.getId()), s);
			}
		}

		StringBuilder url = new StringBuilder();
		for (SuckerType st : suckers) {
			url.append(map.get(Integer.valueOf(st.getId())));
		}

		String[] filename = findFilename(url.toString());

		StringBuilder local = new StringBuilder();
		local.append(replaceVars(map, taskConfig.getDirectory()));
		local.append(replaceVars(map, taskConfig.getPrefix()));
		local.append(filename[IDX_BASE]);
		if (!taskConfig.isSuffixEnd()) {
			local.append(replaceVars(map, taskConfig.getSuffix()));
			local.append(filename[IDX_DOT]);
			local.append(filename[IDX_EXTN]);
		} else {
			local.append(filename[IDX_DOT]);
			local.append(filename[IDX_EXTN]);
			local.append(replaceVars(map, taskConfig.getSuffix()));
		}

		SuckerItem si = new SuckerItem(url.toString(), local.toString());

		return si;
	}

	private String[] findFilename(String url) {
		String[] bits = url.split("/");
		String fn = bits[bits.length - 1];
		int dot = fn.lastIndexOf(".");
		if (dot == -1) {
			return new String[] { fn, "", "" };
		} else {
			String base = fn.substring(0, dot);
			String extn = fn.substring(dot + 1);
			return new String[] { base, ".", extn };
		}
	}

	private String replaceVars(HashMap<Integer, String> map, String text) {
		Matcher m = looperPattern.matcher(text);
		StringBuilder sb = new StringBuilder();

		int pos = 0;

		while (m.find()) {
			int start = m.start();
			int end = m.end();

			if (pos != start) {
				sb.append(text.substring(pos, start));
			}
			String num = m.group().substring(1, m.group().length() - 1);
			sb.append(map.get(Integer.valueOf(num)));
			pos = end;
		}
		if (pos < text.length()) {
			sb.append(text.substring(pos));
		}
		return sb.toString();

	}

}
