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
        BorderPane root = FXMLLoader.load(getClass().getResource("View.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
