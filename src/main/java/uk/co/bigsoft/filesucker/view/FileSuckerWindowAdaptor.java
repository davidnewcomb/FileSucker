package uk.co.bigsoft.filesucker.view;

import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FileSuckerWindowAdaptor extends WindowAdapter {

	private static final FileSuckerPrefHandler fileSuckerPrefHandler = new FileSuckerPrefHandler();

	@Override
	public void windowClosed(WindowEvent e) {
		e.getWindow().dispose();
	}

	@Override
	public void windowClosing(WindowEvent e) {

		Rectangle rect = e.getWindow().getBounds();

		FileSuckerPrefs p = new FileSuckerPrefs();
		p.setBoundX(rect.x);
		p.setBoundY(rect.y);
		p.setBoundHeight(rect.height);
		p.setBoundWidth(rect.width);
		p.setTotalDownloadedFiles(CreditScreen.getTotalDownloadedFiles());
		p.setTotalDownloadedBytes(CreditScreen.getTotalDownloadedBytes());

		fileSuckerPrefHandler.save(p);
		System.exit(0);
	}

}
