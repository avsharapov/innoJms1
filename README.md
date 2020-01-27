# JMS1 example

**Embedded**: просто запустить Main метод.

**Not embedded**: Запустить внешний сервер ActiveMQ, заменить в классе JmsProvider поле url на значение ActiveMQConnection.DEFAULT_BROKER_URL и запустить Main метод.

---
Для смены режима QUEUE/TOPIC требуется менять значение поля CURRENT_SUBJ_TYPE в классе JmsProvider. 