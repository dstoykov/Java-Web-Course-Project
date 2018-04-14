package dst.courseproject.cloud;

import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HttpRequestExecutor {
    private OkHttpClient httpClient;

    public HttpRequestExecutor() {
        this.httpClient = new OkHttpClient.Builder().build();
    }

    public Response executeGetRequest(String url) throws IOException {
        Request request = new Request.Builder().url(url).get().build();

        return this.httpClient.newCall(request).execute();
    }

    public Response executePostRequest(String url, String mediaType, byte[] content) throws IOException {
        RequestBody requestBody = RequestBody.create(MediaType.parse(mediaType), content);
        Request request = new Request.Builder().url(url).post(requestBody).build();

        return this.httpClient.newCall(request).execute();
    }
}
