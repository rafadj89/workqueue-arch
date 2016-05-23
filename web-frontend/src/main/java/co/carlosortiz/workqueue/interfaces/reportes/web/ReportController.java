/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.interfaces.reportes.web;

import co.carlosortiz.workqueue.aplicacion.servicios.ReportResultProcessor;
import co.carlosortiz.workqueue.interfaces.reportes.mensajes.ReportRequestPublisher;
import co.carlosortiz.workqueue.interfaces.reportes.modelo.Report;
import co.carlosortiz.workqueue.interfaces.reportes.modelo.ReportParam;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 *
 * @author Carlos
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    private ReportRequestPublisher reportRequestPublisher;
    private ReportResultProcessor reportResultProcessor;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    public ReportController(ReportRequestPublisher reportRequestPublisher,
            ReportResultProcessor reportResultProcessor) {
        this.reportRequestPublisher = reportRequestPublisher;
        this.reportResultProcessor = reportResultProcessor;
    }

    @RequestMapping(method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<String>> create() {
        DeferredResult<ResponseEntity<String>> deferredResult
                = new DeferredResult<ResponseEntity<String>>();

        String reportId = UUID.randomUUID().toString();
        List<ReportParam> params = new ArrayList<>();
        ReportParam param1 = new ReportParam("idadmitido", "1232478983");
        params.add(param1);
        Report report = new Report(reportId, "admisiones", "cortiz", params);

        reportRequestPublisher.publish(report);
        reportResultProcessor.registerReportCreationJob(reportId,
                deferredResult);

        return deferredResult;
    }

    @RequestMapping(value = "/{reportId}", method = RequestMethod.GET)
    public ResponseEntity download(@PathVariable("reportId") String reportId)
            throws IOException {

        //la longitud y filename se guardan como metadatos en la base de datos
        System.out.println("===>>file_id: " + reportId);

        try {
            GridFS gfsReportes = new GridFS(mongoTemplate.getDb(), "reportes");
            if (gfsReportes == null) {
                System.out.println("===>>gfsReportes is null");
            }
            String filename = reportId + ".pdf";
            GridFSDBFile reportFile = null;
            List<GridFSDBFile> files = gfsReportes.find(filename);
            
            if (files.isEmpty()) {
                System.out.println("===>>report not found");
                return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
            } else {
                reportFile = files.get(0);
                System.out.println("===>>image: " + reportFile);
                System.out.println("===>>image filename" + reportFile.getFilename());
                System.out.println("===>>image contenttype" + reportFile.getContentType());
            }

            HttpHeaders respHeaders = new HttpHeaders();
            respHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            respHeaders.setContentLength(reportFile.getLength());
            respHeaders.setContentDispositionFormData("inline", reportFile.getFilename());

            InputStreamResource isr = new InputStreamResource(new BufferedInputStream(reportFile.getInputStream()));
            return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
