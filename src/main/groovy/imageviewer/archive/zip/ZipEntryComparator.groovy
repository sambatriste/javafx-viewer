package imageviewer.archive.zip

import groovy.transform.TypeChecked
import imageviewer.archive.EntryWrapper

import java.util.zip.ZipEntry

@TypeChecked
class ZipEntryComparator implements Comparator<ZipEntry> {
  @Override
  int compare(ZipEntry o1, ZipEntry o2) {
    def w1 = new ZipEntryWrapper(o1.name)
    def w2 = new ZipEntryWrapper(o2.name)
    return w1.compareTo(w2)
  }

  static class ZipEntryWrapper extends EntryWrapper<ZipEntry> {
    ZipEntryWrapper(String fileName) {
      super(fileName, "/")
    }
  }
}
