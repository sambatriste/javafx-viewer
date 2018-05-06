package imageviewer.archive.rar

import com.github.junrar.rarfile.FileHeader
import groovy.transform.TypeChecked
import imageviewer.archive.EntryWrapper

class RarEntryComparator implements Comparator<FileHeader> {

  @Override
  int compare(FileHeader o1, FileHeader o2) {
    def w1 = new RarEntryWrapper(RarArchiver.getFileNameOf(o1))
    def w2 = new RarEntryWrapper(RarArchiver.getFileNameOf(o2))
    return w1.compareTo(w2)
  }

  @TypeChecked
  static class RarEntryWrapper extends EntryWrapper<FileHeader> {
    RarEntryWrapper(String fileName) {
      super(fileName, "\\")
    }
  }
}
