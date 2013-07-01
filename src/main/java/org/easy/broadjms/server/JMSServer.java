package org.easy.broadjms.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 * 
 */
public class JMSServer implements Runnable {

    public void run() {
        startService();
    }

    private void startService() {
        ServerSocket socket;
        try {
            socket = new ServerSocket(8989);
            while (true) {
                Socket client = socket.accept();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
