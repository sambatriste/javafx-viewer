package imageviewer

import imageviewer.archive.ImageArchive
import javafx.scene.image.Image

/**
 * 見開きページのイテレータ。
 */
class DoublePageIterator implements ListIterator<DoublePage> {

  /** 読み込み元となる画像アーカイブ */
  final ImageArchive archive;

  /**
   * コンストラクタ。
   * @param archive 読み込み元となる画像アーカイブ
   */
  DoublePageIterator(ImageArchive archive) {
    this.archive = archive
  }

  @Override
  boolean hasNext() {
    return archive.hasNext()
  }

  @Override
  DoublePage next() {
    NamedImage right = archive.next()
    NamedImage left = archive.next()
    return new DoublePage(left, right)
  }

  @Override
  boolean hasPrevious() {
    return archive.hasPrevious()
  }

  @Override
  DoublePage previous() {
    Image nop = archive.previous()
    NamedImage left = archive.previous()
    NamedImage right = archive.previous()
    return new DoublePage(left, right)
  }

  @Override
  int nextIndex() {
    return archive.nextIndex()
  }

  @Override
  int previousIndex() {
    def i = archive.previousIndex()
    return i == 0 ?
            0 :
            (i - 1)
  }

  @Override
  @Deprecated
  void remove() {
    throw new UnsupportedOperationException()
  }

  @Override
  @Deprecated
  void set(DoublePage doublePage) {
    throw new UnsupportedOperationException()
  }

  @Override
  @Deprecated
  void add(DoublePage doublePage) {
    throw new UnsupportedOperationException()
  }
}
