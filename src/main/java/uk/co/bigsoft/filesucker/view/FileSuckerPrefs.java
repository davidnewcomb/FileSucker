package uk.co.bigsoft.filesucker.view;

public class FileSuckerPrefs {

	private int boundX;
	private int boundY;
	private int boundWidth;
	private int boundHeight;
	private long totalDownloadedFiles;
	private long totalDownloadedBytes;

	public FileSuckerPrefs() {
		//
	}

	public int getBoundX() {
		return boundX;
	}

	public void setBoundX(int boundX) {
		this.boundX = boundX;
	}

	public int getBoundY() {
		return boundY;
	}

	public void setBoundY(int boundY) {
		this.boundY = boundY;
	}

	public int getBoundWidth() {
		return boundWidth;
	}

	public void setBoundWidth(int boundWidth) {
		this.boundWidth = boundWidth;
	}

	public int getBoundHeight() {
		return boundHeight;
	}

	public void setBoundHeight(int boundHeight) {
		this.boundHeight = boundHeight;
	}

	public long getTotalDownloadedFiles() {
		return totalDownloadedFiles;
	}

	public void setTotalDownloadedFiles(long totalDownloadedFiles) {
		this.totalDownloadedFiles = totalDownloadedFiles;
	}

	public long getTotalDownloadedBytes() {
		return totalDownloadedBytes;
	}

	public void setTotalDownloadedBytes(long totalDownloadedBytes) {
		this.totalDownloadedBytes = totalDownloadedBytes;
	}

}
