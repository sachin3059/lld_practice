
import java.util.Arrays;
import java.util.List;

/**
 * Composite filter: passes only if ALL child filters pass.
 *
 * This is the Composite Pattern on top of Strategy.
 * Also easy to make an OrFilter — just change allMatch to anyMatch.
 *
 * In interview say:
 *   "This lets callers build complex queries like:
 *    (name ends in .log) AND (size > 1MB) AND (modified this week)"
 */
public class AndFilter implements FileFilter {

    private final List<FileFilter> filters;

    public AndFilter(FileFilter... filters) {
        this.filters = Arrays.asList(filters);
    }

    @Override
    public boolean apply(FileEntry entry) {
        return filters.stream().allMatch(f -> f.apply(entry));
    }
}