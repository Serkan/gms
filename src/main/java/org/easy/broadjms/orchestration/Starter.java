package org.easy.broadjms.orchestration;

import org.easy.broadjms.explorer.ExplorerClient;
import org.easy.broadjms.explorer.ExplorerServer;

public class Starter {

    public static void main(String[] args) {
        Thread t1 = new Thread(new ExplorerClient());
        Thread t2 = new Thread(new ExplorerServer());

        t2.start();
        t1.start();
    }

}
