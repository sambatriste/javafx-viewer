package imageviewer

import javafx.scene.image.Image
/**
 * Created with IntelliJ IDEA.
 * User: kawasaki
 * Date: 15/01/02
 * Time: 13:38
 */
class NamedImage extends Image {

  final String name

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
