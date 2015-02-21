package imageviewer.file

import java.nio.file.Files

/**
 * Created by kawasaki on 2015/04/04.
 */
class FileDeleter {

  private static final File HOME = new File(System.getProperty('user.home'))
  private static final File TRASH = new File(HOME, '.Trash')

  static void delete(File fileToDelete) {
    if (hasTrash()) {
      moveToTrash(fileToDelete)
    } else {
      Files.delete(fileToDelete.toPath())
    }
  }

  private static boolean hasTrash() {
    return TRASH.exists() && TRASH.directory
  }

  private static void moveToTrash(File fileToDelete) {
    Files.move(fileToDelete.toPath(),
               asTrashFile(fileToDelete).toPath())
  }

  private static File asTrashFile(File file) {
    String fileName = file.name
    File dest = new File(TRASH, fileName)
    for (int i = 1; dest.exists(); i++) {
      dest = new File(TRASH, "${fileName}_${i}")
    }
    return dest;
  }
}
