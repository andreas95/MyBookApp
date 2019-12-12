package ro.unibuc.MyLibrary.main.java.controller;

import ro.unibuc.MyLibrary.main.java.common.ScreenController;
import ro.unibuc.MyLibrary.main.java.common.Shared;
import ro.unibuc.MyLibrary.main.java.common.StageManager;
import ro.unibuc.MyLibrary.main.java.database.UsersDatabase;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.glyphfont.Glyph;
import java.io.IOException;

/**
 * @author antoneandreas
 */

public class SigninController {

    private Screen screen = Screen.getPrimary();
    private Rectangle2D windows = screen.getVisualBounds();
    @FXML
    private CustomTextField Username;
    @FXML
    private CustomPasswordField Password;

    public void initialize() {
        Username.setFocusTraversable(false);
        Password.setFocusTraversable(false);
        Username.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
                    Password.requestFocus();
                }
            }
        });
        Password.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.ENTER) {
                    login();
                }
            }
        });
    }

    @FXML
    public void login() {
        if (Username.getText().length() <3 || Password.getText().length()<6) {
            if (Username.getText().length() < 3 && Username.getRight() == null) {
                Glyph alert = new Glyph("FontAwesome", "EXCLAMATION_CIRCLE");
                alert.color(Color.web("#dd3e3e"));
                alert.size(20);
                Username.setRight(alert);
            } else if (Username.getText().length() < 3 && Username.getRight() != null) {
                Username.getRight().setVisible(true);
            } else if (Username.getRight() != null) {
                Username.getRight().setVisible(false);
            }
            if (Password.getText().length()<6 && Password.getRight() == null) {
                Glyph alert = new Glyph("FontAwesome", "EXCLAMATION_CIRCLE");
                alert.color(Color.web("#dd3e3e"));
                alert.size(20);
                Password.setRight(alert);
            } else if (Password.getText().length()<6 && Password.getRight() != null) {
                Password.getRight().setVisible(true);
            } else if (Password.getRight() != null) {
                Password.getRight().setVisible(false);
            }
        } else {
            if (UsersDatabase.login(Username.getText(), Password.getText())) {
                try {
                    ScreenController.setScreen(ScreenController.Screen.APPLICATION);
                    //set application sizes
                    StageManager.getStage().setX(windows.getMinX());
                    StageManager.getStage().setY(windows.getMinY());
                    StageManager.getStage().setWidth(windows.getWidth());
                    StageManager.getStage().setHeight(windows.getHeight());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Shared.login_message = "Username or password are incorrect.";
                Shared.login_message_type = "error";
                try {
                    ScreenController.setScreen(ScreenController.Screen.LOGIN_MESSAGE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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

    @FXML
    public void recover() {
        try {
            ScreenController.setScreen(ScreenController.Screen.RECOVER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}