package imageviewer.archive.zip

import groovy.transform.TypeChecked
import imageviewer.NamedImage
import imageviewer.archive.Archiver

import java.nio.charset.Charset
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

@TypeChecked
class ZipArchiver implements Archiver<ZipEntry> {

  private final ZipFile zipFile
  ZipArchiver(File source) {
    this.zipFile = new ZipFile(source, Charset.forName('windows-31j'))
  }

  /**
   * 指定されたファイルからエントリを抽出する。
   * 順序はエントリ名の辞書順となる。
   *
   * @param zip 入力元Zip
   * @return エントリ
   */
  @Override
  List<ZipEntry> createEntries() {
    List<ZipEntry> entries = zipFile.entries().findAll { ZipEntry e ->
      return !e.directory
    } as List<ZipEntry>
    entries.sort(new ZipEntryComparator())
    return Collections.unmodifiableList(entries)
  }

  @Override
  NamedImage createImageOf(ZipEntry entry) {
    String name = entry.name
    InputStream inputStream = zipFile.getInputStream(entry)
    return new NamedImage(name, inputStream)
  }

  @Override
  void close() throws IOException {
    zipFile.close()
  }
}
