package base.database;

public interface Database {
    AutoCommitCollection createCollection(String collName);
}
