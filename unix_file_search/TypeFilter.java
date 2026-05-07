

/**
 * Matches only files or only directories, based on the flag passed in.
 *
 * Usage:
 *   new TypeFilter(true)   — directories only
 *   new TypeFilter(false)  — regular files only
 */
public class TypeFilter implements FileFilter {

    private final boolean matchDirectories;

    public TypeFilter(boolean matchDirectories) {
        this.matchDirectories = matchDirectories;
    }

    @Override
    public boolean apply(FileEntry entry) {
        return entry.isDirectory() == matchDirectories;
    }
}