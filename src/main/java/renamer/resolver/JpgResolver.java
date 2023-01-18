package renamer.resolver;

import lombok.extern.slf4j.Slf4j;
import renamer.parser.JpgPictureParser;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Slf4j(topic = "global")
public class JpgResolver implements NewNameResolver {

    public static final String JPG = ".jpg";
    public static final String JPEG = ".jpeg";
    public static final String PNG = ".png";
    private static final List<String> EXT = List.of(JPG, JPEG, PNG);
    private final JpgPictureParser jpgPictureParser;

    public JpgResolver() {
        jpgPictureParser = new JpgPictureParser();
    }

    @Override
    public boolean canResolve(String fileName) {
        String lowerCase = fileName.toLowerCase();
        return EXT.stream().anyMatch(lowerCase::endsWith);
    }

    @Override
    public String resolve(File file) {
        log.info("File: {} processed by {}", file.getName(), getClass().getSimpleName());
        return Optional.ofNullable(jpgPictureParser.constructItem(file))
                .map(this::createItemNewName)
                .orElse(null);
    }
}
