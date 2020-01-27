package ru.inno.inno20;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

class JmsProvider {
    private static final String url     = ActiveMQConnection.DEFAULT_BROKER_URL; //"vm://localhost";
    static               String SUBJECT = "EXAMPLE_INNO";
    static         SUBJECT_TYPE CURRENT_SUBJ_TYPE = SUBJECT_TYPE.TOPIC;

    static ConnectionFactory getConnectionFactory() {
        return new ActiveMQConnectionFactory(url);
    }

    static Destination getDestination(Session session) throws JMSException {
        return (CURRENT_SUBJ_TYPE.equals(SUBJECT_TYPE.QUEUE)) ?
                session.createQueue(SUBJECT + "_" + CURRENT_SUBJ_TYPE) :
                session.createTopic(SUBJECT + "_" + CURRENT_SUBJ_TYPE) ;
    }

    private JmsProvider() {
    }

    enum SUBJECT_TYPE {
        TOPIC,
        QUEUE
    }
}
