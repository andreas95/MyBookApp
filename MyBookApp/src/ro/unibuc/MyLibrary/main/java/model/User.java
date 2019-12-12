package ro.unibuc.MyLibrary.main.java.model;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import ro.unibuc.MyLibrary.main.java.common.Shared;
import ro.unibuc.MyLibrary.main.java.database.UsersDatabase;
import ro.unibuc.MyLibrary.main.java.ui.AlertMessage;

import java.util.Date;

/**
 * Created by antoneandreas on 1/28/17.
 */
public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String oldUser;
    private String password;
    private String type;
    private Date joinDate;
    private Date lastLogin;

    public User() {}

    public User(String firstName, String lastName, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User(String firstName, String lastName, String email, String username, String oldUser, String type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.oldUser = oldUser;
        this.type = type;
    }

    public User(int id, String firstName, String lastName, String email, String username, String password, String type, Date joinDate, Date lastLogin) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.type = type;
        this.joinDate = joinDate;
        this.lastLogin = lastLogin;
    }

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public HBox getAction() {
        HBox box=new HBox(8);
        box.setAlignment(Pos.CENTER);

        Label delete=new Label("Delete");
        delete.getStyleClass().add("hyperlink");
        delete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                    UsersDatabase.deleteUser(getId());
                    Shared.action_delete_user = true;
            }
        });

        Label edit=new Label("Edit");
        edit.getStyleClass().add("hyperlink");
        edit.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
               Shared.action_edit_user = true;
               Shared.edit_user = new User(getFirstName(), getLastName(), getEmail(), getUsername(), getUsername(), getType());
           }
       });

        box.getChildren().addAll(delete,edit);
        return box;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() { return lastName + " " + firstName;}

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getId() { return id; }

    public String getType() {
        return type;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public String getOldUser() { return oldUser; }
}