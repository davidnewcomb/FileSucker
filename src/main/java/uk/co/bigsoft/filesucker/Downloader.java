package uk.co.bigsoft.filesucker;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.time.Duration;

import uk.co.bigsoft.filesucker.config.ConfigModel;

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

		client = HttpClient.newBuilder().followRedirects(Redirect.NORMAL).proxy(ProxySelector.getDefault())
				// .authenticator(Authenticator.getDefault())
				// TODO add separate connection timeout, maybe!
				.connectTimeout(timeout).cookieHandler(CookieHandler.getDefault()).build();
	}

	public String downloadTextFile(String address) throws IOException, InterruptedException {
		URI url = URI.create(address);

		// TODO setRequestProperty("Authorization", "Basic " + BasicAuth.encode(auth[0],
		// auth[1]));
		HttpRequest req = HttpRequest.newBuilder().uri(url).timeout(timeout).GET().build();

		BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
		HttpResponse<String> resp = client.send(req, handler);
		String body = resp.body();

		return body;
	}

}
