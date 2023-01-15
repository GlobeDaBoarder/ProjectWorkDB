package base.database;

import base.collection.EntryList;

public interface Database {
    EntryList createCollection(String collName);
}
