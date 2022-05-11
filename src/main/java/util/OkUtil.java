package util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;

/**
 * @author dsttl3
 */
public class OkUtil {

	/**
	 * okhttp3
	 * @param url 链接
	 * @return body().string()
	 */
	public String get(String url) {
		final String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36";
		try {
			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder()
					.url(url)
					.header("User-Agent", userAgent)
					.build();
			Response response = client.newCall(request).execute();
			return Objects.requireNonNull(response.body()).string();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
