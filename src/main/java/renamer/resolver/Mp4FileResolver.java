package renamer.resolver;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import renamer.ItemDto;
import renamer.parser.Mp4Parser;

import java.io.File;

@Slf4j(topic = "global")
public class Mp4FileResolver implements NewNameResolver {

    private final Mp4Parser mp4Parser;

    public Mp4FileResolver() {
        mp4Parser = new Mp4Parser();
    }

    @Override
    public boolean canResolve(@NonNull String fileName) {
        return fileName.toLowerCase().endsWith(".mp4");
    }

    @Override
    public String resolve(File file) {
        log.info("File: {} processed by {}", file.getName(), getClass().getSimpleName());
        ItemDto pic = mp4Parser.constructItem(file);
        return createItemNewName(pic);
    }
}
