/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.workers.units.reportes.admisiones;

import co.carlosortiz.workqueue.workers.units.reportes.MongoFileRepository;
import co.carlosortiz.workqueue.workers.units.reportes.ReportBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Carlos
 */
@Component
public class AdmisionesReportBuilder implements ReportBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdmisionesReportBuilder.class);

    @Autowired
    private MongoFileRepository mongoRepository;

    @Override
    public void buildReport(String reportId, Map<String, String> reportParams) {
        LOGGER.info("Inicia generacion del reporte Admisiones , id [{}]", reportId);

        //Simulamos una operacion demorada
        
        
        //Grabamos el reporte en el repositorio mongo
        byte[] data = this.readReportFromFS();
        String filename = reportId + ".pdf";        
        if (data!=null) {
            this.mongoRepository.saveFile(filename, data, "reportes");
        }
        
        //Generamos mensaje de salida notificando la generacion del reporte
        
        
        
        LOGGER.info("reporte admisiones con id [{}] generado exitosamente" , reportId);
    }

    private byte[] readReportFromFS() {
        byte[] data = null;
        try {
            InputStream input = this.getClass().getResourceAsStream("/rpt.pdf");
            data = new byte[input.available()];
            int b = -1;
            int i = 0;
            while ((b = input.read()) != -1) {
                data[i++] = (byte) b;
            }
        } catch (IOException ioe) {
            LOGGER.error("Error leyendo reporte" , ioe);
        }
        return data;
    }

}
