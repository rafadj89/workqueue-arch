/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.workers.units.reportes.impl;

import co.carlosortiz.workqueue.infraestructura.mensajes.aplicacion.ReportProcessedEvent;
import co.carlosortiz.workqueue.infraestructura.mensajes.aplicacion.ReportStatus;
import co.carlosortiz.workqueue.workers.units.reportes.ReportBuilder;
import co.carlosortiz.workqueue.workers.units.reportes.ReportProcessor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 *
 * @author Carlos
 */
@Service
public class ReportProcessorImpl implements ReportProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportProcessorImpl.class);

    @Autowired
    private Map<String, ReportBuilder> reportBuilders;
    
    private final ApplicationEventPublisher publisher;

    @Autowired
    public ReportProcessorImpl(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }
    
    

    @Override
    /*
     * La cadena message esta en formato json y contiene la definicion del
     * reporte que se debe generar asincronamente en el procesador.
     * 
     {
     "report-id": "7b51aa48-36a9-4c07-adbb-bf68f1c7bc97",
     "report-code": "admisiones",
     "report-params": [
     {
     "nombre": "id-admitido",
     "valor": "1297989323"
     },
     {
     "nombre": "nombre-admitido",
     "valor": "Juan Perez"
     }
     ]
     }
    
    
     */
    public void processMessage(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(message);
            String reportId = root.path("report-id").asText();
            String reportCode = root.path("report-code").asText();

            LOGGER.info("report-code: [{}]" , reportCode);
            LOGGER.info("report-id: [{}]" , reportId);

            //Obtiene los parametros del reporte
            Map<String, String> reportParams
                    = mapReportParams(root.path("report-params"));

            //Delega la construccion del reporte al builder especifico
            this.buildReport(reportId, reportCode, reportParams);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void buildReport(String reportId, String reportCode,
            Map<String, String> reportParams) {
        ReportBuilder reportBuilder = reportBuilders.get(reportCode + "ReportBuilder");
        if (reportBuilder == null) {
            LOGGER.error("No existe builder para el reporte [{}]" , reportCode);
            return;
        }
        ReportStatus status = ReportStatus.SUCCESS;
        try {
            reportBuilder.buildReport(reportId, reportParams);            
        } catch (Exception e) {
             status = ReportStatus.FAILED;
             LOGGER.error("Ocurrio un error ejecutando reporte [{}]" , reportCode, e);
        } finally {
                publisher.publishEvent(new ReportProcessedEvent(reportId,
                        reportCode, status));
        }
    }

    private Map<String, String> mapReportParams(JsonNode paramsNode) {
        Map<String, String> reportParams = new HashMap();
        for (JsonNode node : paramsNode) {
            String paramName = node.path("nombre").asText();
            String paramValue = node.path("valor").asText();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("nombre [{}] " ,paramName);
                LOGGER.debug("valor [{}] " , paramValue);
            }
            reportParams.put(paramName, paramValue);
        }
        return reportParams;
    }

}
