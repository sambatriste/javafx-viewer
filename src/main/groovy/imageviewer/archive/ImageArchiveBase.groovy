package imageviewer.archive

import imageviewer.NamedImage

class ImageArchiveBase<E> implements ImageArchive {

  /** Zipファイル内のエントリ */
  private final List<E> entries

  /** エントリのインデックス*/
  private int index = -1;

  private final Archiver<E> archiver
  /**
   * コンストラクタ。
   * @param source 入力元ファイル
   */
  ImageArchiveBase(Archiver<E> archiver) {
    this.entries = archiver.createEntries()
    this.archiver = archiver
  }

  @Override
  NamedImage getAt(int index) {
    assert isInRange(index)
    E entry = entries.get(index)
    return archiver.createImageOf(entry)
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

  @Override void close() {
    archiver.close()
  }

}
