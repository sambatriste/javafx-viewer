package imageviewer
/**
 * 見開きページ
 */
class DoublePage {

  /** 左側のイメージ */
  final NamedImage left

  /** 右側のイメージ */
  final NamedImage right

  /**
   * コンストラクタ。
   * @param left 左側のイメージ
   * @param right 右側のイメージ
   */
  DoublePage(NamedImage left, NamedImage right) {
    this.left = left
    this.right = right
  }
}
