package ru.inno.inno20;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.inno.inno20.JmsProvider.SUBJECT_TYPE;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import static ru.inno.inno20.JmsProvider.CURRENT_SUBJ_TYPE;

public class ExampleSyncMessageReceiver extends Thread {
    private final Logger logger = LoggerFactory.getLogger(ExampleSyncMessageReceiver.class);

    private final Connection      con;
    private final MessageConsumer consumer;

    ExampleSyncMessageReceiver() throws JMSException {
        super("ExampleSyncMessageReceiver");
        this.setDaemon(true);

        ConnectionFactory factory = JmsProvider.getConnectionFactory();
        this.con = factory.createConnection();
        con.start();
        Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination dest = JmsProvider.getDestination(session);
        this.consumer = session.createConsumer(dest);
    }

    @Override
    public void run() {
        try {
            int i = 0;
            while (i < 25) {

                final Message message = consumer.receive();
                if (message instanceof TextMessage) {
                    TextMessage tm = (TextMessage) message;
                    logger.info("Message received: {}", tm.getText());
                }
                Thread.sleep(1000);
                logger.info("Я жду!");
                i++;
            }
            con.close();
        } catch (JMSException | InterruptedException e) {
            logger.error("ERROR!", e);
        }
    }

}