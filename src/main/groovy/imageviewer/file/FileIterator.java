package imageviewer.file;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;

/**
 * Created by kawasaki on 2015/04/04.
 */
public class FileIterator implements ListIterator<File> {

    private final File dir;

    private List<File> sortedFiles;

    private ListIterator<File> itr;

    public FileIterator(File dir, SortOrder sortOrder, String regexp) {
        this(dir, sortOrder, Pattern.compile(regexp));
    }


    public FileIterator(File dir, SortOrder sortOrder, Pattern ptn) {
        this(dir, sortOrder, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return ptn.matcher(pathname.getName()).matches();
            }
        });
    }

    public FileIterator(File file, SortOrder sortOrder, FileFilter fileFilter) {
        this.dir = file.getParentFile();
        if (!dir.exists() || !dir.isDirectory()) {
            throw new IllegalArgumentException("directory not found. [${dir.absolutePath}]");
        }

        List<File> files = Arrays.asList(dir.listFiles(fileFilter));
        assert !files.isEmpty();

        sortedFiles = sortOrder.sort(files);
        itr = sortedFiles.listIterator();
        find(file);
    }

    private File find(File target) {
        while (itr.hasNext()) {
            File f = itr.next();
            if (isEqual(f, target)) {
                return f;
            }
        }
        throw new IllegalArgumentException("file not found. " + target);
    }

    private boolean isEqual(File f1, File f2) {
        try {
            String f1Name = Normalizer.normalize(f1.getCanonicalPath(), Normalizer.Form.NFC);
            String f2Name = Normalizer.normalize(f2.getCanonicalPath(), Normalizer.Form.NFC);
            return f1Name.equals(f2Name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "FileIterator{" +
                "dir=" + dir +
                ", sortedFiles=" + sortedFiles +
                '}';
    }


    @Override
    public boolean hasNext() {
        return itr.hasNext();
    }

    @Override
    public File next() {
        return itr.next();
    }

    @Override
    public boolean hasPrevious() {
        return itr.hasPrevious();
    }

    @Override
    public File previous() {
        return itr.previous();
    }

    @Override
    public int nextIndex() {
        return itr.nextIndex();
    }

    @Override
    public int previousIndex() {
        return itr.previousIndex();
    }

    @Override
    public void remove() {
        itr.remove();
    }

    @Override
    public void set(File file) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(File file) {
        throw new UnsupportedOperationException();
    }

}
