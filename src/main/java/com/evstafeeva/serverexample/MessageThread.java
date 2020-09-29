package com.evstafeeva.serverexample;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class MessageThread extends Thread {
    private final Socket clientSocket;
    private final BufferedReader in;
    private final PrintWriter out;
    private List<MessageThread> listClient;
    private String name = "123";


    public MessageThread(List<MessageThread> listClient, Socket clientSocket) throws IOException {
        this.listClient = listClient;
        this.clientSocket = clientSocket;
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        this.start();
    }

    @Override
    public void run() {
        try {
            //авторизация
//            out.write("Представьтесь.\n");
//            out.flush();
//            name = in.readLine();
//            //сообщение о том, что кто-то подключился
//            for (MessageThread client : listClient) {
//                client.write("      " + name + " подключился кнашему чату!\n");
//            }
            //реализуем рассылку того, что каждый клиент написал
            while (true) {
                System.out.println("Ждем сообщения от " + clientSocket.toString());
                String message = in.readLine();
                System.out.println("Сообщение пришло от " + clientSocket.toString() + " " + message);

                for (MessageThread client : listClient) {
                    System.out.println("Послали сообщение " + client.clientSocket.toString());
                    client.write("[ " + name + " ]:" + message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String string) {

            System.out.println("Сообщение <" + string + "> ушло к " + clientSocket.toString());
            out.println(string + "\n");
    }
}
