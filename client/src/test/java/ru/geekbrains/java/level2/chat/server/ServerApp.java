package ru.geekbrains.java.level2.chat.server;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(8189);
        try {
            System.out.println("Сервер запущен на порту 8189. Ожидаем подключения клиентов ...");
            Socket socket = serverSocket.accept();
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            System.out.println(socket.getInetAddress().getHostAddress());
            System.out.println("Клиент подключился");
//        Thread.sleep(3000);
            String msg;
            while (true) {
                msg = in.readUTF();
                System.out.print(msg);
                out.writeUTF("server -> " + msg);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            serverSocket.close();
        }

    }
}
