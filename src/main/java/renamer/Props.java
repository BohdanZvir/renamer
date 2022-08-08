package renamer;

import lombok.Data;

@Data
public class Props {

    private String sourceDirectory;
    private String targetDirectory;
    private boolean cleanTargetDirectory;
    private boolean removeProcessed;
    private boolean copyOthers;
}
