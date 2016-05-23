/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.interfaces.reportes.modelo;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class Report {
    private String id;
    private String code;
    private String user;
    private List<ReportParam> params;

    public Report( String id,String code, String user, List<ReportParam> params) {
        this.code = code;
        this.id = id;
        this.user = user;
        this.params = params;
    }

    public Report() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    

    public String getCode() {
        return code;
    }

   

    public String getUser() {
        return user;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ReportParam> getParams() {
        return params;
    }

  

    public void setUser(String user) {
        this.user = user;
    }
    
    
    
    
}
