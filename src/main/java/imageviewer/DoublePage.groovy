package imageviewer
/**
 * Created with IntelliJ IDEA.
 * User: kawasaki
 * Date: 15/01/02
 * Time: 12:57
 */
class DoublePage {

  final NamedImage left
  final NamedImage right

  DoublePage(NamedImage left, NamedImage right) {
    this.left = left
    this.right = right
  }
}
