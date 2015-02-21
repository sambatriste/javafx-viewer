package imageviewer

import javafx.scene.layout.HBox
/**
 * 見開きページを持つ{@link HBox}。
 */
class DoublePageHBox extends HBox {

  DoublePageHBox() {
    super();
//    addListeners();
  }

//  private void addListeners() {
//    widthProperty().addListener(new ChangeListener<Number>() {
//      @Override
//      void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//        resizeOnWidthChange(oldValue, newValue)
//      }
//    });
//    heightProperty().addListener(new ChangeListener<Number>() {
//      @Override
//      void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//        resizeOnHeightChange(oldValue, newValue)
//      }
//    })
//  }

  void resizeOnWidthChange(Number oldValue, Number newValue) {

  }

  void resizeOnHeightChange(Number oldValue, Number newValue) {

  }
}
