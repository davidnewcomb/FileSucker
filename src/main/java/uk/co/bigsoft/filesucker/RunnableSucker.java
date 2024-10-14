package uk.co.bigsoft.filesucker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import uk.co.bigsoft.filesucker.view.CreditScreen;

public class RunnableSucker implements Runnable {
	// private static int maxFollowDepth = 5 ; // TODO move to config

	private ProtocolURL url;
	private File localFile;
	private HashMap<String, String> sendHeaders = new HashMap<String, String>();
	private Map<String, String> replyHeaders = new HashMap<String, String>();
	private int returnCode;
	private boolean cancel = false;
	private boolean paused = false;
	private long bytesDownloaded = 0;
	private long expectedDownloadLoadsize = 0;
	private SuckeringFileJProgressBar transfer = null;
	private long localFileLength = 0;
	private SuckerProgressPanel display = null;
	private FileOutputStream fos = null;
	private Socket socket = null;
	private SuckerThread completedNotifier;

	// private int followDepth = 0;

	public RunnableSucker(SuckerProgressPanel _display, String _remotefile, String _localfile, SuckerThread done)
			throws MalformedURLException {
		completedNotifier = done;
		String parseLocalFile = "";

		url = new ProtocolURL(_remotefile);
		parseLocalFile = _localfile.replaceAll("%20", "_");
		if (File.separatorChar == '/') {
			// Unix
			parseLocalFile = parseLocalFile.replaceAll("[=&:?]", "_");
		} else {
			// Windows
			if (parseLocalFile.length() > 3) {
				// c:\
				String rest = parseLocalFile.substring(3);
				rest = rest.replaceAll("[=&:?]", "_");
				parseLocalFile = parseLocalFile.substring(0, 3) + rest;
			} else {
				parseLocalFile = parseLocalFile.replaceAll("[=&?]", "_");
			}
		}

		localFile = new File(parseLocalFile);
		transfer = new SuckeringFileJProgressBar(this);
		display = _display;
	}

	@Override
	public String toString() {
		return url == null ? "" : url.toString();
	}

	private void buildHeaderResume() {
		localFileLength = localFile.length();
		if (localFileLength == 0L)
			return;
		bytesDownloaded = localFileLength;
		System.out.println("Resume from " + localFileLength + " bytes");
		sendHeaders.put("Range", "bytes=" + localFileLength + "-");
	}

	private void buildHeader() {
		sendHeaders.put("Host", url.getHost());
		sendHeaders.put("Connection", "Keep-Alive");
		// sendHeaders.put ("ETag", "11ad62a-1878-3ec82ee3") ;
		// sendHeaders.put ("Accept", "image/gif, image/x-xbitmap,
		// image/jpeg,
		// image/pjpeg, application/x-shockwave-flash, */*") ;
		sendHeaders.put("Accept", "*/*");
		sendHeaders.put("Accept-Language", "en-gb");
		sendHeaders.put("Accept-Encoding", "gzip, deflate");
		sendHeaders.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322)");
	}

	private void buildHeaderLogin() {
		String userinfo = url.getUserInfo();
		if (userinfo != null) {
			String[] auth = userinfo.split(":");
			sendHeaders.put("Authorization", "Basic " + BasicAuth.encode(auth[0], auth[1]));
		}
	}

	private void buildHeaderReferer() {
		// Remove username and password from toString
		String refh = url.toString();
		if (url.getUserInfo() != null) {
			refh = refh.replaceFirst(url.getUserInfo() + "@", "");
		}
		String refs[] = refh.split("/", 4);
		String ref = refs[0] + "//" + refs[2];
		sendHeaders.put("Referer", ref);
	}

	// private void buildCookie(String cookie)
	// {
	// if (cookie == null)
	// return;
	// try
	// {
	// StringBuffer sb = new StringBuffer();
	// String[] vars = cookie.split(";");
	//
	// // sb.append("$Version=\"1\";");
	// for (int i = 0 ; i < vars.length ; ++i)
	// {
	// String[] pair = vars[i].trim().split("=") ;
	// String k = pair[0];
	// String v = pair[1];
	//
	// if (k.compareToIgnoreCase("version") == 0 ||
	// k.compareToIgnoreCase("domain") == 0 || k.compareToIgnoreCase("path")
	// == 0
	// || k.compareToIgnoreCase("expires") == 0 )
	// sb.append(""); // "$" + k + "=" + v);
	// else
	// sb.append(k + "=" + v + ";");
	//
	// // sb.append(";");
	//
	// }
	// sb.deleteCharAt(sb.length() - 1);
	//
	// sendHeaders.put("Cookie", sb.toString());
	// }
	// catch (Exception e)
	// {
	// System.out.println("buildCookies:");
	// e.printStackTrace() ;
	// }
	// }

	public void run() {
		// Utility.delay(5000);
		InputStream dis = null;
		display.addDownloadFile(transfer);
		String jumpTo = null;

		// if (true)
		// {
		// display.removeDownloadFile (transfer) ;
		// display.setDoneAnother () ;
		// try
		// {
		// Thread.sleep (2000) ;
		// }
		// catch (Exception e)
		// {
		// //
		// }
		// return ;
		// }

		try {
			buildHeader();
			buildHeaderResume();
			buildHeaderLogin();
			buildHeaderReferer();

			// Are we being forwarded
			// DataInputStream dis = null ;
			do {
				dis = connect();
				if (dis == null) {
					System.out.println(url.toString() + ": could not connect");
					completedNotifier.jobCompleted();
					return;
				}
				System.out.println(url.toString() + " = " + returnCode);

				jumpTo = replyHeader_get_ic("Location");

				// If we are being forwarded then we have hit
				// that odd
				// download bug, so use alternative download
				// method
				if (jumpTo != null) {
					disconnect();
					// TODO we want to use our method when
					// it is a true forwarder
					dis = getUrlIntoFile(url.toString());
				}
				break;

				// // if (jumpTo == null)
				// // break ;
				// String cookie =
				// replyHeader_get_ic("Set-Cookie");
				// buildCookie (cookie) ;
				// buildHeaderReferer () ;
				//
				// disconnect() ;
				//
				// if (++followDepth > maxFollowDepth)
				// {
				// TaskScreen.setErrorMessage("max followdepth reached");
				// System.out.println("max followdepth reached");
				// display.removeDownloadFile (transfer) ;
				// display.setDoneAnother () ;
				// return ;
				// }
				//
				// String userinfo = url.getUserInfo();
				//
				// url = new ProtocolURL (jumpTo) ;
				// url.setUserInfo(userinfo);
			} while (true);

			openLocalFile();

			expectedDownloadLoadsize = getContentLength();
			if (expectedDownloadLoadsize == 0) {
				System.out.println("Already downloaded");
				completedNotifier.jobCompleted();
				return;
			}

			transfer.setCurrentMaxFileLength(localFileLength + expectedDownloadLoadsize);

			passThru(dis, fos);
		} catch (Exception ex) {
			System.out.println("Exception in download:");
			ex.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
					/* empty */
				}
			}
			if (dis != null) {
				try {
					dis.close();
				} catch (Exception e) {
					/* empty */
				}
			}
		}

		completedNotifier.jobCompleted();
		disconnect();
		display.removeDownloadFile(transfer);
		display.setDoneAnother();
	}

	private String replyHeader_get_ic(String key) {
		for (Map.Entry<String, String> me : replyHeaders.entrySet()) {
			String k = me.getKey();
			if (k == null)
				continue;

			if (k.compareToIgnoreCase(key) == 0) {
				return replyHeaders.get(key);
			}
		}
		return null;
	}

	private void disconnect() {
		if (socket != null) {
			try {
				socket.close();
			} catch (Exception e) {
				/* empty */
			}
		}
	}

	private InputStream connect() {
		InputStream is = null;
		try {
			StringBuffer sb = new StringBuffer();

			sb.append("GET " + url.getFile() + " HTTP/1.0\r\n");
			for (String k : sendHeaders.keySet()) {
				String v = sendHeaders.get(k);
				sb.append(k);
				sb.append(": ");
				sb.append(v);
				sb.append("\r\n");
			}
			sb.append("\r\n");
			// char [] chr = sb.toString().toCharArray() ;
			byte[] bytes = sb.toString().getBytes();

			socket = new Socket(url.getHost(), url.getPort());
			OutputStream os = socket.getOutputStream();
			os.write(bytes, 0, bytes.length);
			System.out.println("len=" + bytes.length);

			// boolean autoflush = true ;
			// PrintWriter socout = new PrintWriter
			// (socket.getOutputStream (),
			// autoflush) ;

			// socout.print(chr) ;
			// socout.flush();

			// dis = new DataInputStream (socket.getInputStream ())
			// ;
			is = socket.getInputStream();

			// getReplyHeaders (dis) ;
			getReplyHeaders(is);
		} catch (java.net.ConnectException e) {
			System.out.println(e.toString());
		} catch (Exception e) {
			System.out.println("Exception in download:");
			e.printStackTrace();
		}
		// return dis ;
		return is;
	}

	private FileOutputStream openLocalFile() throws FileNotFoundException {
		try {
			fos = new FileOutputStream(localFile, true);
		} catch (Exception e) {
			String p = localFile.getParent();
			File pp = new File(p);
			pp.mkdirs();
			fos = new FileOutputStream(localFile, true);
		}

		return fos;
	}

	private boolean timeout_read(InputStream in, int timeout) {
		int t = timeout;
		try {
			int bytes = in.available();
			while (bytes == 0) {
				t -= 100;
				if (t < 0)
					return false;
				Thread.sleep(100);
				bytes = in.available();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private void passThru(InputStream in, FileOutputStream out) throws IOException {
		byte[] buffer = new byte[4096];
		int i;

		CreditScreen.addFiles(1);
		while (true) {
			if (timeout_read(in, 2000) == false) {
				StringBuffer s = new StringBuffer("Read failed: ");
				s.append(getRemoteUrl());
				System.out.println(s.toString());
				break;
			}
			i = in.read(buffer);
			if (i == -1)
				break;

			if (cancel)
				return;
			if (paused)
				syncWait();

			out.write(buffer, 0, i);
			bytesDownloaded += i;
			Utility.delay(FileSucker.configData.getDelaySockReadMs().intValue());
			transfer.setCurrentDownloadedFileLength(bytesDownloaded);
			CreditScreen.addBytes(i);
		}
	}

	public void pauseThread() {
		paused = true;
		StringBuffer sb = new StringBuffer(url.toString());
		sb.append(" [Paused]");
		transfer.setString(sb.toString());
	}

	private void syncWait() {
		synchronized (this) {
			try {
				wait();
			} catch (Exception e) {// empty
			}
		}
	}

	private void syncNotify() {
		synchronized (this) {
			notify();
		}
	}

	public void resumeThread() {
		paused = false;
		transfer.setString(url.toString());
		syncNotify();
	}

	public boolean isRunning() {
		return !paused;
	}

	public void cancelThread() {
		cancel = true;
		StringBuffer sb = new StringBuffer(url.toString());
		sb.append(" [canceled]");
		transfer.setString(sb.toString());
		syncNotify();
	}

	private void getReplyHeaders(InputStream in) {
		String line;
		String[] headerLine;

		replyHeaders.clear();

		do {
			line = getReadLine(in);
			if (line.equals("\r\n"))
				break;
			line = line.substring(0, line.length() - 2);
			headerLine = line.split(":", 2);
			if (headerLine.length == 1) {
				// HTTP/1.1 200 OK
				headerLine = headerLine[0].split(" ");
				returnCode = Integer.parseInt(headerLine[1]);
				continue;
			}
			replyHeaders.put(headerLine[0].trim(), headerLine[1].trim());
		} while (true);
	}

	private int getContentLength() {
		String len = replyHeader_get_ic("Content-Length");

		if (len == null) {
			System.out.println("Content-Length not specified");
			return -1;
		}

		try {
			return Integer.parseInt(len);
		} catch (Exception e) {
			return -1;
		}
	}

	private String getReadLine(InputStream i) {
		StringBuffer sb = new StringBuffer();
		try {
			int c = 0;
			do {
				c = i.read();
				sb.append((char) c);
			} while ((c == -1 || c == '\n') == false);
		} catch (Exception eee) {
			System.out.println("Exception in download:");
			eee.printStackTrace();
		}
		return sb.toString();
	}

	public void cancel() {
		System.out.print("Canceled: " + url.toString());
		cancel = true;
	}

	public String getRemoteUrl() {
		return url.toString();
	}

	InputStream getUrlIntoFile(String remoteFile) {
		System.out.println("===== ALTERNATIVE: " + remoteFile);
		try {
			long toFileLength = localFile.length();

			URL urlt = URI.create(remoteFile).toURL();
			java.net.URLConnection urlc = urlt.openConnection();

			if (toFileLength != 0) {
				urlc.setRequestProperty("Range", "bytes=" + localFile.length() + "-");
			}

			int expectedLength = urlc.getContentLength();
			if (expectedLength == 0) {
				System.err.println("Already downloaded");
				return null;
			}
			boolean supportsContentRange = urlc.getHeaderField("Content-Range") == null ? false : true;
			if (toFileLength != 0 && supportsContentRange == false) {
				System.err.println("Resume not supported");
				// if (Config.skipifRangeNotSupportted)
				// return;
			}

			if (supportsContentRange && toFileLength > 0) {
				if (toFileLength == expectedLength) {
					System.err.println("Already downloaded");
					return null;
				}
				System.err.println("Resuming from " + toFileLength);
				fos = new FileOutputStream(localFile, true);
			} else {
				fos = new FileOutputStream(localFile);
			}

			if (expectedLength != -1)
				System.err.println("Expected size: " + expectedLength);

			InputStream is = null;
			try {
				is = urlc.getInputStream(); // To download
			} catch (IOException ioe) {
				String err = ioe.getCause().getMessage();

				if (err.indexOf(" 416 ") != -1)
					System.err.println("Already downloaded");
				else {
					System.err.println("IOException on getInputStream");
					ioe.printStackTrace();
				}
				return null;
			}

			return is;
		} catch (Exception e) {
			System.err.println("getUrlFile: " + remoteFile);
			e.printStackTrace();
			return null;
		}
	}

}
