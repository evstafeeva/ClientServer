package com.evstafeeva.clientexample;

import javafx.scene.control.TextInputDialog;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class MessageClient {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private BufferedReader inputUser;
    private MessageClientIn messageClientIn;
    private MessageClientOut messageClientOut;


    public MessageClient(String hostName, int port) throws IOException {
        //создали сокет для соединения с сервером
        clientSocket = new Socket(hostName, port);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        inputUser = new BufferedReader(new InputStreamReader(System.in));
    }

    public void start() throws IOException {
        try {
            //регистрация
//            System.out.println(in.readLine());
//            out.write(scanner.nextLine() + "\n");
//            out.flush();
            //запускаем ввод и вывод соощений разными потоками=
            new MessageClientIn().start();
            new MessageClientOut().start();
        } finally {
            in.close();
            out.close();
            clientSocket.close();
        }
    }

    private class MessageClientIn extends Thread {
        @Override
        public void run() {
            String string;
            try {
                while (true) {
                    string = in.readLine();
                    System.out.println(string);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class MessageClientOut extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
//                TextInputDialog dialog = new TextInputDialog();
//                dialog.setTitle("");
//                dialog.setHeaderText("Введите сообщение!");
//                Optional<String> string = dialog.showAndWait();
//                string.ifPresent(message -> {
//                    try {
//                        out.write(message + "\n");
//                        out.flush();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                });
                    String message =  inputUser.readLine();
                    out.println(message + "\n");
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
