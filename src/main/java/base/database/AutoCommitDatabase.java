package base.database;

import java.io.File;
import java.nio.file.Path;

public class AutoCommitDatabase extends DatabaseAbstract {

    AutoCommitDatabase(Path dbPath) {
        super(dbPath);
    }


    @Override
    public AutoCommitCollection createCollection(String collName) {
        Path pathToCollection = (Path.of(dbPath.toString() + "\\" + collName + ".json"));

        File file = new File(pathToCollection.toUri());
        if (!file.exists()){
            AutoCommitCollection autoCommitCollection = new AutoCommitCollection(pathToCollection);
            collections.add(autoCommitCollection);
            return autoCommitCollection;
        }

        return new AutoCommitCollection(pathToCollection);
    }
}
