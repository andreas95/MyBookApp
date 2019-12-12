package ro.unibuc.MyLibrary.main.java.model;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import ro.unibuc.MyLibrary.main.java.common.Shared;
import ro.unibuc.MyLibrary.main.java.database.BooksDatabase;
import ro.unibuc.MyLibrary.main.java.database.UsersDatabase;

import java.util.Date;

/**
 * Created by antoneandreas on 1/28/17.
 */
public class Book {

    private int idBook;
    private String title;
    private String author;
    private String ISBN;
    private int pages;
    private String cover;
    private String category;
    private String description;
    private String language;
    private String link;
    private int userID;
    private int views;
    private Date publicationDate;
    private Date favDate;
    private String status;

    public Book() {}

    public Book(String title, String author, String ISBN, int pages, String cover, String category, String description, String language, String link,  int userID, int views, Date publicationDate) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.pages = pages;
        this.cover = cover;
        this.category = category;
        this.description = description;
        this.language = language;
        this.link = link;
        this.userID = userID;
        this.views = views;
        this.publicationDate = publicationDate;
    }

    public Book(String title, String author, String ISBN, int pages, String cover, String category, String description, String language, String link,  int userID) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.pages = pages;
        this.cover = cover;
        this.category = category;
        this.description = description;
        this.language = language;
        this.link = link;
        this.userID = userID;
    }

    public Book(int idBook, String title, String author, String ISBN, int pages, String cover, String category, String description, String language, String link,  int userID) {
        this.idBook = idBook;
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.pages = pages;
        this.cover = cover;
        this.category = category;
        this.description = description;
        this.language = language;
        this.link = link;
        this.userID = userID;
    }

    public Book(int idBook, String title, String author, String ISBN, int pages, String cover, String category, String description, String language, String link,  int userID, Date favDate) {
        this.idBook = idBook;
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.pages = pages;
        this.cover = cover;
        this.category = category;
        this.description = description;
        this.language = language;
        this.link = link;
        this.userID = userID;
        this.favDate = favDate;
    }

    public Book(int idBook, String title, String author, String category, String language, int userID, String status, String ISBN, int pages, String cover, String link, String description) {
        this.idBook = idBook;
        this.title = title;
        this.author = author;
        this.category = category;
        this.language = language;
        this.userID = userID;
        this.status = status;
        this.ISBN = ISBN;
        this.pages = pages;
        this.cover = cover;
        this.link = link;
        this.description = description;
    }

    public HBox getAction() {
        HBox box=new HBox(8);
        box.setAlignment(Pos.CENTER);

        Label delete=new Label("Delete");
        delete.getStyleClass().add("hyperlink");
        delete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                BooksDatabase.deleteBook(getIdBook());
                Shared.action_delete_book = true;
            }
        });

        Label edit=new Label("Edit");
        edit.getStyleClass().add("hyperlink");
        edit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Shared.action_edit_book = true;
                Shared.edit_book = new Book(getIdBook(), getTitle(), getAuthor(), getCategory(), getLanguage(), getUserID(), getStatus(), getISBN(), getPages(), getCover(), getLink(), getDescription());
            }
        });

        Label approve=new Label("Approve");
        approve.getStyleClass().add("hyperlink");
        approve.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (getStatus().equals("pending")) {
                    BooksDatabase.approveBook(getIdBook());
                    Shared.action_approve_book = true;
                }
            }
        });

        box.getChildren().addAll(delete,edit,approve);
        return box;
    }

    public int getIdBook() { return idBook; }

    public void setUserID(int userID) { this.userID = userID; }

    public Date getPublicationDate() {return publicationDate; }

    public void setPublicationDate(Date publicationDate) { this.publicationDate = publicationDate; }

    public int getViews() { return views; }

    public void setViews(int views) { this.views = views; }

    public void setFavDate(Date favDate) { this.favDate = favDate; }

    public String getTitle() { return title; }

    public String getAuthor() {
        return author;
    }

    public String getISBN() { return ISBN; }

    public int getPages() { return pages; }

    public String getCover() { return cover; }

    public String getCategory() { return category; }

    public String getDescription() { return description; }

    public String getLanguage() { return language; }

    public String getLink() { return link; }

    public int getUserID() {
        return userID;
    }

    public String getUsername() { return UsersDatabase.getUserByID(getUserID()); }

    public String getStatus() { return status; }

    public Date getFavDate() { return favDate; }
}