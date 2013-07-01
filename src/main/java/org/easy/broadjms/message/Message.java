package org.easy.broadjms.message;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 4860555362647878097L;

    private Object msg;

    /**
     * 
     * Default constructor.
     */
    public Message() {
    }

    /**
     * @return the msg
     */
    public Object getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(Object msg) {
        this.msg = msg;
    }

}
