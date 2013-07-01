package org.easy.broadjms.context.resource;

import org.easy.broadjms.config.ResourceType;

public @interface ListenerConfig {

    String name();

    ResourceType type();

}
