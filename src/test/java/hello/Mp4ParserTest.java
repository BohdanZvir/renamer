package hello;

import org.junit.Ignore;
import org.junit.Test;
import renamer.ItemDto;
import renamer.parser.Mp4Parser;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;
import static org.hamcrest.text.IsEmptyString.isEmptyString;
@Ignore
public class Mp4ParserTest extends AbstractTest {

    private static final String VIDEO_1 = TEST_DIR + "CAM00865.mp4";

    @Test
    public void extractMp4Date() throws IOException {
        ItemDto picture = new Mp4Parser().constructItem(new File(VIDEO_1));

        String date = picture.getNewNameDate();
        assertThat(date, notNullValue());
        assertThat(date, not(isEmptyString()));
    }

    @Test
    public void shiftDateValue() {
        String origin = "Tue Apr 27 14:54:22 EET 1948";
        String shifted = new Mp4Parser().shiftDate(origin);

        assertThat(shifted, not(isEmptyOrNullString()));
        assertThat(shifted, not(equalToIgnoringCase(origin)));
//        assertThat(shifted, equalTo("Mon Apr 28 14:54:22 EEST 2014"));
        assertThat(shifted, equalTo("2014-04-28 14.54.22"));
    }

    @Test
    public void constructPicture() {
        ItemDto pic = null;
//                new Mp4Parser().constructPicture(VIDEO_1);

        assertThat(pic, notNullValue());
        assertThat(pic.getFile(), notNullValue());
        assertThat(pic.getName(), not(isEmptyOrNullString()));
        assertThat(pic.getNewNameDate(), not(isEmptyOrNullString()));
    }
}
