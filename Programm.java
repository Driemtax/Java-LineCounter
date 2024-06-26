import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.stream.Stream;

public class Programm {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide a directory path as a command line argument.");
            return;
        }

        Path dir = Paths.get(args[0]);
        if (!Files.isDirectory(dir)) {
            System.out.println("The provided path is not a directory.");
            return;
        }

        try (Stream<Path> paths = Files.walk(dir)) {
            long totalLines = paths
                .filter(Files::isRegularFile)
                .filter(p -> p.toString().endsWith(".java"))
                .mapToLong(Programm::countLines)
                .sum();
            
            System.out.println("This project has: " + totalLines + " lines of code");
        } catch (IOException e) {
            System.err.println("An IOException was caught: " + e.getMessage());
        }
    }

    private static long countLines(Path path) {
        try {
            return Files.lines(path).count();
        } catch (IOException e) {
            System.err.println("An IOException was caught while counting lines in file: " + path + ". " + e.getMessage());
            return 0;
        }
    }
}
