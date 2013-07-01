package org.easy.broadjms.explorer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.easy.broadjms.util.BaseConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExplorerClient implements Runnable {

    private static Logger logger = LoggerFactory
            .getLogger(ExplorerClient.class);

    private DatagramPacket discoverPacket;

    public void discover() throws IOException, InterruptedException {
        while (true) {
            // determine broadcast address
            List<InetAddress> broadcastAdressList = getBroadcastAddressList();
            for (InetAddress broadcast : broadcastAdressList) {
                logger.info("Broadcasting discovery message to : " + broadcast);
                // send discover mesagge all broadcast end points
                DatagramSocket udpScoket = new DatagramSocket();
                udpScoket.setBroadcast(true);
                discoverPacket.setAddress(InetAddress
                        .getByName("192.168.1.255"));
                discoverPacket.setPort(5252);
                udpScoket.send(discoverPacket);
            }
            Thread.sleep(5000);
        }
    }

    private List<InetAddress> getBroadcastAddressList() throws SocketException {
        List<InetAddress> broadcastList = new ArrayList<InetAddress>();
        Enumeration<NetworkInterface> interfaces = NetworkInterface
                .getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            if (networkInterface.isLoopback())
                continue; // Don't want to broadcast to the loopback interface
            for (InterfaceAddress interfaceAddress : networkInterface
                    .getInterfaceAddresses()) {
                InetAddress broadcast = interfaceAddress.getBroadcast();
                if (broadcast == null)
                    continue;
                broadcastList.add(broadcast);
            }
        }
        return broadcastList;
    }

    private void initDiscoverPacket() {
        String helloMsg = "HELLOJMS";
        byte[] helloBytes = helloMsg.getBytes();
        byte[] portInt = ByteBuffer.allocate(4)
                .putInt(ExplorerServer.RUNNING_PORT).array();
        byte[] discoverMsg = new byte[BaseConstants.HELLOJMS_LENGTH];
        int index = 0;
        System.arraycopy(helloBytes, 0, discoverMsg, index, helloBytes.length);
        index += helloBytes.length;
        System.arraycopy(portInt, 0, discoverMsg, index, 4);
        discoverPacket = new DatagramPacket(discoverMsg, discoverMsg.length);
    }

    public void run() {
        try {
            logger.info("Explorer service starting to discover cluster nodes.");
            initDiscoverPacket();
            discover();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
