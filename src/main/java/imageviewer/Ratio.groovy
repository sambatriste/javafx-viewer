package imageviewer

import javafx.scene.image.Image

/**
 * 画像の縦横比率を表すクラス。
 */
class Ratio {

  /** 幅 */
  final double width;

  /** 高さ */
  final double height;

  /**
   * コンストラクタ。
   * @param image 画像
   */
  Ratio(Image image) {
    this(image?.width ?: 0, image?.height ?: 0)
  }

  /**
   * コンストラクタ。
   * @param width 幅
   * @param height 高さ
   */
  Ratio(double width, double height) {
    this.width = width
    this.height = height
  }

  /**
   * 与えられた幅に対応する高さを取得する。
   * @param newWidth 幅
   * @return 高さ
   */
  double getHeightOfWidth(double newWidth) {
    return newWidth * height / width
  }

  /**
   * 与えられた高さに対応する幅を取得する。
   * @param newHeight 高さ
   * @return 幅
   */
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