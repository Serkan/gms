package org.easy.broadjms.context.event;

public class MessagingEvent {

    private String source;

    private MessagingEventType type;

    /**
     * 
     * Default constructor.
     * 
     * @param source
     * @param type
     */
    public MessagingEvent(String source, MessagingEventType type) {
        this.source = source;
        this.type = type;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return the type
     */
    public MessagingEventType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(MessagingEventType type) {
        this.type = type;
    }

}
