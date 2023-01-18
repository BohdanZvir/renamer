package renamer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilTest {

    @Test
    public void aliasName() {
        assertEquals("2020-11-28 12.59.08-1.jpg", Util.aliasName("2020-11-28 12.59.08.jpg"));
    }
}