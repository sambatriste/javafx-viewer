package imageviewer.archive

import groovy.transform.TypeChecked

import java.util.regex.Pattern

@TypeChecked
abstract class EntryWrapper<T> implements Comparable<EntryWrapper<T>> {

  final String name
  final String rawName
  final String prefix

  EntryWrapper(String fileName, String pathSeparator) {
    assert fileName != null
    assert !fileName.isEmpty()
    def first = fileName.lastIndexOf(pathSeparator)
    first = (first == -1) ? 0 : first + 1
    def last = fileName.lastIndexOf(".")
    assert first <= last
    this.rawName = fileName.substring(first, last)
    this.prefix = fileName.substring(0, first)
    this.name = fileName
  }


  private static final Pattern NUM_REGEXP = Pattern.compile("[0-9]+")

  protected boolean isInteger() {
    return NUM_REGEXP.matcher(rawName).matches()
  }
  protected Integer parseInt() {
    return Integer.parseInt(rawName)
  }

  @Override
  int compareTo(EntryWrapper<T> o) {

    if (this.prefix == o.prefix) {
      if (this.integer && o.integer) {
        if (this.name.length() == o.name.length()) {
          return this.parseInt().compareTo(o.parseInt())
        } else {
          return name.length().compareTo(o.name.length())
        }
      }
    }
    return this.name.compareTo(o.name)
  }
}

