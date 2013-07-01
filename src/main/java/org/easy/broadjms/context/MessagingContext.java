package org.easy.broadjms.context;

import java.util.HashMap;
import java.util.Map;

import org.easy.broadjms.context.resource.MessageQueue;
import org.easy.broadjms.context.resource.MessageTopic;

public class MessagingContext {

    // key is jndi name
    private Map<String, MessageQueue> queueMap;

    // key is jndi name
    private Map<String, MessageTopic> topicMap;

    private static MessagingContext instance;

    public static MessagingContext getContext() {
        if (instance == null) {
            instance = new MessagingContext();
        }
        return instance;
    }

    private MessagingContext() {
        queueMap = new HashMap<String, MessageQueue>();
        topicMap = new HashMap<String, MessageTopic>();
    }

    protected void addQueue(String jndiName) {
        MessageQueue queue = queueMap.get(jndiName);
        if (queue == null) {
            queue = new MessageQueue();
            queueMap.put(jndiName, queue);
        }
    }

    protected void addTopic(String jndiName) {
        MessageTopic topic = topicMap.get(jndiName);
        if (topic == null) {
            topic = new MessageTopic();
            topicMap.put(jndiName, topic);
        }
    }

    protected MessageQueue getQueue(String jndiName) {
        return queueMap.get(jndiName);
    }

    protected MessageTopic getTopic(String jndiName) {
        return topicMap.get(jndiName);
    }

}
