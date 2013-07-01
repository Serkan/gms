package org.easy.broadjms.context;

import java.util.Set;

import org.easy.broadjms.message.MessageListener;
import org.reflections.Reflections;

public class ClasspathScanner {

    public static Set<Class<? extends MessageListener>> scanClasspath() {
        Reflections reflections = new Reflections("");
        Set<Class<? extends MessageListener>> listenerSet = reflections
                .getSubTypesOf(MessageListener.class);
        return listenerSet;
    }

}
