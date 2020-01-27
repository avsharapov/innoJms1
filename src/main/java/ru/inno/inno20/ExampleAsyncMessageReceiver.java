package ru.inno.inno20;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.inno.inno20.JmsProvider.SUBJECT_TYPE;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import static ru.inno.inno20.JmsProvider.CURRENT_SUBJ_TYPE;

public class ExampleAsyncMessageReceiver implements MessageListener {
    private final Logger logger = LoggerFactory.getLogger(ExampleAsyncMessageReceiver.class);

    private Connection con;

    void startAsyncReceiver() throws JMSException {
        ConnectionFactory factory = JmsProvider.getConnectionFactory();

        this.con = factory.createConnection();
        con.start();

        Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination dest = JmsProvider.getDestination(session);
        session.createConsumer(dest).setMessageListener(this);
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage tm = (TextMessage) message;
            try {
                logger.info("Message received: {}", tm.getText());
            } catch (JMSException e) {
                logger.error("ERROR!, e");
            }
        }
    }

    void destroy() throws JMSException {
        con.close();
    }
}
