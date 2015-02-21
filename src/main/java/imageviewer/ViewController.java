package imageviewer;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * コントロールクラス。
 */
public class ViewController implements Initializable {

    @FXML
    DoublePageHBox doublePage;

    @FXML
    private ScrollPane main;

    @FXML
    private MenuBar menuBar;

    @FXML
    private ImageView leftView;

    @FXML
    private ImageView rightView;

    private final FileChooser chooser = new FileChooser();


    @FXML
    public void handleExit(ActionEvent event) {
        Platform.exit();
    }

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Zip", "*.zip");
        chooser.getExtensionFilters().add(filter);
        chooser.setInitialDirectory(initialDir());
    }

    private File initialDir() {
        File home = new File(System.getProperty("user.home"));
        File ini = new File(home, "Downloads");
        assert ini.exists() : ini.getAbsolutePath();
        return ini;

    }

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
            System.out.println("no more image.");
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

    /**
     * ウィンドウのサイズを受け取る。
     * ウィンドウの横幅　＞　画像の横幅のとき、横幅を縮小する。
     * ウィンドウの縦幅　＞　縮小した縦幅のとき、を縮小する。
     * どちらか
     */
    public final ChangeListener<Number> heightListener = (observable, oldValue, newValue) -> {
        System.out.println("height = " + newValue);
        onChangeHeight(leftView, newValue);
        onChangeHeight(rightView, newValue);
    };
    private void onChangeHeight(ImageView imageView, Number newValue) {
        Ratio ratio = new Ratio(imageView.getImage());
        double height = newValue.doubleValue();
        double width = ratio.getWidthOfHeight(height);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
    }
    private void onChangeWidth(ImageView imageView, Number newValue) {

        Ratio ratio = new Ratio(imageView.getImage());
        double width = newValue.doubleValue();
        double height = ratio.getHeightOfWidth(width);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
    }

    public final ChangeListener<Number> widthListener = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            System.out.println("width = " + newValue);
            onChangeWidth(leftView, newValue);
            onChangeWidth(rightView, newValue);
        }
    };
}
