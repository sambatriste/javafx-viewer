package imageviewer

import org.junit.Test

class ZipEntryWrapperTest {

  @Test
  void test() {
    def first = new ZipEntryComparator.ZipEntryWrapper("2.jpg")
    def tenth = new ZipEntryComparator.ZipEntryWrapper("10.jpg")
    assert first.compareTo(tenth) == -1
  }
}
