package imageviewer

import javafx.scene.image.Image

/**
 * Created with IntelliJ IDEA.
 * User: kawasaki
 * Date: 15/01/02
 * Time: 12:59
 */
class DoublePageIterator implements ListIterator<DoublePage> {

  final ImageArchive archive;

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
  void remove() {
    throw new UnsupportedOperationException()
  }

  @Override
  void set(DoublePage doublePage) {
    throw new UnsupportedOperationException()
  }

  @Override
  void add(DoublePage doublePage) {
    throw new UnsupportedOperationException()
  }
}
