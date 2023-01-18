package renamer;

import hello.ItemDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static hello.JpgPictureParser.NAMED_DATE_PATTERN;
import static hello.JpgPictureParser.ORIGIN_DATE_PATTERN;

public interface NewNameResolver {

    Logger log = LoggerFactory.getLogger("global");
    String NEW_NAME_PATTERN = "yyyy-MM-dd HH.mm.ss";

    boolean canResolve(String filename);

    default String resolve(String filename) {
        return null;
    }

    default String resolve(File file) {
        String name = file.getName();
        log.info("{} process {}", getClass().getSimpleName(), name);
        return resolve(name);
    }

    default String createItemNewName(ItemDto pic) {
        Date named = getNamedDate(pic);
        Date origin = getOriginDate(pic);
        String newName = getNewNameDate(origin, named, pic.getName());
        if (!newName.isEmpty()) {
            log.warn("IllegalArgumentException: file date is null: {}", pic.getFile());
            pic.setName(newName);
        }
        return pic.getName();
    }

    private String getNewNameDate(Date origin, Date named, String name) {
        if (named == null && origin == null) {
            return "";
        }
        // set elder date as origin
        if (named != null) {
            if (origin == null || origin.compareTo(named) > 0) {
                origin = named;
            }
        }

        DateFormat format = new SimpleDateFormat(NEW_NAME_PATTERN);
        return format.format(origin) + Util.getExtension(name);
    }

    private Date getNamedDate(ItemDto pic) {
        return parseDate(NAMED_DATE_PATTERN, pic.getNewNameDate());
    }

    private Date getOriginDate(ItemDto pic) {
        return parseDate(ORIGIN_DATE_PATTERN, pic.getOriginalDate());
    }

    default Date parseDate(String pattern, String text) {
        if (text == null || "".equals(text)) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);

        Date date = null;
        try {
            date = format.parse(text);
        } catch (ParseException e) {
            log.error("Can't parse {} with pattern {}", text, pattern, e);
        }
        return date;
    }
}
