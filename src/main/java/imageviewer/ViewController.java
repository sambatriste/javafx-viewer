package imageviewer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.MalformedURLException;
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

    @FXML
    public void handleOpen(ActionEvent event) {
        try {
            File file = chooser.showOpenDialog(null);
            if (file != null) {
                Image image = new Image(file.toURL().toString());
                view.setImage(image);
            }
        } catch (MalformedURLException ex) {
            System.err.println("File isn't suitable for ImageViewer.");
        }
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
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image", "*.jpg", "*.gif", "*.png");
        chooser.getExtensionFilters().add(filter);
chooser.setInitialDirectory(new File("c:\\NetBeansProjects"));
    }
}
