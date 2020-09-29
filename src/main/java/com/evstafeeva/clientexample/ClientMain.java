package com.evstafeeva.clientexample;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) {
        try {
            MessageClient messageClient = new MessageClient("localhost", 5050);
            messageClient.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
