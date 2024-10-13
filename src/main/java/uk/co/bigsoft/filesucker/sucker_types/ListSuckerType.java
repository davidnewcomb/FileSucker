package uk.co.bigsoft.filesucker.sucker_types;

import java.util.ArrayList;

public class ListSuckerType extends SuckerType // implements Iterator
{
	/*
	 * {n,from,to,npad} {n,buffer,from,to,npad}
	 */
	private ArrayList<String> list;

	ListSuckerType(String f) {
		super(f);

		list = new ArrayList<String>(20);
		String t;
		do {
			t = formatTokens.nextToken();
			list.add(t);
		} while (formatTokens.hasMoreTokens());
	}

	@Override
	public int numberOfIterations() {
		return list.size() - 1;
	}

	@Override
	public String indexOf(int idx) {
		String s = list.get(idx);
		return s;
	}
}
