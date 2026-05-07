

/**
 * Matches files whose name contains the given pattern.
 * Can be upgraded to regex — keep it simple first in interviews.
 */
public class NameFilter implements FileFilter {

    private final String pattern;

    public NameFilter(String pattern) {
        this.pattern = pattern.toLowerCase();
    }

    @Override
    public boolean apply(FileEntry entry) {
        return entry.getName().toLowerCase().contains(pattern);
    }
}