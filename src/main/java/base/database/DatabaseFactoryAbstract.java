package base.database;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

abstract class DatabaseFactoryAbstract implements DatabaseFactory{
    private static final Path DEFAULT_PATH = Paths.get(".").normalize().toAbsolutePath().resolve("DBs");
    protected Path pathToDatabase;

    public DatabaseFactoryAbstract(Path pathToDatabase) {
        this.pathToDatabase = pathToDatabase;
    }

    public DatabaseFactoryAbstract(String pathStringToDatabase) {
        this(Path.of(pathStringToDatabase));
    }

    public DatabaseFactoryAbstract() {
        File configYml = new File(System.getProperty("user.dir")+"\\src\\main\\resources\\application.yml");
        if(configYml.exists()){
            try (BufferedReader reader = new BufferedReader(new FileReader(configYml))) {
                String line = reader.readLine();
                if (line.matches("globeDb.dbPath = \".*\"")){
                    this.pathToDatabase = Path.of(line.substring(line.indexOf("\"") + 1, line.length() -1));
                }
                else {
                    useDefaultMethod();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            useDefaultMethod();
        }
    }

    private void useDefaultMethod(){
        try {
            Files.createDirectories(DEFAULT_PATH);
            this.pathToDatabase = DEFAULT_PATH;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public abstract Database createDatabase(String databaseName);

    protected Path createPathOfDatabase(String databaseName){
        return this.pathToDatabase.resolve(databaseName);
    }

}
