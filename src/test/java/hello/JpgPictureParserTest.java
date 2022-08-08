package hello;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
@Ignore
public class JpgPictureParserTest extends AbstractTest {

    private JpgPictureParser util;

    @Before
    public void setUp() {
        this.util = new JpgPictureParser();
    }

    @Test
    public void constructPicture() {
        File file = new File(NOT_RENAME_FILE);
        ItemDto picture = util.constructItem(file);

        assertThat(picture, notNullValue());
        assertThat(picture.getName(), not(isEmptyString()));
        assertThat(picture.getNewNameDate(), not(isEmptyString()));

        assertThat(picture.getFile(), notNullValue());
        assertThat(picture.getOriginalDate(), notNullValue());
    }

//    @Test
//    public void createPictureNewName() {
//        PictureDto pic = new PictureDto.PictureDtoBuilder()
//                .name("name")
//                .file(new File("dir/name"))
//                .newNameDate("Thu Aug 26 19:47:50 EEST 2010")
//                             //yy:MM:dd HH:mm:ss
//                .originalDate("10:08:26 19:47:50")
//                .build();
//        PictureDto actual = util.createPictureNewName(pic);
//        String actualFileName = actual.getFile().getName();
//        assertThat("name", not(equalToIgnoringCase(actual.getName())));
//        assertThat(actualFileName, not(equalToIgnoringCase(actual.getName())));
//    }

//    @Test
//    public void setNameFromNamedDate() {
//        PictureDto pic = new PictureDto.PictureDtoBuilder()
//                .newNameDate("Thu Aug 24 19:47:50 EEST 2010")
//                .originalDate("10:08:26 19:47:50")
//                .build();
//        PictureDto actual = util.createPictureNewName(pic);
//        String actualFileName = actual.getName();
//        assertThat(actualFileName, equalToIgnoringCase("2010-08-24 19.47.50.jpg"));
//    }

//    @Test
//    public void setNameFromOrigindDate() {
//        PictureDto pic = new PictureDto.PictureDtoBuilder()
//                .newNameDate("Thu Aug 26 19:47:50 EEST 2010")
//                .originalDate("10:08:24 19:47:50")
//                .build();
//        PictureDto actual = util.createPictureNewName(pic);
//        String actualFileName = actual.getName();
//        assertThat(actualFileName, equalToIgnoringCase("2010-08-24 19.47.50.jpg"));
//    }

//    @Test (expected = IllegalArgumentException.class)
//    public void failsFromNullDate() {
//        PictureDto pic = new PictureDto.PictureDtoBuilder()
//                .newNameDate("")
//                .originalDate("")
//                .build();
//        util.createPictureNewName(pic);
//    }

//    @Test
//    public void updateFile() {
//        File file = new File(EXISTING_FILE);
//
//        PictureDto prepared = new PictureDto.PictureDtoBuilder()
//                .name(NEW_NAME_FILE)
//                .file(file)
//                .build();
//        PictureDto updated = util.updateFile(prepared);
//        assertThat(updated.getFile().getName(), equalToIgnoringCase(NEW_NAME_FILE));
//        assertThat("updated doesn't file exists", updated.getFile().exists());
//        assertThat("old file still exists", !file.exists());
//    }

}
