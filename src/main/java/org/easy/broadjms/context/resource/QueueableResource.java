package org.easy.broadjms.context.resource;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class QueueableResource<T> implements JmsResource {

    private BlockingQueue<T> memory;

    private String name;

    public QueueableResource() {
        memory = new LinkedBlockingQueue<T>();
    }

    public String getName() {
        return name;
    }

    public void addItem(T item) {
        memory.add(item);
    }

    public T pollItem() {
        return memory.poll();
    }

    public T peekItem() {
        return memory.peek();
    }

    @Override
    public void clean() {
        memory.clear();
    }

}
