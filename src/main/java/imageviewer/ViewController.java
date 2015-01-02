package imageviewer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuBar;
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
    private MenuBar menuBar;

    @FXML
    private CheckMenuItem fitSizeItem;

    @FXML
    private ImageView leftView;

    @FXML
    private ImageView rightView;

    private final FileChooser chooser = new FileChooser();

    @FXML
    public void handleExit(ActionEvent event) {
        Platform.exit();
    }

//    ImageArchive archive;
    private DoublePageIterator pageItr;

    @FXML
    public void handleOpen(ActionEvent event) {
        File file = chooser.showOpenDialog(null);
        if (file == null) {
            return;
        }
        ImageArchive archive = new ZippedArchive(file);
        pageItr = new DoublePageIterator(archive);
        nextImage();
    }

    private void setImages(DoublePage doublePage) {
        NamedImage left = doublePage.getLeft();
        System.out.println("left = " + left);
        leftView.setImage(left);

        NamedImage right = doublePage.getRight();
        System.out.println("right = " + right);
        rightView.setImage(right);
    }

    @FXML
    public void handleFitSize(ActionEvent event) {
//        if (fitSizeItem.isSelected()) {
//            view.setFitWidth(FIT_WIDTH);
//            view.setFitHeight(FIT_HEIGHT);
//        } else {
//            view.setFitWidth(0);
//            view.setFitHeight(0);
//        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
        if (pageItr.hasNext()) {
            DoublePage doublePage = pageItr.next();
            setImages(doublePage);
        } else {
            System.out.println("no more image." );
        }
    }

    private void previousImage() {
        if (pageItr.hasPrevious()) {
            DoublePage doublePage = pageItr.previous();
            setImages(doublePage);
        } else {
            System.out.println("no more image before.");
        }
    }

}
