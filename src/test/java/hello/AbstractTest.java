package hello;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
@Ignore
public abstract class AbstractTest {

    protected static List<File> toDelete = new LinkedList<>();
    protected static final String TEST_DIR =
            "/home/bohdan/Java/Java_projects/mine/rename-photo/test_data/";
    protected static final String EXISTING_FILE = TEST_DIR + "4D9unzXpHbA.jpg";
    protected static final String NOT_RENAME_FILE = EXISTING_FILE + "1";
    protected static final String NEW_NAME_FILE = "newNameForExisting.file";


    @Before
    public void setUp() throws IOException {
        File notRename = new File(NOT_RENAME_FILE);
        if (!notRename.exists()) {
            File file = new File(EXISTING_FILE);
            if (!file.exists()) {
                throw new IllegalArgumentException("Test data folder is empty.");
            }
            Files.copy(file.toPath(), notRename.toPath());
        }
    }


    @After
    public void cleanUp() {
        if (!toDelete.isEmpty()) {
            toDelete.stream()
                    .filter(file -> file.exists())
                    .forEach(file -> file.delete());
        }
    }

    @AfterClass
    public static void tearDown() throws IOException {
        File newFile = new File(TEST_DIR + NEW_NAME_FILE);
        if (newFile.exists()) {
            newFile.delete();
        }
        File existingFile = new File(EXISTING_FILE);
        if (!existingFile.exists()) {
            File providedFile = new File(NOT_RENAME_FILE);
            Files.copy(providedFile.toPath(), existingFile.toPath());
        }
    }
}
