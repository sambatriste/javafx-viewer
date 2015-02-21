package imageviewer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * メインクラス。
 */
public class ImageViewerFXML extends Application {

    /** FXMLローダ */
    private FXMLLoader loader;

    /**
     * コンストラクタ。
     *
     * @throws IOException リソース読み込みに失敗した場合
     */
    public ImageViewerFXML() throws IOException {

        // FXMLファイルをロードする
        loader = new FXMLLoader();
        loader.load(getClass().getResourceAsStream("View.fxml"));
        controller = loader.getController();
    }

    ViewController controller;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Image Viewer FXML");

        Parent root = prepareParent();
        Scene scene = prepareScene(root);

        stage.setScene(scene);
        stage.show();
    }

    private Parent prepareParent() throws IOException {
        BorderPane root = loader.getRoot();

        root.widthProperty().addListener(controller.widthListener);
        root.heightProperty().addListener(controller.heightListener);
        return root;
    }

    private Scene prepareScene(Parent root) {
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(controller::handleKeyPress);
        return scene;
    }

    /**
     * メインメソッド。
     *
     * @param args プログラム引数
     */
    public static void main(String[] args) {
        launch(ImageViewerFXML.class, args);
    }
}
