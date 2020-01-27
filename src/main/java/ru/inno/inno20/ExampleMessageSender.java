package ru.inno.inno20;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.inno.inno20.JmsProvider.SUBJECT_TYPE;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import static ru.inno.inno20.JmsProvider.CURRENT_SUBJ_TYPE;

class ExampleMessageSender {
    private final Logger logger = LoggerFactory.getLogger(ExampleMessageSender.class);

    private final MessageProducer producer;
    private final Session         session;
    private final Connection      con;

    ExampleMessageSender() throws JMSException {
        ConnectionFactory factory = JmsProvider.getConnectionFactory();
        this.con = factory.createConnection();
        con.start();

        this.session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination dest = JmsProvider.getDestination(session);
        this.producer = session.createProducer(dest);
    }

    void sendMessage(String message) throws JMSException {
        logger.info("Sending message: {}", message);
        TextMessage textMessage = session.createTextMessage(message);
        producer.send(textMessage);
    }

    void destroy() throws JMSException {
        con.close();
    }
}