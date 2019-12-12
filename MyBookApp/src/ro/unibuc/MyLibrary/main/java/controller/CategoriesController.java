package ro.unibuc.MyLibrary.main.java.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.Reflection;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import ro.unibuc.MyLibrary.main.java.common.ScreenController;
import ro.unibuc.MyLibrary.main.java.common.Shared;
import ro.unibuc.MyLibrary.main.java.common.StageManager;
import ro.unibuc.MyLibrary.main.java.database.BooksDatabase;
import ro.unibuc.MyLibrary.main.java.model.Book;

import java.io.IOException;
import java.util.List;


/**
 * @author antoneandreas
 */

public class CategoriesController {

    @FXML
    private Line HeaderSeparator;
    @FXML
    private Line HeaderSeparator2;
    @FXML
    private AnchorPane HeaderContentPanel;
    @FXML
    private ScrollPane Sp;
    @FXML
    private AnchorPane BottomContentPanel;
    private double left = 240.0;
    private double top = 380.0;


    public void initialize() {
        HeaderSeparator.setEndX(StageManager.getStage().getWidth()-200);
        HeaderSeparator2.setEndX(StageManager.getStage().getWidth()-200);

        BottomContentPanel.setPrefHeight(StageManager.getStage().getHeight()-340.5);

        Sp.setContent(BottomContentPanel);
        Sp.addEventFilter(ScrollEvent.SCROLL,new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (event.getDeltaX() != 0) {
                    event.consume();
                }
            }
        });

        ToggleGroup groupCategories = new ToggleGroup();

        ToggleButton cat1 = new ToggleButton("Adventure");
        cat1.setToggleGroup(groupCategories);
        cat1.setSelected(true);
        showBooks("Adventure");
        AnchorPane.setLeftAnchor(cat1,10.0);
        AnchorPane.setTopAnchor(cat1,80.0);
        cat1.setOnMouseClicked(event -> showBooks("Adventure"));

        ToggleButton cat2 = new ToggleButton("Antiques & Collectibles");
        cat2.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat2,105.0);
        AnchorPane.setTopAnchor(cat2,80.0);
        cat2.setOnMouseClicked(event -> showBooks("Antiques & Collectibles"));

        ToggleButton cat3 = new ToggleButton("Architecture");
        cat3.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat3,280.0);
        AnchorPane.setTopAnchor(cat3,80.0);
        cat3.setOnMouseClicked(event -> showBooks("Architecture"));

        ToggleButton cat4 = new ToggleButton("Art");
        cat4.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat4,390.0);
        AnchorPane.setTopAnchor(cat4,80.0);
        cat4.setOnMouseClicked(event -> showBooks("Art"));

        ToggleButton cat5 = new ToggleButton("Bibles");
        cat5.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat5,445.0);
        AnchorPane.setTopAnchor(cat5,80.0);
        cat5.setOnMouseClicked(event -> showBooks("Bibles"));

        ToggleButton cat6 = new ToggleButton("Biography & Autobiography");
        cat6.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat6,515.0);
        AnchorPane.setTopAnchor(cat6,80.0);
        cat6.setOnMouseClicked(event -> showBooks("Biography & Autobiography"));

        ToggleButton cat7 = new ToggleButton("Business & Economics");
        cat7.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat7,720.0);
        AnchorPane.setTopAnchor(cat7,80.0);
        cat7.setOnMouseClicked(event -> showBooks("Business & Economics"));

        ToggleButton cat8 = new ToggleButton("Comics & Graphic Novels");
        cat8.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat8,895.0);
        AnchorPane.setTopAnchor(cat8,80.0);
        cat8.setOnMouseClicked(event -> showBooks("Comics & Graphic Novels"));

        ToggleButton cat9 = new ToggleButton("Computers");
        cat9.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat9,1085.0);
        AnchorPane.setTopAnchor(cat9,80.0);
        cat9.setOnMouseClicked(event -> showBooks("Computers"));

        ToggleButton cat10 = new ToggleButton("Cooking");
        cat10.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat10,1185.0);
        AnchorPane.setTopAnchor(cat10,80.0);
        cat10.setOnMouseClicked(event -> showBooks("Cooking"));

        ToggleButton cat11 = new ToggleButton("Crafts & Hobbies");
        cat11.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat11,1270.0);
        AnchorPane.setTopAnchor(cat11,80.0);
        cat11.setOnMouseClicked(event -> showBooks("Crafts & Hobbies"));

        ToggleButton cat12 = new ToggleButton("Design");
        cat12.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat12,1410.0);
        AnchorPane.setTopAnchor(cat12,80.0);
        cat12.setOnMouseClicked(event -> showBooks("Design"));

        ToggleButton cat13 = new ToggleButton("Drama");
        cat13.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat13,1485.0);
        AnchorPane.setTopAnchor(cat13,80.0);
        cat13.setOnMouseClicked(event -> showBooks("Drama"));

        ToggleButton cat14 = new ToggleButton("Education");
        cat14.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat14,1560.0);
        AnchorPane.setTopAnchor(cat14,80.0);
        cat14.setOnMouseClicked(event -> showBooks("Education"));

        ToggleButton cat15 = new ToggleButton("Encyclopedia");
        cat15.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat15,10.0);
        AnchorPane.setTopAnchor(cat15,110.0);
        cat15.setOnMouseClicked(event -> showBooks("Encyclopedia"));

        ToggleButton cat16 = new ToggleButton("Erotic");
        cat16.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat16,125.0);
        AnchorPane.setTopAnchor(cat16,110.0);
        cat16.setOnMouseClicked(event -> showBooks("Erotic"));

        ToggleButton cat17 = new ToggleButton("Esotericism");
        cat17.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat17,195.0);
        AnchorPane.setTopAnchor(cat17,110.0);
        cat17.setOnMouseClicked(event -> showBooks("Esotericism"));

        ToggleButton cat18 = new ToggleButton("Essay");
        cat18.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat18,300.0);
        AnchorPane.setTopAnchor(cat18,110.0);
        cat18.setOnMouseClicked(event -> showBooks("Essay"));

        ToggleButton cat19 = new ToggleButton("Fairy Tale");
        cat19.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat19,370.0);
        AnchorPane.setTopAnchor(cat19,110.0);
        cat19.setOnMouseClicked(event -> showBooks("Fairy Tale"));

        ToggleButton cat20 = new ToggleButton("Family & Relationships");
        cat20.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat20,465.0);
        AnchorPane.setTopAnchor(cat20,110.0);
        cat20.setOnMouseClicked(event -> showBooks("Family & Relationships"));

        ToggleButton cat21 = new ToggleButton("Fantasy");
        cat21.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat21,640.0);
        AnchorPane.setTopAnchor(cat21,110.0);
        cat21.setOnMouseClicked(event -> showBooks("Fantasy"));

        ToggleButton cat22 = new ToggleButton("Fiction");
        cat22.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat22,725.0);
        AnchorPane.setTopAnchor(cat22,110.0);
        cat22.setOnMouseClicked(event -> showBooks("Fiction"));

        ToggleButton cat23 = new ToggleButton("Foreign Language Study");
        cat23.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat23,800.0);
        AnchorPane.setTopAnchor(cat23,110.0);
        cat23.setOnMouseClicked(event -> showBooks("Foreign Language Study"));

        ToggleButton cat24 = new ToggleButton("Games");
        cat24.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat24,985.0);
        AnchorPane.setTopAnchor(cat24,110.0);
        cat24.setOnMouseClicked(event -> showBooks("Games"));

        ToggleButton cat25 = new ToggleButton("Gardening");
        cat25.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat25,1060.0);
        AnchorPane.setTopAnchor(cat25,110.0);
        cat25.setOnMouseClicked(event -> showBooks("Gardening"));

        ToggleButton cat26 = new ToggleButton("Health & Fitness");
        cat26.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat26,1160.0);
        AnchorPane.setTopAnchor(cat26,110.0);
        cat26.setOnMouseClicked(event -> showBooks("Health & Fitness"));

        ToggleButton cat27 = new ToggleButton("History");
        cat27.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat27,1295.0);
        AnchorPane.setTopAnchor(cat27,110.0);
        cat27.setOnMouseClicked(event -> showBooks("History"));

        ToggleButton cat28 = new ToggleButton("Horror");
        cat28.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat28,1375.0);
        AnchorPane.setTopAnchor(cat28,110.0);
        cat28.setOnMouseClicked(event -> showBooks("Horror"));

        ToggleButton cat29 = new ToggleButton("House & Home");
        cat29.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat29,1450.0);
        AnchorPane.setTopAnchor(cat29,110.0);
        cat29.setOnMouseClicked(event -> showBooks("House & Home"));

        ToggleButton cat30 = new ToggleButton("Humor");
        cat30.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat30,1575.0);
        AnchorPane.setTopAnchor(cat30,110.0);
        cat30.setOnMouseClicked(event -> showBooks("Humor"));

        ToggleButton cat31 = new ToggleButton("Juvenile Fiction");
        cat31.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat31,10.0);
        AnchorPane.setTopAnchor(cat31,140.0);
        cat31.setOnMouseClicked(event -> showBooks("Juvenile Fiction"));

        ToggleButton cat32 = new ToggleButton("Juvenile Nonfiction");
        cat32.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat32,140.0);
        AnchorPane.setTopAnchor(cat32,140.0);
        cat32.setOnMouseClicked(event -> showBooks("Juvenile Nonfiction"));

        ToggleButton cat33 = new ToggleButton("Language & Literature");
        cat33.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat33,290.0);
        AnchorPane.setTopAnchor(cat33,140.0);
        cat33.setOnMouseClicked(event -> showBooks("Language & Literature"));

        ToggleButton cat34 = new ToggleButton("Law");
        cat34.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat34,460.0);
        AnchorPane.setTopAnchor(cat34,140.0);
        cat34.setOnMouseClicked(event -> showBooks("Law"));

        ToggleButton cat35 = new ToggleButton("Letters");
        cat35.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat35,520.0);
        AnchorPane.setTopAnchor(cat35,140.0);
        cat35.setOnMouseClicked(event -> showBooks("Letters"));

        ToggleButton cat36 = new ToggleButton("Literary Collections");
        cat36.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat36,600.0);
        AnchorPane.setTopAnchor(cat36,140.0);
        cat36.setOnMouseClicked(event -> showBooks("Literary Collections"));

        ToggleButton cat37 = new ToggleButton("Literary Criticism");
        cat37.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat37,755.0);
        AnchorPane.setTopAnchor(cat37,140.0);
        cat37.setOnMouseClicked(event -> showBooks("Literary Criticism"));

        ToggleButton cat38 = new ToggleButton("Mathematics");
        cat38.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat38,895.0);
        AnchorPane.setTopAnchor(cat38,140.0);
        cat38.setOnMouseClicked(event -> showBooks("Mathematics"));

        ToggleButton cat39 = new ToggleButton("Medical");
        cat39.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat39,1010.0);
        AnchorPane.setTopAnchor(cat39,140.0);
        cat39.setOnMouseClicked(event -> showBooks("Medical"));

        ToggleButton cat40 = new ToggleButton("Music");
        cat40.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat40,1090.0);
        AnchorPane.setTopAnchor(cat40,140.0);
        cat40.setOnMouseClicked(event -> showBooks("Music"));

        ToggleButton cat41 = new ToggleButton("Mystery & Crime");
        cat41.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat41,1160.0);
        AnchorPane.setTopAnchor(cat41,140.0);
        cat41.setOnMouseClicked(event -> showBooks("Mystery & Crime"));

        ToggleButton cat42 = new ToggleButton("Nature");
        cat42.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat42,1295.0);
        AnchorPane.setTopAnchor(cat42,140.0);
        cat42.setOnMouseClicked(event -> showBooks("Nature"));

        ToggleButton cat43 = new ToggleButton("Performing Arts");
        cat43.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat43,1370.0);
        AnchorPane.setTopAnchor(cat43,140.0);
        cat43.setOnMouseClicked(event -> showBooks("Performing Arts"));

        ToggleButton cat44 = new ToggleButton("Pets");
        cat44.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat44,1500.0);
        AnchorPane.setTopAnchor(cat44,140.0);
        cat44.setOnMouseClicked(event -> showBooks("Pets"));

        ToggleButton cat45 = new ToggleButton("Philosophy");
        cat45.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat45,1560.0);
        AnchorPane.setTopAnchor(cat45,140.0);
        cat45.setOnMouseClicked(event -> showBooks("Philosophy"));

        ToggleButton cat46 = new ToggleButton("Photography");
        cat46.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat46,10.0);
        AnchorPane.setTopAnchor(cat46,170.0);
        cat46.setOnMouseClicked(event -> showBooks("Photography"));

        ToggleButton cat47 = new ToggleButton("Poetry");
        cat47.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat47,125.0);
        AnchorPane.setTopAnchor(cat47,170.0);
        cat47.setOnMouseClicked(event -> showBooks("Poetry"));

        ToggleButton cat48 = new ToggleButton("Political Science");
        cat48.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat48,200.0);
        AnchorPane.setTopAnchor(cat48,170.0);
        cat48.setOnMouseClicked(event -> showBooks("Political Science"));

        ToggleButton cat49 = new ToggleButton("Psychology");
        cat49.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat49,340.0);
        AnchorPane.setTopAnchor(cat49,170.0);
        cat49.setOnMouseClicked(event -> showBooks("Psychology"));

        ToggleButton cat50 = new ToggleButton("Religion");
        cat50.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat50,450.0);
        AnchorPane.setTopAnchor(cat50,170.0);
        cat50.setOnMouseClicked(event -> showBooks("Religion"));

        ToggleButton cat51 = new ToggleButton("Romance");
        cat51.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat51,535.0);
        AnchorPane.setTopAnchor(cat51,170.0);
        cat51.setOnMouseClicked(event -> showBooks("Romance"));

        ToggleButton cat52 = new ToggleButton("Satire");
        cat52.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat52,630.0);
        AnchorPane.setTopAnchor(cat52,170.0);
        cat52.setOnMouseClicked(event -> showBooks("Satire"));

        ToggleButton cat53 = new ToggleButton("Science");
        cat53.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat53,700.0);
        AnchorPane.setTopAnchor(cat53,170.0);
        cat53.setOnMouseClicked(event -> showBooks("Science"));

        ToggleButton cat54 = new ToggleButton("Science Fiction");
        cat54.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat54,785.0);
        AnchorPane.setTopAnchor(cat54,170.0);
        cat54.setOnMouseClicked(event -> showBooks("Science Fiction"));

        ToggleButton cat55 = new ToggleButton("Self-Help");
        cat55.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat55,915.0);
        AnchorPane.setTopAnchor(cat55,170.0);
        cat55.setOnMouseClicked(event -> showBooks("Self-Help"));

        ToggleButton cat56 = new ToggleButton("Short Story");
        cat56.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat56,1010.0);
        AnchorPane.setTopAnchor(cat56,170.0);
        cat56.setOnMouseClicked(event -> showBooks("Short Story"));

        ToggleButton cat57 = new ToggleButton("Social Science");
        cat57.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat57,1115.0);
        AnchorPane.setTopAnchor(cat57,170.0);
        cat57.setOnMouseClicked(event -> showBooks("Social Science"));

        ToggleButton cat58 = new ToggleButton("Sports & Recreation");
        cat58.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat58,1245.0);
        AnchorPane.setTopAnchor(cat58,170.0);
        cat58.setOnMouseClicked(event -> showBooks("Sports & Recreation"));

        ToggleButton cat59 = new ToggleButton("Study Aids");
        cat59.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat59,1405.0);
        AnchorPane.setTopAnchor(cat59,170.0);
        cat59.setOnMouseClicked(event -> showBooks("Study Aids"));

        ToggleButton cat60 = new ToggleButton("Technology & Engineering");
        cat60.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat60,1510.0);
        AnchorPane.setTopAnchor(cat60,170.0);
        cat60.setOnMouseClicked(event -> showBooks("Technology & Engineering"));

        ToggleButton cat61 = new ToggleButton("Thriller");
        cat61.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat61,10.0);
        AnchorPane.setTopAnchor(cat61,200.0);
        cat61.setOnMouseClicked(event -> showBooks("Thriller"));

        ToggleButton cat62 = new ToggleButton("Transportation");
        cat62.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat62,90.0);
        AnchorPane.setTopAnchor(cat62,200.0);
        cat62.setOnMouseClicked(event -> showBooks("Transportation"));

        ToggleButton cat63 = new ToggleButton("Travel");
        cat63.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat63,220.0);
        AnchorPane.setTopAnchor(cat63,200.0);
        cat63.setOnMouseClicked(event -> showBooks("Travel"));

        ToggleButton cat64 = new ToggleButton("True Crime");
        cat64.setToggleGroup(groupCategories);
        AnchorPane.setLeftAnchor(cat64,300.0);
        AnchorPane.setTopAnchor(cat64,200.0);
        cat64.setOnMouseClicked(event -> showBooks("True Crime"));

        HeaderContentPanel.getChildren().addAll(cat1, cat2, cat3, cat4, cat5, cat6, cat7, cat8, cat9, cat10, cat11, cat12,
                cat13, cat14, cat15, cat16, cat17, cat18, cat19, cat20, cat21, cat22, cat23, cat24, cat25, cat26, cat27,
                cat28, cat29, cat30, cat31, cat32, cat33, cat34, cat35, cat36, cat37, cat38, cat39, cat40, cat41, cat42,
                cat43, cat44, cat45, cat46, cat47, cat48, cat49, cat50, cat51, cat52, cat53, cat54, cat55, cat56, cat57,
                cat58, cat59, cat60, cat61, cat62, cat63, cat64);

    }

    public void showBooks(String category) {
        BottomContentPanel.getChildren().clear();

        List<Book> books = BooksDatabase.getBooksByCategory(70, category);
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

}