package ru.inno.inno20;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        final ExampleMessageSender sender = new ExampleMessageSender();

        final ExampleAsyncMessageReceiver receiverAsync = new ExampleAsyncMessageReceiver();
        receiverAsync.startAsyncReceiver();

        final ExampleSyncMessageReceiver receiverSync = new ExampleSyncMessageReceiver();
        receiverSync.start();

        for (int i = 1; i <= 25; i++) {
            String m = "Hello world! " + i;
            logger.info("Send message {}", m);
            sender.sendMessage(m);
            Thread.sleep(3000);
        }

        sender.destroy();
        receiverAsync.destroy();
    }
}
