package uk.co.bigsoft.filesucker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.time.Duration;

import uk.co.bigsoft.filesucker.config.ConfigModel;
import uk.co.bigsoft.filesucker.transfer.download.HttpSupport;
import uk.co.bigsoft.filesucker.transfer.view.SuckerItemModel;

public class Downloader {

	private static Downloader downloader = null;

	private HttpClient client;
	private Duration timeout;

	public static Downloader getInstance(ConfigModel cfg) {
		if (downloader != null) {
			throw new RuntimeException("downloader has already been initialised");
		}
		CookieHandler.setDefault(new CookieManager());
		downloader = new Downloader(cfg);
		return downloader;
	}

	public static Downloader getInstance() {
		if (downloader == null) {
			throw new RuntimeException("downloader has not been initialised");
		}
		return downloader;
	}

	private Downloader(ConfigModel configModel) {

		timeout = Duration.ofMillis(configModel.getDelaySockReadMs());

		client = HttpClient.newBuilder() //
				.followRedirects(Redirect.NORMAL) //
				.proxy(ProxySelector.getDefault())
				// .authenticator(Authenticator.getDefault())
				// TODO add separate connection timeout, maybe!
				.connectTimeout(timeout) //
				.cookieHandler(CookieHandler.getDefault()) //
				.build();
	}

	public String downloadTextFile(String address) throws IOException, InterruptedException {
		URI url = URI.create(address);

		// TODO setRequestProperty("Authorization", "Basic " + BasicAuth.encode(auth[0],
		// auth[1]));
		HttpRequest req = HttpRequest.newBuilder() //
				.uri(url) //
				.timeout(timeout) //
				.GET() //
				.build();

		BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
		HttpResponse<String> resp = client.send(req, handler);
		String body = resp.body();

		return body;
	}

	public void downloadBinaryFileProgressable(SuckerItemModel sim) {

		sim.started();
		FileOutputStream fos = null;
		InputStream is = null;

		try {
			URI url = URI.create(sim.getWorkItem().getUrl());
			File lf = new File(sim.getWorkItem().getLocal());

			// Resume features
			long currentFileSize = 0L;

			Builder builder = HttpRequest.newBuilder() //
					.version(HttpClient.Version.HTTP_1_1) //
					.uri(url) //
					.timeout(timeout) //
					.GET();

			if (lf.exists()) {
				currentFileSize = lf.length();
				String rangeLastModified = HttpSupport.rangeLastModifiedDateString(sim.getWorkItem().getLocal());
				if (rangeLastModified != null) {
					builder = builder.header("Range", "bytes=" + currentFileSize + "-");
					builder = builder.header("If-Range", rangeLastModified + " GMT");
				}
			}

			HttpRequest req = builder.build();

			BodyHandler<InputStream> handler = HttpResponse.BodyHandlers.ofInputStream();
			HttpResponse<InputStream> resp = client.send(req, handler);

			System.out.println("response-code=" + resp.statusCode());

			long bytesDownloaded;
			if (resp.statusCode() == HttpURLConnection.HTTP_PARTIAL) {
				fos = new FileOutputStream(lf, true);
				bytesDownloaded = currentFileSize;
			} else if (resp.statusCode() == HttpURLConnection.HTTP_OK) {
				if (currentFileSize > 0) {
					System.out.println("Server would not honour range request, fresh download");
				}
				bytesDownloaded = 0L;
				fos = new FileOutputStream(lf);
			} else {
				throw new IOException("Bad code " + resp.statusCode());
			}

			HttpHeaders headers = resp.headers();

			int contentLength = HttpSupport.retrieveContentLength(headers);
			sim.setBytesToDownload(contentLength);
			System.out.println("content-length=" + contentLength);

			is = resp.body();

			byte[] buffer = new byte[4096];

			while (true) {
				int bytesRead = is.read(buffer);
				if (bytesRead == 0) {
					Thread.sleep(Duration.ofMillis(500));
					continue;
				}
				if (bytesRead == -1) {
					break;
				}
				bytesDownloaded += bytesRead;
				sim.setBytesDownloaded(bytesDownloaded);
				// System.out.println("downloaded=" + bytesDownloaded);

				fos.write(buffer, 0, bytesRead);
				// Utility.delay(1_000);
			}
			sim.completed();
		} catch (IOException | InterruptedException e) {
			System.out.println("FAILED " + sim.getWorkItem().getUrl() + ": " + e.toString());
			e.printStackTrace();
			sim.setError(e);
		} finally {
			Utility.closeSafely(fos);
			Utility.closeSafely(is);
		}
	}
}
