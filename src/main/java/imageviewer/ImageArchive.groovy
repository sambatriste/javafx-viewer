package imageviewer
/**
 * Created with IntelliJ IDEA.
 * User: kawasaki
 * Date: 14/12/31
 * Time: 20:06
 */
interface ImageArchive extends ListIterator<NamedImage>, Closeable {

  NamedImage getAt(int index)
}