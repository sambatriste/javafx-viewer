package imageviewer

import javafx.scene.image.Image
/**
 * 名前付きの{@link Image}クラス。
 */
class NamedImage extends Image {

  /** 画像の名前 */
  final String name

  /**
   * コンストラクタ。
   * @param name 名前
   * @param is 画像の入力ストリーム
   */
  NamedImage(String name, InputStream is) {
    super(is)
    this.name = name
  }

  @Override
  public String toString() {
    return "NamedImage{" +
            "name='" + name + '\'' +
            '}';
  }

}
