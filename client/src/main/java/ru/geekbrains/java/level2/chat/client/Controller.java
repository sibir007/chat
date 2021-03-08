package ru.geekbrains.java.level2.chat.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML
    public TextField msgField;
    public TextArea msgArea;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public void send(ActionEvent actionEvent){

        try {
            msgArea.appendText("client -> " + msgField.getText() + "\n");
            out.writeUTF( msgField.getText() + "\n");;

            msgField.clear();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "невозможно отправить сообщение", ButtonType.OK);
            alert.showAndWait();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**
         * создаём соединение
         */
        try {
            socket = new Socket("localhost", 8189);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

//            System.out.println("Клиент пытается подключиться к серверу localhost:8189");

        } catch (IOException e) {
//            throw new RuntimeException("Unable to connect to server localhost:8189");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Unable to connect to server localhost:8189", ButtonType.OK);
            alert.showAndWait();
        }

        /**
         * создаём поток обработки входящих сообщений
         */
        Runnable run = () -> {
            String msg;
            try {
                while (true) {
                    msg = in.readUTF();
                    msgArea.appendText(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        };
        Thread t = new Thread(run);
        t.start();
    }
}
