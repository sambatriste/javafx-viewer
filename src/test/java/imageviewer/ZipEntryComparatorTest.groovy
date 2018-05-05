package imageviewer

import org.junit.Test

import java.util.zip.ZipEntry

class ZipEntryComparatorTest {

  ZipEntryComparator sut = new ZipEntryComparator()

  @Test
  void test() {
    def zipEntries = ['002.jpg', '001.jpg', '02.jpg', '01.jpg'].collect({ new ZipEntry(it) })

    zipEntries.sort(sut)

    def actual = zipEntries.collect({ it.toString() })
    assert actual == ['01.jpg', '02.jpg', '001.jpg', '002.jpg']
  }
}
