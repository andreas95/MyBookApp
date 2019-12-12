package ro.unibuc.MyLibrary.main.java.controller;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.controlsfx.control.Rating;
import ro.unibuc.MyLibrary.main.java.common.Shared;
import ro.unibuc.MyLibrary.main.java.common.StageManager;
import ro.unibuc.MyLibrary.main.java.database.BooksDatabase;
import ro.unibuc.MyLibrary.main.java.model.Book;
import ro.unibuc.MyLibrary.main.java.utils.OpenURL;
import java.text.SimpleDateFormat;

/**
 * @author antoneandreas
 */

public class ViewBookController {

    @FXML
    private Line HeaderSeparator;
    @FXML
    private Line HeaderSeparator2;
    @FXML
    private Text BookName;
    @FXML
    private Text BookAuthor;
    @FXML
    private AnchorPane BottomContentPanel;
    @FXML
    private Text description_text;
    @FXML
    private Text language;
    @FXML
    private Text ISBN;
    @FXML
    private Text bottom_text;
    @FXML
    private Rating Favourite;
    @FXML
    private Label ReadBook;
    private boolean toggle_favourite;

    public void initialize() {
        HeaderSeparator.setEndX(StageManager.getStage().getWidth() - 200);
        HeaderSeparator2.setEndX(StageManager.getStage().getWidth() - 200);

        //Add +1 to views
        BooksDatabase.addView(Shared.openedBook);

        //Add to history
        BooksDatabase.addToHistory();

        //Get Book from Database
        Book book = BooksDatabase.getBook(Shared.openedBook);

        BookName.setText(book.getTitle());
        BookAuthor.setText("by " + book.getAuthor());

        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        webEngine.load(book.getCover());
        browser.setDisable(true);
        browser.setMaxWidth(340.0);
        browser.setPrefWidth(340.0);
        browser.setMinWidth(340.0);
        browser.setMaxHeight(475.0);
        browser.setPrefHeight(475.0);
        browser.setMinHeight(475.0);
        AnchorPane.setLeftAnchor(browser, 0.0);
        AnchorPane.setTopAnchor(browser, 0.0);

        description_text.setText(book.getDescription());

        language.setText("Language: " + book.getLanguage());

        ISBN.setText("ISBN: " + book.getISBN());

        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
        String pdate = DATE_FORMAT.format(book.getPublicationDate());

        bottom_text.setText("Category: " + book.getCategory() + " | Pages: " + book.getPages() + " | Views: " + book.getViews() + " | Publication Date: " + pdate);

        toggle_favourite = BooksDatabase.isFavoriteBook();
        Favourite.setRating(toggle_favourite?1:0);

        ReadBook.setOnMouseEntered(e -> {
            StageManager.getStage().getScene().setCursor(Cursor.HAND);
        });
        ReadBook.setOnMouseExited(e -> {
            StageManager.getStage().getScene().setCursor(Cursor.DEFAULT);
        });
        ReadBook.setOnMouseClicked(e-> {
            OpenURL.open(book.getLink());
        });


        BottomContentPanel.getChildren().add(browser);
    }

    @FXML
    public void togglefav() {
        if (toggle_favourite == false) {
            Favourite.setRating(1);
            toggle_favourite = true;
            BooksDatabase.setFavoriteBook();
        } else {
            Favourite.setRating(0);
            toggle_favourite = false;
            BooksDatabase.removeFavoriteBook();
        }
    }

}