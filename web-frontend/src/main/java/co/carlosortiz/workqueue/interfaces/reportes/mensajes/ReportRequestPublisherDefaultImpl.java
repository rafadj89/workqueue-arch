/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.interfaces.reportes.mensajes;

import co.carlosortiz.workqueue.interfaces.reportes.modelo.Report;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

/**
 *
 * @author Carlos
 */
@Component
public class ReportRequestPublisherDefaultImpl implements ReportRequestPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportRequestPublisherDefaultImpl.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${work.queue.output}")
    private String queueOut;
            
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void publish(final Report report) {
        LOGGER.info("Start sending message...!");
        MessageCreator messageCreator = new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                String textMessage = "{\"reportid\": \"" 
                        + report.getId()+ "\"}";
               
                try {
                    textMessage = objectMapper.writeValueAsString(report);
                } catch (JsonProcessingException ex) {
                    LOGGER.error("Error convirtiendo status del reporte a JSON");
                }
                return session.createTextMessage(textMessage);
            }
        };
        LOGGER.info("Sending message.");
        jmsTemplate.send(queueOut, messageCreator);
    }
}
