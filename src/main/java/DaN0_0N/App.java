package DaN0_0N;

import DaN0_0N.bitmexbot.model.Order;
import DaN0_0N.bitmexbot.model.OrderType;
import DaN0_0N.bitmexbot.model.Symbol;
import DaN0_0N.bitmexbot.service.BitmexClient;
import DaN0_0N.bitmexbot.service.BitmexClientFactory;
import DaN0_0N.bitmexbot.util.BitmexWebSocketClient;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {

        Order order = Order.builder()
                .symbol(Symbol.XBTUSD)
                .isBuy(true)
                .orderQty(100.)
                .price(37100.)
                .orderType(OrderType.LMT)
                .build();

        String apiKey = "x-WHo8nSkP_yVs2Yp2uUEcQo";
        String secretKey = "m2uFbV8RmiAfV8c4TMkYNfqRVd4vobyjqZOpdtKzM4GzLxtf";

        BitmexClient bitmexClient = BitmexClientFactory.newTestnetBitmexClient(apiKey, secretKey);
       /* bitmexClient.getOrder(order);
        bitmexClient.sendOrder(order);
        //bitmexClient.cancelOrder("2b49ed11-4815-4187-afbc-78eee8171add");
        bitmexClient.getOrder(order);*/

        BitmexWebSocketClient client = new BitmexWebSocketClient();
        client.connect();

    }
}
