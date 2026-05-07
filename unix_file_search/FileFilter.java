

/**
 * Strategy interface for filtering files.
 *
 * Why an interface?
 *   - Open/Closed Principle: add new filter types without touching FileSearcher
 *   - Each filter is independently testable
 *   - Filters can be composed (AndFilter, OrFilter)
 *
 * In the interview say:
 *   "I'm using the Strategy Pattern here so the searcher
 *    doesn't need to know about specific filter logic."
 */
public interface FileFilter {
    boolean apply(FileEntry entry);
}