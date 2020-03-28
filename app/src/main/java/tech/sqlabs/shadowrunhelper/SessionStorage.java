package tech.sqlabs.shadowrunhelper;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketState;

import java.io.IOException;

public class SessionStorage {
    private static WebSocket sWebSocket = null;
    public static Thread sThread = null;

    public static WebSocket getWebSocket() {
        if (sWebSocket == null || sWebSocket.getState() != WebSocketState.OPEN) {
            sWebSocket = startWebSocket();
        }


        return sWebSocket;
    }

    private static WebSocket startWebSocket() {
        WebSocketFactory factory = new WebSocketFactory();

        try {
            return factory.createSocket(Const.SERVER_URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
