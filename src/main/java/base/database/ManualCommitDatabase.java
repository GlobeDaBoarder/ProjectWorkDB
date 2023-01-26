package base.database;

import java.io.File;
import java.nio.file.Path;

public class ManualCommitDatabase extends DatabaseAbstract {
    public ManualCommitDatabase(Path dbPath) {
        super(dbPath);
    }

    @Override
    public ManualCommitCollection createCollection(String collName) {
        Path pathToCollection = (Path.of(dbPath.toString() + "\\" + collName + ".json"));

        File file = new File(pathToCollection.toUri());
        if (!file.exists()){
            ManualCommitCollection autoCommitCollection = new ManualCommitCollection(pathToCollection);
            collections.add(autoCommitCollection);
            return autoCommitCollection;
        }

        return new ManualCommitCollection(pathToCollection);
    }
}
