package imageviewer

import com.sun.org.apache.bcel.internal.generic.NOP
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
    // 前回が逆方向の場合、1回を多く読み込んで数合わせする
    if (!forwarding) {
      archive.next() // NOP
    }
    forwarding = true

    NamedImage right = archive.next()
    NamedImage left = hasNext() ? archive.next() : null
    return new DoublePage(left, right)
  }

  @Override
  boolean hasPrevious() {
    return archive.hasPrevious()
  }

  /** 前回のページ送りの方向 */
  private boolean forwarding = true

  @Override
  DoublePage previous() {
    // 前回が順方向の場合、1回を多く読み込んで数合わせする
    if (forwarding) {
      archive.previous()  // NOP
    }
    forwarding = false

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
