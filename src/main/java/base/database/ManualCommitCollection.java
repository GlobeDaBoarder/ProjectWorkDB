package base.database;

import java.nio.file.Path;

public class ManualCommitCollection extends CollectionOfDatabaseAbstract{
    public ManualCommitCollection(Path pathToColl) {
        super(pathToColl);
    }
}
