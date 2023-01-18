package renamer;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import renamer.resolver.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Collections.reverseOrder;
import static java.util.Objects.isNull;

@Slf4j(topic = "global")
public class RenamerApp {

    private final Path sourceDirectory;
    private final Path targetDirectory;

    private final Props props;


    private final List<NewNameResolver> resolverList;

    public RenamerApp() {
        MyLogger.setup();

        props = Util.readProps("application.yaml", Props.class, "/renamer");
        sourceDirectory = Util.toAbsolute(Path.of(props.getSourceDirectory()));
        targetDirectory = Optional.ofNullable(props.getTargetDirectory())
                                  .map(Path::of)
                                  .map(Util::toAbsolute)
                                  .orElse(sourceDirectory);
        validateTarget(targetDirectory);
        log.info("Properties: {}", props);

        resolverList = List.of(new PrefixNameResolver(), new JpgResolver(), new Mp4FileResolver(),
                               new SpotifyFileResolver(), new NewNameResolver() {
                    @Override
                    public boolean canResolve(String filename) {
                        return props.isCopyOthers();
                    }

                    @Override
                    public String resolve(String filename) {
                        return filename;
                    }
                });
    }

    @SneakyThrows
    private void validateTarget(Path targetDirectory) {
        if (!Files.exists(targetDirectory)) {
            try {
                Files.createDirectories(this.targetDirectory);
            } catch (IOException e) {
                log.error("Can't create target directory", e);
            }
        } else {
            try (var list = Files.walk(targetDirectory, FileVisitOption.FOLLOW_LINKS)) {
                long size = list.sorted(reverseOrder()).map(Path::toFile).map(File::delete).count();
                log.warn("{} files were removed in target before processing", size);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @SneakyThrows
    public void process(Path directory) {
        try (Stream<Path> list = Files.list(directory)) {
            list.forEach(this::processPath);
        }
    }

    private NewNameResolver getResolver(Path path) {
        String fileName = path.toFile().getName();

        return resolverList.stream()
                .filter(d -> d.canResolve(fileName))
                .findFirst()
                .orElse(null);
    }

    private void processPath(Path path) {
        if (path.toFile().isDirectory()) {
            process(path);
        }
        NewNameResolver resolver = getResolver(path);
        if (isNull(resolver)) {
            log.warn("Can't find resolver for path: {}", path);
            return;
        }
        updateFile(props.isRemoveProcessed(), path, resolver);
    }

    public void updateFile(boolean remove, Path sourcePath, NewNameResolver resolver) {
        String newName = resolver.resolve(sourcePath.toFile());
        if (Files.exists(sourcePath)) {
            Path targetPath = validateTargetFile(newName);
            if (isNull(targetPath)) {
                log.error("Target path is null for source: {}", sourcePath);
                return;
            }
            log.info("Renaming\t{}\t\t\t-> {}", sourcePath, targetPath);
            if (props.isDebug()) {
                return;
            }
            try {
                Files.copy(sourcePath, targetPath);
                if (remove) {
                    log.warn("Removing {}", sourcePath);
                    Files.delete(sourcePath);
                }
            } catch (IOException e) {
                log.warn(e.getMessage() + " \n" + sourcePath + "-->" + targetPath);
            }
        }
    }

    private Path validateTargetFile(String newName) {
        if (isNull(newName) || newName.isBlank()) {
            return null;
        }
        Path targetPath = Path.of(targetDirectory.toString(), newName);
        if (Files.exists(targetPath)) {
            log.warn("{} - already exists.", targetPath);
            validateTargetFile(Util.aliasName(newName));
        }
        return targetPath;
    }

    public static void main(String[] args) {
        RenamerApp renamerApp = new RenamerApp();
        renamerApp.process(renamerApp.sourceDirectory);
    }
}
