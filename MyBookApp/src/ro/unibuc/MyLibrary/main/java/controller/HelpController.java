package ro.unibuc.MyLibrary.main.java.controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import ro.unibuc.MyLibrary.main.java.common.ScreenController;
import ro.unibuc.MyLibrary.main.java.common.StageManager;
import ro.unibuc.MyLibrary.main.java.database.ReportsDatabase;
import ro.unibuc.MyLibrary.main.java.model.Report;
import ro.unibuc.MyLibrary.main.java.ui.AlertMessage;
import ro.unibuc.MyLibrary.main.java.utils.FTPUploader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author antoneandreas
 */

public class HelpController {

    @FXML
    private Line HeaderSeparator;
    @FXML
    private Line HeaderSeparator2;
    @FXML
    private TextArea FieldDescription;
    @FXML
    private ComboBox FieldCategory;
    private Path screenshotPath;
    private String screenshotName;
    private FTPUploader ftpUploader;

    public void initialize() {
        HeaderSeparator.setEndX(StageManager.getStage().getWidth()-200);
        HeaderSeparator2.setEndX(StageManager.getStage().getWidth()-200);
    }

    @FXML
    public void clear() {
        try {
            ScreenController.setScreen(ScreenController.Screen.HELP);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void send() {
        if (FieldCategory.getSelectionModel().getSelectedItem() == null) {
            new AlertMessage("Report a Problem Error", "You must enter a topic.", Alert.AlertType.ERROR);
        } else if (FieldDescription.getText().isEmpty()) {
            new AlertMessage("Report a Problem Error", "Please provide more details about the problem you are experiencing.", Alert.AlertType.ERROR);
        } else {
            //Get current date
            DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
            Date date = new Date();

            if (screenshotPath == null) {
                screenshotName = "N/A";
            } else {
                screenshotName =  dateFormat.format(date) + "_" + screenshotPath.getFileName();
            }
            //Add a report to the database
            if (ReportsDatabase.addReport(new Report(FieldCategory.getSelectionModel().getSelectedItem().toString(), FieldDescription.getText().toString(), screenshotName, Boolean.FALSE))) {
                new AlertMessage("Add a Book", "The book was uploaded successfully.", Alert.AlertType.INFORMATION);
                clear();
                if (screenshotPath != null) {
                    ftpUploader = null;
                    try {
                        ftpUploader = new FTPUploader("ftp.byethost9.com", "b9_21380244", "howtolove");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Task task = new Task() {
                        @Override
                        protected Object call() throws Exception {
                            //Upload book and cover

                            //FTP server path is relative. So if FTP account HOME directory is "/home/pankaj/public_html/" and you need to upload
                            // files to "/home/pankaj/public_html/wp-content/uploads/image2/", you should pass directory parameter as "/wp-content/uploads/image2/"
                            try {
                                if (screenshotPath != null) {
                                    ftpUploader.uploadFile(screenshotPath.toString(), screenshotName, "/htdocs/reports/");
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
                }
            } else {
                new AlertMessage("Report a Problem Error", "Please try again.", Alert.AlertType.ERROR);
                clear();
            }
        }
    }

    @FXML
    public void openScreenshot() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("View Images");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            screenshotPath = file.toPath();
            BufferedImage bufferedImage = null;
            try {
                bufferedImage = ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}