package imageviewer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;

public class ImageViewer extends Application {
    private final static int FIT_WIDTH = 290;
    private final static int FIT_HEIGHT = 370;

    private ImageView view;
    
    @Override
    public void start(Stage stage) {
        stage.setTitle("Image Viewer");
        
        BorderPane root = new BorderPane();
        root.setPrefSize(300, 400);
        
        // メニューバーをアプリケーション上部に表示させる
        root.setTop(initializeMenu(stage));

        // イメージをアプリケーション中央に表示させる
        root.setCenter(initializeImage());
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    private MenuBar initializeMenu(final Stage stage) {
        final MenuBar bar = new MenuBar();

        Menu fileMenu = new Menu("_File");

        // イメージファイルに対するファイルチューザ
        final FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image", "*.jpg", "*.gif", "*.png");
        chooser.getExtensionFilters().add(filter);

        // Open項目でファイルチューザを表示する
        MenuItem openItem = new MenuItem("_Open");
        openItem.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        openItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                try {
                    File file = chooser.showOpenDialog(stage);
                    if (file != null) {
                        Image image = new Image(file.toURL().toString());
                        view.setImage(image);
                    }
                } catch (MalformedURLException ex) {
                    System.err.println("File isn't suitable for ImageViewer.");
                }
            }
        });

        MenuItem exitItem = new MenuItem("E_xit");
        exitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        exitItem.setOnAction(t -> {
            Platform.exit();
        });

        fileMenu.getItems().addAll(openItem, exitItem);
        
        Menu editMenu = new Menu("_Edit");

        // イメージを規定サイズに縮小するかどうかを設定する
        final CheckMenuItem fitImageItem = new CheckMenuItem("Fit Size");
        // デフォルトは縮小させる
        fitImageItem.setSelected(true);
        fitImageItem.setOnAction(t -> {
            if (fitImageItem.isSelected()) {
                view.setFitWidth(FIT_WIDTH);
                view.setFitHeight(FIT_HEIGHT);
            } else {
                view.setFitWidth(0);
                view.setFitHeight(0);
            }
        });
        editMenu.getItems().addAll(fitImageItem);
        
        // [File]と[Edit]メニューをメニューバーに追加
        bar.getMenus().addAll(fileMenu, editMenu);

        return bar;
    }
    
    private Node initializeImage() {
        view = new ImageView();
        
        // イメージの表示サイズを指定する
        view.setFitWidth(FIT_WIDTH);
        view.setFitHeight(FIT_HEIGHT);

        // イメージの縦横比を保持したままスケールさせる
        view.setPreserveRatio(true);
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(view);
        
        return scrollPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
