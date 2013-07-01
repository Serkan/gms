package org.easy.broadjms.config;

public enum MessagingConfig {

    SERVICE_PORT_RANGE("service-port-range"),

    DISCOVERY_PORT_RANGE("discover-port-range");

    private String value;

    private MessagingConfig(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
