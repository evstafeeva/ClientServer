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
            try {
                //авторизация
                out.println("Представьтесь:");
                name = in.readLine();
                //сообщение о том, что кто-то подключился
                for (MessageThread client : listClient) {
                    if (client == this)
                        continue;
                    client.write(name + " подключился к нашему чату!");
                }
                //реализуем рассылку того, что каждый клиент написал
                while (true) {
                    String message = in.readLine();
                    System.out.println("Сообщение пришло от " + clientSocket.toString() + " " + message);

                    for (MessageThread client : listClient) {
                        if (client == this)
                            continue;
                        client.write("[ " + name + " ]:" + message);
                    }
                }
            }
            finally{
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String string) {
        System.out.print("Сообщение <" + string + "> ушло к " + clientSocket.toString());
        out.println(string);
    }
}
