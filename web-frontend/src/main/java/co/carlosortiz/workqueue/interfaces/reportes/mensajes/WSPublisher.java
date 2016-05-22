/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.interfaces.reportes.mensajes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author Carlos
 */
@Component
public class WSPublisher {

    private SimpMessagingTemplate template;

    @Autowired
    public WSPublisher(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void publish(String msg) {
        this.template.convertAndSend("/topic/greetings", msg);
    }
}
