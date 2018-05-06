package imageviewer;

import imageviewer.archive.Archiver;
import imageviewer.archive.ImageArchive;
import imageviewer.archive.ImageArchiveBase;
import imageviewer.archive.rar.RarArchiver;
import imageviewer.archive.zip.ZipArchiver;
import imageviewer.file.FileDeleter;
import imageviewer.file.FileIterator;
import imageviewer.file.SortOrder;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;

/**
 * コントロールクラス。
 */
public class ViewController implements Initializable {


    @FXML
    private ScrollPane main;

    @FXML
    private MenuBar menuBar;

    @FXML
    private ImageView leftView;

    @FXML
    private ImageView rightView;

    private final FileChooser chooser = new FileChooser();

    private File currentFile;
    private FileIterator fileIterator;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void handleExit(ActionEvent event) {
        Platform.exit();
    }

    private DoublePageIterator pageItr;

    @FXML
    public void handleOpen(ActionEvent event) {
        File chosen = chooser.showOpenDialog(null);
        if (chosen == null) {
            return;

        }
        fileIterator = FileIterator.fromChosenFile(chosen, SortOrder.ALPHABETIC, "(.+\\.zip$|.+\\.rar$)");
        open(chosen);
    }

    private void open(File file) {
        currentFile = file;
        Archiver<?> archiver = createArchiver(file);
        ImageArchive archive = new ImageArchiveBase<>(archiver);
        pageItr = new DoublePageIterator(archive);
        stage.setTitle(file.getName());
        nextImage();
        onChangeWidth(leftView, leftView.getFitWidth());
        onChangeWidth(rightView, rightView.getFitWidth());

    }
    private Archiver<?> createArchiver(File file) {
        if (file.getName().endsWith(".zip")) {
            return new ZipArchiver(file);
        } else if (file.getName().endsWith(".rar")) {
            return new RarArchiver(file);
        }
        throw new IllegalArgumentException("unexpected file. " + file);
    }

    @FXML
    public void handleDelete(ActionEvent event) throws IOException {
        if (currentFile == null) {
            return;
        }
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("削除確認");
        DialogPane dialog = alert.getDialogPane();
        dialog.setContentText("対象ファイル=[" + currentFile.getName() + "]\n" +
                                      "ファイルを消しますか？");
        ButtonType buttonType = alert.showAndWait().orElse(ButtonType.CANCEL);
        if (!buttonType.equals(ButtonType.OK)) return;


        //assert currentFile.delete();
        FileDeleter.delete(currentFile);
        System.out.println("file deleted. [" + currentFile.getName() + "]");
        fileIterator.remove();
        nextFile();
    }

    @FXML
    public void handleNextFile(ActionEvent event) {
        nextFile();
    }

    private void nextFile() {
        if (!isOpened() || !fileIterator.hasNext()) {
            return;
        }
        File f = fileIterator.next();
        if (f.equals(currentFile)) {
            f = fileIterator.next();
        }
        open(f);
    }

    @FXML
    public void handlePreviousFile(ActionEvent event) {
        if (!isOpened() || !fileIterator.hasPrevious()) {
            return;
        }
        File f = fileIterator.previous();
        if (f.equals(currentFile)) {
            f = fileIterator.previous();
        }
        open(f);
    }

    private boolean isOpened() {
        return currentFile != null;
    }

    private void setImages(DoublePage doublePage) {
        NamedImage left = doublePage.getLeft();
        System.out.println("left = " + left);
        leftView.setImage(left);

        NamedImage right = doublePage.getRight();
        System.out.println("right = " + right);
        rightView.setImage(right);

        resize();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
                "Zip", "*.zip", "*.rar");
        chooser.getExtensionFilters().add(filter);
        chooser.setInitialDirectory(initialDir());
    }

    private File initialDir() {
        File home = new File(System.getProperty("user.home"));
        assert home.exists();
        return home;

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

    @FXML
    public void nextImage() {
        if (pageItr.hasNext()) {
            DoublePage doublePage = pageItr.next();
            setImages(doublePage);
        } else {
            System.out.println("no more image.");
        }
    }

    @FXML
    public void previousImage() {
        if (pageItr.hasPrevious()) {
            DoublePage doublePage = pageItr.previous();
            setImages(doublePage);
        } else {
            System.out.println("no more image before.");
        }
    }

    private void resize() {
        onChangeWidth(leftView, leftView.getFitWidth());
        onChangeWidth(rightView, rightView.getFitWidth());
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
    private void onChangeHeight(ImageView imageView, Number newHeight) {
        Ratio ratio = new Ratio(imageView.getImage());
        double height = newHeight.doubleValue();
        double width = ratio.getWidthFrom(height);
        resetSize(imageView, width, height);
    }
    private void onChangeWidth(ImageView imageView, Number newWidth) {
        Ratio ratio = new Ratio(imageView.getImage());
        double width = newWidth.doubleValue();
        double height = ratio.getHeightFrom(width);
        resetSize(imageView, width , height);
    }

    private void resetSize(ImageView imageView, double width, double height) {
        height -= 40;
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
