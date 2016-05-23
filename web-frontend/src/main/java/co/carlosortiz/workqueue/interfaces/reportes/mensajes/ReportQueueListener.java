/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.interfaces.reportes.mensajes;

import co.carlosortiz.workqueue.aplicacion.servicios.ReportResultProcessor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author Carlos
 */
@Component
public class ReportQueueListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportQueueListener.class);

    @Autowired
    private ReportResultProcessor reportResultProcessor;

    @JmsListener(destination = "${work.queue.input}",
            containerFactory = "jmsQueueListenerContainerFactory")
    public void receiveAndProcessMessage(String message) {
        LOGGER.info("message to process: [{}]", message);
        //Recibe mensaje de procesamiento de un reporte y notifia al cliente
        //el resultado de la ejeucion.

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(message);
            String reportId = root.path("reportId").asText();
            String reportCode = root.path("reportCode").asText();
            String reportUser = root.path("reportUser").asText();
            String reportStatus = root.path("reportStatus").asText();

            LOGGER.info("report-code: [{}]", reportCode);
            LOGGER.info("report-id: [{}]", reportId);
            LOGGER.info("report-user: [{}]", reportUser);
            
            this.reportResultProcessor.setJobResult(reportId, reportStatus);
        } catch (Exception e) {
            LOGGER.error("Ocurrio un error procesando resultado" , e);
        }
    }
}
