

/**
 * Matches files whose size falls within [minBytes, maxBytes].
 * Directories have no meaningful size — skip them.
 *
 * Interview tip: mention that you guard against directories explicitly.
 */
public class SizeFilter implements FileFilter {

    private final long minBytes;
    private final long maxBytes;

    public SizeFilter(long minBytes, long maxBytes) {
        this.minBytes = minBytes;
        this.maxBytes = maxBytes;
    }

    @Override
    public boolean apply(FileEntry entry) {
        if (entry.isDirectory()) return false;
        return entry.getSize() >= minBytes && entry.getSize() <= maxBytes;
    }
}