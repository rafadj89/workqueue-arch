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
public enum ReportStatus {
    SUCCESS("success"), FAILED("failed");
    
    private String value;
    
    ReportStatus(String value) {
        this.value = value;             
    }
    
}
