package imageviewer.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum SortOrder {
    ALPHABETIC {
        @Override
        Comparator<File> getComparator() {
            return (o1, o2) ->
                    o1.getName().compareTo(o2.getName());
        }
    },
    FILE_SIZE {
        @Override
        Comparator<File> getComparator() {
            return (o1, o2) -> {
                try {
                    Long s1 = Files.size(o1.toPath());
                    Long s2 = Files.size(o2.toPath());
                    return s1.compareTo(s2);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            };
        }
    },
    TIMESTAMP {
        @Override
        Comparator<File> getComparator() {
            return (o1, o2) ->
                    Long.valueOf(o2.lastModified()).compareTo(o1.lastModified());
        }
    };

    abstract Comparator<File> getComparator();


    List<File> sort(List<File> original) {
        List<File> copied = new ArrayList<>(original);
        Collections.sort(copied, getComparator());
        return copied;
    }
}