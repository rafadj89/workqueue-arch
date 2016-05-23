/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.aplicacion.servicios;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

/**
 *
 * @author Carlos
 */
@Component
public class ReportResultProcessor {

    private Map<String, DeferredResult<ResponseEntity<String>>> currentReportJobs;

    public ReportResultProcessor() {
        currentReportJobs = new HashMap();
    }

    public void registerReportCreationJob(String id, DeferredResult<ResponseEntity<String>> result) {
        currentReportJobs.put(id, result);
    }

    public void setJobResult(String id, String resultStatus) {
        DeferredResult<ResponseEntity<String>> deferedResult
                = currentReportJobs.get(id);
        if (deferedResult != null) {
            deferedResult.setResult(new ResponseEntity<String>(id, HttpStatus.OK));
        }
    }

}
