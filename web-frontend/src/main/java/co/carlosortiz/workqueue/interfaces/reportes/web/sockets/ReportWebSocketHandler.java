/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.interfaces.reportes.web.sockets;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 *
 * @author Carlos
 */
public class ReportWebSocketHandler extends TextWebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportWebSocketHandler.class);

    private static final Set< WebSocketSession> sessions
            = Collections.synchronizedSet(new HashSet< WebSocketSession>());

    @Override
    public void afterConnectionEstablished(WebSocketSession wss) throws Exception {
        LOGGER.info("Session connected [{}] ", wss);
        sessions.add(wss);
       
    }

    @Override
    public void handleMessage(WebSocketSession wss, WebSocketMessage<?> wsm) throws Exception {
        LOGGER.info("Message received: [{}] ", wsm.getPayload());
        
    }

    /*
    @Override
    public void handleTransportError(WebSocketSession wss, Throwable thrwbl) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    */

    @Override
    public void afterConnectionClosed(WebSocketSession wss, CloseStatus cs) throws Exception {
        LOGGER.info("Session [{}] closed, removing from memory", wss);
        
        sessions.remove(wss);
    }
    
    

 

}
