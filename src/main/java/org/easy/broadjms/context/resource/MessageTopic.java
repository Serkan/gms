package org.easy.broadjms.context.resource;

import org.easy.broadjms.message.Message;

public class MessageTopic extends QueueableResource<Message> {

    public void sendMsg(Message msg) {
        addItem(msg);
    }

    public Message getMsg() {
        return peekItem();
    }

    public void destroyMsg() {
        pollItem();
    }

    @Override
    public void persist() {
        // file or jdbc persistence
    }

}
