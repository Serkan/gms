package org.easy.broadjms.context;

import org.easy.broadjms.message.Message;
import org.easy.broadjms.message.MessageListener;

public class MessageConsumer extends Thread {

    private MessageListener handler;

    private Message message;

    @Override
    public void run() {
        while (true) {
            if (message != null) {
                handler.handleMessage(message);
                message = null;
            }
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isFree() {
        return message == null;
    }

    /**
     * @param handler the handler to set
     */
    public void setHandler(MessageListener handler) {
        this.handler = handler;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(Message message) {
        this.message = message;
    }

}
