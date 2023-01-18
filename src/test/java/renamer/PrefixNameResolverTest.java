package renamer;

import org.junit.Before;
import org.junit.Test;
import renamer.resolver.PrefixNameResolver;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        Map.of("IMG_20201128_125908~2.jpg", "2020-11-28 12.59.08-2.jpg", "VID_20200928_191615.mp4", "2020-09-28 19.16.15.mp4")
                .forEach((key, value) -> assertEquals(value, service.resolve(key)));
    }

    @Test
    public void canResolve() {
        Stream.of("IMG_20201128_125908~2.jpg", "VIDrrr_20200928_191615.mp4")
                .forEach(s -> assertTrue(service.canResolve(s)));
    }
}
