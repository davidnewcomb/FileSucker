package uk.co.bigsoft.filesucker.transfer.download;

import java.util.HashMap;

import uk.co.bigsoft.filesucker.SuckerParams;

public class UrlSequenceIteration {
	private String remotefile = "";
	private String basename = "";
	private String extname = "";
	private HashMap<Integer, String> variables = new HashMap<Integer, String>();

	public UrlSequenceIteration(String r, HashMap<Integer, String> v) {
		remotefile = r;
		variables = v;

		int s = remotefile.lastIndexOf("/");
		int e = remotefile.lastIndexOf(".");

		if (e == -1) {
			basename = remotefile.substring(s + 1);
		} else if (s < e) {
			basename = remotefile.substring(s + 1, e);
			extname = remotefile.substring(e);
		}
	}

	public String getRemoteFile() {
		return remotefile;
	}

	public String getLocalFile(SuckerParams p) {
		StringBuffer file = new StringBuffer();

		file.append(expandVars("Directory", p.getIntoDir()));

		file.append(expandVars("Prefix", p.getPrefix()));
		file.append(basename);
		if (p.isSuffixEnd()) {
			file.append(expandVars("Suffix", p.getSuffix()));
			file.append(extname);
		} else {
			file.append(extname);
			file.append(expandVars("Suffix", p.getSuffix()));
		}
		return file.toString();
	}

	public String expandVars(String what, String toExpand) {
		if (toExpand == null) {
			return "";
		}

		StringBuffer newBuf = new StringBuffer();

		for (int i = 0; i < toExpand.length(); i++) {
			if (toExpand.charAt(i) != '{') {
				newBuf.append(toExpand.charAt(i));
				continue;
			}

			int nextParentiseas = toExpand.indexOf('}', i);
			String xx = toExpand.substring(i + 1, nextParentiseas);
			Integer num = Integer.valueOf(xx);
			i = nextParentiseas;
			Object o = variables.get(num);
			if (o == null) {
				StringBuffer s = new StringBuffer();
				s.append("expandVars: unknown variable {");
				s.append(xx);
				s.append("} in ");
				s.append(what);

				System.out.println(s.toString());
				continue;
			}
			newBuf.append(o.toString());
		}
		return newBuf.toString();
	}

}
