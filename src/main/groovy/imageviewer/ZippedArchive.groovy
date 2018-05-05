package imageviewer

import java.nio.charset.Charset
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
/**
 * Zip圧縮された画像イメージ
 */
class ZippedArchive implements ImageArchive {

  /** 入力元ファイル*/
  private final File source

  /** Zipファイル */
  private final ZipFile zipFile

  /** Zipファイル内のエントリ */
  private final List<ZipEntry> entries

  /** エントリのインデックス*/
  private int index = -1;

  /**
   * コンストラクタ。
   * @param source 入力元ファイル
   */
  ZippedArchive(File source) {
    this.source = source
    zipFile = new ZipFile(source, Charset.forName('windows-31j'))
    entries = extractEntriesOf(zipFile)
  }

  /**
   * 指定されたファイルからエントリを抽出する。
   * 順序はエントリ名の辞書順となる。
   *
   * @param zip 入力元Zip
   * @return エントリ
   */
  private static List<ZipEntry> extractEntriesOf(ZipFile zip) {

    def entries = zip.entries().findAll { ZipEntry e ->
      return !e.directory
    }
    entries.sort(new ZipEntryComparator())
    println entries
    return Collections.unmodifiableList(entries)
  }


  @Override
  NamedImage getAt(int index) {
    assert isInRange(index)
    ZipEntry entry = entries.get(index)
    return new NamedImage(entry.name, zipFile.getInputStream(entry));
  }

  /**
   * 指定されたインデックスが範囲内かどうか判定する。
   * @param index インデックス
   * @return 範囲内である場合、真
   */
  private boolean isInRange(int index) {
    return 0 <= index && index <= lastEffectiveIndex
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
  @Deprecated
  void remove() {
    throw new UnsupportedOperationException()
  }

  @Override
  @Deprecated
  void set(NamedImage image) {
    throw new UnsupportedOperationException()
  }

  @Override
  @Deprecated
  void add(NamedImage image) {
    throw new UnsupportedOperationException()
  }

  @Override
  boolean hasPrevious() {
    return index > 0
  }

  @Override
  boolean hasNext() {
    return index < lastEffectiveIndex
  }

  /**
   * エントリ終端のインデックスを取得する。
   * @return
   */
  private int getLastEffectiveIndex() {
    return entries.size() - 1
  }

  @Override
  void close() {
    zipFile.close()
  }
}
