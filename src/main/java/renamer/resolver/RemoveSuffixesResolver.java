package renamer.resolver;

import renamer.Props;

public class RemoveSuffixesResolver implements NewNameResolver {

    private final Props props;

    public RemoveSuffixesResolver(Props props) {
        this.props = props;
    }

    @Override
    public boolean canResolve(String filename) {
        return props.getSuffixesToProcess().stream().anyMatch(filename::contains);
    }

    @Override
    public String resolve(String filename) {
        for (String s : props.getSuffixesToProcess()) {
            filename = filename.replaceAll(s, "");
        }
        return filename;
    }
}
