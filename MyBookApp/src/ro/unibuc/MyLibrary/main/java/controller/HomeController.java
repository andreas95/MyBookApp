package ro.unibuc.MyLibrary.main.java.controller;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import ro.unibuc.MyLibrary.main.java.database.QuotesDatabase;


/**
 * @author antoneandreas
 */

public class HomeController {


    @FXML
    private Line HeaderSeparator;
    @FXML
    private Line HeaderSeparator2;
    @FXML
    private ImageView HomeImage;
    @FXML
    private Text TextQuote;
    private Screen screen = Screen.getPrimary();
    private Rectangle2D windows = screen.getVisualBounds();

    public void initialize() {
        HeaderSeparator.setEndX(windows.getWidth() - 200);
        HeaderSeparator2.setEndX(windows.getWidth() - 200);
        HomeImage.setFitWidth(windows.getWidth() - 201);
        HomeImage.setFitHeight(windows.getHeight() - 210);

        //set quote of the day
        TextQuote.setText(QuotesDatabase.getQuote().getAuthor()+" : "+QuotesDatabase.getQuote().getQuote());
    }

}