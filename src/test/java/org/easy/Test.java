package org.easy;

import java.net.DatagramSocket;
import java.net.SocketException;

public class Test {

    public static void main(String[] args) throws SocketException {
        DatagramSocket s = new DatagramSocket(5555);
        System.out.println("ilk");
        DatagramSocket sa = new DatagramSocket(5555);

    }
}
