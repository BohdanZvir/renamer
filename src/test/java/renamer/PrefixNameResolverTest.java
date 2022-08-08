package renamer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PrefixNameResolverTest {

    private PrefixNameResolver service;

    @Before
    public void setUp() {
        service = new PrefixNameResolver();
    }

    @Test
    public void resolve() {
        assertEquals("2020-11-28 12.59.18.jpg", service.resolve("IMG_20201128_125918.jpg"));
    }

    @Test
    public void resolveWithNotion() {
        assertEquals("2020-11-28 12.59.08-2.jpg", service.resolve("IMG_20201128_125908~2.jpg"));
    }
}
