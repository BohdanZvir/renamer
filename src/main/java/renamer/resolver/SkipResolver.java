package renamer.resolver;

import renamer.Props;

public class SkipResolver implements NewNameResolver {

    private final Props props;

    public SkipResolver(Props props) {
        this.props = props;
    }

    @Override
    public boolean canResolve(String filename) {
        return props.isCopyOthers();
    }

    @Override
    public String resolve(String filename) {
        return NewNameResolver.super.resolve(filename);
    }
}
