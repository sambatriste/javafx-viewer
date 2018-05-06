package imageviewer.archive.zip

import java.util.regex.Pattern
import java.util.zip.ZipEntry

class ZipEntryComparator implements Comparator<ZipEntry> {
  @Override
  int compare(ZipEntry o1, ZipEntry o2) {
    def w1 = new ZipEntryWrapper(o1)
    def w2 = new ZipEntryWrapper(o2)
    return w1.compareTo(w2)
  }


  static class ZipEntryWrapper implements Comparable<ZipEntryWrapper> {
    final String name
    final String rawName
    final String prefix

    ZipEntryWrapper(ZipEntry zipEntry) {
      this(zipEntry.name)
    }

    ZipEntryWrapper(String fileName) {
      def first = fileName.lastIndexOf("/")
      first = (first == -1) ? 0 : first + 1
      def last = fileName.lastIndexOf(".")
      assert first <= last
      this.rawName = fileName.substring(first, last)
      this.prefix = fileName.substring(0, first)
      this.name = fileName
    }


    private static final Pattern NUM_REGEXP = Pattern.compile("[0-9]+")

    private boolean isInteger() {
      return NUM_REGEXP.matcher(rawName).matches()
    }
    private Integer parseInt() {
      return Integer.parseInt(rawName)
    }

    @Override
    int compareTo(ZipEntryWrapper o) {

      if (this.prefix == o.prefix) {
        if (this.integer && o.integer) {
          if (this.name.length() == o.name.length()) {
            return this.parseInt().compareTo(o.parseInt())
          } else {
            return name.length().compareTo(o.name.length())
          }
        }
      }
      return this.name.compareTo(o.name)
    }
  }
}
