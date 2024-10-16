package uk.co.bigsoft.filesucker.prefs;

import java.util.prefs.Preferences;

public class FileSuckerPrefsHandler {

	private static final Class<FileSuckerPrefsHandler> key = FileSuckerPrefsHandler.class;
	private static final String BOUND_X = "x";
	private static final String BOUND_Y = "y";
	private static final String BOUND_HEIGHT = "height";
	private static final String BOUND_WIDTH = "width";
	private static final String TOTAL_DOWNLOADED_FILES = "totalFiles";
	private static final String TOTAL_DOWNLOADED_BYTES = "totalBytes";

	public void save(FileSuckerPrefs p) {
		Preferences prefs = Preferences.userNodeForPackage(key);

		prefs.putInt(BOUND_X, p.getBoundX());
		prefs.putInt(BOUND_Y, p.getBoundY());
		prefs.putInt(BOUND_HEIGHT, p.getBoundHeight());
		prefs.putInt(BOUND_WIDTH, p.getBoundWidth());
		prefs.putLong(TOTAL_DOWNLOADED_FILES, p.getTotalDownloadedFiles());
		prefs.putLong(TOTAL_DOWNLOADED_BYTES, p.getTotalDownloadedBytes());
	}

	public FileSuckerPrefs load() {
		FileSuckerPrefs p = new FileSuckerPrefs();
		Preferences prefs = Preferences.userNodeForPackage(key);

		p.setBoundX(prefs.getInt(BOUND_X, 50));
		p.setBoundY(prefs.getInt(BOUND_Y, 50));
		p.setBoundHeight(prefs.getInt(BOUND_HEIGHT, 200));
		p.setBoundWidth(prefs.getInt(BOUND_WIDTH, 400));
		p.setTotalDownloadedFiles(prefs.getLong(TOTAL_DOWNLOADED_FILES, 0));
		p.setTotalDownloadedBytes(prefs.getLong(TOTAL_DOWNLOADED_BYTES, 0));

		return p;
	}

}
