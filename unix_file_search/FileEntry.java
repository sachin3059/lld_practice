/**
 * Represents a single file or directory found during search.
 * Write this first — it's the data contract everything else builds on.
 */
public class FileEntry {

    private final String name;
    private final String path;
    private final long size;           // bytes
    private final long lastModified;   // epoch ms
    private final boolean isDirectory;

    public FileEntry(String name, String path, long size,
                     long lastModified, boolean isDirectory) {
        this.name         = name;
        this.path         = path;
        this.size         = size;
        this.lastModified = lastModified;
        this.isDirectory  = isDirectory;
    }

    // --- getters ---

    public String getName()        { return name; }
    public String getPath()        { return path; }
    public long   getSize()        { return size; }
    public long   getLastModified(){ return lastModified; }
    public boolean isDirectory()   { return isDirectory; }

    @Override
    public String toString() {
        return String.format("[%s] %s (%d bytes)",
                isDirectory ? "DIR" : "FILE", path, size);
    }
}