package base.database;

import java.nio.file.Path;

public class ManualCommitCollection extends CollectionOfDatabaseAbstract{
    ManualCommitCollection(Path pathToColl) {
        super(pathToColl);
    }
}
