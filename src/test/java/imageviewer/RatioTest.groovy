package imageviewer

import org.junit.Test

/**
 * Created with IntelliJ IDEA.
 * User: kawasaki
 * Date: 15/01/02
 * Time: 15:33
 */
class RatioTest extends GroovyTestCase {

  @Test
  void test() {
    def ratio = new Ratio(1, 2)

    assert ratio.width == 1
    assert ratio.height == 2
    assert ratio.getHeightOfWidth(2) == 4
    assert ratio.getWidthOfHeight(1) == 0.5
  }
}
