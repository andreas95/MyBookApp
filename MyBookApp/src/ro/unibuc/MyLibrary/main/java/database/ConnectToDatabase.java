package ro.unibuc.MyLibrary.main.java.database;

import ro.unibuc.MyLibrary.main.java.ui.AlertMessage;
import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by antoneandreas on 1/28/17.
 */

public class ConnectToDatabase {

    private static Connection myConnection;
    private String url="jdbc:mysql://localhost:3306/mybookapp?useSSL=false";
    private String user="root";
    private String pass="1234";

    public ConnectToDatabase() {
        try {
            ConnectToDatabase.myConnection= DriverManager.getConnection(url,user,pass);
        } catch (SQLException e) {
            new AlertMessage("Error","The server could not be contacted. Please verify your connection.", Alert.AlertType.ERROR);
            System.exit(-1);
        }
    }

    public static Connection getConnection() {
        return myConnection;
    }
}