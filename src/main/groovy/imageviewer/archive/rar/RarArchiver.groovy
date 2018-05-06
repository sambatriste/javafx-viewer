package imageviewer.archive.rar

import com.github.junrar.Archive
import com.github.junrar.rarfile.FileHeader
import imageviewer.NamedImage
import imageviewer.archive.Archiver

class RarArchiver implements Archiver<FileHeader> {
  
  private final Archive rarArchive
  
  RarArchiver(File file) {
    this.rarArchive = new Archive(file)
  }
  
  @Override
  List<FileHeader> createEntries() {
    List<FileHeader> entries = rarArchive.fileHeaders.findAll { FileHeader e ->
      return !e.directory
    }
    entries.sort(new RarEntryComparator())
    return Collections.unmodifiableList(entries)
  }

  @Override
  NamedImage createImageOf(FileHeader entry) {
    String fileName = getFileNameOf(entry)
    InputStream inputStream = rarArchive.getInputStream(entry)
    return new NamedImage(fileName, inputStream)
  }

  @Override
  void close() throws IOException {
    rarArchive.close()
  }

  static String getFileNameOf(FileHeader header) {
    String fileName = header.fileNameW
    if (fileName.isEmpty()) {
      fileName = header.fileNameString
    }
    return fileName
  }
}
