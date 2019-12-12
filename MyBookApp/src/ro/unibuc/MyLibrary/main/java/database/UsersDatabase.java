package ro.unibuc.MyLibrary.main.java.database;

import ro.unibuc.MyLibrary.main.java.common.Shared;
import ro.unibuc.MyLibrary.main.java.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * @author antoneandreas
 */

public class UsersDatabase {

    public UsersDatabase() {}

    public static boolean addUser(User user) {
        String SQL = "SELECT * FROM users;";
        PreparedStatement myStatement2 = null;
        Calendar calendar = Calendar.getInstance();
        java.sql.Date ourJavaDateObject = new java.sql.Date(calendar.getTime().getTime());
        try {
            Statement myStatement=ConnectToDatabase.getConnection().createStatement();
            ResultSet myResultSet=myStatement.executeQuery(SQL);
            if (myResultSet.next()) {
                SQL = "INSERT INTO users (username, email, password, last_name, first_name, join_date) VALUES (?,?,MD5(?),?,?,?);";
                myStatement2 = ConnectToDatabase.getConnection().prepareStatement(SQL);
                myStatement2.setString(1, user.getUsername());
                myStatement2.setString(2, user.getEmail());
                myStatement2.setString(3, user.getPassword());
                myStatement2.setString(4, user.getLastName());
                myStatement2.setString(5, user.getFirstName());
                myStatement2.setDate(6, ourJavaDateObject);
            } else {
                SQL = "INSERT INTO users (username, email, password, last_name, first_name, type, join_date) VALUES (?,?,MD5(?),?,?,?,?);";
                myStatement2 = ConnectToDatabase.getConnection().prepareStatement(SQL);
                myStatement2.setString(1, user.getUsername());
                myStatement2.setString(2, user.getEmail());
                myStatement2.setString(3, user.getPassword());
                myStatement2.setString(4, user.getLastName());
                myStatement2.setString(5, user.getFirstName());
                myStatement2.setString(6, "admin");
                myStatement2.setDate(7, ourJavaDateObject);
            }
            if (myStatement2.executeUpdate() != 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static String recover(String email) {
        String SQL = "SELECT * FROM users where email='"+email+"';";
        try {
            Statement myStatement = ConnectToDatabase.getConnection().createStatement();
            ResultSet myResultSet = myStatement.executeQuery(SQL);
            if (myResultSet.next()) {
                String password = Long.toHexString(Double.doubleToLongBits(Math.random()));
                SQL = "UPDATE users SET password=MD5('"+password+"')"+" WHERE email='"+email+"';";
                myStatement = ConnectToDatabase.getConnection().createStatement();
                myStatement.executeUpdate(SQL);
                return password;
            } else {
                return "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean login(String username, String password) {
        String SQL = "SELECT * FROM users WHERE username=? AND password=MD5(?);";
        try {
            PreparedStatement myStatement = ConnectToDatabase.getConnection().prepareStatement(SQL);
            myStatement.setString(1, username);
            myStatement.setString(2, password);
            ResultSet myResultSet = myStatement.executeQuery();
            if (myResultSet.next()) {
                //set user details
                Shared.username = username;
                Shared.firstName = myResultSet.getString("first_name");
                Shared.userType = myResultSet.getString("type");
                Shared.session_status = true;
                //set last login date
                SQL = "UPDATE users set last_login=? where username=?";
                myStatement = ConnectToDatabase.getConnection().prepareStatement(SQL);
                Calendar calendar = Calendar.getInstance();
                java.sql.Date ourJavaDateObject = new java.sql.Date(calendar.getTime().getTime());
                myStatement.setDate(1, ourJavaDateObject);
                myStatement.setString(2, username);
                myStatement.executeUpdate();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public static User getUser(String username) {
        String SQL = "SELECT * FROM users WHERE username=?;";
        try {
            PreparedStatement myStatement = ConnectToDatabase.getConnection().prepareStatement(SQL);
            myStatement.setString(1, username);
            ResultSet myResultSet = myStatement.executeQuery();
            if (myResultSet.next()) {
                return new User(myResultSet.getInt("ID"), myResultSet.getString("first_name"), myResultSet.getString("last_name"),
                        myResultSet.getString("email"), myResultSet.getString("username"),
                        myResultSet.getString("password"), myResultSet.getString("type"),
                        myResultSet.getDate("join_date"), myResultSet.getDate("last_login"));
            } else {
                return new User();
            }
        } catch (SQLException e) {
            return new User();
        }
    }

    public static List<User> getAllUsers() {
        List<User> temp = new ArrayList<User>();
        String SQL = "SELECT * FROM users;";
        Statement myStatement = null;
        try {
            myStatement = ConnectToDatabase.getConnection().createStatement();
            ResultSet myResultSet = myStatement.executeQuery(SQL);
            while (myResultSet.next()) {
                temp.add(new User(myResultSet.getInt("ID"), myResultSet.getString("first_name"), myResultSet.getString("last_name"),
                        myResultSet.getString("email"), myResultSet.getString("username"),
                        myResultSet.getString("password"), myResultSet.getString("type"),
                        myResultSet.getDate("join_date"), myResultSet.getDate("last_login")));
            }
        } catch (SQLException e) {
           return temp;
        }
        return temp;
    }

    public static List<User> search(String query) {
        List<User> temp = new ArrayList<User>();
        String SQL = "SELECT * FROM USERS WHERE USERNAME like '%"+query+"%' or EMAIL like '%"+query+"%' or LAST_NAME like '%"+query+"%' or FIRST_NAME like '%"+query+"%'";
        try {
            Statement myStatement = ConnectToDatabase.getConnection().createStatement();
            ResultSet myResultSet = myStatement.executeQuery(SQL);
            while (myResultSet.next()) {
                temp.add(new User(myResultSet.getInt("ID"), myResultSet.getString("first_name"), myResultSet.getString("last_name"),
                        myResultSet.getString("email"), myResultSet.getString("username"),
                        myResultSet.getString("password"), myResultSet.getString("type"),
                        myResultSet.getDate("join_date"), myResultSet.getDate("last_login")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public static int getUserId(String username) {
        String SQL = "SELECT ID FROM USERS WHERE USERNAME=?;";
        try {
            PreparedStatement myStatement = ConnectToDatabase.getConnection().prepareStatement(SQL);
            myStatement.setString(1, username);
            ResultSet myResultSet = myStatement.executeQuery();
            if (myResultSet.next()) {
                return myResultSet.getInt("ID");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            return -1;
        }
    }

    public static String getUserByID(int id) {
        String SQL = "SELECT USERNAME FROM USERS WHERE ID=?;";
        try {
            PreparedStatement myStatement = ConnectToDatabase.getConnection().prepareStatement(SQL);
            myStatement.setInt(1, id);
            ResultSet myResultSet = myStatement.executeQuery();
            if (myResultSet.next()) {
                return myResultSet.getString("USERNAME");
            } else {
                return "";
            }
        } catch (SQLException e) {
            return "";
        }
    }

    public static boolean editProfile(User user) {
        String SQL = "UPDATE users SET first_name=?, last_name=?, email=? WHERE username=?";
        PreparedStatement myStatement = null;
        try {
            myStatement = ConnectToDatabase.getConnection().prepareStatement(SQL);
            myStatement.setString(1, user.getFirstName());
            myStatement.setString(2, user.getLastName());
            myStatement.setString(3, user.getEmail());
            myStatement.setString(4, Shared.username);
            if (myStatement.executeUpdate() == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean editProfile2(User user) {
        String SQL = "UPDATE users SET first_name=?, last_name=?, email=?, username=?, type=? WHERE username=?";
        PreparedStatement myStatement = null;
        try {
            myStatement = ConnectToDatabase.getConnection().prepareStatement(SQL);
            myStatement.setString(1, user.getFirstName());
            myStatement.setString(2, user.getLastName());
            myStatement.setString(3, user.getEmail());
            myStatement.setString(4, user.getUsername());
            myStatement.setString(5, user.getType());
            myStatement.setString(6, user.getOldUser());
            if (myStatement.executeUpdate() == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean changePassword(String oldPassword, String newPassword) {
        String SQL = "UPDATE users SET password=MD5(?) WHERE username=? and password=MD5(?)";
        PreparedStatement myStatement = null;
        try {
            myStatement = ConnectToDatabase.getConnection().prepareStatement(SQL);
            myStatement.setString(1, newPassword);
            myStatement.setString(2, Shared.username);
            myStatement.setString(3, oldPassword);
            if (myStatement.executeUpdate() == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public static void deleteUser(int id) {
        String SQL="DELETE FROM USERS WHERE ID=?";
        try {
            PreparedStatement myStatement = ConnectToDatabase.getConnection().prepareStatement(SQL);
            myStatement.setInt(1, id);
            myStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}