package ro.unibuc.MyLibrary.main.java.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

import java.util.Optional;

import static javafx.scene.control.Alert.AlertType;

/**
 * @author antoneandreas
 */

public class AlertMessage {

    public AlertMessage(String title, String content, AlertType type) {
        Alert alert = new Alert(type,content);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(title);
        alert.showAndWait();
    }
}