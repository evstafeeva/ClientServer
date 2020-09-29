package com.evstafeeva.serverexample;

import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) {
        try {
            MessageServer messageServer = new MessageServer();
            messageServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
