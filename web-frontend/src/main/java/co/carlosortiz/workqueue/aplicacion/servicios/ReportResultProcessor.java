/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.aplicacion.servicios;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 *
 * @author Carlos
 */
@Component
public class ReportResultProcessor {

    private Map<String, DeferredResult<ResponseEntity<String>>> currentReportJobs;
    private Map<String, WebSocketSession> currentWSSReportJobs;

    public ReportResultProcessor() {
        currentReportJobs = new HashMap();
        currentWSSReportJobs = new HashMap();
    }

    public void registerReportCreationJob(String id, DeferredResult<ResponseEntity<String>> result) {
        currentReportJobs.put(id, result);
    }

    public void registerWSSReportCreationJob(String id, WebSocketSession wss) {
        currentWSSReportJobs.put(id, wss);
    }

    public void setJobResult(String id, String resultStatus) {
        DeferredResult<ResponseEntity<String>> deferedResult
                = currentReportJobs.get(id);
        if (deferedResult != null) {
            deferedResult.setResult(new ResponseEntity<String>(id, HttpStatus.OK));
            currentReportJobs.remove(id);
        }

    }

    public void setWSSJobResult(String id, String resultStatus) {
        WebSocketSession wss = currentWSSReportJobs.get(id);
        if (wss != null) {
            try {
                wss.sendMessage(new TextMessage(id));
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            currentReportJobs.remove(id);
        }

    }

}
