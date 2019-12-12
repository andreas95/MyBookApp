package ro.unibuc.MyLibrary.main.java.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import javafx.stage.Screen;
import javafx.util.Duration;
import ro.unibuc.MyLibrary.main.java.common.ScreenController;
import ro.unibuc.MyLibrary.main.java.common.Shared;
import ro.unibuc.MyLibrary.main.java.common.StageManager;
import ro.unibuc.MyLibrary.main.java.database.BooksDatabase;
import ro.unibuc.MyLibrary.main.java.database.UsersDatabase;
import ro.unibuc.MyLibrary.main.java.model.Book;
import ro.unibuc.MyLibrary.main.java.model.User;
import ro.unibuc.MyLibrary.main.java.ui.AlertMessage;
import ro.unibuc.MyLibrary.main.java.utils.ValidateEmail;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParsePosition;

/**
 * @author antoneandreas
 */

public class SettingsController {

    @FXML
    private Line HeaderSeparator;
    @FXML
    private Line HeaderSeparator2;
    @FXML
    private ListView<Label> ListOptions;
    @FXML
    private Label LabelAdmin1;
    @FXML
    private Label LabelAdmin2;
    @FXML
    private AnchorPane BottomContentPanel;
    @FXML
    private AnchorPane PaneManageUsersViewMode;
    @FXML
    private AnchorPane PaneManageUsersEditMode;
    @FXML
    private AnchorPane PaneProfileSettings;
    @FXML
    private AnchorPane PaneChangePassword;
    @FXML
    private AnchorPane PaneManageBooksViewMode;
    @FXML
    private AnchorPane PaneManageBooksEditMode;
    @FXML
    private TextField FirstName;
    @FXML
    private TextField LastName;
    @FXML
    private TextField Email;
    @FXML
    private PasswordField CurrentPassword;
    @FXML
    private PasswordField NewPassword;
    @FXML
    private PasswordField ConfirmPassword;
    @FXML
    private TableColumn ColumnID;
    @FXML
    private TableColumn ColumnName;
    @FXML
    private TableColumn ColumnUser;
    @FXML
    private TableColumn ColumnEmail;
    @FXML
    private TableColumn ColumnType;
    @FXML
    private TableColumn ColumnAction;
    @FXML
    private TableView TableUser;
    @FXML
    private TextField SearchUsers;
    private ObservableList<User> listUsers;
    @FXML
    private TableColumn ColumnBookID;
    @FXML
    private TableColumn ColumnBookTitle;
    @FXML
    private TableColumn ColumnBookAuthor;
    @FXML
    private TableColumn ColumnBookCategory;
    @FXML
    private TableColumn ColumnBookLanguage;
    @FXML
    private TableColumn ColumnBookUser;
    @FXML
    private TableColumn ColumnBookStatus;
    @FXML
    private TableColumn ColumnBookAction;
    @FXML
    private TableView TableBooks;
    @FXML
    private TextField SearchBooks;
    @FXML
    private TextField EditedFirstName;
    @FXML
    private TextField EditedLastName;
    @FXML
    private TextField EditedUsername;
    @FXML
    private TextField EditedEmail;
    @FXML
    private ComboBox EditedUserType;
    @FXML
    private TextField BookAuthor;
    @FXML
    private TextField BookISBN;
    @FXML
    private TextField BookTitle;
    @FXML
    private TextField BookPages;
    @FXML
    private TextField BookCover;
    @FXML
    private TextField BookLink;
    @FXML
    private TextArea BookDescription;
    @FXML
    private ComboBox BookCategory;
    @FXML
    private ComboBox BookLanguage;
    @FXML
    private ComboBox BookStatus;
    private ObservableList<Book> listBooks;
    private User currentUser;
    private Screen screen = Screen.getPrimary();
    private Rectangle2D windows = screen.getVisualBounds();

    public void initialize() {
        currentUser = UsersDatabase.getUser(Shared.username);
        BottomContentPanel.getChildren().clear();

        CurrentPassword.clear();
        NewPassword.clear();
        ConfirmPassword.clear();

        HeaderSeparator.setEndX(StageManager.getStage().getWidth() - 200);
        HeaderSeparator2.setEndX(StageManager.getStage().getWidth() - 200);

        if (Shared.userType.equals("user")) {
            ListOptions.getItems().remove(LabelAdmin1);
            ListOptions.getItems().remove(LabelAdmin2);
        } else if (Shared.userType.equals("moderator")) {
            ListOptions.getItems().remove(LabelAdmin2);
        }

        ListOptions.requestFocus();

        BottomContentPanel.getChildren().add(PaneProfileSettings);
        ListOptions.getSelectionModel().select(0);

        if (Shared.settings_page == 0) {
            Shared.settings_page = 0;
            ListOptions.getSelectionModel().select(0);
            BottomContentPanel.getChildren().clear();
            BottomContentPanel.getChildren().add(PaneProfileSettings);
            FirstName.setText(currentUser.getFirstName());
            LastName.setText(currentUser.getLastName());
            Email.setText(currentUser.getEmail());
        } else if (Shared.settings_page == 1) {
            Shared.settings_page = 0;
            ListOptions.getSelectionModel().select(1);
            BottomContentPanel.getChildren().clear();
            BottomContentPanel.getChildren().add(PaneChangePassword);
        } else if (Shared.settings_page == 3) {
            Shared.settings_page = 0;
            ListOptions.getSelectionModel().select(3);
            BottomContentPanel.getChildren().clear();
            BottomContentPanel.getChildren().add(PaneManageUsersViewMode);

            //PaneManageUsers.getChildren().clear();
            //PaneManageUsers.getChildren().add(PaneManageUsersViewMode);

            ColumnID.impl_setReorderable(false);
            ColumnName.impl_setReorderable(false);
            ColumnUser.impl_setReorderable(false);
            ColumnEmail.impl_setReorderable(false);
            ColumnType.impl_setReorderable(false);
            ColumnAction.impl_setReorderable(false);

            TableUser.prefHeightProperty().bind(Bindings.size(TableUser.getItems()).multiply(TableUser.getFixedCellSize()).add(50));
            TableUser.setMinHeight(windows.getHeight() - 330.0);
            ColumnID.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
            ColumnName.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
            ColumnUser.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
            ColumnEmail.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
            ColumnType.setCellValueFactory(new PropertyValueFactory<User, String>("type"));
            ColumnAction.setCellValueFactory(new PropertyValueFactory<User, HBox>("action"));

            listUsers = FXCollections.observableArrayList(UsersDatabase.getAllUsers());
            TableUser.setItems(listUsers);

            SearchUsers.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    listUsers = FXCollections.observableArrayList(UsersDatabase.search(SearchUsers.getText()));
                    TableUser.setItems(listUsers);
                }
            });

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1),
                            new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    if (Shared.action_delete_user) {
                                        Shared.action_delete_user = false;
                                        Shared.settings_page = 3;
                                        try {
                                            ScreenController.setScreen(ScreenController.Screen.SETTINGS);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    if (Shared.action_edit_user) {
                                        Shared.action_edit_user = false;
                                        Shared.settings_page = 33;
                                        try {
                                            ScreenController.setScreen(ScreenController.Screen.SETTINGS);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        } else if (Shared.settings_page == 33) {
            Shared.settings_page = 0;
            ListOptions.getSelectionModel().select(3);
            BottomContentPanel.getChildren().clear();
            BottomContentPanel.getChildren().add(PaneManageUsersEditMode);

            EditedFirstName.setText(Shared.edit_user.getFirstName());
            EditedLastName.setText(Shared.edit_user.getLastName());
            EditedUsername.setText(Shared.edit_user.getUsername());
            EditedEmail.setText(Shared.edit_user.getEmail());
            EditedUserType.getItems().addAll("user", "moderator", "admin");
            EditedUserType.setValue(Shared.edit_user.getType());
        } else if (Shared.settings_page == 2) {
            Shared.settings_page = 0;
            ListOptions.getSelectionModel().select(2);
            BottomContentPanel.getChildren().clear();
            BottomContentPanel.getChildren().add(PaneManageBooksViewMode);

            ColumnBookID.impl_setReorderable(false);
            ColumnBookTitle.impl_setReorderable(false);
            ColumnBookAuthor.impl_setReorderable(false);
            ColumnBookCategory.impl_setReorderable(false);
            ColumnBookLanguage.impl_setReorderable(false);
            ColumnBookUser.impl_setReorderable(false);
            ColumnBookStatus.impl_setReorderable(false);
            ColumnBookAction.impl_setReorderable(false);

            TableBooks.prefHeightProperty().bind(Bindings.size(TableUser.getItems()).multiply(TableUser.getFixedCellSize()).add(50));
            TableBooks.setMinHeight(windows.getHeight()-330.0);
            ColumnBookID.setCellValueFactory(new PropertyValueFactory<User, String>("idBook"));
            ColumnBookTitle.setCellValueFactory(new PropertyValueFactory<User, String>("title"));
            ColumnBookAuthor.setCellValueFactory(new PropertyValueFactory<User, String>("author"));
            ColumnBookCategory.setCellValueFactory(new PropertyValueFactory<User, String>("category"));
            ColumnBookLanguage.setCellValueFactory(new PropertyValueFactory<User, String>("language"));
            ColumnBookUser.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
            ColumnBookStatus.setCellValueFactory(new PropertyValueFactory<User, String>("status"));
            ColumnBookAction.setCellValueFactory(new PropertyValueFactory<User, HBox>("action"));

            listBooks = FXCollections.observableArrayList(BooksDatabase.getAllBooks());
            TableBooks.setItems(listBooks);

            SearchBooks.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    listBooks = FXCollections.observableArrayList(BooksDatabase.search(SearchBooks.getText()));
                    TableBooks.setItems(listBooks);
                }
            });

                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.seconds(1),
                                new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent actionEvent) {
                                        if (Shared.action_delete_book) {
                                            Shared.action_delete_book = false;
                                            Shared.settings_page = 2;
                                            try {
                                                ScreenController.setScreen(ScreenController.Screen.SETTINGS);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        if (Shared.action_approve_book) {
                                            Shared.action_approve_book = false;
                                            Shared.settings_page = 2;
                                            try {
                                                ScreenController.setScreen(ScreenController.Screen.SETTINGS);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        if (Shared.action_edit_book) {
                                            Shared.action_edit_book = false;
                                            Shared.settings_page = 22;
                                            try {
                                                ScreenController.setScreen(ScreenController.Screen.SETTINGS);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }));
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();

        } else if (Shared.settings_page == 22) {
            Shared.settings_page = 0;
            ListOptions.getSelectionModel().select(2);
            BottomContentPanel.getChildren().clear();
            BottomContentPanel.getChildren().add(PaneManageBooksEditMode);
            //BookTitle BookAuthor BookISBN BookPages BookCategory BookDescription BookLanguage BookCover BookLink BookStatus
            BookTitle.setText(Shared.edit_book.getTitle());
            BookAuthor.setText(Shared.edit_book.getAuthor());
            BookISBN.setText(Shared.edit_book.getISBN());
            BookPages.setText(String.valueOf(Shared.edit_book.getPages()));
            BookDescription.setText(Shared.edit_book.getDescription());
            BookCover.setText(Shared.edit_book.getCover());
            BookLink.setText(Shared.edit_book.getLink());
            BookStatus.getItems().addAll("approved", "pending");
            BookStatus.setValue(Shared.edit_book.getStatus());
            BookCategory.getItems().addAll("Adventure", "Antiques & Collectibles", "Architecture", "Art", "Bibles", "Biography & Autobiography", "Business & Economics", "Comics & Graphic Novels", "Computers", "Cooking", "Crafts & Hobbies", "Design", "Drama", "Education", "Encyclopedia", "Erotic", "Esotericism", "Essay", "Fairy Tale", "Family & Relationships", "Fantasy", "Fiction", "Foreign Language Study", "Games", "Gardening", "Health & Fitness", "History", "Horror", "House & Home", "Humor", "Juvenile Fiction", "Juvenile Nonfiction", "Language & Literature", "Law", "Letters", "Literary Collections", "Literary Criticism", "Mathematics", "Medical", "Music", "Mystery & Crime", "Nature", "Performing Arts", "Pets", "Philosophy", "Photography", "Poetry", "Political Science", "Psychology", "Religion", "Romance", "Satire", "Science", "Science Fiction", "Self-Help", "Short Story", "Social Science", "Sports & Recreation", "Study Aids", "Technology & Engineering", "Thriller", "Transportation", "Travel", "True Crime");
            BookCategory.setValue(Shared.edit_book.getCategory());
            BookLanguage.getItems().addAll("Romanian", "English", "French", "Italian", "German");
            BookLanguage.setValue(Shared.edit_book.getLanguage());
        }

    }

    @FXML
    public void settingsOptions(MouseEvent event) throws IOException {
        switch(ListOptions.getSelectionModel().getSelectedIndex()){
            case 0:
                BottomContentPanel.getChildren().clear();
                BottomContentPanel.getChildren().add(PaneProfileSettings);
                currentUser = UsersDatabase.getUser(Shared.username);
                FirstName.setText(currentUser.getFirstName());
                LastName.setText(currentUser.getLastName());
                Email.setText(currentUser.getEmail());
                break;
            case 1:
                BottomContentPanel.getChildren().clear();
                BottomContentPanel.getChildren().add(PaneChangePassword);
                CurrentPassword.clear();
                NewPassword.clear();
                ConfirmPassword.clear();
                break;
            case 3:
                Shared.settings_page = 3;
                ScreenController.setScreen(ScreenController.Screen.SETTINGS);
                break;
            case 2:
                Shared.settings_page = 2;
                ScreenController.setScreen(ScreenController.Screen.SETTINGS);
                break;
            default:
                break;
        }
    }

    @FXML
    public void cancelProfile() throws IOException {
        ScreenController.setScreen(ScreenController.Screen.HOME);
    }

    @FXML
    public void saveProfile() {
        if (FirstName.getText().isEmpty() || LastName.getText().isEmpty() || Email.getText().isEmpty()) {
            new AlertMessage("Edit Profile Error", "Please don't leave any field blank.", Alert.AlertType.ERROR);
        } else if (!ValidateEmail.validate(Email.getText())) {
            new AlertMessage("Edit Profile Error", "The email address is badly formatted.", Alert.AlertType.ERROR);
        } else if (UsersDatabase.editProfile(new User(FirstName.getText(), LastName.getText(), Email.getText()))) {
            new AlertMessage("Edit Profile", "Success!", Alert.AlertType.INFORMATION);
        } else {
            new AlertMessage("Edit Profile Error", "Please try again!", Alert.AlertType.WARNING);
        }
    }

    @FXML
    public void cancelPassword() throws IOException {
        ScreenController.setScreen(ScreenController.Screen.HOME);
    }

    @FXML
    public void savePassword() {
        if (CurrentPassword.getText().isEmpty() || NewPassword.getText().isEmpty() || ConfirmPassword.getText().isEmpty()) {
            new AlertMessage("Change Password Error", "Please don't leave any field blank.", Alert.AlertType.ERROR);
        } else if (!NewPassword.getText().equals(ConfirmPassword.getText())) {
            new AlertMessage("Change Password Error", "Password does not match the confirm password.", Alert.AlertType.ERROR);
        } else if (UsersDatabase.changePassword(CurrentPassword.getText(),  NewPassword.getText())) {
            new AlertMessage("Change Password", "Success!", Alert.AlertType.INFORMATION);
            CurrentPassword.clear();
            NewPassword.clear();
            ConfirmPassword.clear();
        } else {
            new AlertMessage("Change Password Error", "Please try again!", Alert.AlertType.WARNING);
            CurrentPassword.clear();
            NewPassword.clear();
            ConfirmPassword.clear();
        }
    }

    @FXML
    public void cancelEditUser() throws IOException {
        Shared.settings_page = 3;
        ScreenController.setScreen(ScreenController.Screen.SETTINGS);
    }

    @FXML
    public void saveEditUser() throws IOException {
        if (EditedFirstName.getText().isEmpty() || EditedLastName.getText().isEmpty() || EditedUsername.getText().isEmpty() || EditedEmail.getText().isEmpty()) {
            new AlertMessage("Edit Profile Error", "Please don't leave any field blank.", Alert.AlertType.ERROR);
        } else if (!ValidateEmail.validate(EditedEmail.getText())) {
            new AlertMessage("Edit Profile Error", "The email address is badly formatted.", Alert.AlertType.ERROR);
        } else if (UsersDatabase.editProfile2(new User(EditedFirstName.getText(), EditedLastName.getText(), EditedEmail.getText(), EditedUsername.getText(), Shared.edit_user.getOldUser(), EditedUserType.getSelectionModel().getSelectedItem().toString()))) {
            new AlertMessage("Edit Profile", "Success!", Alert.AlertType.INFORMATION);
            Shared.settings_page = 3;
            ScreenController.setScreen(ScreenController.Screen.SETTINGS);
        } else {
            new AlertMessage("Edit Profile Error", "Please try again!", Alert.AlertType.WARNING);
        }
    }

    @FXML
    public void cancelEditBook() throws IOException {
        Shared.settings_page = 2;
        ScreenController.setScreen(ScreenController.Screen.SETTINGS);
    }

    @FXML
    public void saveEditBook() throws IOException {
        if (BookTitle.getText().isEmpty()) {
            new AlertMessage("Edit Book Error", "You must enter a book title.", Alert.AlertType.ERROR);
        } else if (BookAuthor.getText().isEmpty()) {
            new AlertMessage("Edit Book Error", "You must enter an author.", Alert.AlertType.ERROR);
        } else if (BookISBN.getText().isEmpty()) {
            new AlertMessage("Edit Book Error", "You must enter an ISBN.", Alert.AlertType.ERROR);
        } else if (BookPages.getText().isEmpty()) {
            new AlertMessage("Edit Book Error", "You must enter number of pages.", Alert.AlertType.ERROR);
        } else if (isNumber(BookISBN.getText()) == false) {
            new AlertMessage("Edit Book Error", "ISBN must be a whole number.", Alert.AlertType.ERROR);
        } else if (isNumber(BookPages.getText()) == false) {
            new AlertMessage("Edit Book Error", "Number of pages must be a whole number.", Alert.AlertType.ERROR);
        } else if (BookLink.getText().isEmpty()) {
            new AlertMessage("Edit Book Error", "You must enter a book link.", Alert.AlertType.ERROR);
        } else if (BooksDatabase.editBook(new Book(Shared.edit_book.getIdBook(), BookTitle.getText(), BookAuthor.getText(), BookCategory.getSelectionModel().getSelectedItem().toString(),
                BookLanguage.getSelectionModel().getSelectedItem().toString(), Shared.edit_book.getUserID(), BookStatus.getSelectionModel().getSelectedItem().toString(),
                BookISBN.getText(), Integer.parseInt(BookPages.getText()), BookCover.getText(), BookLink.getText(), BookDescription.getText()))){
            new AlertMessage("Edit Book", "Success!", Alert.AlertType.INFORMATION);
            Shared.settings_page = 2;
            ScreenController.setScreen(ScreenController.Screen.SETTINGS);
        } else {
            new AlertMessage("Edit Book Error", "Please try again!", Alert.AlertType.WARNING);
        }
    }

    public boolean isNumber(String str) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }

}