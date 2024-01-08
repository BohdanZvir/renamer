package renamer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.nio.file.Path;

import static java.util.Objects.isNull;
import static renamer.resolver.NewNameResolver.NEW_NAME_PATTERN;


@UtilityClass
@Slf4j
public class Util {

    public static <T> T readProps(String filename, Class<T> clazz, String pointer) {
        var mapper = new ObjectMapper(new YAMLFactory())
                .setPropertyNamingStrategy(PropertyNamingStrategy.KEBAB_CASE);
        try (InputStream resourceStream = clazz.getClassLoader().getResourceAsStream(filename)) {
            var node = mapper.readTree(resourceStream).at(pointer);
            return mapper.treeToValue(node, clazz);
        } catch (Exception e) {
            log.error("Can't read property", e);
            throw new IllegalArgumentException(e);
        }
    }

    public static Path toAbsolute(Path path) {
        if (path.isAbsolute()) {
            return path;
        }
        return path.toAbsolutePath();
    }


    public static String aliasName(String name) {
        if (isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException("Name is empty");
        }
        String extensionPart = getExtension(name);
        String namePart = name.replaceAll(extensionPart, "");
        int versionPosition = namePart.indexOf('-', NEW_NAME_PATTERN.length() - 1);
        if (versionPosition != -1) {
            var version = Integer.parseInt(namePart.substring(versionPosition +1));
            return namePart.substring(0, NEW_NAME_PATTERN.length()) + "-" + ++version + extensionPart;
        }
        return namePart + "-1" + extensionPart;
    }

    public static String getExtension(String newName) {
        int i = newName.lastIndexOf('.');
        return newName.substring(i);
    }
}
