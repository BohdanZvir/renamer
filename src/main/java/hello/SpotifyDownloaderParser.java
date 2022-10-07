package hello;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

import static renamer.SpotifyFileResolver.SPOTIFY_DOWNLOADER_PREFIX;

@Slf4j(topic = "global")
public class SpotifyDownloaderParser implements Parser {

    @Override
    public ItemDto constructItem(File file) {
        return ItemDto.builder()
                      .file(file)
                      .name(file.getName())
                      .newNameDate(file.getName()
                                       .replace(SPOTIFY_DOWNLOADER_PREFIX, ""))
                      .build();
    }
}
