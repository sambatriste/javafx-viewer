package imageviewer.archive.zip

import org.junit.Test

class ZipEntryWrapperTest {

  @Test
  void test() {
    def second = new ZipEntryComparator.ZipEntryWrapper("2.jpg")
    def tenth = new ZipEntryComparator.ZipEntryWrapper("10.jpg")
    assert second.compareTo(tenth) == -1
  }
}
