package imageviewer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ImageViewerFXML extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Image Viewer FXML");

        // FXMLファイルをロードする
        FXMLLoader loader = new FXMLLoader();
        loader.load(getClass().getResourceAsStream("View.fxml"));
        BorderPane root = loader.getRoot();
        Scene scene = new Scene(root);
        ViewController controller = loader.getController();
        scene.setOnKeyReleased(controller::handleKeyPress);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(ImageViewerFXML.class, args);
    }
}
