package ro.unibuc.MyLibrary.main.java.database;

import ro.unibuc.MyLibrary.main.java.common.Shared;
import ro.unibuc.MyLibrary.main.java.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author antoneandreas
 */

public class BooksDatabase {

    public static boolean addBook(Book book) {
        String SQL = "";
        if (!Shared.userType.equals("user")) {
            SQL = "INSERT INTO BOOKS (title, author, ISBN, pages, cover, category, description, language, link, userID, publication_date, approved) VALUES (?,?,?,?,?,?,?,?,?,?,?, 1);";
        } else {
            SQL = "INSERT INTO BOOKS (title, author, ISBN, pages, cover, category, description, language, link, userID, publication_date) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
        }
        PreparedStatement myStatement = null;
        try{
            Calendar calendar = Calendar.getInstance();
            java.sql.Date ourJavaDateObject = new java.sql.Date(calendar.getTime().getTime());

            myStatement = ConnectToDatabase.getConnection().prepareStatement(SQL);
            myStatement.setString(1, book.getTitle());
            myStatement.setString(2, book.getAuthor());
            myStatement.setString(3, book.getISBN());
            myStatement.setInt(4, book.getPages());
            myStatement.setString(5, Shared.ftp_server + "covers/" + book.getCover());
            myStatement.setString(6, book.getCategory());
            myStatement.setString(7, book.getDescription());
            myStatement.setString(8,book.getLanguage());
            myStatement.setString(9, Shared.ftp_server + "books/" + book.getLink());
            myStatement.setInt(10,book.getUserID());
            myStatement.setDate(11, ourJavaDateObject);

            if (myStatement.executeUpdate() != 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static List<Book> getBooks(int nr, String text) {
        List <Book> temp = new ArrayList<>();
        String SQL = "";
        if (text.trim().length() == 0) {
            SQL = "SELECT * FROM BOOKS WHERE approved=1 order by idBOOK DESC LIMIT "+nr;
        } else {
            SQL = "SELECT * FROM BOOKS WHERE author LIKE '%"+text+"%' or title LIKE '%"+text+"%' and approved=1 order by idBOOK DESC LIMIT "+nr;
        }
        Statement myStatement = null;
        try {
            myStatement = ConnectToDatabase.getConnection().createStatement();
            ResultSet myResultSet = myStatement.executeQuery(SQL);
            while (myResultSet.next()) {
                temp.add(new Book(myResultSet.getInt("idBook"), myResultSet.getString("title"), myResultSet.getString("author"), myResultSet.getString("ISBN"),
                        myResultSet.getInt("pages"), myResultSet.getString("cover"), myResultSet.getString("category"),
                        myResultSet.getString("description"), myResultSet.getString("language"), myResultSet.getString("link"), myResultSet.getInt("userID")));
            }
        } catch (SQLException e) {
            return temp;
        }
        return temp;
    }

    public static List<Book> getBooksByCategory(int nr, String category) {
        List <Book> temp = new ArrayList<>();
        String SQL = "SELECT * FROM BOOKS WHERE approved=1 and category='"+category+"' order by idBOOK DESC LIMIT "+nr;
        Statement myStatement = null;
        try {
            myStatement = ConnectToDatabase.getConnection().createStatement();
            ResultSet myResultSet = myStatement.executeQuery(SQL);
            while (myResultSet.next()) {
                temp.add(new Book(myResultSet.getInt("idBook"), myResultSet.getString("title"), myResultSet.getString("author"), myResultSet.getString("ISBN"),
                        myResultSet.getInt("pages"), myResultSet.getString("cover"), myResultSet.getString("category"),
                        myResultSet.getString("description"), myResultSet.getString("language"), myResultSet.getString("link"), myResultSet.getInt("userID")));
            }
        } catch (SQLException e) {
            return temp;
        }
        return temp;
    }

    public static Book getBook(int bookId) {
        String SQL = "SELECT * FROM BOOKS WHERE approved=1 and idBook=" + bookId;
        Statement myStatement = null;
        try {
            myStatement = ConnectToDatabase.getConnection().createStatement();
            ResultSet myResultSet = myStatement.executeQuery(SQL);
            while (myResultSet.next()) {
                return new Book(myResultSet.getString("title"), myResultSet.getString("author"), myResultSet.getString("ISBN"),
                        myResultSet.getInt("pages"), myResultSet.getString("cover"), myResultSet.getString("category"),
                        myResultSet.getString("description"), myResultSet.getString("language"), myResultSet.getString("link"),
                        myResultSet.getInt("userID"), myResultSet.getInt("views"), myResultSet.getDate("publication_date"));
            }
        } catch (SQLException e) {
            return new Book();
        }
        return new Book();
    }

    public static List<Book> getAllBooks() {
        List<Book> temp = new ArrayList<Book>();
        String SQL = "SELECT * FROM BOOKS;";
        Statement myStatement = null;
        try {
            myStatement = ConnectToDatabase.getConnection().createStatement();
            ResultSet myResultSet = myStatement.executeQuery(SQL);
            while (myResultSet.next()) {
                temp.add(new Book(myResultSet.getInt("idBOOK"), myResultSet.getString("title"), myResultSet.getString("author"), myResultSet.getString("category"),
                        myResultSet.getString("language"), myResultSet.getInt("userID"), (myResultSet.getInt("approved") == 1)?"approved":"pending",
                        myResultSet.getString("ISBN"), myResultSet.getInt("pages"),
                        myResultSet.getString("cover"), myResultSet.getString("link"), myResultSet.getString("description")));
            }
        } catch (SQLException e) {
            return temp;
        }
        return temp;
    }

    public static boolean editBook(Book book) {
        String SQL = "UPDATE BOOKS SET title=?, author=?, ISBN=?, pages=?, category=?, description=?, language=?, cover=?, link=?, approved=? WHERE idBook=?";
        PreparedStatement myStatement = null;
        try {
            myStatement = ConnectToDatabase.getConnection().prepareStatement(SQL);
            myStatement.setString(1, book.getTitle());
            myStatement.setString(2, book.getAuthor());
            myStatement.setString(3, book.getISBN());
            myStatement.setInt(4, book.getPages());
            myStatement.setString(5, book.getCategory());
            myStatement.setString(6, book.getDescription());
            myStatement.setString(7, book.getLanguage());
            myStatement.setString(8, book.getCover());
            myStatement.setString(9, book.getLink());
            myStatement.setInt(10, (book.getStatus().equals("pending"))?0:1);
            myStatement.setInt(11, book.getIdBook());
            if (myStatement.executeUpdate() == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public static List<Book> search(String query) {
        List<Book> temp = new ArrayList<Book>();
        String SQL = "SELECT * FROM BOOKS WHERE title like '%"+query+"%' or author like '%"+query+"%' or category like '%"+query+"%' or language like '%"+query+"%'";
        Statement myStatement = null;
        try {
            myStatement = ConnectToDatabase.getConnection().createStatement();
            ResultSet myResultSet = myStatement.executeQuery(SQL);
            while (myResultSet.next()) {
                temp.add(new Book(myResultSet.getInt("idBOOK"), myResultSet.getString("title"), myResultSet.getString("author"), myResultSet.getString("category"),
                        myResultSet.getString("language"), myResultSet.getInt("userID"), (myResultSet.getInt("approved") == 1)?"approved":"pending",
                        myResultSet.getString("ISBN"), myResultSet.getInt("pages"),
                        myResultSet.getString("cover"), myResultSet.getString("link"), myResultSet.getString("description")));
            }
        } catch (SQLException e) {
            return temp;
        }
        return temp;
    }

    public static void addView(int bookId) {
        String SQL = "UPDATE BOOKS SET views=views+1 WHERE idBook=?";
        PreparedStatement myStatement = null;
        try {
            myStatement = ConnectToDatabase.getConnection().prepareStatement(SQL);
            myStatement.setInt(1, bookId);
            myStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getBooksTitle() {
        List <String> temp = new ArrayList<>();
        String SQL = "SELECT title FROM BOOKS;";
        Statement myStatement = null;
        try {
            myStatement = ConnectToDatabase.getConnection().createStatement();
            ResultSet myResultSet = myStatement.executeQuery(SQL);
            if (myResultSet.next()) {
                temp.add(myResultSet.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public static List<String> getBooksAuthor() {
        List <String> temp = new ArrayList<>();
        String SQL = "SELECT author FROM BOOKS;";
        Statement myStatement = null;
        try {
            myStatement = ConnectToDatabase.getConnection().createStatement();
            ResultSet myResultSet = myStatement.executeQuery(SQL);
            if (myResultSet.next()) {
                temp.add(myResultSet.getString("author"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public static boolean isFavoriteBook() {
        String SQL = "SELECT * FROM FAVORITE_BOOKS WHERE idUser="+ UsersDatabase.getUserId(Shared.username) +" and idBook=" + Shared.openedBook;
        Statement myStatement = null;
        try {
            myStatement = ConnectToDatabase.getConnection().createStatement();
            ResultSet myResultSet = myStatement.executeQuery(SQL);
            if (myResultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    public static void removeFavoriteBook() {
        String SQL = "DELETE FROM FAVORITE_BOOKS WHERE idUser="+ UsersDatabase.getUserId(Shared.username) +" and idBook=" + Shared.openedBook;
        Statement myStatement = null;
        try {
            myStatement = ConnectToDatabase.getConnection().createStatement();
            myStatement.executeUpdate(SQL);
        } catch (SQLException e) {
        }
    }

    public static void setFavoriteBook() {
        String SQL = "INSERT INTO FAVORITE_BOOKS (idUser, idBook, time) VALUES (?,?,?);";
        PreparedStatement myStatement = null;
        try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            myStatement = ConnectToDatabase.getConnection().prepareStatement(SQL);
            myStatement.setInt(1, UsersDatabase.getUserId(Shared.username));
            myStatement.setInt(2, Shared.openedBook);
            myStatement.setTimestamp(3, timestamp);
            myStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Book> getFavouriteBooks(int nr) {
        List <Book> temp = new ArrayList<>();
        String SQL = "SELECT * FROM BOOKS INNER JOIN FAVORITE_BOOKS ON BOOKS.idBOOK=FAVORITE_BOOKS.idBook WHERE FAVORITE_BOOKS.idUser="+UsersDatabase.getUserId(Shared.username)+" and BOOKS.approved=1 order by FAVORITE_BOOKS.id DESC LIMIT "+nr;
        Statement myStatement = null;
        try {
            myStatement = ConnectToDatabase.getConnection().createStatement();
            ResultSet myResultSet = myStatement.executeQuery(SQL);
            while (myResultSet.next()) {
                temp.add(new Book(myResultSet.getInt("idBook"), myResultSet.getString("title"), myResultSet.getString("author"), myResultSet.getString("ISBN"),
                        myResultSet.getInt("pages"), myResultSet.getString("cover"), myResultSet.getString("category"),
                        myResultSet.getString("description"), myResultSet.getString("language"), myResultSet.getString("link"), myResultSet.getInt("userID"), myResultSet.getTimestamp("time")));
            }
        } catch (SQLException e) {
            return temp;
        }
        return temp;
    }

    public static void addToHistory() {
        String SQL = "SELECT * FROM HISTORY WHERE idUser="+UsersDatabase.getUserId(Shared.username)+" and idBook="+Shared.openedBook;
        Statement myStatement1 = null;
        PreparedStatement myStatement = null;
        try {
            myStatement1 = ConnectToDatabase.getConnection().createStatement();
            if (!myStatement1.executeQuery(SQL).next()) {
                SQL = "INSERT INTO HISTORY (idUser, idBook) VALUES (?,?)";
                myStatement = ConnectToDatabase.getConnection().prepareStatement(SQL);
                myStatement.setInt(1, UsersDatabase.getUserId(Shared.username));
                myStatement.setInt(2, Shared.openedBook);
                myStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Book> getHistory(int nr) {
        List <Book> temp = new ArrayList<>();
        String SQL = "SELECT * FROM BOOKS INNER JOIN HISTORY ON BOOKS.idBOOK=HISTORY.idBook WHERE HISTORY.idUser="+UsersDatabase.getUserId(Shared.username)+" and BOOKS.approved=1 order by BOOKS.idBOOK DESC LIMIT "+nr;
        Statement myStatement = null;
        try {
            myStatement = ConnectToDatabase.getConnection().createStatement();
            ResultSet myResultSet = myStatement.executeQuery(SQL);
            while (myResultSet.next()) {
                temp.add(new Book(myResultSet.getInt("idBook"), myResultSet.getString("title"), myResultSet.getString("author"), myResultSet.getString("ISBN"),
                        myResultSet.getInt("pages"), myResultSet.getString("cover"), myResultSet.getString("category"),
                        myResultSet.getString("description"), myResultSet.getString("language"), myResultSet.getString("link"), myResultSet.getInt("userID")));
            }
        } catch (SQLException e) {
            return temp;
        }
        return temp;
    }

    public static void deleteBook(int id) {
        String SQL="DELETE FROM BOOKS WHERE idBOOK=?";
        try {
            PreparedStatement myStatement = ConnectToDatabase.getConnection().prepareStatement(SQL);
            myStatement.setInt(1, id);
            myStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void approveBook(int id) {
        String SQL="UPDATE BOOKS SET approved=1 WHERE idBOOK=?";
        try {
            PreparedStatement myStatement = ConnectToDatabase.getConnection().prepareStatement(SQL);
            myStatement.setInt(1, id);
            myStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 }