package base.database;

import java.nio.file.Path;
import java.util.List;

public interface Database {
    CollectionOfDatabase createCollection(String collName);

    void deleteDatabase();

    List<CollectionOfDatabase> getCollections();

    void deleteCollection(String collectionName);

    Path getPath();
}
