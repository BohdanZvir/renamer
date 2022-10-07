package hello;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.file.FileMetadataDirectory;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j(topic = "global")
public class JpgPictureParser implements Parser {

    public static final String NAMED_DATE_PATTERN = "EEE MMM dd HH:mm:ss zzzz yyyy";
    public static final String ORIGIN_DATE_PATTERN = "yy:MM:dd HH:mm:ss";

    @Override
    public ItemDto constructItem(File file) {
        if (!file.exists()) {
           log.error("File doesn't exists {}", file);
           return null;
        }
        if (file.length() == 0) {
           log.error("File is empty {}", file);
           file.delete();
           return null;
        }
        Metadata metadata;
        try {
            metadata = ImageMetadataReader.readMetadata(file);
        } catch (ImageProcessingException | IOException e) {
            log.warn(e.getMessage() + " file: " + file.getAbsolutePath());
            return ItemDto.builder().build();
        }
        FileMetadataDirectory fileDir = metadata.getFirstDirectoryOfType(FileMetadataDirectory.class);
        ExifSubIFDDirectory subIfDir = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

        String fileName = fileDir.getString(1);
        String lastModifiedDate = fileDir.getString(3);
        String originalDate = subIfDir != null ? subIfDir.getString(36867) : "";

        return ItemDto.builder()
                .file(file)
                .name(fileName)
                .originalDate(originalDate)
                .newNameDate(lastModifiedDate)
                .build();
    }

}
