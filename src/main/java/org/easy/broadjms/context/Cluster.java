package org.easy.broadjms.context;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cluster {

    private static Logger logger = LoggerFactory.getLogger(Cluster.class);

    private static Cluster instance;

    private Map<String, Long> nodeMap;

    private Object lock;

    private Cluster() {
        lock = new Object();
        nodeMap = new HashMap<String, Long>();
    }

    public static Cluster getInstance() {
        if (instance == null) {
            instance = new Cluster();
        }
        return instance;
    }

    public void checkIn(InetAddress nodeAddress, int nodePort) {
        logger.info(nodeAddress + "/" + nodePort + " just checked in.");
        long current = System.currentTimeMillis();
        String key = nodeAddress + "/" + nodePort;
        synchronized (lock) {
            Long lastCheckIn = nodeMap.get(key);
            if (lastCheckIn != null) {
                nodeMap.remove(key);
            }
            nodeMap.put(key, current);
        }
    }
}
