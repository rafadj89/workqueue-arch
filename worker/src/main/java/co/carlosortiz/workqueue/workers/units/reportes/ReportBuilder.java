/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.workers.units.reportes;

import java.util.Map;

/**
 *
 * @author Carlos
 */
public interface ReportBuilder {
   void buildReport(String reportid, Map<String,String> reportParams); 
}
