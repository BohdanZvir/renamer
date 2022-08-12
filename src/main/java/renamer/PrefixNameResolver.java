package renamer;

import lombok.SneakyThrows;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrefixNameResolver implements NewNameResolver {

    public static final DateFormat NEW_DATE_FORMAT = new SimpleDateFormat(NewNameResolver.NEW_NAME_PATTERN);
    private static final String IMG_DATE_REGEX = "^\\w{3}_\\d{8}_\\d{6}.+";
    public static final String IMG_DATE_PATTERN = "yyyyMMdd_HHmmss";
    private final SimpleDateFormat format;

    public PrefixNameResolver() {
        format = new SimpleDateFormat(IMG_DATE_PATTERN);
    }

    @Override
    public boolean canResolve(String filename) {
        return filename.matches(IMG_DATE_REGEX);
    }

    @Override
    @SneakyThrows
    public String resolve(String filename) {
        Date date = format.parse(filename.substring(4, 19));
        return NEW_DATE_FORMAT.format(date) + filename.substring(19).replaceAll("~", "-");
    }
}
