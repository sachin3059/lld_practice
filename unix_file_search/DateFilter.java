
/**
 * Matches files last modified within [afterMs, beforeMs] (epoch milliseconds).
 *
 * Interview tip: mention this is easily constructed from
 * Instant.now().minus(7, ChronoUnit.DAYS).toEpochMilli() for "last 7 days".
 */
public class DateFilter implements FileFilter {

    private final long afterMs;
    private final long beforeMs;

    public DateFilter(long afterMs, long beforeMs) {
        this.afterMs  = afterMs;
        this.beforeMs = beforeMs;
    }

    @Override
    public boolean apply(FileEntry entry) {
        long modified = entry.getLastModified();
        return modified >= afterMs && modified <= beforeMs;
    }
}