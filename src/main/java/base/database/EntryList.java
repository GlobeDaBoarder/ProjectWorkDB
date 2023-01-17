package base.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Stream;

public class EntryList extends ArrayList<Entry> {

    private final String collectionName;
    private final Path collPath;

    EntryList(Path pathToColl) {
        this.collectionName = pathToColl.getFileName().toString();
        this.collPath = pathToColl;

        File file = new File(pathToColl.toUri());
        if (!file.exists()){
            System.out.println("creating file");
            try {
                Files.createFile(pathToColl);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            useCollection(pathToColl);
        }
    }

    public void useCollection(Path pathToColl) {
        try (Stream<String> lines = Files.lines(pathToColl)) {
            lines
                    .forEach(line -> this.addWithoutUpdate(new Entry(line)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        System.out.println("reading file content");
//        try (BufferedReader br = new BufferedReader(new FileReader(new File(pathToColl.toUri())))) {
//            for(String line; (line = br.readLine()) != null; ) {
//                this.addWithoutUpdate(new Entry(line));
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public Entry getById(String uuid){
        for (Entry currentEntry : this) {
            if (uuid.equals(currentEntry.getObjId())) {
                return currentEntry;
            }
        }

        System.err.println("No entity with UUID = " + uuid );
        return new Entry();
    }

    @Override
    public boolean add(Entry entry) {
        boolean flag = super.add(entry);
        if(flag)
            updateFile(entry);
        return flag;
    }

    @Override
    public void add(int index, Entry entry) {
        super.add(index, entry);
        updateFile(entry);
    }

    private void addWithoutUpdate(Entry entry){
        super.add(entry);
    }

    private void updateFile(Entry entry){
        try (Writer writer = new FileWriter(collPath.toFile(), true)) {
            Gson gson = new GsonBuilder()
                    .create();
            gson.toJson(entry.getJson(), writer);
            writer.write('\n');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCollectionName() {
        return collectionName;
    }

    public Path getCollPath() {
        return collPath;
    }
}
