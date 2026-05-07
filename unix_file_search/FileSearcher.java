

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSearcher {

    private final List<FileFilter> filters;

    public FileSearcher(List<FileFilter> filters) {
        this.filters = filters;
    }

    public List<FileEntry> search(String rootPath) {
        List<FileEntry> results = new ArrayList<>();
        dfs(new File(rootPath), results);
        return results;
    }

    private void dfs(File dir, List<FileEntry> results) {
        File[] entries = dir.listFiles();
        if (entries == null) return;

        for (File f : entries) {
            FileEntry entry = toFileEntry(f);

            if (f.isDirectory()) {
                dfs(f, results);
            }

            if (allFiltersPass(entry)) {
                results.add(entry);
            }
        }
    }

    private boolean allFiltersPass(FileEntry entry) {
        for (FileFilter filter : filters) {
            if (!filter.apply(entry)) return false;
        }
        return true;
    }

    private FileEntry toFileEntry(File f) {
        return new FileEntry(
            f.getName(),
            f.getAbsolutePath(),
            f.length(),
            f.lastModified(),
            f.isDirectory()
        );
    }
}