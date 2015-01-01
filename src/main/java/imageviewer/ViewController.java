package imageviewer;

import imageviewer.zip.ZippedArchive;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewController implements Initializable {
    private final static int FIT_WIDTH = 290;
    private final static int FIT_HEIGHT = 370;

    @FXML
    private MenuBar bar;
    @FXML
    private CheckMenuItem fitSizeItem;
    @FXML
    private ImageView view;

    private FileChooser chooser;

    @FXML
    public void handleExit(ActionEvent event) {
        Platform.exit();
    }

    ImageArchive archive;

    @FXML
    public void handleOpen(ActionEvent event) {

        File file = chooser.showOpenDialog(null);
        if (file == null) {
            return;
        }
        archive = new ZippedArchive(file);
        Image next = archive.next();
        view.setImage(next);
    }

    @FXML
    public void handleFitSize(ActionEvent event) {
        if (fitSizeItem.isSelected()) {
            view.setFitWidth(FIT_WIDTH);
            view.setFitHeight(FIT_HEIGHT);
        } else {
            view.setFitWidth(0);
            view.setFitHeight(0);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ImageViewのサイズを指定
        view.setFitWidth(FIT_WIDTH);
        view.setFitHeight(FIT_HEIGHT);

        // ファイルチューザの生成
        chooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Zip", "*.zip");
        chooser.getExtensionFilters().add(filter);
        chooser.setInitialDirectory(new File("."));
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                nextImage();
                break;
            case RIGHT:
                previousImage();
                break;
            default:
                break;
        }

        System.out.println("event = " + event);
    }

    private void nextImage() {
        if (archive.hasNext()) {
            Image next = archive.next();
            view.setImage(next);
        } else {
            System.out.println("no more image." );
        }
    }

    private void previousImage() {
        if (archive.hasPrevious()) {
            Image previous = archive.previous();
            view.setImage(previous);
        } else {
            System.out.println("no more image before.");
        }
    }

    private void setImage(Image image) {


        view.setImage(image);
    }
}
