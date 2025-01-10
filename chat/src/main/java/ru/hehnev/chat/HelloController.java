package ru.hehnev.chat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private TextArea textArea;

    @FXML
    private TextField textField;

    @FXML
    protected void onActionField() {
        getTextFieldInArea();
    }

    private void getTextFieldInArea() {
        textArea.appendText(textField.getText() + System.lineSeparator());
        textField.clear();
        textField.requestFocus();
    }
}