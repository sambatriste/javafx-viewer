package imageviewer.zip

import javafx.scene.image.Image
import org.junit.After
import org.junit.Test
/**
 * Created with IntelliJ IDEA.
 * User: kawasaki
 * Date: 14/12/31
 * Time: 22:16
 */
class ZippedArchiveTest extends GroovyTestCase {

  def file = new File(getClass().getResource('/img.zip').toURI())
  ZippedArchive sut = new ZippedArchive(file)

  @Test
  void testNext() {
    assert !sut.hasPrevious() : "初期状態には前のイメージはない"

    assert sut.hasNext()
    Image first = sut.next()
    assert first != null
    assert !sut.hasPrevious()

    assert sut.hasNext()
    Image second = sut.next()
    assert second != null
    assert sut.hasPrevious()

    assert !sut.hasNext()
  }


  @After
  void tearDown() {
    sut.close()
  }
}
