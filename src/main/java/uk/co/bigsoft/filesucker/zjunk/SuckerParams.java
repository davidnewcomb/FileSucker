package uk.co.bigsoft.filesucker.zjunk;

import java.util.Hashtable;

public class SuckerParams {
	private String name;
	private String orginalUrl;
	private String intoDir;
	private String prefix;
	private String suffix;
	private Hashtable<String, String> headers;
	private boolean suffixEnd;
	private String orginalAddress;

	public SuckerParams(String name_, String orginalurl_, String intodir_, String prefix_, String suffix_,
			Hashtable<String, String> headers_, boolean suffixend_, String orginalAddress_) {
		setName(name_);
		setOrginalUrl(orginalurl_);
		setIntoDir(intodir_);
		setPrefix(prefix_);
		setSuffix(suffix_);
		setHeaders(headers_);
		setSuffixEnd(suffixend_);
		setAddress(orginalAddress_);
	}

	private void setAddress(String address_) {
		orginalAddress = address_;
	}

	public void setName(String name_) {
		name = name_;
	}

	public String getName() {
		return name;
	}

	public void setOrginalUrl(String orginalUrl_) {
		orginalUrl = orginalUrl_;
	}

	public String getOrginalUrl() {
		return orginalUrl;
	}

	public void setIntoDir(String intoDir_) {
		intoDir = intoDir_;
	}

	public String getIntoDir() {
		return intoDir;
	}

	public void setPrefix(String prefix_) {
		prefix = prefix_;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setSuffix(String suffix_) {
		suffix = suffix_;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffixEnd(boolean suffixEnd_) {
		suffixEnd = suffixEnd_;
	}

	public boolean isSuffixEnd() {
		return suffixEnd;
	}

	private void setHeaders(Hashtable<String, String> headers_) {
		headers = headers_;
	}

	public Hashtable<String, String> getHeaders() {
		return headers;
	}

	public String getOrginalAddress() {
		return orginalAddress;
	}
}
