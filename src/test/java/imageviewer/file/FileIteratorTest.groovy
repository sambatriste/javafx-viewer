package imageviewer.file

import org.junit.Before
import org.junit.Rule
import org.junit.rules.TemporaryFolder

/**
 * Created by kawasaki on 2015/04/05.
 */
class FileIteratorTest extends GroovyTestCase {

  @Rule
  public TemporaryFolder temp = new TemporaryFolder()
  File dir
  @Before
  public void setUp() {
    temp.create()
    dir = temp.newFolder()
    touch(dir, "zzz.zip", "bbb.txt", "zippo", "aaa.zip")
  }

  void testSortAlphabetically() {
    FileIterator sut = new FileIterator(
            dir, FileIterator.SortOrder.ALPHABETIC, '.*\\.zip$')
    assert sut.hasNext()
    assert sut.next().name == 'aaa.zip'

    assert sut.hasNext()
    assert sut.next().name == 'zzz.zip'

    assert !sut.hasNext()
  }

  void testSortByTimestamp() {
    FileIterator sut = new FileIterator(
            dir, FileIterator.SortOrder.TIMESTAMP, '.*zip.*')

    assert sut.hasNext()
    assert sut.next().name == 'zzz.zip'

    assert sut.hasNext()
    assert sut.next().name == 'zippo'

    assert sut.hasNext()
    assert sut.next().name == 'aaa.zip'

    assert !sut.hasNext()
  }

  private static void touch(File dir, String... files) {
    for (String e : files) {
      File newFile = new File(dir, e)
      if (!newFile.exists()) {
        newFile.createNewFile()
        Thread.sleep(500)
        assert newFile.absolutePath : newFile.exists()
      }
    }
  }
}
