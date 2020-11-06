package woods.datamodel;

import lombok.Data;

import java.util.List;

@Data
public class FilesDirectory {
    private String filePath;
    private List<String> files;
}
