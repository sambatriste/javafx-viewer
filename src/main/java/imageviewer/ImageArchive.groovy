package imageviewer

import javafx.scene.image.Image

/**
 * Created with IntelliJ IDEA.
 * User: kawasaki
 * Date: 14/12/31
 * Time: 20:06
 */
interface ImageArchive extends ListIterator<Image>, Closeable {

  Image getAt(int index)
}