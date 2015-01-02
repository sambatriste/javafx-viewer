package imageviewer

import java.util.zip.ZipEntry
import java.util.zip.ZipFile
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

    def entries = zip.entries().findAll { ZipEntry e ->
      return !e.directory
    }
    entries.sort(new Comparator<ZipEntry>() {
      @Override
      int compare(ZipEntry o1, ZipEntry o2) {
        return o1.name.compareTo(o2.name)
      }
    })
    return Collections.unmodifiableList(entries)
  }

  @Override
  NamedImage getAt(int index) {
    assert isInRange(index)
    String nameOfEntry = entries.get(index)
    def entry = zipFile.getEntry(nameOfEntry)
    return new NamedImage(entry.name, zipFile.getInputStream(entry));
  }

  private boolean isInRange(int index) {
    return 0 <= index && index <= lastIndex
  }

  @Override
  NamedImage next() {
    index = nextIndex()
    return getAt(index)
  }

  @Override
  NamedImage previous() {
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
  void set(NamedImage image) {
    throw new UnsupportedOperationException()
  }

  @Override
  void add(NamedImage image) {
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
