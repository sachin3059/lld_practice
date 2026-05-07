

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<FileFilter> filters = Arrays.asList(
            new NameFilter(".txt")
        );

        FileSearcher searcher = new FileSearcher(filters);
        List<FileEntry> results = searcher.search("var/docs");

        for (FileEntry entry : results) {
            System.out.println(entry);
        }
    }
}