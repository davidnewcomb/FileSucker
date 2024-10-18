package uk.co.bigsoft.filesucker.transfer.download;

public class SuckerItem {
	private String url;
	private String local;

	public SuckerItem(String url, String local) {
		this.url = url;
		this.local = local;
	}

	public String getUrl() {
		return url;
	}

	public String getLocal() {
		return local;
	}
}
