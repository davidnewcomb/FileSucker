package uk.co.bigsoft.filesucker.transfer.suckertype;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class SuckerType {

	private int id;
	protected ArrayList<String> things = new ArrayList<String>();

	SuckerType(int looperId) {
		id = looperId;
	}

	public int getId() {
		return id;
	}

	public Iterator<String> getIterator() {
		return things.iterator();
	}

	public String get(int idx) {
		return things.get(idx);
	}

	public List<String> getList() {
		return things;
	}

	public abstract String toStringBraces();

	public String toString() {
		return toStringBraces();
	}
}
