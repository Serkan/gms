package org.easy.broadjms.context.resource;

import org.easy.broadjms.context.persistence.Persistable;
import org.easy.broadjms.util.Cleanable;

public interface JmsResource extends Persistable, Cleanable {

    String getName();

}
