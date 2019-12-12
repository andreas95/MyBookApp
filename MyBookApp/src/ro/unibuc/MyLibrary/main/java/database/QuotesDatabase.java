package ro.unibuc.MyLibrary.main.java.database;

import ro.unibuc.MyLibrary.main.java.model.Quote;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

/**
 * @author antoneandreas
 */

public class QuotesDatabase {

    public static Quote getQuote() {
        Quote temp = new Quote();
        Date date; // your date
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String SQL = "SELECT * FROM QUOTES WHERE id="+String.valueOf(day);
        Statement myStatement = null;
        try {
            myStatement = ConnectToDatabase.getConnection().createStatement();
            ResultSet myResultSet = myStatement.executeQuery(SQL);
            while (myResultSet.next()) {
                temp.setAuthor(myResultSet.getString("author"));
                temp.setQuote(myResultSet.getString("quote"));
            }
        } catch (SQLException e) {
            return temp;
        }
        return temp;
    }


}