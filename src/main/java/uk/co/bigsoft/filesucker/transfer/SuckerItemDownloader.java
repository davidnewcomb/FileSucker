package uk.co.bigsoft.filesucker.transfer;

import uk.co.bigsoft.filesucker.Downloader;
import uk.co.bigsoft.filesucker.transfer.download.si.SuckerItem;
import uk.co.bigsoft.filesucker.transfer.view.SuckerItemModel;

public class SuckerItemDownloader implements Runnable {

	private SuckerItemModel suckerItem;

	public SuckerItemDownloader(SuckerItemModel item) {
		suckerItem = item;
	}

	@Override
	public void run() {
		Downloader dl = Downloader.getInstance();
		dl.downloadBinaryFileProgressable(suckerItem);
	}

}
