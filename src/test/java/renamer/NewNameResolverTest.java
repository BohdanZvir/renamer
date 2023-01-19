package renamer;

import org.junit.Test;
import renamer.resolver.NewNameResolver;

import java.util.Date;

import static org.junit.Assert.assertNotNull;

public class NewNameResolverTest {

    @Test
    public void parseDate() {
        String date ="Mon Sep 28 19:16:23 EEST 2020";
        Date date1 = ((NewNameResolver) filename -> false).parseDate("EEE MMM dd HH:mm:ss zzzz yyyy", date);

        assertNotNull(date1);
    }
}