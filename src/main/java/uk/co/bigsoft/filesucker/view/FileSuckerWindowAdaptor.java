package uk.co.bigsoft.filesucker.view;

import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import uk.co.bigsoft.filesucker.credits.CreditsModel;
import uk.co.bigsoft.filesucker.prefs.FileSuckerPrefs;
import uk.co.bigsoft.filesucker.prefs.FileSuckerPrefsHandler;

public class FileSuckerWindowAdaptor extends WindowAdapter {

	private static final FileSuckerPrefsHandler fileSuckerPrefHandler = new FileSuckerPrefsHandler();

	private CreditsModel creditsModel;

	public FileSuckerWindowAdaptor(CreditsModel creditsModel) {
		super();
		this.creditsModel = creditsModel;
	}

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
		p.setTotalDownloadedFiles(creditsModel.getTotalNumFiles());
		p.setTotalDownloadedBytes(creditsModel.getTotalNumBytes());

		fileSuckerPrefHandler.save(p);
		System.exit(0);
	}

}
