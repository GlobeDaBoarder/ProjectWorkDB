package base.database;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class AutoCommitCollection extends CollectionOfDatabaseAbstract {

    AutoCommitCollection(Path pathToColl) {
        super(pathToColl);

        File file = new File(pathToColl.toUri());
        if (!file.exists()){
            try {
                Files.createFile(pathToColl);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            loadCollection(pathToColl);
        }
    }


}
