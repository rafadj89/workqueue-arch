/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.infraestructura.mensajes.aplicacion;

import co.carlosortiz.workqueue.interfaces.reportes.ReportExecutionPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author Carlos
 */
@Component
public class ReportProcessedLocalEventHandler {

    @Autowired
    private ReportExecutionPublisher reportExecutionPublisher;
    
    @EventListener
    public void handleReportProcessdEvent(ReportProcessedEvent reportProcessedEvent) {
        reportExecutionPublisher.publish(reportProcessedEvent);
    }
}
