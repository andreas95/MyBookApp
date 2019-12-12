package ro.unibuc.MyLibrary.main.java.controller;

import ro.unibuc.MyLibrary.main.java.common.ScreenController;
import ro.unibuc.MyLibrary.main.java.common.Shared;
import ro.unibuc.MyLibrary.main.java.database.UsersDatabase;
import ro.unibuc.MyLibrary.main.java.model.User;
import ro.unibuc.MyLibrary.main.java.utils.ValidateEmail;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.glyphfont.Glyph;

import java.io.IOException;

/**
 * @author antoneandreas
 */

public class SignupController {

    @FXML
    CustomTextField FieldFirstName;
    @FXML
    CustomTextField FieldLastName;
    @FXML
    CustomTextField FieldUsername;
    @FXML
    CustomTextField FieldEmail;
    @FXML
    CustomPasswordField FieldPassword;
    @FXML
    CustomPasswordField FieldConfirmPassword;

    public void initialize() {
        FieldFirstName.setFocusTraversable(false);
        FieldLastName.setFocusTraversable(false);
        FieldUsername.setFocusTraversable(false);
        FieldEmail.setFocusTraversable(false);
        FieldPassword.setFocusTraversable(false);
        FieldConfirmPassword.setFocusTraversable(false);
        FieldFirstName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
                    FieldLastName.requestFocus();
                }
            }
        });
        FieldLastName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
                    FieldEmail.requestFocus();
                }
            }
        });
        FieldEmail.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
                    FieldUsername.requestFocus();
                }
            }
        });
        FieldUsername.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
                    FieldPassword.requestFocus();
                }
            }
        });
        FieldPassword.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
                    FieldConfirmPassword.requestFocus();
                }
            }
        });
        FieldConfirmPassword.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.ENTER) {
                    signup();
                }
            }
        });
    }

    @FXML
    public void signup() {
        if (FieldFirstName.getText().isEmpty() || FieldLastName.getText().isEmpty() ||
                FieldUsername.getText().length()<3 || !ValidateEmail.validate(FieldEmail.getText())
                || FieldPassword.getText().length()<6 || FieldConfirmPassword.getText().length()<6
                || !FieldPassword.getText().equals(FieldConfirmPassword.getText())) {

            if (FieldFirstName.getText().isEmpty() && FieldFirstName.getRight() == null) {
                Glyph alert = new Glyph("FontAwesome", "EXCLAMATION_CIRCLE");
                alert.color(Color.web("#dd3e3e"));
                alert.size(20);
                FieldFirstName.setRight(alert);
            } else if (FieldFirstName.getText().isEmpty() && FieldFirstName.getRight() != null) {
                FieldFirstName.getRight().setVisible(true);
            } else if (FieldFirstName.getRight() != null) {
                FieldFirstName.getRight().setVisible(false);
            }

            if (FieldLastName.getText().isEmpty() && FieldLastName.getRight() == null) {
                Glyph alert = new Glyph("FontAwesome", "EXCLAMATION_CIRCLE");
                alert.color(Color.web("#dd3e3e"));
                alert.size(20);
                FieldLastName.setRight(alert);
            } else if (FieldLastName.getText().isEmpty() && FieldLastName.getRight() != null) {
                FieldLastName.getRight().setVisible(true);
            } else if (FieldLastName.getRight() != null) {
                FieldLastName.getRight().setVisible(false);
            }

            if (FieldUsername.getText().length()<3 && FieldUsername.getRight() == null) {
                Glyph alert = new Glyph("FontAwesome", "EXCLAMATION_CIRCLE");
                alert.color(Color.web("#dd3e3e"));
                alert.size(20);
                FieldUsername.setRight(alert);
            } else if (FieldUsername.getText().length()<3 && FieldUsername.getRight() != null) {
                FieldUsername.getRight().setVisible(true);
            } else if (FieldUsername.getRight() != null) {
                FieldUsername.getRight().setVisible(false);
            }

            if (!ValidateEmail.validate(FieldEmail.getText()) && FieldEmail.getRight() == null) {
                Glyph alert = new Glyph("FontAwesome", "EXCLAMATION_CIRCLE");
                alert.color(Color.web("#dd3e3e"));
                alert.size(20);
                FieldEmail.setRight(alert);
            } else if (!ValidateEmail.validate(FieldEmail.getText()) && FieldEmail.getRight() != null) {
                FieldEmail.getRight().setVisible(true);
            } else if (FieldEmail.getRight() != null) {
                FieldEmail.getRight().setVisible(false);
            }

            if ((!FieldPassword.getText().equals(FieldConfirmPassword.getText()) &&
                    FieldConfirmPassword.getRight() == null &&
                    FieldPassword.getRight() == null) || (FieldPassword.getText().length()<6 &&
                    FieldConfirmPassword.getRight() == null &&
                    FieldPassword.getRight() == null)) {
                Glyph alert = new Glyph("FontAwesome", "EXCLAMATION_CIRCLE");
                alert.color(Color.web("#dd3e3e"));
                alert.size(20);
                FieldPassword.setRight(alert);
                alert = new Glyph("FontAwesome", "EXCLAMATION_CIRCLE");
                alert.color(Color.web("#dd3e3e"));
                alert.size(20);
                FieldConfirmPassword.setRight(alert);
            } else if (!FieldPassword.getText().equals(FieldConfirmPassword.getText()) &&
                    FieldConfirmPassword.getRight() != null &&
                    FieldPassword.getRight() != null || FieldPassword.getText().length()<6) {
                FieldPassword.getRight().setVisible(true);
                FieldConfirmPassword.getRight().setVisible(true);
            } else if (FieldConfirmPassword.getRight() != null &&
                    FieldPassword.getRight() != null) {
                FieldPassword.getRight().setVisible(false);
                FieldConfirmPassword.getRight().setVisible(false);
            }

        } else {
            if (UsersDatabase.addUser(new User(FieldFirstName.getText(), FieldLastName.getText(), FieldEmail.getText(),
                    FieldUsername.getText(), FieldPassword.getText()))) {
                Shared.login_message = "Your account has been created successfully.";
                Shared.login_message_type = "success";
            } else {
                Shared.login_message = "Username or email already exists.";
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
    public void recover() {
        try {
            ScreenController.setScreen(ScreenController.Screen.RECOVER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}