package base.CLI;

import base.CLI.Subcommands.CreateDbCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
        name = "db",
        description = "command to create a document database locally ",
        mixinStandardHelpOptions = true,
        subcommands = {
                CreateDbCommand.class
        }
)
public class LocalDbCommand implements Runnable{
    public static void main(String[] args) {
        CommandLine.run(new LocalDbCommand(), args);
    }
    @Override
    public void run() {
        System.out.println("test");
    }
}
