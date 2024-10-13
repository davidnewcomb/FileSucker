package uk.co.bigsoft.filesucker.credits;


public class CreditsModel {

	private long numFiles = 0;
	private long numBytes = 0;
	private long totalNumFiles = 0;
	private long totalNumBytes = 0;

	public CreditsModel() {
		//
	}

	public long getNumFiles() {
		return numFiles;
	}

	public void setNumFiles(long numFiles) {
		this.numFiles = numFiles;
	}

	public long getNumBytes() {
		return numBytes;
	}

	public void setNumBytes(long numBytes) {
		this.numBytes = numBytes;
	}

	public long getTotalNumFiles() {
		return totalNumFiles;
	}

	public void setTotalNumFiles(long totalNumFiles) {
		this.totalNumFiles = totalNumFiles;
	}

	public long getTotalNumBytes() {
		return totalNumBytes;
	}

	public void setTotalNumBytes(long totalNumBytes) {
		this.totalNumBytes = totalNumBytes;
	}

	public void addBytes(long b) {
		numBytes += b;
		totalNumBytes += b;
	}

	public void addFiles(long f) {
		numFiles += f;
		totalNumFiles += f;
	}
}
