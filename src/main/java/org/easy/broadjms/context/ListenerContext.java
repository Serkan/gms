package org.easy.broadjms.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.easy.broadjms.config.ResourceType;
import org.easy.broadjms.context.resource.ListenerConfig;
import org.easy.broadjms.context.resource.MessageQueue;
import org.easy.broadjms.message.MessageListener;

public class ListenerContext {

    private int POOL_SIZE = 5;

    private List<MessageConsumer> pool;

    private Map<String, Class<? extends MessageListener>> queueHandlers;

    private Map<String, Class<? extends MessageListener>> topicHandlers;

    public void initialize() {
        // init thread pool
        pool = new ArrayList<MessageConsumer>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++) {
            MessageConsumer consumer = new MessageConsumer();
            consumer.start();
            pool.add(consumer);
        }
        // init handler map
        queueHandlers = new HashMap<String, Class<? extends MessageListener>>();
        topicHandlers = new HashMap<String, Class<? extends MessageListener>>();
        Set<Class<? extends MessageListener>> clazzList = ClasspathScanner
                .scanClasspath();
        for (Class<? extends MessageListener> clazz : clazzList) {
            ListenerConfig config = clazz.getAnnotation(ListenerConfig.class);
            String name = config.name();
            if (config.type() == ResourceType.QUEUE) {
                queueHandlers.put(name, clazz);
            } else if (config.type() == ResourceType.TOPIC) {
                topicHandlers.put(name, clazz);
            }
        }
    }

    public void notifyContext(String resourceName, ResourceType type) {
        if (type == ResourceType.QUEUE) {
            notifyHandler(resourceName, queueHandlers);
        } else if (type == ResourceType.TOPIC) {
            notifyHandler(resourceName, topicHandlers);
        }
    }

    private void notifyHandler(String resourceName,
            Map<String, Class<? extends MessageListener>> handlerMap) {
        MessageQueue queue = MessagingContext.getContext().getQueue(
                resourceName);
        MessageConsumer consumer = pickConsumer();
        // get handler
        Class<? extends MessageListener> handlerClazz = handlerMap
                .get(resourceName);
        MessageListener handler = null;
        try {
            handler = handlerClazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        consumer.setHandler(handler);
        consumer.setMessage(queue.getMsg());
        consumer.notify();
    }

    private MessageConsumer pickConsumer() {
        while (true) {
            for (MessageConsumer consumer : pool) {
                if (consumer.isFree()) {
                    return consumer;
                }
            }
        }
    }

}
