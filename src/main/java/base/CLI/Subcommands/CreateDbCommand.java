package base.CLI.Subcommands;

import base.database.DatabaseFactory;
import base.database.AutoCommitDatabaseFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
        name = "createDb",
        description = "create database folder",
        mixinStandardHelpOptions = true
)
public class CreateDbCommand implements Runnable{

    @Option(names = {"-n", "--name"}, required = true)
    String name;

    @Override
    public void run() {
        DatabaseFactory factory = new AutoCommitDatabaseFactory();
        factory.createDatabase(name);
        System.out.println("created database");
    }
}
