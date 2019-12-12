package ro.unibuc.MyLibrary.main.java.controller;

import ro.unibuc.MyLibrary.main.java.common.ScreenController;
import ro.unibuc.MyLibrary.main.java.common.Shared;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import org.controlsfx.glyphfont.Glyph;

/**
 * @author antoneandreas
 */

public class LoginMessageController {

    @FXML
    private Label LabelMessage;

    public void initialize() {
        LabelMessage.setText(Shared.login_message);
        Glyph icon = null;
        switch (Shared.login_message_type) {
            case "error":
                icon = new Glyph("FontAwesome", "EXCLAMATION_TRIANGLE");
                icon.setColor(Color.web("#fbbc05"));
                break;
            case "success":
                icon = new Glyph("FontAwesome", "CHECK_CIRCLE");
                icon.setColor(Color.web("#3fa952"));
                break;
        }
        icon.setFontSize(30);
        LabelMessage.setGraphic(icon);
    }

    @FXML
    public void back() {
        ScreenController.onBack();
    }
}