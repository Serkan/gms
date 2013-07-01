package org.easy.broadjms.context.resource;

import org.easy.broadjms.message.Message;

public class MessageQueue extends QueueableResource<Message> {

    public void sendMsg(Message msg) {
        addItem(msg);
    }

    public synchronized Message getMsg() {
        return pollItem();
    }

    @Override
    public void persist() {
        // file or jdbc persistence
    }

}
