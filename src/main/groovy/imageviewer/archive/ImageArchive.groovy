package imageviewer.archive

import imageviewer.NamedImage

/**
 * 画像アーカイブを表すインタフェース。
 */
interface ImageArchive extends ListIterator<NamedImage>, Closeable {

  NamedImage getAt(int index)
}