package DaN0_0N.bitmexbot.service;

import DaN0_0N.bitmexbot.util.Endpoints;

public class BitmexClientFactory {
    public static BitmexClient newTestnetBitmexClient(String apiKey, String apiSecret) {
        return new BitmexClient(apiKey, apiSecret, Endpoints.BASE_TEST_URL+Endpoints.API_URL);
    }

   /* public BitmexClient newRealBitmexClient(String apiKey) {
        return new BitmexClient(apiKey, Endpoints.BASE_REAL_URL, true);
    }*/
}
