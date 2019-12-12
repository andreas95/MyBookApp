package ro.unibuc.MyLibrary.main.java.controller;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import java.awt.Desktop;
import org.controlsfx.control.textfield.TextFields;
import ro.unibuc.MyLibrary.main.java.common.Shared;
import ro.unibuc.MyLibrary.main.java.common.StageManager;
import ro.unibuc.MyLibrary.main.java.database.BooksDatabase;
import ro.unibuc.MyLibrary.main.java.database.UsersDatabase;
import ro.unibuc.MyLibrary.main.java.model.Book;
import ro.unibuc.MyLibrary.main.java.ui.AlertMessage;
import ro.unibuc.MyLibrary.main.java.utils.FTPUploader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author antoneandreas
 */
public class AddABookController {

    @FXML
    private Line HeaderSeparator;
    @FXML
    private TextField BookTitle;
    @FXML
    private TextField BookAuthor;
    @FXML
    private TextField BookISBN;
    @FXML
    private TextArea BookDescription;
    @FXML
    private ComboBox BookCategory;
    @FXML
    private TextField BookPages;
    @FXML
    private ComboBox BookLanguage;
    @FXML
    private ImageView ImageBook;
    private Desktop desktop = Desktop.getDesktop();
    private Path bookPath;
    private Path coverPath;
    private String coverName;
    private String bookName;
    private FTPUploader ftpUploader;


    public void initialize() {
        HeaderSeparator.setEndX(StageManager.getStage().getWidth() - 200);

        BookLanguage.getItems().addAll("Romanian", "English", "French", "Italian", "German");
        BookLanguage.setValue("Romanian");

        BookCategory.getItems().addAll("Adventure", "Antiques & Collectibles", "Architecture", "Art", "Bibles", "Biography & Autobiography", "Business & Economics", "Comics & Graphic Novels", "Computers", "Cooking", "Crafts & Hobbies", "Design", "Drama", "Education", "Encyclopedia", "Erotic", "Esotericism", "Essay", "Fairy Tale", "Family & Relationships", "Fantasy", "Fiction", "Foreign Language Study", "Games", "Gardening", "Health & Fitness", "History", "Horror", "House & Home", "Humor", "Juvenile Fiction", "Juvenile Nonfiction", "Language & Literature", "Law", "Letters", "Literary Collections", "Literary Criticism", "Mathematics", "Medical", "Music", "Mystery & Crime", "Nature", "Performing Arts", "Pets", "Philosophy", "Photography", "Poetry", "Political Science", "Psychology", "Religion", "Romance", "Satire", "Science", "Science Fiction", "Self-Help", "Short Story", "Social Science", "Sports & Recreation", "Study Aids", "Technology & Engineering", "Thriller", "Transportation", "Travel", "True Crime");
        BookCategory.setValue("Adventure");

        TextFields.bindAutoCompletion(BookAuthor, FXCollections.observableArrayList(BooksDatabase.getBooksAuthor()));

        TextFields.bindAutoCompletion(BookTitle, FXCollections.observableArrayList(BooksDatabase.getBooksTitle()));

        ImageBook.setImage(new Image("ro/unibuc/MyLibrary/main/resources/images/books/nocover.jpg"));
    }

    @FXML
    public void addBook() {
        if (BookTitle.getText().isEmpty()) {
            new AlertMessage("Add a Book Error", "You must enter a book title.", Alert.AlertType.ERROR);
        } else if (BookAuthor.getText().isEmpty()) {
            new AlertMessage("Add a Book Error", "You must enter an author.", Alert.AlertType.ERROR);
        } else if (BookISBN.getText().isEmpty()) {
            new AlertMessage("Add a Book Error", "You must enter an ISBN.", Alert.AlertType.ERROR);
        } else if (BookPages.getText().isEmpty()) {
            new AlertMessage("Add a Book Error", "You must enter number of pages.", Alert.AlertType.ERROR);
        } else if (isNumber(BookISBN.getText()) == false) {
            new AlertMessage("Add a Book Error", "ISBN must be a whole number.", Alert.AlertType.ERROR);
        } else if (isNumber(BookPages.getText()) == false) {
            new AlertMessage("Add a Book Error", "Number of pages must be a whole number.", Alert.AlertType.ERROR);
        } else if (bookPath == null) {
            new AlertMessage("Add a Book Error", "The pdf file does not exist.", Alert.AlertType.ERROR);
        } else {
            //Get current date
            DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
            Date date = new Date();
            bookName = dateFormat.format(date) + "_" + bookPath.getFileName();
            bookName = bookName.replaceAll("\\s+","");

            if (coverPath == null) {
                coverName = "nocover.jpg";
            } else {
                coverName = dateFormat.format(date) + "_" + coverPath.getFileName();
                coverName = coverName.replaceAll("\\s+","");
            }

            //Add book to the database
            if (BooksDatabase.addBook(new Book(BookTitle.getText(), BookAuthor.getText(), BookISBN.getText(), Integer.parseInt(BookPages.getText()), coverName, BookCategory.getSelectionModel().getSelectedItem().toString(), BookDescription.getText(), BookLanguage.getSelectionModel().getSelectedItem().toString(), bookName, UsersDatabase.getUserId(Shared.username)))) {
                new AlertMessage("Add a Book", "The book was uploaded successfully.", Alert.AlertType.INFORMATION);
                clear();

                ftpUploader = null;
                try {
                    ftpUploader = new FTPUploader("ftp.byethost9.com", "b9_21380244", "howtolove");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Task task=new Task() {
                    @Override
                    protected Object call() throws Exception {
                //Upload book and cover

                //FTP server path is relative. So if FTP account HOME directory is "/home/pankaj/public_html/" and you need to upload
                // files to "/home/pankaj/public_html/wp-content/uploads/image2/", you should pass directory parameter as "/wp-content/uploads/image2/"
                ftpUploader.uploadFile(bookPath.toString(), bookName, "/htdocs/books/");

                try {
                    if (coverPath != null) {
                        ftpUploader.uploadFile(coverPath.toString(), coverName, "/htdocs/covers/");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ftpUploader.disconnect();
                        return null;
                    }
                };
                new Thread(task).start();
                clear();

            } else{
            new AlertMessage("Add a Book Error", "The book was not uploaded successfully. Please try again.", Alert.AlertType.ERROR);
            clear();
        }
    }

}

    @FXML
    public void openBook() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("View Books");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            bookPath = file.toPath();
        }
    }

    @FXML
    public void openCover() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("View Images");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            coverPath = file.toPath();
            BufferedImage bufferedImage = null;
            try {
                bufferedImage = ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            ImageBook.setImage(image);
        }
    }

    @FXML
    public void clear() {
        BookTitle.clear();
        BookAuthor.clear();
        BookISBN.clear();
        BookPages.clear();
        BookCategory.setValue("Adventure");
        BookDescription.clear();
        BookLanguage.setValue("Romanian");
    }

    public boolean isNumber(String str) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }
}