package uk.co.bigsoft.filesucker.task;

public class TaskConfig {

	private String url;
	private String directory;
	private String prefix;
	private String suffix;
	private boolean suffixEnd;

	public TaskConfig(String url, String directory, String prefix, String suffix, boolean suffixEnd) {
		this.url = url;
		this.directory = directory;
		this.prefix = prefix;
		this.suffix = suffix;
		this.suffixEnd = suffixEnd;
	}

	public String getUrl() {
		return url;
	}

	public String getDirectory() {
		return directory;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public boolean isSuffixEnd() {
		return suffixEnd;
	}

}
