package renamer.parser;

import renamer.ItemDto;

import java.io.File;

/**
 * Created by bohdan.
 */
public interface Parser {

    ItemDto constructItem(File file);
}
