package renamer;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

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
        targetDirectory = Optional.ofNullable(props.getTargetDirectory()).map(Path::of).map(Util::toAbsolute).orElse(sourceDirectory);
        validateTarget(targetDirectory);
        log.info("Properties: {}", props);

        resolverList = List.of(new JpgResolver(), new Mp4FileResolver(), new PrefixNameResolver(), new NewNameResolver() {
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

    private void validateTarget(Path targetDirectory) {
        if (!Files.exists(targetDirectory)) {
            try {
                Files.createDirectories(this.targetDirectory);
            } catch (IOException e) {
                log.error("Can't create target directory", e);
            }
        }
        File[] targetFiles = targetDirectory.toFile().listFiles();
        if (requireNonNull(targetFiles).length != 0) {
            if (!props.isCleanTargetDirectory()) {
                throw new IllegalArgumentException("Target is not empty!!");
            } else {
                Stream.of(targetFiles).forEach(File::delete);
            }
        }
    }

    public void process() {
        try (Stream<Path> list = Files.list(sourceDirectory)) {
            for (Path file : list.toList()) {
                String newName = processFile(file);
                updateFile(props.isRemoveProcessed(), file, newName);
            }
        } catch (Exception ex) {
            log.error("process error", ex);
        }
    }

    private String processFile(Path path) {
        File file = path.toFile();
        String fileName = file.getName();
        return resolverList.stream()
                .filter(d -> d.canResolve(fileName))
                .findFirst()
                .map(s -> s.resolve(file)).orElse(null);
    }

    public void updateFile(boolean remove, Path sourcePath, String newName) {
        if (isNull(newName) || newName.isEmpty()) {
            log.warn("new Name is empty for {}", sourcePath);
            return;
        }
        if (Files.exists(sourcePath)) {
            Path targetPath = Path.of(targetDirectory.toString(), newName);
            if (Files.exists(targetPath)) {
                log.warn("Already exists {}", targetPath);
                updateFile(remove, sourcePath, Util.aliasName(newName));
            }
            try {
                log.info("renaming\t{}\n\t\t\t\t-> {}", sourcePath, targetPath);
                Files.copy(sourcePath, targetPath);
                if (remove) {
                    log.info("removing {}", sourcePath);
                    Files.delete(sourcePath);
                }
            } catch (IOException e) {
                log.warn(e.getMessage() + " \n" + sourcePath + "-->" + targetPath);
            }
        }
    }

    public static void main(String[] args) {
        new RenamerApp().process();
    }
}
