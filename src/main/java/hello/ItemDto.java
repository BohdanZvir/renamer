package hello;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.File;

@Getter
@Builder
@ToString
public class ItemDto {

    @Setter
    private File file;
    @Setter
    private String name;
    private String originalDate;
    private String newNameDate;
}
