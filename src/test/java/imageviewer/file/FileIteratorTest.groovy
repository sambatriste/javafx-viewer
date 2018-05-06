package imageviewer.file

import groovy.transform.TypeChecked
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TemporaryFolder

/**
 * Created by kawasaki on 2015/04/05.
 */
@TypeChecked
class FileIteratorTest extends GroovyTestCase {

  @Rule
  public TemporaryFolder temp = new TemporaryFolder()
  File dir
  @Before
  void setUp() {
    temp.create()
    dir = temp.newFolder()
    touch(dir, 1000,"zzz.zip", "bbb.txt", "zippo", "aaa.zip")
  }

  void testSortAlphabetically() {
    FileIterator sut = new FileIterator(
            dir, SortOrder.ALPHABETIC, '.*\\.zip$')
    assert sut.hasNext()
    assert sut.next().name == 'aaa.zip'

    assert sut.hasNext()
    assert sut.next().name == 'zzz.zip'

    assert !sut.hasNext()
  }

  void testSortByTimestamp() {
    FileIterator sut = new FileIterator(
            dir, SortOrder.TIMESTAMP, '.*zip.*')
    File next
    assert sut.hasNext()

    next = sut.next()
    println "${next.name} : ${next.lastModified()}"
    assert next.name == 'zzz.zip'

    assert sut.hasNext()
    next = sut.next()
    println "${next.name} : ${next.lastModified()}"
    assert next.name == 'zippo'

    assert sut.hasNext()
    next = sut.next()
    println "${next.name} : ${next.lastModified()}"
    assert next.name == 'aaa.zip'
    assert !sut.hasNext()
  }

  private static void touch(File dir, long sleep, String... files) {
    for (String e : files) {
      File newFile = new File(dir, e)
      if (!newFile.exists()) {
        newFile.createNewFile()
        Thread.sleep(sleep)
        assert newFile.absolutePath : newFile.exists()
      }
    }
  }
}
