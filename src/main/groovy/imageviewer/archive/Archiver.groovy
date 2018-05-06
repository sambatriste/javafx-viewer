package imageviewer.archive

import imageviewer.NamedImage

interface Archiver<E> extends Closeable {
  List<E> createEntries()

  NamedImage createImageOf(E entry)
}