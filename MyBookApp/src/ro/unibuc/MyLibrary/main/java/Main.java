package ro.unibuc.MyLibrary.main.java;

import ro.unibuc.MyLibrary.main.java.common.Shared;
import ro.unibuc.MyLibrary.main.java.common.StageManager;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ro.unibuc.MyLibrary.main.java.database.ConnectToDatabase;

/**
 * @author antoneandreas
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // set application version
        Shared.application_version="1.0";

        //set application name
        primaryStage.setTitle("MyBookApp");

        //set undecorated and transparent stage
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        //connect database
        new ConnectToDatabase();

        //set stage
        new StageManager(primaryStage);

        //set application icon
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/ro/unibuc/MyLibrary/main/resources/images/icons/app.png")));

        //set FTP server
        Shared.ftp_server = "http://mybookapp.byethost9.com/";

    }

    public static void main(String[] args) {
        launch(args);
    }

}