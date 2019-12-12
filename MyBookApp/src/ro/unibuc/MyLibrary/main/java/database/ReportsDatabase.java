package ro.unibuc.MyLibrary.main.java.database;

import ro.unibuc.MyLibrary.main.java.common.Shared;
import ro.unibuc.MyLibrary.main.java.model.Report;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author antoneandreas
 */

public class ReportsDatabase {

    public static boolean addReport(Report rep) {
        PreparedStatement myStatement = null;
        Calendar calendar = Calendar.getInstance();
        java.sql.Date ourJavaDateObject = new java.sql.Date(calendar.getTime().getTime());
        String SQL = "INSERT INTO REPORT (category, description, screenshot, reportDate, status) VALUES (?,?,?,?,?);";
        try {
            myStatement = ConnectToDatabase.getConnection().prepareStatement(SQL);
            myStatement.setString(1, rep.getCategory());
            myStatement.setString(2, rep.getDescription());
            if (rep.getScreenshot() == "N/A") {
                myStatement.setString(3, rep.getScreenshot());
            } else {
                myStatement.setString(3, Shared.ftp_server + "reports/" + rep.getScreenshot());
            }
            myStatement.setDate(4, ourJavaDateObject);
            myStatement.setBoolean(5, rep.getStatus());
            if (myStatement.executeUpdate() != 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static Report getReport() {
        return null;
    }

    public static List<Report> getReports() {
        List <Report> temp = new ArrayList<>();

        return temp;
    }

    public static boolean editReport(Report rep) {
        return false;
    }

    public static boolean deleteReport(Report rep) {
        return false;
    }
}