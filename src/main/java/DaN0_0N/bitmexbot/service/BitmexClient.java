package DaN0_0N.bitmexbot.service;

import DaN0_0N.bitmexbot.model.*;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import DaN0_0N.bitmexbot.util.Endpoints;
import DaN0_0N.bitmexbot.util.HttpMetod;
import DaN0_0N.bitmexbot.util.Signature;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

@RequiredArgsConstructor
public class BitmexClient {
    private final HttpClient httpClient = HttpClient.newBuilder().build();
    private final Signature signature = new Signature();
    private final String apiKey;
    private final String apiSecretKey;
    private final String baseUrl;
    private static final Gson gson = new Gson();
    private final int EXPIRES_DELAY = 3600;
    public void sendOrder(Order order){
        String data;
        if (order == null){
            data = "";
        } else {
            OrderRequest orderRequest = OrderRequest.toRequest(order);
            data = gson.toJson(orderRequest);
        }
        System.out.println(data);
        OrderHttpRequest orderHttpRequest = new OrderHttpRequest(order, baseUrl, Endpoints.ORDER_ENDPOINT,
                HttpMetod.POST, getAuthenticationHeaders(HttpMetod.POST, data, Endpoints.API_URL + Endpoints.ORDER_ENDPOINT));

        try {
            HttpResponse<String> response = httpClient.send(orderHttpRequest.getHttpRequest(), HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
            System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void getOrder(Order order) {
        OrderHttpRequest orderHttpRequest = new OrderHttpRequest(order, baseUrl, Endpoints.ORDER_ENDPOINT,
                HttpMetod.GET, getAuthenticationHeaders(HttpMetod.GET, "", Endpoints.API_URL + Endpoints.ORDER_ENDPOINT));
        try {
            HttpResponse<String> response = httpClient.send(orderHttpRequest.getHttpRequest(), HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
            System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void cancelOrder(String orderId) {
        String data = "{\"orderID\":\""+orderId+"\"}";

        System.out.println(data);
       CloseOrderHttpRequest close =  new CloseOrderHttpRequest(orderId, baseUrl, Endpoints.ORDER_ENDPOINT, getAuthenticationHeaders(HttpMetod.DELETE, data, Endpoints.API_URL + Endpoints.ORDER_ENDPOINT));

        try {
            HttpResponse<String> response = httpClient.send(close.getHttpRequest(), HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
            System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private AuthenticationHeaders getAuthenticationHeaders(String httpMethod, String data, String path) {
        long expires = System.currentTimeMillis() / 1000 + EXPIRES_DELAY;

        String signatureStr = signature.getSignature(apiSecretKey, httpMethod + path + expires + data);

        return AuthenticationHeaders.builder()
                .apiKey(apiKey)
                .signature(signatureStr)
                .expires(Long.toString(expires))
                .build();
    }

}
