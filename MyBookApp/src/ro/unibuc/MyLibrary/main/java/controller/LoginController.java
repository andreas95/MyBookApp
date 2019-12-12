package ro.unibuc.MyLibrary.main.java.controller;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import ro.unibuc.MyLibrary.main.java.common.ScreenController;
import ro.unibuc.MyLibrary.main.java.common.StageManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * @author antoneandreas
 */

public class LoginController {

    @FXML
    private HBox HeaderButtons;
    @FXML
    private Label ButtonClose;
    @FXML
    private Label ButtonMinimize;
    @FXML
    private Label ButtonFullScreen;
    @FXML
    private AnchorPane PaneFragment;
    @FXML
    private Label AppName;

    public void initialize() {
        AppName.setText(StageManager.getStage().getTitle());
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        StageManager.getStage().setX((primScreenBounds.getWidth() - 350) / 2);
        StageManager.getStage().setY((primScreenBounds.getHeight() - 400) / 2);
        StageManager.setPane(PaneFragment);
        HeaderButtons.setOnMouseEntered(e -> {
            ButtonClose.setText("\u2022");
            ButtonMinimize.setText("-");
            ButtonFullScreen.setText("+");
        });
        HeaderButtons.setOnMouseExited(e -> {
            ButtonClose.setText("");
            ButtonMinimize.setText("");
            ButtonFullScreen.setText("");
        });
        try {
            ScreenController.setScreen(ScreenController.Screen.SINGIN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void close() {
        System.exit(0);
    }

    @FXML
    public void minimize() {
        StageManager.getStage().setIconified(true);
    }

    @FXML
    public void full_screen() {
        StageManager.getStage().setFullScreen(true);
    }
}