package ro.unibuc.MyLibrary.main.java.common;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author antoneandreas
 */

public class ScreenController {

    private static List<Screen> openedScreens = new ArrayList<Screen>();

    public static enum Screen {
        LOGIN,
        SINGIN,
        RECOVER,
        SIGNUP,
        LOGIN_MESSAGE,
        APPLICATION,
        HOME,
        BROWSE,
        FAVOURITE_BOOKS,
        CATEGORIES,
        HISTORY,
        ADD_A_BOOK,
        HELP,
        SETTINGS,
        VIEW_BOOK
    }

    public ScreenController() {
    }

    public static void setScreen(Screen screen) throws IOException {
        switch (screen) {
            case APPLICATION:
                StageManager.setRoot(FXMLLoader.load(ScreenController.class.getResource("../view/ApplicationView.fxml")));
                Shared.screen= Screen.APPLICATION;
                openedScreens.add(Screen.APPLICATION);
                break;
            case LOGIN:
                StageManager.setRoot(FXMLLoader.load(ScreenController.class.getResource("../view/LoginView.fxml")));
                Shared.screen= Screen.LOGIN;
                openedScreens.add(Screen.LOGIN);
                break;
            case SINGIN:
                StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource("../view/SigninView.fxml")));
                Shared.screen= Screen.SINGIN;
                openedScreens.add(Screen.SINGIN);
                break;
            case RECOVER:
                StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource("../view/RecoverView.fxml")));
                Shared.screen= Screen.RECOVER;
                openedScreens.add(Screen.RECOVER);
                break;
            case SIGNUP:
                StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource("../view/SignupView.fxml")));
                Shared.screen= Screen.SIGNUP;
                openedScreens.add(Screen.SIGNUP);
                break;
            case LOGIN_MESSAGE:
                StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource("../view/LoginMessageView.fxml")));
                Shared.screen= Screen.LOGIN_MESSAGE;
                openedScreens.add(Screen.LOGIN_MESSAGE);
                break;
            case HOME:
                StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource("../view/HomeView.fxml")));
                Shared.screen= Screen.HOME;
                openedScreens.add(Screen.HOME);
                break;
            case BROWSE:
                StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource("../view/BrowseView.fxml")));
                Shared.screen= Screen.BROWSE;
                openedScreens.add(Screen.BROWSE);
                break;
            case FAVOURITE_BOOKS:
                StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource("../view/FavouriteBooksView.fxml")));
                Shared.screen= Screen.FAVOURITE_BOOKS;
                openedScreens.add(Screen.FAVOURITE_BOOKS);
                break;
            case CATEGORIES:
                StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource("../view/CategoriesView.fxml")));
                Shared.screen= Screen.CATEGORIES;
                openedScreens.add(Screen.CATEGORIES);
                break;
            case HISTORY:
                StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource("../view/HistoryView.fxml")));
                Shared.screen= Screen.HISTORY;
                openedScreens.add(Screen.HISTORY);
                break;
            case HELP:
                StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource("../view/HelpView.fxml")));
                Shared.screen= Screen.HELP;
                openedScreens.add(Screen.HELP);
                break;
            case SETTINGS:
                StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource("../view/SettingsView.fxml")));
                Shared.screen= Screen.SETTINGS;
                openedScreens.add(Screen.SETTINGS);
                break;
            case ADD_A_BOOK:
                StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource("../view/AddABookView.fxml")));
                Shared.screen= Screen.ADD_A_BOOK;
                openedScreens.add(Screen.ADD_A_BOOK);
                break;
            case VIEW_BOOK:
                StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource("../view/ViewBookView.fxml")));
                Shared.screen= Screen.VIEW_BOOK;
                openedScreens.add(Screen.VIEW_BOOK);
                break;
            default:
                break;
        }
    }

    public static void onBack() {
        if (openedScreens.size() > 0) {
            openedScreens.remove(openedScreens.size() - 1);
            try {
                ScreenController.setScreen(ScreenController.openedScreens.get(openedScreens.size() - 1));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}