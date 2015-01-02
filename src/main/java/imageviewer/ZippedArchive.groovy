package imageviewer
import javafx.scene.image.Image

import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import imageviewer.ImageArchive;
/**
 * Created with IntelliJ IDEA.
 * User: kawasaki
 * Date: 14/12/31
 * Time: 18:53
 */
class ZippedArchive implements ImageArchive {

  private final File source
  private final ZipFile zipFile
  private final List<ZipEntry> entries
  private int index = -1;

  ZippedArchive(File source) {
    this.source = source
    zipFile = new ZipFile(source)
    entries = extractEntriesOf(zipFile)
  }

  private static List<ZipEntry> extractEntriesOf(ZipFile zip) {
    return zip.entries().findAll { ZipEntry e ->
      return !e.directory
    } as List<ZipEntry>
  }

  @Override
  Image getAt(int index) {
    assert isInRange(index)
    String nameOfEntry = entries.get(index)
    def entry = zipFile.getEntry(nameOfEntry)
    return new Image(zipFile.getInputStream(entry));
  }

  private boolean isInRange(int index) {
    return 0 <= index && index <= lastIndex
  }

  @Override
  Image next() {
    index = nextIndex()
    return getAt(index)
  }

  @Override
  Image previous() {
    index = previousIndex()
    return getAt(index)
  }


  @Override
  int nextIndex() {
    return hasNext() ? index + 1 : index
  }

  @Override
  int previousIndex() {
    return hasPrevious() ? index - 1 : 0
  }

  @Override
  void remove() {
    throw new UnsupportedOperationException()
  }

  @Override
  void set(Image image) {
    throw new UnsupportedOperationException()
  }

  @Override
  void add(Image image) {
    throw new UnsupportedOperationException()
  }

  @Override
  boolean hasPrevious() {
    return index > 0
  }

  @Override
  boolean hasNext() {
    return index < lastIndex
  }

  private int getLastIndex() {
    numberOfPictures - 1
  }

  private int getNumberOfPictures() {
    return entries.size()
  }

  @Override
  void close() {
    zipFile.close()
  }
}
