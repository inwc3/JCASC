package hiveworkshop.lang;

import com.hiveworkshop.blizzard.casc.io.WarcraftIIICASC;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;

/**
 * A simple application for extracting a single file from the Warcraft III CASC
 * archive.
 * <p>
 * First argument is a file path to the Warcraft III install folder. Second
 * argument is then a flag, either -l to list all files or -e to extract a
 * specific file. With -e the a source file to extract and a destination folder
 * must be specified.
 */
public class WC3CASCExtractor {

  public static void main(String... args) {
    if (args.length < 1) {
      System.out.println("No arguments specified. Running in interactive mode.");
      Scanner s = new Scanner(System.in);
      System.out.println("Enter Warcraft III's install path: (C:\\Program files (x86)"
        + "\\Warcraft III)");
      String installPString = s.nextLine();
      char op;
      String sourceFilePString = null, destinationFolderPString = null;
      do {
        System.out.println("What operation do you wish to run: (extract/list/quit)");
        op = s.nextLine().charAt(0);
        if (op == 'e') {
          System.out.println("Enter the name of file you wish to extract.");
          sourceFilePString = s.nextLine();
          System.out.println("Enter the name of folder to which to extract.");
          destinationFolderPString = s.nextLine();
        } else if (op == 'q') {
          System.out.println("Quitting as requested.");
          s.close();
          return;
        } else if (op == 'l') {
        } else
          System.out.println("Invalid operation");
      } while (op != 'e' && op != 'l' && op != 'q');
      args = new String[(op == 'e' ? 4 : 2)];
      args[0] = installPString;
      args[1] = "-" + op;
      if (op == 'e') {
        args[2] = sourceFilePString;
        args[3] = destinationFolderPString;
      }
      s.close();
    }

    final String installPathString = args[0];
    final Path installPath = Paths.get(installPathString);
    if (!Files.isDirectory(installPath)) {
      System.out.println("Install path is not a folder.");
      return;
    }

    System.out.println("Mounting.");
    try (final WarcraftIIICASC dataSource = new WarcraftIIICASC(installPath, true)) {
      final WarcraftIIICASC.FileSystem root = dataSource.getRootFileSystem();

      if (args.length < 2) {
        System.out.println("No operation specified.");
        return;
      }
      final String operationString = args[1];
      switch (operationString) {
        case "-l":
          System.out.println("Enumerating files.");
          final List<String> filePaths = root.enumerateFiles();
          for (final String filePath : filePaths) {
            System.out.println(filePath);
          }
          break;
        case "-e":
          if (args.length < 4) {
            System.out.println("Not enough operands.");
            return;
          }

          final String sourceFilePathString = args[2];
          final String destinationFolderPathString = args[3];

          if (!root.isFile(sourceFilePathString)) {
            System.out.println("Specified file path does not exist.");
            return;
          } else if (!root.isFileAvailable(sourceFilePathString)) {
            System.out.println("Specified file is not available.");
            return;
          }

          final Path destinationFilePath = Paths.get(destinationFolderPathString, sourceFilePathString);
          final Path destinationFolderPath = destinationFilePath.getParent();
          Files.createDirectories(destinationFolderPath);
          try (final FileChannel destinationChannel = FileChannel.open(destinationFilePath, StandardOpenOption.CREATE, StandardOpenOption.WRITE,
            StandardOpenOption.TRUNCATE_EXISTING)) {
            System.out.println("Reading file data.");
            final ByteBuffer fileBuffer = dataSource.getRootFileSystem().readFileData(sourceFilePathString);
            System.out.println("Writing file data.");
            while (fileBuffer.hasRemaining()) {
              destinationChannel.write(fileBuffer);
            }
          }
          break;
        default:
          System.out.println("Unknown operation.");
          return;
      }

      System.out.println("Done.");
    } catch (IOException e) {
      System.out.println("An exception occured.");
      e.printStackTrace(System.out);
    } finally {
      System.out.println("Unmounted.");
    }

  }
}
