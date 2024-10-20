package uk.co.bigsoft.filesucker.zjunk.transfer.download;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import uk.co.bigsoft.filesucker.zjunk.sucker_types.CopySuckerType;
import uk.co.bigsoft.filesucker.zjunk.sucker_types.FixedSuckerType;
import uk.co.bigsoft.filesucker.zjunk.sucker_types.LabelSuckerType;
import uk.co.bigsoft.filesucker.zjunk.sucker_types.SuckerType;

public class CIterator implements Iterator<UrlSequenceIteration> {
	private int[] iter;
	private HashMap<Integer, String> ivariables;
	private boolean hasnext;
	private List<SuckerType> urlChunks;

	public CIterator(List<SuckerType> uc) {
		urlChunks = uc;
	}

	public Iterator<UrlSequenceIteration> iterator() {
		iter = new int[urlChunks.size()];
		hasnext = true;

		for (int i = 0; i < iter.length; ++i) {
			iter[i] = 0;
		}

		ivariables = new HashMap<Integer, String>();

		return this;
	}

	public boolean hasNext() {
		return hasnext; // (current < totalFilesToGet);
	}

	public UrlSequenceIteration next() {

		// rotate iterators
		String remoteFile = convert();
		UrlSequenceIteration usi = new UrlSequenceIteration(remoteFile, ivariables);

		hasnext = rotate(iter.length - 1);
		return usi;
	}

	public void remove() {
		/* empty */
	}

	private boolean rotate(int idx) {
		if (idx == 0) {
			return false;
		}

		SuckerType st = urlChunks.get(idx);

		if (st instanceof LabelSuckerType == true || st instanceof CopySuckerType == true
				|| st instanceof FixedSuckerType == true) {
			return rotate(--idx);
		}

		if (st.numberOfIterations() > iter[idx]) {
			iter[idx] += 1;
			return true;
		}

		iter[idx] = 0;

		return rotate(--idx);
	}

	public String convert() {
		StringBuffer sb = new StringBuffer();
		int k = 0;

		for (Iterator<SuckerType> i = urlChunks.iterator(); i.hasNext(); k++) {
			SuckerType st = i.next();

			if (st instanceof CopySuckerType == true) {
				continue;
			}

			if (st.getSaveBuffer() != 0) {
				ivariables.put(st.getSaveBuffer(), st.indexOf(iter[k]));
			}
		}

		String s;
		k = 0;
		for (Iterator<SuckerType> i = urlChunks.iterator(); i.hasNext(); k++) {
			SuckerType st = i.next();

			if (st instanceof FixedSuckerType) {
				continue;
			}

			if (st instanceof CopySuckerType) {
				s = ivariables.get(st.getSaveBuffer());
			} else {
				s = st.indexOf(iter[k]);
			}

			sb.append(s);
		}
		return sb.toString();
	}
}
