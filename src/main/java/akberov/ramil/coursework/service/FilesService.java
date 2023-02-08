package akberov.ramil.coursework.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public interface FilesService {

    boolean saveToFile(String json);

    String readFromFile();

    File getDataFile();

    Path createTempFile(String suffix);

    boolean cleanDataFile();

}
