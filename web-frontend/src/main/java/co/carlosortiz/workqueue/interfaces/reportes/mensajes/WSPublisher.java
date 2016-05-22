/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.interfaces.reportes.mensajes;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 *
 * @author Carlos
 */
@Component
public class WSPublisher {
    @SendTo("/topic/greetings")
    public String publish(String msg) {
        return msg;
    }
}
