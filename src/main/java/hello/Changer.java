package hello;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Changer {

    public static final String NEW_NAME_PATTERN = "yyyy-MM-dd HH.mm.ss";
    private JpgPictureParser picture = new JpgPictureParser();
    private Mp4Parser mp4 = new Mp4Parser();
    private Predicate<? super File> compositePredicate;

    public Changer() {
//        compositePredicate = picture.getFilter().or(mp4.getFilter());
    }

    private List<File> filter(File[] files) {
        return Arrays.stream(files)
                .filter(compositePredicate)
                .collect(Collectors.toList());
    }
}