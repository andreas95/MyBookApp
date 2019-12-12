package ro.unibuc.MyLibrary.main.java.controller;

import ro.unibuc.MyLibrary.main.java.common.ScreenController;
import ro.unibuc.MyLibrary.main.java.common.Shared;
import ro.unibuc.MyLibrary.main.java.database.UsersDatabase;
import ro.unibuc.MyLibrary.main.java.utils.SendEmail;
import ro.unibuc.MyLibrary.main.java.utils.ValidateEmail;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.glyphfont.Glyph;

import java.io.IOException;

/**
 * @author antoneandreas
 */

public class RecoverController {

    @FXML
    CustomTextField FieldEmail;

    public void initialize() {
        FieldEmail.setFocusTraversable(false);
        FieldEmail.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.ENTER) {
                    recover();
                }
            }
        });
    }

    @FXML
    public void recover() {
        if (!ValidateEmail.validate(FieldEmail.getText())) {
            if (!ValidateEmail.validate(FieldEmail.getText()) && FieldEmail.getRight() == null) {
                Glyph alert = new Glyph("FontAwesome", "EXCLAMATION_CIRCLE");
                alert.color(Color.web("#dd3e3e"));
                alert.size(20);
                FieldEmail.setRight(alert);
            }
        } else {
            String password = UsersDatabase.recover(FieldEmail.getText());
            if (!password.equals("")) {
                Task task=new Task() {
                    @Override
                    protected Object call() throws Exception {
                Shared.login_message = "Your new password was sent via email.";
                Shared.login_message_type = "success";
                SendEmail.send(FieldEmail.getText(), "MyBookApp - Recover your password",
                        "Someone, hopefully you, requested that the password for the account" +
                                " associated with this email address.\n\nYour new password is: "+password+"\n\n" +
                                "Please do not reply.");
                        return null;
                    }
                };
                new Thread(task).start();
            } else {
                Shared.login_message = "The email address was not found in the database.";
                Shared.login_message_type = "error";
            }
            try {
                ScreenController.setScreen(ScreenController.Screen.LOGIN_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void singin() {
        try {
            ScreenController.setScreen(ScreenController.Screen.SINGIN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void signup() {
        try {
            ScreenController.setScreen(ScreenController.Screen.SIGNUP);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}