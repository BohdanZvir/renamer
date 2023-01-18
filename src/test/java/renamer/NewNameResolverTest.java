package renamer;

import org.junit.Test;
import renamer.resolver.NewNameResolver;

import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static renamer.resolver.NewNameResolver.NEW_NAME_PATTERN;

public class NewNameResolverTest {

    @Test
    public void parseDate() {
        String date ="Mon Sep 28 19:16:23 EEST 2020";
        Date date1 = ((NewNameResolver) filename -> false).parseDate(NEW_NAME_PATTERN, date);

        assertNotNull(date1);
    }
}