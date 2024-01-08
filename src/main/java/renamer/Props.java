package renamer;

import lombok.Data;

import java.util.List;

@Data
public class Props {

    private String sourceDirectory;
    private String targetDirectory;
    private boolean cleanTargetDirectory;
    private boolean removeProcessed;
    private boolean copyOthers;
    private boolean debug;
    private List<String> suffixesToRemove;

    public void setSuffixesToRemove(List<Object> suffixesToRemove) {
        this.suffixesToRemove = suffixesToRemove.stream().map(Object::toString).toList();
    }
}
