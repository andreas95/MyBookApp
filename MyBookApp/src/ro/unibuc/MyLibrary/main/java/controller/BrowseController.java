package ro.unibuc.MyLibrary.main.java.controller;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import ro.unibuc.MyLibrary.main.java.common.ScreenController;
import ro.unibuc.MyLibrary.main.java.common.Shared;
import ro.unibuc.MyLibrary.main.java.common.StageManager;
import ro.unibuc.MyLibrary.main.java.database.BooksDatabase;
import ro.unibuc.MyLibrary.main.java.model.Book;
import java.io.IOException;
import java.util.List;

/**
 * Created by antoneandreas on 2/11/17.
 */
public class BrowseController {

    @FXML
    private Line HeaderSeparator;
    @FXML
    private Line HeaderSeparator2;
    @FXML
    private CustomTextField FieldSearch;
    @FXML
    private ListView<Label> ListBrowse;
    @FXML
    private ScrollPane Sp;
    @FXML
    private AnchorPane BottomContentPanel;
    private double left = 240.0;
    private double top = 380.0;

    public void initialize() {
        HeaderSeparator.setEndX(StageManager.getStage().getWidth()-200);
        HeaderSeparator2.setEndX(StageManager.getStage().getWidth()-200);
        ListBrowse.getSelectionModel().select(0);
        ListBrowse.requestFocus();

        BottomContentPanel.setPrefHeight(StageManager.getStage().getHeight()-216.5);

        Sp.setContent(BottomContentPanel);
        Sp.addEventFilter(ScrollEvent.SCROLL,new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (event.getDeltaX() != 0) {
                    event.consume();
                }
            }
        });

        FieldSearch.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.ENTER) {
                    search();
                }
            }
        });

        TextFields.bindAutoCompletion(FieldSearch, FXCollections.observableArrayList(BooksDatabase.getBooksAuthor()));

        TextFields.bindAutoCompletion(FieldSearch, FXCollections.observableArrayList(BooksDatabase.getBooksTitle()));

        showBooks(70,"");
    }

    @FXML
    public void modeListBrowse(MouseEvent event) throws IOException {
        switch(ListBrowse.getSelectionModel().getSelectedIndex()){
            case 0:
                FieldSearch.clear();
                showBooks(70, "");
                break;
            case 1:
                FieldSearch.clear();
                showBooks(14, "");
                break;
            case 2:
                FieldSearch.clear();
                showBooks(14, "");
                break;
            default:
                break;
        }
    }

    public void showBooks(int limit, String text) {
        BottomContentPanel.getChildren().clear();

        List<Book> books = BooksDatabase.getBooks(limit, text);
        if (books.isEmpty()) {
            Text emptyText = new Text("We did not found any books into database.");
            emptyText.setX(10.0f);
            emptyText.setY(50.0f);
            emptyText.setCache(true);
            emptyText.setFill(Color.RED);
            emptyText.setFont(Font.font(null, FontWeight.BOLD, 30));
            Reflection r = new Reflection();
            r.setFraction(0.7f);
            emptyText.setEffect(r);
            emptyText.setTranslateY(400);

            AnchorPane.setLeftAnchor(emptyText,500.0);
            AnchorPane.setTopAnchor(emptyText,0.0);
            BottomContentPanel.getChildren().add(emptyText);
        } else {
            int count_books = 0;
            int count_7books = 0;

            for (Book book : books) {
                VBox pane = new VBox();
                pane.setMinHeight(350.0);
                pane.setPrefHeight(350.0);
                pane.setMaxHeight(350.0);
                pane.setMinWidth(250.0);
                pane.setPrefWidth(250.0);
                pane.setMaxWidth(250.0);
                AnchorPane.setLeftAnchor(pane, count_books * left);
                AnchorPane.setTopAnchor(pane, count_7books * top + 20.0);
                WebView browser = new WebView();
                WebEngine webEngine = browser.getEngine();
                webEngine.load(book.getCover().toString());
                browser.setDisable(true);
                browser.setMaxWidth(250.0);
                browser.setPrefWidth(250.0);
                browser.setMinWidth(250.0);
                browser.setMaxHeight(275.0);
                browser.setPrefHeight(275.0);
                browser.setMinHeight(275.0);
                Text title = new Text(book.getTitle());
                title.setFill(Color.web("#444c63"));
                title.setFont(new Font("Proxima Nova Regular", 16));
                title.setWrappingWidth(200.0);
                title.setOnMouseEntered(e -> {
                    StageManager.getStage().getScene().setCursor(Cursor.HAND);
                    title.setFill(Color.web("#F2795A"));
                    title.setUnderline(true);
                });
                title.setOnMouseExited(e -> {
                    StageManager.getStage().getScene().setCursor(Cursor.DEFAULT);
                    title.setFill(Color.web("#444c63"));
                    title.setUnderline(false);
                });
                title.setOnMouseClicked(e-> {
                    Shared.openedBook = book.getIdBook();
                    try {
                        ScreenController.setScreen(ScreenController.Screen.VIEW_BOOK);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });
                Text author = new Text("by " + book.getAuthor());
                author.setFill(Color.web("#6f7d95"));
                VBox.setMargin(title, new Insets(7, 0, 3, 0));
                VBox.setMargin(author, new Insets(0, 0, 5, 0));
                author.setFont(new Font("Proxima Nova Regular", 12));
                pane.getChildren().addAll(browser, title, author);
                BottomContentPanel.getChildren().add(pane);
                count_books++;
                if (count_books == 7) {
                    count_7books++;
                    count_books = 0;
                }
            }
        }
    }

    @FXML
    public void search() {
        showBooks(99,FieldSearch.getText().toString());
    }

}