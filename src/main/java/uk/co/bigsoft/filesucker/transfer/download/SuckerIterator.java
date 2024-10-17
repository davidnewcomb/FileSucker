package uk.co.bigsoft.filesucker.transfer.download;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import uk.co.bigsoft.filesucker.transfer.suckertype.SuckerType;

public class SuckerIterator implements Iterator<String> {

	private List<SuckerType> suckers;

	private Iterator<int[]> iter;

	public SuckerIterator(List<SuckerType> suckersList, ArrayList<int[]> things) {
		suckers = suckersList;
		iter = things.iterator();
	}

	@Override
	public boolean hasNext() {
		return iter.hasNext();
	}

	@Override
	public String next() {
		HashMap<Integer, String> map = new HashMap<>();

		int[] current = iter.next();

		for (int i = 0; i < suckers.size(); ++i) {
			SuckerType st = suckers.get(i);
			String s = st.getList().get(current[i]);
			if (s != null) {
				map.put(Integer.valueOf(st.getId()), s);
			}
		}

		StringBuilder s = new StringBuilder();
		for (SuckerType st : suckers) {
			s.append(map.get(Integer.valueOf(st.getId())));
		}
		return s.toString();
	}

}
