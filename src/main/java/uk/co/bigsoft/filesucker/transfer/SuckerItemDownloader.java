package uk.co.bigsoft.filesucker.transfer;

import uk.co.bigsoft.filesucker.Downloader;
import uk.co.bigsoft.filesucker.transfer.download.si.SuckerItem;

public class SuckerItemDownloader implements Runnable {

	private SuckerItem suckerItem;

	public SuckerItemDownloader(SuckerItem item) {
		suckerItem = item;
	}

	@Override
	public void run() {
		Downloader dl = Downloader.getInstance();
		dl.downloadBinaryFileProgressable(suckerItem.getUrl(), suckerItem.getLocal(), suckerItem.getModel());
	}

}
