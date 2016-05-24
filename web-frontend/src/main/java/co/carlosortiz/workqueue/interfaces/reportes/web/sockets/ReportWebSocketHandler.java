/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.interfaces.reportes.web.sockets;

import co.carlosortiz.workqueue.aplicacion.servicios.ReportResultProcessor;
import co.carlosortiz.workqueue.interfaces.reportes.mensajes.ReportRequestPublisher;
import co.carlosortiz.workqueue.interfaces.reportes.modelo.Report;
import co.carlosortiz.workqueue.interfaces.reportes.modelo.ReportParam;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 *
 * @author Carlos
 */
@Component
public class ReportWebSocketHandler extends TextWebSocketHandler {

    private ReportRequestPublisher reportRequestPublisher;
    private ReportResultProcessor reportResultProcessor;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportWebSocketHandler.class);

    private static final Set< WebSocketSession> sessions
            = Collections.synchronizedSet(new HashSet< WebSocketSession>());

    @Autowired
    public ReportWebSocketHandler(ReportRequestPublisher reportRequestPublisher,
            ReportResultProcessor reportResultProcessor) {
        this.reportRequestPublisher = reportRequestPublisher;
        this.reportResultProcessor = reportResultProcessor;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession wss) throws Exception {
        LOGGER.info("Session connected [{}] ", wss);
        sessions.add(wss);

    }

    @Override
    public void handleMessage(WebSocketSession wss, WebSocketMessage<?> wsm) throws Exception {
        LOGGER.info("Message received: [{}] ", wsm.getPayload());
        String message = wsm.getPayload().toString();
        if (message.startsWith("REPORT") && message.indexOf('|') != -1) {
            String[] command = message.split("\\|");
            if (command.length == 3) {
                String reportCode = command[1];
                String reportUser = command[2];

                String reportId = UUID.randomUUID().toString();
                List<ReportParam> params = new ArrayList<>();
                ReportParam param1 = new ReportParam("idadmitido", "1232478983");
                params.add(param1);
                Report report = new Report(reportId, reportCode, reportUser, params);

                reportResultProcessor.registerWSSReportCreationJob(reportId,
                        wss);
                reportRequestPublisher.publish(report);

            }
        }
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
