package org.easy.broadjms.explorer;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.List;

import org.easy.broadjms.config.ConfigurationManager;
import org.easy.broadjms.config.MessagingConfig;
import org.easy.broadjms.context.Cluster;
import org.easy.broadjms.util.BaseConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExplorerServer implements Runnable {

    public static int RUNNING_PORT;

    private static Logger logger = LoggerFactory
            .getLogger(ExplorerServer.class);

    public void collect() throws IOException {
        DatagramSocket socket = selectPort();
        while (true) {
            byte[] content = new byte[BaseConstants.HELLOJMS_LENGTH];
            DatagramPacket explorerPacket = new DatagramPacket(content,
                    content.length);
            logger.debug("New hello message received.");
            socket.receive(explorerPacket);
            InetAddress nodeAddress = explorerPacket.getAddress();
            int nodePort = extractPort(content);
            Cluster.getInstance().checkIn(nodeAddress, nodePort);
        }
    }

    private DatagramSocket selectPort() throws SocketException {
        DatagramSocket socket;
        List<String> configList = ConfigurationManager.getInstance()
                .getConfigList(MessagingConfig.DISCOVERY_PORT_RANGE.getValue());
        for (String port : configList) {
            int portNumber = Integer.parseInt(port);
            try {
                socket = new DatagramSocket(portNumber);
                RUNNING_PORT = portNumber;
                return socket;
            } catch (BindException e) {
                continue;
            }
        }
        throw new BindException(
                "It couldn't find a unused port to start discovery service");
    }

    private int extractPort(byte[] content) {
        byte[] portInt = new byte[4];
        System.arraycopy(content, 8, portInt, 0, 4);
        return ByteBuffer.wrap(portInt).getInt();
    }

    @Override
    public void run() {
        try {
            logger.info("Explorer server is starting collect node informations.");
            collect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
