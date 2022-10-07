package hello;

import java.io.File;

/**
 * Created by bohdan.
 */
public interface Parser {

    ItemDto constructItem(File file);
}
