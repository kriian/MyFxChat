package ru.hehnev.chat;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class EchoClient {
    private final Logger logger = Logger.getLogger(EchoClient.class.getName());
    private final static String SERVER_IP = "127.0.0.1";
    private final static int SERVER_PORT = 8189;

    @FXML
    private TextArea textArea;

    @FXML
    private TextField textField;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public EchoClient() {
        try {
            openConnection();
        }catch (IOException e) {
            logger.severe("ERROR: ".concat(e.getMessage()));
        }
    }

    private void openConnection() throws IOException {
        socket = new Socket(SERVER_IP, SERVER_PORT);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        new Thread(() -> {
            try {
                while (true) {
                    var msg = in.readUTF();
                    logger.info(msg);
                    if ("/and".equals(msg)) {
                        break;
                    }
                    textArea.appendText(msg.concat(System.lineSeparator()));
                }
                closeConnection();
            } catch (IOException e) {
                logger.severe("ERROR: ".concat(e.getMessage()));
                closeConnection();
            }
        }).start();
    }

    @FXML
    protected void onActionField() {
        sendMessage();
    }

    private void sendMessage() {
        try {
            out.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            logger.severe("ERROR: ".concat(e.getMessage()));
            closeConnection();
        }
    }

    private void closeConnection() {
        logger.info("Closing connection...");
        try {
            in.close();
        } catch (IOException e) {
            logger.severe("ERROR: ".concat(e.getMessage()));
        }
        try {
            out.close();
        } catch (IOException e) {
            logger.severe("ERROR: ".concat(e.getMessage()));
        }
        try {
            socket.close();
        } catch (IOException e) {
            logger.severe("ERROR: ".concat(e.getMessage()));
        }
        System.exit(0);
    }
}