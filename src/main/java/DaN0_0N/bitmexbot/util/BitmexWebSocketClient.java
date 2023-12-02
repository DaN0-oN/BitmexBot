package DaN0_0N.bitmexbot.util;
import jakarta.websocket.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;
@ClientEndpoint
@Slf4j
public class BitmexWebSocketClient {
    private String serverUri = "wss://ws.testnet.bitmex.com/realtime";
    private Session session;
    private Boolean isConnected;
    private String apiKey = "x-WHo8nSkP_yVs2Yp2uUEcQo";
    private String secretKey = "m2uFbV8RmiAfV8c4TMkYNfqRVd4vobyjqZOpdtKzM4GzLxtf";
    private long expires = System.currentTimeMillis() / 1000 + 50;
    public void connect(){

        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(this, new URI(serverUri));
            session.setMaxIdleTimeout(TimeUnit.MINUTES.toMillis(60));
            if (session.isOpen()){
                isConnected = true;
                log.info("Session is open");
                Signature signature = new Signature();
                String signatureStr = signature.getSignature(secretKey, "GET/realtime" + expires);
                session.getBasicRemote().sendText("{\"op\": \"authKeyExpires\", \"args\": [\"" + apiKey + "\", "+ expires +", \""+ signatureStr +"\"]}\n");
                session.getBasicRemote().sendText("{\"op\": \"subscribe\", \"args\": [\"order\"]}");
            }
        }
         catch (DeploymentException | IOException | URISyntaxException e) {
            log.error("Cannoot connect to the simulator server.");
        }
    }

    @OnMessage
    public void onMassage(String massage){
        System.out.println(massage);
    }

    @OnMessage
    public void onMassage(PongMessage pongMessage){

    }

    @OnError
    public void onError(Throwable error){
        log.info("error" + error.getMessage());
    }
}
