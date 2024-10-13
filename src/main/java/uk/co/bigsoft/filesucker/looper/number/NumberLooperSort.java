package uk.co.bigsoft.filesucker.looper.number;

import java.util.Comparator;

import uk.co.bigsoft.filesucker.HistoryElement;

public class NumberLooperSort implements Comparator<HistoryElement> {

	public int compare(HistoryElement s1, HistoryElement s2) {
		String[] s1a = s1.toString().split(",");
		String[] s2a = s2.toString().split(",");
		int n1;
		int n2;
		int d = 0;

		for (int i = 0; i < 3; i++) {
			try {
				n1 = Integer.parseInt(s1a[i]);
			} catch (NumberFormatException e) {
				return -1;
			}
			try {
				n2 = Integer.parseInt(s2a[i]);
			} catch (NumberFormatException e) {
				return 1;
			}

			d = n1 - n2;
			if (d != 0)
				return d;
		}
		return d;
	}

}
