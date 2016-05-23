/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.interfaces.reportes.modelo;

/**
 *
 * @author Carlos
 */
public class ReportParam {
    private String name;
    private String value;

    public ReportParam(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public ReportParam() {
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
    
    
}
