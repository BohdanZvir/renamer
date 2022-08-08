package hello;

import java.io.File;
import java.util.function.Predicate;

/**
 * Created by bohdan.
 */
public interface Parser {

    Predicate<? super File> getFilter();
    ItemDto constructItem(File file);
}
