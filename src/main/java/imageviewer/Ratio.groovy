package imageviewer

import javafx.scene.image.Image

class Ratio {
  final double width;
  final double height;

  Ratio(Image image) {
    this(image.width, image.height)
  }

  Ratio(double width, double height) {
    this.width = width
    this.height = height
  }

  double getHeightOfWidth(double newWidth) {
    return newWidth * height / width
  }

  double getWidthOfHeight(double newHeight) {
    return width * newHeight / height
  }


  @Override
  public String toString() {
    return "Ratio{" +
            "width=" + width +
            ", height=" + height +
            '}';
  }
}