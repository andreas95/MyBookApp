package ro.unibuc.MyLibrary.main.java.controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import ro.unibuc.MyLibrary.main.java.common.ScreenController;
import ro.unibuc.MyLibrary.main.java.common.Shared;
import ro.unibuc.MyLibrary.main.java.common.StageManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import ro.unibuc.MyLibrary.main.java.database.BooksDatabase;
import ro.unibuc.MyLibrary.main.java.model.Book;
import ro.unibuc.MyLibrary.main.java.utils.ConvertDate;
import ro.unibuc.MyLibrary.main.java.utils.OpenURL;

import java.io.IOException;
import java.util.List;


/**
 * @author antoneandreas
 */

public class ApplicationController {

    @FXML
    private Label user;
    @FXML
    private AnchorPane FooterPanel;
    @FXML
    private ListView<Label> ListMenu;
    @FXML
    private AnchorPane PopOverUserMenu;
    @FXML
    private AnchorPane ContentPanel;
    @FXML
    private AnchorPane MenuLeftPanel;
    @FXML
    private Button AddBookButton;
    @FXML
    private Label ApplicationTitle;
    @FXML
    private Label ButtonHelp;
    @FXML
    private Label ButtonSettings;
    @FXML
    private VBox FavBook1;
    @FXML
    private VBox FavBook2;
    @FXML
    private VBox FavBook3;
    @FXML
    private VBox FavBook4;
    @FXML
    private Text FavBook1Title;
    @FXML
    private Text FavBook2Title;
    @FXML
    private Text FavBook3Title;
    @FXML
    private Text FavBook4Title;
    @FXML
    private Text FavBook1Author;
    @FXML
    private Text FavBook2Author;
    @FXML
    private Text FavBook3Author;
    @FXML
    private Text FavBook4Author;
    @FXML
    private Label FavBook1Time;
    @FXML
    private Label FavBook2Time;
    @FXML
    private Label FavBook3Time;
    @FXML
    private Label FavBook4Time;
    private Screen screen = Screen.getPrimary();
    private Rectangle2D windows = screen.getVisualBounds();

    public void initialize() {
        if (windows.getWidth()<=1300) {
            AnchorPane.setLeftAnchor(ApplicationTitle,windows.getWidth()-700.0);
        } else if (windows.getWidth() >1300 && windows.getWidth()<1800) {
            AnchorPane.setLeftAnchor(ApplicationTitle,windows.getWidth()-800.0);
        } else if (windows.getWidth() >=1800) {
            AnchorPane.setLeftAnchor(ApplicationTitle,windows.getWidth()-1000.0);
        }
        StageManager.setPane(ContentPanel);
        ContentPanel.getChildren().remove(PopOverUserMenu);
        user.setText(Shared.firstName);
        ListMenu.getSelectionModel().select(0);
        ListMenu.requestFocus();
        try {
            modeListMenu(null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        MenuLeftPanel.setOnMouseClicked(e -> {
            if (ContentPanel.getChildren().contains(PopOverUserMenu)) {
                ContentPanel.getChildren().remove(PopOverUserMenu);
            }
        });
        ListMenu.setOnMouseClicked(e -> {
            if (ContentPanel.getChildren().contains(PopOverUserMenu)) {
                ContentPanel.getChildren().remove(PopOverUserMenu);
            }
        });
        AddBookButton.setOnMouseClicked(e -> {
            //deselect menu items
            ListMenu.getSelectionModel().select(-1);
            if (ContentPanel.getChildren().contains(PopOverUserMenu)) {
                ContentPanel.getChildren().remove(PopOverUserMenu);
            }
            try {
                ScreenController.setScreen(ScreenController.Screen.ADD_A_BOOK);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        ContentPanel.setOnMouseClicked(e -> {
            if (ContentPanel.getChildren().contains(PopOverUserMenu)) {
                ContentPanel.getChildren().remove(PopOverUserMenu);
            }
        });
        FooterPanel.setOnMouseClicked(e -> {
            if (ContentPanel.getChildren().contains(PopOverUserMenu)) {
                ContentPanel.getChildren().remove(PopOverUserMenu);
            }
        });

        ButtonHelp.setOnMouseClicked(event -> {
            try {
                //deselect menu items
                ListMenu.getSelectionModel().select(-1);
                ScreenController.setScreen(ScreenController.Screen.HELP);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ButtonSettings.setOnMouseClicked(event -> {
            try {
                //deselect menu items
                ListMenu.getSelectionModel().select(-1);
                ScreenController.setScreen(ScreenController.Screen.SETTINGS);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Show favorite books to the left panel
        showFavorites();

        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                int i = 0;
                while (true) {
                    final int finalI = i;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            showFavorites();
                        }
                    });
                    i++;
                    Thread.sleep(1000);
                }
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    public void showFavorites() {
        MenuLeftPanel.getChildren().removeAll(FavBook1, FavBook2, FavBook3, FavBook4);

        List<Book> books = BooksDatabase.getFavouriteBooks(4);

        try {
            books.get(0);
            MenuLeftPanel.getChildren().add(FavBook1);
            AnchorPane.setTopAnchor(FavBook1, 545.0);
            FavBook1Title.setText(books.get(0).getTitle());
            FavBook1Author.setText(books.get(0).getAuthor());
            FavBook1Time.setText(ConvertDate.convert(books.get(0).getFavDate()) + " ago");

            FavBook1Title.setOnMouseClicked(e-> {
                Shared.openedBook = books.get(0).getIdBook();
                try {
                    ScreenController.setScreen(ScreenController.Screen.VIEW_BOOK);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
        } catch ( IndexOutOfBoundsException e ) {
            // no book
        }

        try {
            books.get(1);
            MenuLeftPanel.getChildren().add(FavBook2);
            AnchorPane.setTopAnchor(FavBook2, 655.0);
            FavBook2Title.setText(books.get(1).getTitle());
            FavBook2Author.setText(books.get(1).getAuthor());
            FavBook2Time.setText(ConvertDate.convert(books.get(1).getFavDate()) + " ago");

            FavBook2Title.setOnMouseClicked(e-> {
                Shared.openedBook = books.get(1).getIdBook();
                try {
                    ScreenController.setScreen(ScreenController.Screen.VIEW_BOOK);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
        } catch ( IndexOutOfBoundsException e ) {
            // no book
        }

        try {
            books.get(2);
            MenuLeftPanel.getChildren().add(FavBook3);
            AnchorPane.setTopAnchor(FavBook3, 760.0);
            FavBook3Title.setText(books.get(2).getTitle());
            FavBook3Author.setText(books.get(2).getAuthor());
            FavBook3Time.setText(ConvertDate.convert(books.get(2).getFavDate()) + " ago");

            FavBook3Title.setOnMouseClicked(e-> {
                Shared.openedBook = books.get(2).getIdBook();
                try {
                    ScreenController.setScreen(ScreenController.Screen.VIEW_BOOK);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
        } catch ( IndexOutOfBoundsException e ) {
            // no book
        }

        try {
            books.get(3);
            MenuLeftPanel.getChildren().add(FavBook4);
            AnchorPane.setTopAnchor(FavBook4, 865.0);
            FavBook4Title.setText(books.get(3).getTitle());
            FavBook4Author.setText(books.get(3).getAuthor());
            FavBook4Time.setText(ConvertDate.convert(books.get(3).getFavDate()) + " ago");

            FavBook4Title.setOnMouseClicked(e-> {
                Shared.openedBook = books.get(3).getIdBook();
                try {
                    ScreenController.setScreen(ScreenController.Screen.VIEW_BOOK);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
        } catch ( IndexOutOfBoundsException e ) {
            // no book
        }
    }

    @FXML
    public void modeListMenu(MouseEvent event) throws IOException {
        switch(ListMenu.getSelectionModel().getSelectedIndex()){
            case 0:
                ScreenController.setScreen(ScreenController.Screen.HOME);
                break;
            case 1:
                ScreenController.setScreen(ScreenController.Screen.BROWSE);
                break;
            case 2:
                ScreenController.setScreen(ScreenController.Screen.CATEGORIES);
                break;
            case 3:
                ScreenController.setScreen(ScreenController.Screen.FAVOURITE_BOOKS);
                break;
            case 4:
                ScreenController.setScreen(ScreenController.Screen.HISTORY);
                break;
            default:
                break;
        }
    }

    @FXML
    public void close() {
        System.exit(0);
    }

    @FXML
    public void minimize() {
        StageManager.getStage().setIconified(true);
    }

    @FXML
    public void full_screen() {
        StageManager.getStage().setFullScreen(true);
    }

    @FXML
    public void UserMenu() {
        if (ContentPanel.getChildren().contains(PopOverUserMenu)) {
            ContentPanel.getChildren().remove(PopOverUserMenu);
        } else {
            ContentPanel.getChildren().add(PopOverUserMenu);
        }

    }

    @FXML
    public void logout() {
        Shared.session_status = false;
        try {
            ScreenController.setScreen(ScreenController.Screen.LOGIN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void helpCenter() {
        OpenURL.open("http://www.unibuc.ro/");
    }

    @FXML
    public void ourSupport() {
        OpenURL.open("http://www.unibuc.ro/");
    }

    @FXML
    public void profileSettings() throws IOException {
        Shared.settings_page = 0;
        ListMenu.getSelectionModel().select(-1);
        ScreenController.setScreen(ScreenController.Screen.SETTINGS);
    }

    @FXML
    public void changePassword() throws IOException {
        Shared.settings_page = 1;
        ListMenu.getSelectionModel().select(-1);
        ScreenController.setScreen(ScreenController.Screen.SETTINGS);
    }

}