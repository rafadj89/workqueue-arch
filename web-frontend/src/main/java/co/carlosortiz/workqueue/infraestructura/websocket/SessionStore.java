/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.infraestructura.websocket;

import co.carlosortiz.workqueue.interfaces.reportes.web.sockets.ReportWebSocketHandler;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

/**
 *
 * @author Carlos
 */

public class SessionStore {
     private static final Logger LOGGER = LoggerFactory.getLogger(ReportWebSocketHandler.class);

    private static final Set< WebSocketSession> sessions
            = Collections.synchronizedSet(new HashSet< WebSocketSession>());
    
    public void addSession(WebSocketSession wss) {
        sessions.add(wss);
    }
    
    public void sendToSession(WebSocketSession wss, String msg) {
       
    }
}
