package DaN0_0N.bitmexbot.model;

import com.google.gson.Gson;
import DaN0_0N.bitmexbot.util.HttpMetod;

import java.net.URI;
import java.net.http.HttpRequest;

public class CloseOrderHttpRequest {
    private static final Gson gson = new Gson();
    private static HttpRequest httpRequest;

    private final String HttpMethod = HttpMetod.DELETE;

    public CloseOrderHttpRequest(String orderId, String baseUrl, String endpoint, AuthenticationHeaders authenticationHeaders) {
        createHttpRequest(orderId, baseUrl, endpoint, this.HttpMethod, authenticationHeaders);
    }


    private void createHttpRequest(String orderId, String baseUrl, String endpoint, String httpMethod, AuthenticationHeaders authenticationHeaders) {
        String data = "{\"orderID\":\""+orderId+"\"}";
        System.out.println(data);

        httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + endpoint))
                .method(httpMethod, HttpRequest.BodyPublishers.ofString(data))
                .header("content-type", "application/json")
                .header("api-key", authenticationHeaders.getApiKey())
                .header("api-signature", authenticationHeaders.getSignature())
                .header("api-expires", authenticationHeaders.getExpires())
                .build();
    }

    public static HttpRequest getHttpRequest() {
        return httpRequest;
    }
}

