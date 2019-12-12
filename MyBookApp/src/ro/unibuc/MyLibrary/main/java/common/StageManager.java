package ro.unibuc.MyLibrary.main.java.common;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class StageManager {
    private static Stage stage;
    private static AnchorPane pane;
    private double xOffset = 0;
    private double yOffset = 0;

    public StageManager(Stage stage) throws IOException {
        StageManager.stage=stage;
        Parent root;
        root = FXMLLoader.load(ScreenController.class.getResource("/ro/unibuc/MyLibrary/main/java/view/LoginView.fxml"));
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

        Scene s=new Scene(root);
        s.setFill(Color.TRANSPARENT);
        stage.setScene(s);
        stage.show();
    }

    public static Stage getStage() {return stage;}
    public static void setRoot(Parent root) {StageManager.stage.getScene().setRoot(root);}
    public static void setPane(AnchorPane pane) {StageManager.pane=pane;}
    public static void setPaneFragment(Parent root) {
        StageManager.pane.getChildren().setAll(root);
    }
}