package hello;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static java.util.Objects.requireNonNull;
import static renamer.JpgResolver.JPG;

@Ignore
public class ChangerTest extends AbstractTest {

    @Before
    public void fillTestDirWithPhoto() {
        File dir = new File(TEST_DIR, "dir");
        for (File file : requireNonNull(dir.listFiles())) {
            file.delete();
        }
        File[] files = new File(TEST_DIR).listFiles(pathname -> pathname.getName().contains(JPG));
        for (File file : requireNonNull(files)) {
            try {
                Files.copy(file.toPath(), dir.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void changedFileExist() {
//        Changer changer = new Changer();
//        File file = new File(EXISTING_FILE);
//        File actual = changer.fixFile(file);
//
//        assertThat(actual, notNullValue());
//        toDelete.add(actual);
//        assertThat(actual, not(equalTo(file)));
//        assertTrue(actual.exists());
    }

//    @Test (expected = RuntimeException.class)
//    public void failsOnNullFileParameter() {
//        new Changer().fixFile(null);
//    }
//
//    @Test (expected = RuntimeException.class)
//    public void failsOnNonExistingFile() {
//        new Changer().fixFile(new File("/tmp/NON-EXITING.FILE_1"));
//    }
//
//    @Test (expected = IllegalArgumentException.class)
//    public void failsOnNonJpgFile() {
//        new Changer().fixFile(new File("/tmp/.touchpaddefaults"));
//    }
}
