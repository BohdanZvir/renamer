package renamer.resolver;

public class SkipResolevedResolver implements NewNameResolver {

    public static final String NAME_PATTERN = "\\d{4}-\\d{2}-\\d{2}\\s\\d{2}\\.\\d{2}\\.\\d{2}.*";

    @Override
    public boolean canResolve(String filename) {
        return !filename.matches(NAME_PATTERN);
    }
}
