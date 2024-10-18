package uk.co.bigsoft.filesucker.transfer.download;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import uk.co.bigsoft.filesucker.zjunk.sucker_types.CopySuckerType;
import uk.co.bigsoft.filesucker.zjunk.sucker_types.LabelSuckerType;
import uk.co.bigsoft.filesucker.zjunk.sucker_types.SuckerType;

public class UrlSequencer {
	private String orginalString;

	private ArrayList<SuckerType> urlChunks = new ArrayList<SuckerType>();

	private HashMap<Integer, SuckerType> lineParms = new HashMap<Integer, SuckerType>();

	private int totalFilesToGet = 1;

	/**
	 * Create a url sequencer
	 * 
	 * @param s url sequence
	 */
	public UrlSequencer(String s) {
		orginalString = s;

		urlChunks = new ArrayList<SuckerType>();
		StringBuffer tok = new StringBuffer();
		SuckerType stype;
		boolean inBrackets = false;

		for (int i = 0; i < orginalString.length(); i++) {
			if (orginalString.charAt(i) == '{' || orginalString.charAt(i) == '}') {
				stype = getSuckerType(inBrackets, tok.toString());
				inBrackets = orginalString.charAt(i) == '{';

				if (stype == null) {
					continue;
				}

				Integer saveB = stype.getSaveBuffer();
				if (!(stype instanceof CopySuckerType)) {
					if (saveB != null && saveB.intValue() != 0) {
						lineParms.put(saveB, stype);
					}
					totalFilesToGet *= stype.numberOfIterations();
				}
				urlChunks.add(stype);
				tok = new StringBuffer();
				continue;
			}

			tok.append(orginalString.charAt(i));
		}

		if (tok.toString().length() > 0) {
			stype = getSuckerType(inBrackets, tok.toString());
			urlChunks.add(stype);
		}
	}

	private SuckerType getSuckerType(boolean inBrackets, String tok) {
		if (inBrackets) {
			return SuckerType.getSuckerType(tok.toString());
		}
		if ("".equals(tok)) {
			return null;
		}
		return new LabelSuckerType(tok);
	}

	public Iterator<UrlSequenceIteration> iterator() {
		CIterator ci = new CIterator(urlChunks);
		return ci.iterator();
	}

	public int size() {
		return totalFilesToGet;
	}
}
