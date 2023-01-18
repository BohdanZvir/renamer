package renamer.parser;

import com.coremedia.iso.IsoFile;
import lombok.extern.slf4j.Slf4j;
import renamer.ItemDto;

import java.io.File;
import java.io.IOException;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;
import java.util.Date;

import static renamer.resolver.NewNameResolver.NEW_NAME_PATTERN;

@Slf4j(topic = "global")
public class Mp4Parser implements Parser {

    public static final String INPUT_PATTERN = "EEE MMM d HH:mm:ss z yyyy";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(INPUT_PATTERN);
    public static final String OUTPUT_PATTERN = NEW_NAME_PATTERN;

    @Override
    public ItemDto constructItem(File file) {
        try (IsoFile isoFile = new IsoFile(file.getAbsolutePath())) {
            Date date = isoFile.getMovieBox()
                               .getMovieHeaderBox()
                               .getCreationTime();
            return ItemDto.builder()
                          .file(file)
                          .name(file.getName())
                          .newNameDate(date.toString())
                          .build();
        } catch (IOException e) {
            log.warn("can't read video file: " + file);
            return null;
        }

    }

    public String shiftDate(String origin) {
        ZonedDateTime dt = ZonedDateTime.parse(origin, DATE_TIME_FORMATTER);
        TemporalAmount amount = Period.ofYears(66)
                                      .plusDays(1);
        //"EEE MMM d HH:mm:ss z yyyy";
        return dt.plus(amount)
                 .format(DateTimeFormatter.ofPattern(OUTPUT_PATTERN));
    }

}
