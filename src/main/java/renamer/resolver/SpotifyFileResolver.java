package renamer.resolver;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import renamer.ItemDto;
import renamer.parser.SpotifyDownloaderParser;

import java.io.File;

@Slf4j(topic = "global")
public class SpotifyFileResolver implements NewNameResolver {

    public static final String SPOTIFY_DOWNLOADER_PREFIX = "[SPOTIFY-DOWNLOADER.COM] ";

    private SpotifyDownloaderParser parser;

    public SpotifyFileResolver() {
        this.parser = new SpotifyDownloaderParser();
    }

    @Override
    public boolean canResolve(@NonNull String fileName) {
        return fileName.toUpperCase()
                       .startsWith(SPOTIFY_DOWNLOADER_PREFIX);
    }

    @Override
    public String resolve(File file) {
        log.debug("File: {} processed by {}", file.getName(), getClass().getSimpleName());
        ItemDto pic = parser.constructItem(file);
        return pic.getNewNameDate();
    }
}
