/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.infraestructura.mensajes.aplicacion;

/**
 *
 * @author Carlos
 */
public class ReportProcessedEvent implements IReportProcessedEvent{
    private String reportId;
    private String reportCode;
    private String reportUser;
    private ReportStatus reportStatus;

    public ReportProcessedEvent() {
    }

    
    
    public ReportProcessedEvent(String reportId, String reportCode, String reportUser, ReportStatus reportStatus) {
        this.reportId = reportId;
        this.reportCode = reportCode;
        this.reportUser = reportUser;
        this.reportStatus = reportStatus;
    }

    public String getReportCode() {
        return reportCode;
    }

    public String getReportId() {
        return reportId;
    }

    public ReportStatus getReportStatus() {
        return reportStatus;
    }

    public String getReportUser() {
        return reportUser;
    }

    
    
}
