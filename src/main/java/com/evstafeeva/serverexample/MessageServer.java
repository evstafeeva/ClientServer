package com.evstafeeva.serverexample;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MessageServer {
    private final ServerSocket server;
    private final List<MessageThread> listClient = new ArrayList<>();

    public MessageServer() throws IOException {
        this(5050);
    }

    public MessageServer(int port) throws IOException {
        server = new ServerSocket(port);
        System.out.println("Сервер запущен на локлаьной машине, порт: " + port);
    }

    public void start() throws IOException {
        try {
            while (true) {
                //ждем, пока к нему кто-нибудь подключится
                System.out.println("Ждем подключения кого-то");
                Socket clientSocket = server.accept();
                System.out.println(clientSocket.toString() + "подключился");
                listClient.add(new MessageThread(listClient, clientSocket));
            }
        } finally {
            server.close();
            System.out.println("Работа сервера завершена!");
        }
    }
}
