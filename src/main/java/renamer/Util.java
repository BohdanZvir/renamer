package renamer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.nio.file.Path;


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


    public static String aliasName(String newName) {
        String extensionPart = getExtension(newName);
        String namePart = newName.replaceAll(extensionPart, "");
        return namePart + "-1" + extensionPart;
    }

    public static String getExtension(String newName) {
        int i = newName.lastIndexOf('.');
        return newName.substring(i);
    }
}
